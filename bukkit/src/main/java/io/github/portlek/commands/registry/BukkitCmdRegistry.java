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

import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdRegistry;
import io.github.portlek.commands.Guard;
import io.github.portlek.commands.executor.BukkitExecutor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class BukkitCmdRegistry implements CmdRegistry {

    private final Map<String, Guard> guards = new HashMap<>();

    @NotNull
    private final JavaPlugin plugin;

    @Override
    public void register(@NotNull final Cmd cmd) {
        cmd.registry(this);
        final PluginCommand command = this.plugin.getCommand(cmd.getName());
        if (command == null) {
            throw new IllegalStateException("Couldn't found a command named " + cmd.getName() + " in plugin.yml!");
        }
        final BukkitExecutor executor = new BukkitExecutor(cmd);
        command.setExecutor(executor);
        command.setTabCompleter(executor);
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

}
