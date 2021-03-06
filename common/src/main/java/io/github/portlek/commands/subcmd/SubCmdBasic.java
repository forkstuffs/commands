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

package io.github.portlek.commands.subcmd;

import io.github.portlek.commands.ArgType;
import io.github.portlek.commands.CmdPart;
import io.github.portlek.commands.SubCmd;
import io.github.portlek.commands.argtype.LiteralType;
import io.github.portlek.commands.part.CmdPartBasic;
import org.jetbrains.annotations.NotNull;

public final class SubCmdBasic extends CmdPartBasic<SubCmdBasic> implements SubCmd {

    @NotNull
    private final CmdPart<?> previous;

    @NotNull
    private ArgType type;

    public SubCmdBasic(@NotNull final String name, final CmdPart<?> previous) {
        super(name);
        this.type = new LiteralType(name);
        this.previous = previous;
    }

    @NotNull
    public CmdPart<?> previous() {
        return this.previous;
    }

    @NotNull
    @Override
    public SubCmdBasic type(final @NotNull ArgType type) {
        this.type = type;
        return this.self();
    }

    @NotNull
    @Override
    public ArgType type() {
        return this.type;
    }

    @NotNull
    @Override
    public SubCmdBasic self() {
        return this;
    }

}
