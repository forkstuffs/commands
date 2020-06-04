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

package io.github.portlek.commands.registry;

import co.aikar.timings.lib.MCTiming;
import co.aikar.timings.lib.TimingManager;
import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdRegistry;
import io.github.portlek.commands.Guard;
import io.github.portlek.commands.RootCmd;
import io.github.portlek.commands.context.CmdCompletions;
import io.github.portlek.commands.root.BukkitRootCmd;
import io.github.portlek.commands.util.Patterns;
import io.github.portlek.reflection.clazz.ClassOf;
import java.util.*;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BukkitCommandRegistry implements CmdRegistry {

    private final Map<String, Guard> guards = new HashMap<>();

    private final Map<String, BukkitRootCmd> roots = new HashMap<>();

    @NotNull
    private final Plugin plugin;

    @NotNull
    private final Logger logger;

    @NotNull
    private final TimingManager timingManager;

    @NotNull
    private final MCTiming commandTiming;

    @NotNull
    private final CommandMap commandMap;

    @NotNull
    private final Map<String, Command> knownCommands;

    @Nullable
    private BukkitCommandCompletions completions;

    public BukkitCommandRegistry(@NotNull final Plugin plugin) {
        this.plugin = plugin;
        this.logger = Logger.getLogger(this.plugin.getName());
        this.timingManager = TimingManager.of(plugin);
        this.commandTiming = this.timingManager.of("Commands");
        this.commandMap = BukkitCommandRegistry.initializeCommandMap();
        this.knownCommands = this.initializeKnowCommand();
    }

    /**
     * Initializes the command map.
     *
     * @return {@link CommandMap} from the craft server.
     */
    @NotNull
    private static CommandMap initializeCommandMap() {
        return (CommandMap) new ClassOf<>(Server.class)
            .findMethodByName("getCommandMap")
            .flatMap(refMethod -> refMethod.of(Bukkit.getServer()).call())
            .orElseThrow(() -> new RuntimeException("Failed to get Command Map. Commands will not function."));
    }

    @NotNull
    @Override
    public Map<String, RootCmd> roots() {
        return Collections.unmodifiableMap(this.roots);
    }

    @Override
    public void register(@NotNull final Cmd cmd, final boolean force) {
        cmd.register(this);
        cmd.roots().forEach((s, rootCmd) -> {
            final BukkitRootCmd bukkitRootCmd = (BukkitRootCmd) rootCmd;
            if (!bukkitRootCmd.isCommandRegistered()) {
                final String commandname = s.toLowerCase(Locale.ENGLISH);
                final Command oldcommand = this.commandMap.getCommand(commandname);
                if (oldcommand instanceof PluginIdentifiableCommand &&
                    ((PluginIdentifiableCommand) oldcommand).getPlugin().equals(this.plugin)) {
                    this.knownCommands.remove(commandname);
                    oldcommand.unregister(this.commandMap);
                } else if (oldcommand != null && force) {
                    this.knownCommands.remove(commandname);
                    for (final Map.Entry<String, Command> entry : this.knownCommands.entrySet()) {
                        final String key = entry.getKey();
                        final Command value = entry.getValue();
                        if (key.contains(":") && oldcommand.equals(value)) {
                            final String[] split = Patterns.COLON.split(key, 2);
                            if (split.length > 1) {
                                oldcommand.unregister(this.commandMap);
                                oldcommand.setLabel(split[0] + ':' + cmd.getName());
                                oldcommand.register(this.commandMap);
                            }
                        }
                    }
                }
                this.commandMap.register(commandname, this.plugin.getName().toLowerCase(Locale.ENGLISH), bukkitRootCmd);
            }
            bukkitRootCmd.setRegistered(true);
            this.roots.put(s, bukkitRootCmd);
        });
    }

    @Override
    public void registerGuard(@NotNull final String guardid, @NotNull final Guard guard) {
        this.guards.put(guardid, guard);
    }

    @NotNull
    @Override
    public Optional<Guard> getGuard(@NotNull final String guardid) {
        return Optional.ofNullable(this.guards.get(guardid));
    }

    @NotNull
    @Override
    public RootCmd createRoot(@NotNull final String name) {
        return new BukkitRootCmd(name, this);
    }

    @NotNull
    @Override
    public Optional<RootCmd> root(@NotNull final String name) {
        return Optional.ofNullable(this.roots.get(name));
    }

    @Override
    public CmdCompletions<?> getCommandCompletions() {
        if (this.completions == null) {
            this.completions = new BukkitCommandCompletions(this);
        }
        return this.completions;
    }

    @NotNull
    private Map<String, Command> initializeKnowCommand() {
        // noinspection unchecked
        return (Map<String, Command>) new ClassOf<>(SimpleCommandMap.class)
            .getField("knownCommands")
            .flatMap(refField -> refField.of(this.commandMap).get())
            .orElseThrow(() -> new RuntimeException("Failed to get Known Commands. Commands will not function."));
    }

}
