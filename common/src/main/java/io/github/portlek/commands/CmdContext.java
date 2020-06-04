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

import java.util.LinkedList;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public interface CmdContext {

    @NotNull
    CmdSender sender();

    @NotNull
    Cmd cmd();

    @NotNull
    default Arg arg() {
        return this.args().getLast();
    }

    @NotNull
    LinkedList<Arg> args();

    @NotNull
    default Arg previous(final int index) {
        if (index < 1) {
            throw new UnsupportedOperationException("The index must bigger than 0!");
        }
        return this.args().get(this.args().size() - index);
    }

    @NotNull
    default Arg previous() {
        return this.previous(1);
    }

    @NotNull
    default Optional<SubCmd> current() {
        final LinkedList<Arg> args = this.args();
        if (args.isEmpty()) {
            return Optional.empty();
        }
        for (int index = 0; index < args.size(); index++) {
            final Arg arg = args.get(index);
            final boolean islast = index == args.size() - 1;

        }
        return Optional.empty();
    }

}
