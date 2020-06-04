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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface CmdPart<X extends CmdPart<?>> extends Self<X>, Named {

    @NotNull
    X description(@Nullable String description);

    @NotNull
    Optional<String> description();

    @NotNull
    X permission(@Nullable String permission);

    @NotNull
    Optional<String> permission();

    @NotNull
    X createSub(@NotNull String partcommand, @NotNull Function<SubCmd, SubCmd> subfunc);

    @NotNull
    X createSub(@NotNull SubCmd subCmd, @NotNull Function<SubCmd, SubCmd> subfunc);

    @NotNull
    X createSub(@NotNull SubCmd... subCmd);

    @NotNull
    Map<String, SubCmd> subs();

    @NotNull
    X guard(@NotNull Guard guard);

    @NotNull
    X guard(@NotNull String guardid);

    @NotNull
    Collection<Guard> guards();

    @NotNull
    X execute(@NotNull Execute execute);

    @NotNull Collection<Execute> executes();

    @NotNull
    X executePrevious(boolean executePrevious);

    @NotNull
    boolean executePrevious();

    @NotNull
    Optional<CmdRegistry> registry();

    X registry(@NotNull CmdRegistry registry);

}
