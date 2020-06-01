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
import io.github.portlek.commands.Command;
import io.github.portlek.commands.CommandRegistry;
import java.util.logging.Logger;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class BukkitCommandRegistry implements CommandRegistry {

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

    public BukkitCommandRegistry(@NotNull final Plugin plugin) {
        this.plugin = plugin;
        this.logger = Logger.getLogger(this.plugin.getName());
        this.timingManager = TimingManager.of(plugin);
        this.commandTiming = this.timingManager.of("Commands");
        this.commandMap = initializeCommandMap();
    }

    @Override
    public void register(@NotNull final Command command) {

    }

}
