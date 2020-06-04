/*
 * MIT License
 *
 * Copyright (c) 2020 Hasan Demirtaş
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.portlek.commands.cmd;

import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdRegistry;
import io.github.portlek.commands.CmdSender;
import io.github.portlek.commands.RootCmd;
import io.github.portlek.commands.context.RegisteredCmd;
import io.github.portlek.commands.part.BasicCmdPart;
import io.github.portlek.commands.util.CommonLib;
import io.github.portlek.commands.util.Patterns;
import java.util.*;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BasicCmd extends BasicCmdPart<BasicCmd> implements Cmd {

    private final Map<String, RegisteredCmd> subCommands = new HashMap<>();

    private final List<Cmd> subScopes = new ArrayList<>();

    private final Collection<String> aliases = new ArrayList<>();

    private final Map<String, RootCmd> registeredCommands = new HashMap<>();

    @Nullable
    private String execSubcommand;

    @Nullable
    private String[] origArgs;

    @Nullable
    private String execLabel;

    public BasicCmd(@NotNull final String name) {
        super(name);
    }

    private static List<String> filterTabComplete(final String arg, final List<String> cmds) {
        return cmds.stream()
            .distinct()
            .filter(cmd -> cmd != null && (arg.isEmpty() || CommonLib.startsWithIgnoreCase(cmd, arg)))
            .collect(Collectors.toList());
    }

    @NotNull
    @Override
    public Cmd aliases(@NotNull final String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this.self();
    }

    @NotNull
    @Override
    public Collection<String> aliases() {
        return Collections.unmodifiableCollection(this.aliases);
    }

    @NotNull
    @Override
    public Map<String, RegisteredCmd> registeredCommands() {
        return Collections.unmodifiableMap(this.subCommands);
    }

    @Override
    public void register(@NotNull final CmdRegistry registry) {
        this.registry(registry);
        this.register(this.getName(), this);
        this.aliases().forEach(s ->
            this.register(s, this));
    }

    @NotNull
    @Override
    public Map<String, RootCmd> roots() {
        return Collections.unmodifiableMap(this.registeredCommands);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull final CmdSender sender, @NotNull final String commandLabel,
                                    @NotNull final String[] args) {
        return this.tabComplete(sender, commandLabel, args, false);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull final CmdSender sender, @NotNull final String commandLabel,
                                    @NotNull final String[] args, final boolean isAsync)
        throws IllegalArgumentException {
        return this.registry()
            .map(registry -> registry
                .root(commandLabel.toLowerCase(Locale.ENGLISH))
                .map(rootCmd -> this.tabComplete(sender, rootCmd, args, isAsync))
                .orElse(Collections.emptyList()))
            .orElse(Collections.emptyList());
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull final CmdSender sender, @NotNull final RootCmd rootCommand,
                                    @NotNull final String[] args, final boolean isAsync)
        throws IllegalArgumentException {
        final String[] finalargs;
        if (args.length == 0) {
            finalargs = new String[]{""};
        } else {
            finalargs = args;
        }
        final String commandLabel = rootCommand.getCommandName();
        try {
            final Optional<CmdRegistry> optional = this.registry();
            if (!optional.isPresent()) {
                return Collections.emptyList();
            }
            final CommandRouter router = optional.get().getRouter();
            preCommandOperation(sender, commandLabel, finalargs, isAsync);
            final RouteSearch search = router.routeCommand(rootCommand, commandLabel, finalargs, true);
            final List<String> cmds = new ArrayList<>();
            if (search != null) {
                final CommandRouter.CommandRouteResult result = router.matchCommand(search, true);
                if (result != null) {
                    cmds.addAll(this.completeCommand(sender, result.cmd, result.args, isAsync));
                }
            }
            return BasicCmd.filterTabComplete(finalargs[finalargs.length - 1], cmds);
        } finally {
            this.postCommandOperation();
        }
    }

    @NotNull
    @Override
    public List<String> getCommandsForCompletion(@NotNull final CmdSender sender, @NotNull final String[] args) {
        final Set<String> cmds = new HashSet<>();
        final int cmdIndex = Math.max(0, args.length - 1);
        final String argString = CommonLib.join(args, " ").toLowerCase(Locale.ENGLISH);
        for (final Map.Entry<String, RegisteredCmd> entry : this.roots.entrySet()) {
            final String key = entry.getKey();
            if (key.startsWith(argString) && !BasicCmd.CATCHUNKNOWN.equals(key) && !BasicCmd.DEFAULT.equals(key)) {
                final RegisteredCmd value = entry.getValue();
                if (!value.hasPermission(sender) || value.isPrivate) {
                    continue;
                }
                final String[] split = Patterns.SPACE.split(value.prefSubCommand);
                cmds.add(split[cmdIndex]);
            }
        }
        return new ArrayList<>(cmds);
    }

    @NotNull
    @Override
    public BasicCmd self() {
        return this;
    }

    private void register(@NotNull final String name, @NotNull final Cmd cmd) {
        final String nameLower = name.toLowerCase(Locale.ENGLISH);
        this.registry().ifPresent(registry -> {
            final RootCmd rootCommand = registry.obtainRoot(nameLower);
            rootCommand.child(cmd);
            this.registeredCommands.put(nameLower, rootCommand);
        });
    }

    private void postCommandOperation() {
        CmdRegistry.cmdOperationContext.get().pop();
        this.execSubcommand = null;
        this.execLabel = null;
        this.origArgs = new String[]{};
    }

    @NotNull
    private List<String> completeCommand(@NotNull final CmdSender sender, @NotNull final RegisteredCmd cmd,
                                         @NotNull final String[] args, final boolean isAsync) {
        if (!cmd.hasPermission(sender) || args.length == 0 || cmd.parameters.length == 0) {
            return Collections.emptyList();
        }
        if (!cmd.parameters[cmd.parameters.length - 1].consumesRest && args.length > cmd.consumeInputResolvers) {
            return Collections.emptyList();
        }
        final Optional<CmdRegistry> optional = this.registry();
        if (!optional.isPresent()) {
            return Collections.emptyList();
        }
        return BasicCmd.filterTabComplete(
            args[args.length - 1],
            optional.get().getCommandCompletions().of(cmd, sender, args, isAsync));
    }

}
