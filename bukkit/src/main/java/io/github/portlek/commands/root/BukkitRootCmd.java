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

package io.github.portlek.commands.root;

import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdRegistry;
import io.github.portlek.commands.RootCmd;
import io.github.portlek.commands.context.RegisteredCmd;
import io.github.portlek.commands.util.Patterns;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BukkitRootCmd extends Command implements RootCmd {

    private final Map<String, RegisteredCmd> subCommands = new HashMap<>();

    private final List<Cmd> children = new ArrayList<>();

    @NotNull
    private final CmdRegistry registry;

    @Nullable
    private Cmd defcmd;

    private boolean registered = false;

    public BukkitRootCmd(@NotNull final String name, @NotNull final CmdRegistry registry) {
        super(name);
        this.registry = registry;
    }

    public boolean isCommandRegistered() {
        return this.registered;
    }

    public void setRegistered(final boolean registered) {
        this.registered = registered;
    }

    @Override
    public boolean execute(final CommandSender sender, String label, final String[] args) {
        if (label.contains(":")) {
            label = Patterns.COLON.split(label, 2)[1];
        }
        this.execute(this.registry.getCommandSender(sender), label, args);
        return true;
    }

    @Override
    public List<String> tabComplete(final CommandSender sender, String commandLabel, final String[] args)
        throws IllegalArgumentException {
        if (commandLabel.contains(":")) {
            commandLabel = Patterns.COLON.split(commandLabel, 2)[1];
        }
        return this.getTabCompletions(this.registry.getCommandSender(sender), commandLabel, args);
    }

    @Override
    public String getDescription() {
        final RegisteredCmd command = getDefaultRegisteredCommand();
        if (command != null && !command.getHelpText().isEmpty()) {
            return command.getHelpText();
        }
        if (command != null && command.scope.description != null) {
            return command.scope.description;
        }
        if (this.defcmd != null && this.defcmd.description().isPresent()) {
            return this.defcmd.description().get();
        }
        return super.getDescription();
    }

    @NotNull
    @Override
    public List<Cmd> children() {
        return null;
    }

    @Override
    public void child(@NotNull final Cmd cmd) {
        if (this.defcmd == null || !cmd.registeredCommands().get(Cmd.DEFAULT).isEmpty()) {
            this.defcmd = cmd;
        }
        this.addChildShared(this.children, this.subCommands, cmd);
        this.setPermission(this.getUniquePermission());
    }

}
