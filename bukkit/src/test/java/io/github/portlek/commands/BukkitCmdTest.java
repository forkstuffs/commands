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

import io.github.portlek.commands.cmd.BasicCmd;
import io.github.portlek.commands.registry.BukkitCmdRegistry;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

final class BukkitCmdTest {

    private final Plugin plugin;

    BukkitCmdTest(@NotNull final Plugin plugin) {
        this.plugin = plugin;
    }

    void creation() {
        final BukkitCmdRegistry registry = new BukkitCmdRegistry(this.plugin);
        final BasicCmd command = new BasicCmd("test-command")
            .aliases("test-aliases")
            .permission("plugin.test-command.main")
            .guard(context ->
                true)
            .execute(context -> {
                // executes /test-command
            })
            .createSub("message", subCmd -> subCmd
                .permission("plugin.test-command.message")
                .executePrevious(true)
                .createSub("player-argument", playerSub -> playerSub
                    .type(BukkitArgType.players())
                    .execute(context -> {
                        // executes /test-command message <online-players>
                    })))
            .createSub("test-sub", sub -> sub
                .permission("plugin.test-command.test-sub")
                .execute(context -> {
                    // executes /test-command test-sub
                })
                .createSub("test-sub-sub", subsub -> subsub
                    .permission("plugin.test-command.test-sub.sub")
                    .execute(context -> {
                        // executes /test-command test-sub test-subsub
                    })))
            .createSub("test-sub-2", subCmd -> subCmd
                .permission("plugin.test-command.test-sub-2")
                .type(ArgType.literal("asd", "dsa", "sdda"))
                .execute(context -> {
                    // executes /test-command [asd|dsa|sdda]
                }));
        registry.register(command);
    }

}
