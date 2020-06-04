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

import io.github.portlek.commands.CmdRegistry;
import io.github.portlek.commands.CmdSender;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class CmdExecutionContext<C extends CmdExecutionContext<?, ?>, S extends CmdSender> {

    private final S sender;

    private final RegisteredCmd cmd;

    private final CmdParameter param;

    private final List<String> args;

    private final int index;

    private final Map<String, Object> passedArgs;

    private final Map<String, String> flags;

    private final CmdRegistry manager;

    CmdExecutionContext(@NotNull final RegisteredCmd cmd, @NotNull final CmdParameter param,
                        @NotNull final S sender, @NotNull final List<String> args, final int index,
                        @NotNull final Map<String, Object> passedArgs) {
        this.cmd = cmd;
        this.manager = cmd.getScope().registry().orElseThrow(() ->
            new IllegalArgumentException("Couldn't get registry!"));
        this.param = param;
        this.sender = sender;
        this.args = args;
        this.index = index;
        this.passedArgs = passedArgs;
        this.flags = param.getFlags();
    }

}
