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

import io.github.portlek.commands.argtype.LiteralType;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SubCmd {

    @NotNull
    String getName();

    @NotNull
    SubCmd description(@Nullable String description);

    @NotNull
    SubCmd permission(@Nullable String permission);

    @NotNull
    SubCmd createSub(@NotNull String subcommand, @NotNull Function<SubCmd, SubCmd> sub);

    @NotNull
    SubCmd createSub(@NotNull SubCmd subcommand, @NotNull Function<SubCmd, SubCmd> sub);

    @NotNull
    SubCmd createSub(@NotNull SubCmd subcommand);

    @NotNull
    SubCmd aliases(@NotNull String... aliases);

    @NotNull
    SubCmd guard(@NotNull Predicate<CmdContext> guard);

    @NotNull
    SubCmd execute(@NotNull Consumer<CmdContext> execute);

    @NotNull
    SubCmd type(@NotNull ArgType type);

    @NotNull
    default SubCmd typeLiteral(@NotNull final String... literals) {
        return this.type(new LiteralType(literals));
    }

}
