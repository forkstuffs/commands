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
import io.github.portlek.commands.argtype.NonLiteralType;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public interface ArgType extends Function<CmdContext, Collection<String>> {

    @NotNull
    static ArgType literal(@NotNull final String... literals) {
        return new LiteralType(literals);
    }

    @NotNull
    static ArgType integer(@NotNull final String... examples) {
        return new NonLiteralType<>(Integer.class, examples);
    }

    @NotNull
    static ArgType integer(@NotNull final Integer... examples) {
        return new NonLiteralType<>(Integer.class, examples);
    }

    @NotNull
    static ArgType integer() {
        return ArgType.integer(1, 10, 100);
    }

    @NotNull
    static ArgType text(@NotNull final List<String> examples) {
        return new NonLiteralType<>(String.class, examples);
    }

    @NotNull
    static ArgType text(@NotNull final String... examples) {
        return ArgType.text(Arrays.asList(examples));
    }

    default boolean isLiteral() {
        return false;
    }

}
