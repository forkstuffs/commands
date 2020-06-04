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

package io.github.portlek.commands;

import io.github.portlek.commands.context.RegisteredCmd;
import java.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RootCmd {

    @NotNull
    List<Cmd> children();

    void child(@NotNull Cmd cmd);

    default void addChildShared(@NotNull final List<Cmd> children,
                                @NotNull final Map<String, RegisteredCmd> subCommands, @NotNull final Cmd command) {
        command.registeredCommands().forEach(subCommands::put);
        children.add(command);
    }

    @Nullable
    default String getUniquePermission() {
        final Set<String> permissions = new HashSet<>();
        for (final Cmd child : children()) {
            for (final RegisteredCmd<?> value : child.registeredCommands().values()) {
                final Set<String> requiredPermissions = value.getRequiredPermissions();
                if (requiredPermissions.isEmpty()) {
                    return null;
                } else {
                    permissions.addAll(requiredPermissions);
                }
            }
        }
        if (permissions.size() == 1) {
            return permissions.iterator().next();
        }
        return null;
    }

    default List<String> getTabCompletions(@NotNull final CmdSender sender, @NotNull final String alias,
                                           @NotNull final String[] args) {
        return this.getTabCompletions(sender, alias, args, false);
    }

    default List<String> getTabCompletions(@NotNull final CmdSender sender, @NotNull final String alias,
                                           @NotNull final String[] args, final boolean commandsOnly) {
        return this.getTabCompletions(sender, alias, args, commandsOnly, false);
    }

    default List<String> getTabCompletions(@NotNull final CmdSender sender, @NotNull final String alias,
                                           @NotNull final String[] args, final boolean commandsOnly,
                                           final boolean isAsync) {
        final Set<String> completions = new HashSet<>();
        this.children().forEach(child -> {
            if (!commandsOnly) {
                completions.addAll(child.tabComplete(sender, this, args, isAsync));
            }
            completions.addAll(child.getCommandsForCompletion(sender, args));
        });
        return new ArrayList<>(completions);
    }

}
