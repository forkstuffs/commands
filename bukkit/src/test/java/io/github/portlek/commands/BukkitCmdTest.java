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
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

final class BukkitCmdTest {

    private final JavaPlugin plugin;

    BukkitCmdTest(@NotNull final JavaPlugin plugin) {
        this.plugin = plugin;
    }

    void creation() {
        final BukkitCmdRegistry registry = new BukkitCmdRegistry(this.plugin);
        final BasicCmd command = new BasicCmd("test-command", "test-aliases")
            .permission("plugin.test-command.main")
            .guard(context ->
                true)
            .execute(context -> {
                // executes /test-command
            })
            .createSub("help", subCmd -> subCmd
                .permission("plugin.test-command.help")
                .executePrevious(true))
            .createSub("giveseed", seedsub -> seedsub
                .permission("plugin.test-command.giveseed")
                .executePrevious(true)
                .createSub("seeds", seedssub -> seedssub
                    .type(ArgType.text())
                    .executePrevious(true)
                    .createSub("player-argument", playersub -> playersub
                        .type(BukkitArgType.players())
                        .execute(context -> {
                            
                        })
                        .createSub("amount-argument", amountsub -> amountsub
                            .type(ArgType.integer("[amount]"))
                            .executePrevious(true)))))
            .createSub("reload", reloadsub -> reloadsub
                .permission("plugin.test-command.reload")
                .execute(context -> {
                    // executes /test-command reload
                }))
            .createSub("list", subCmd -> subCmd
                .executePrevious(true)
                .createSub("page-argument", pagesub -> pagesub
                    .type(ArgType.integer("[page]"))

                    .execute(context -> {
                        // executes /test-command list [page]
                    })))
            .createSub("perm", subCmd -> subCmd
                .permission("plugin.test-command.perm")
                .executePrevious(true)
                .createSub("list", listsub -> listsub
                    .permission("plugin.test-command.perm.list")
                    .executePrevious(true)
                    .createSub("page-argument", pagesub -> pagesub
                        .type(ArgType.integer("[page]"))
                        .execute(context -> {
                            // executes test-command perm list [page]
                        })))
                .createSub("add", addsub -> addsub
                    .permission("plugin.test-command.perm.add")
                    .executePrevious(true)
                    .createSub("player-argument", playersub -> playersub
                        .type(BukkitArgType.players())
                        .execute(context -> {
                            // executes test-command perm add <player>
                        })))
                .createSub("remove", removesub -> removesub
                    .permission("plugin.test-command.perm.remove")
                    .executePrevious(true)
                    .createSub("player-argument", playersub -> playersub
                        .type(BukkitArgType.players())
                        .execute(context -> {
                            // executes test-command perm remove <player>
                        }))));
        registry.register(command);
    }

}
