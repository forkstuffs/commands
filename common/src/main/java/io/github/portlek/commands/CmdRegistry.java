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

import io.github.portlek.commands.context.CmdCompletions;
import io.github.portlek.commands.context.CmdOperationContext;
import io.github.portlek.commands.util.Patterns;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CmdRegistry {

    ThreadLocal<Stack<CmdOperationContext>> cmdOperationContext = ThreadLocal.withInitial(() -> new Stack<CmdOperationContext>() {
        @Nullable
        @Override
        public synchronized CmdOperationContext peek() {
            if (this.size() == 0) {
                return null;
            }
            return super.peek();
        }
    });

    default void register(@NotNull final Cmd cmd) {
        this.register(cmd, false);
    }

    @NotNull
    Map<String, RootCmd> roots();

    void register(@NotNull Cmd cmd, boolean force);

    void registerGuard(@NotNull String guardid, @NotNull Guard guard);

    @NotNull
    Optional<Guard> getGuard(@NotNull String guardid);

    @NotNull
    default RootCmd obtainRoot(@NotNull final String name) {
        return this.roots()
            .computeIfAbsent(Patterns.SPACE.split(name.toLowerCase(Locale.ENGLISH), 2)[0], this::createRoot);
    }

    @NotNull
    RootCmd createRoot(@NotNull String name);

    @NotNull
    Optional<RootCmd> root(@NotNull String name);

    CmdCompletions<?> getCommandCompletions();

}
