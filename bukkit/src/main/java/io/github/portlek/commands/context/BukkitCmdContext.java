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

package io.github.portlek.commands.context;

import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdContext;
import io.github.portlek.commands.CmdSender;
import io.github.portlek.commands.SubCmd;
import java.util.Optional;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public final class BukkitCmdContext implements CmdContext {

    @NotNull
    private final Cmd cmd;

    @NotNull
    private final CmdSender sender;

    @NotNull
    private final String[] args;

    public BukkitCmdContext(@NotNull final Cmd cmd, @NotNull final CommandSender sender, final @NotNull String[] args) {
        this.cmd = cmd;
        this.sender = new BukkitCmdSender(sender);
        this.args = args;
    }

    @NotNull
    @Override
    public CmdSender getSender() {
        return this.sender;
    }

    @NotNull
    @Override
    public String getArg() {
        return this.args[this.args.length - 1];
    }

    @NotNull
    @Override
    public String[] getAllArgs() {
        return this.args.clone();
    }

    @NotNull
    @Override
    public Optional<SubCmd> getCurrentSub(final boolean silent) {
        if (this.args.length == 0) {
            return Optional.empty();
        }
        for (final String arg : this.getAllArgs()) {
            for (final SubCmd sub : this.cmd.subs()) {

            }
        }
        return Optional.empty();
    }

}
