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

package io.github.portlek.commands.executor;

import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdContext;
import io.github.portlek.commands.SubCmd;
import io.github.portlek.commands.arg.ArgBasic;
import io.github.portlek.commands.context.BukkitCmdContextBasic;
import io.github.portlek.commands.context.BukkitCmdSender;
import io.github.portlek.commands.context.CmdContextBasic;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class BukkitExecutor implements TabExecutor {

    @NotNull
    private final Cmd cmd;

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
                             @NotNull final String label, @NotNull final String[] args) {
        final CmdContext context = new BukkitCmdContextBasic(
            new CmdContextBasic(
                this.cmd,
                new BukkitCmdSender(sender),
                new LinkedList<>(
                    Arrays.stream(args)
                        .map(ArgBasic::new)
                        .collect(Collectors.toList()))));
        if (this.cmd.guards().stream().anyMatch(guard ->
            guard.negate().test(context))) {
            return true;
        }
        if (args.length == 0) {
            this.cmd.executes().forEach(execute -> execute.accept(context));
            return true;
        }
        final Optional<SubCmd> optional = context.current();
        if (!optional.isPresent()) {
            return true;
        }
        final SubCmd current = optional.get();
        if (current.guards().stream().anyMatch(guard ->
            guard.negate().test(context))) {
            return true;
        }
        current.executes().forEach(execute ->
            execute.accept(context));
        return true;
    }

    @NotNull
    @Override
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command,
                                      @NotNull final String label, @NotNull final String[] args) {
        return Collections.emptyList();
    }

}
