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

package io.github.portlek.commands.context;

import io.github.portlek.commands.Arg;
import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.CmdContext;
import io.github.portlek.commands.CmdSender;
import io.github.portlek.commands.util.UnmodifiableLinkedList;
import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class CmdContextBasic implements CmdContext {

    @NotNull
    private final Cmd cmd;

    @NotNull
    private final CmdSender sender;

    @NotNull
    private final LinkedList<Arg> args;

    @NotNull
    @Override
    public CmdSender sender() {
        return this.sender;
    }

    @NotNull
    @Override
    public Cmd cmd() {
        return this.cmd;
    }

    @Override
    public LinkedList<Arg> args() {
        return new UnmodifiableLinkedList<>(this.args);
    }

}
