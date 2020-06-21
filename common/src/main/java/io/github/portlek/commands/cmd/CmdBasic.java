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

package io.github.portlek.commands.cmd;

import io.github.portlek.commands.Cmd;
import io.github.portlek.commands.part.CmdPartBasic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

public final class CmdBasic extends CmdPartBasic<CmdBasic> implements Cmd {

    private final Collection<String> aliases = new ArrayList<>();

    public CmdBasic(@NotNull final String name) {
        super(name);
    }

    public CmdBasic(@NotNull final String name, final String... aliases) {
        super(name);
        this.aliases(aliases);
    }

    @NotNull
    @Override
    public Cmd aliases(@NotNull final String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this.self();
    }

    @NotNull
    @Override
    public Collection<String> aliases() {
        return Collections.unmodifiableCollection(this.aliases);
    }

    @NotNull
    @Override
    public CmdBasic self() {
        return this;
    }

}
