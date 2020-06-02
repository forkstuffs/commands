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

package io.github.portlek.commands.part;

import io.github.portlek.commands.CmdPart;
import io.github.portlek.commands.Execute;
import io.github.portlek.commands.Guard;
import io.github.portlek.commands.SubCmd;
import io.github.portlek.commands.subcmd.BasicSubCmd;
import java.util.*;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BasicCmdPart<X extends CmdPart<?>> implements CmdPart<X> {

    private final Map<String, SubCmd> subcommands = new HashMap<>();

    private final Collection<String> aliases = new ArrayList<>();

    private final Collection<Guard> guards = new ArrayList<>();

    private final Collection<Execute> executes = new ArrayList<>();

    @NotNull
    private final String name;

    @Nullable
    private String permission;

    @Nullable
    private String description;

    private boolean executePrevious = false;

    protected BasicCmdPart(@NotNull final String name) {
        this.name = name.toLowerCase(Locale.ENGLISH);
    }

    @NotNull
    @Override
    public final String getName() {
        return this.name;
    }

    @NotNull
    @Override
    public final X description(@Nullable final String description) {
        this.description = description;
        return this.self();
    }

    @NotNull
    @Override
    public final X permission(@Nullable final String permission) {
        this.permission = permission;
        return this.self();
    }

    @NotNull
    @Override
    public final X createSub(@NotNull final String partcommand, @NotNull final Function<SubCmd, SubCmd> subfunc) {
        return this.createSub(new BasicSubCmd(partcommand), subfunc);
    }

    @NotNull
    @Override
    public final X createSub(@NotNull final SubCmd partcommand, @NotNull final Function<SubCmd, SubCmd> subfunc) {
        return this.createSub(subfunc.apply(partcommand));
    }

    @NotNull
    @Override
    public final X createSub(@NotNull final SubCmd subcommand) {
        this.subcommands.put(subcommand.getName(), subcommand);
        return this.self();
    }

    @NotNull
    @Override
    public final X aliases(@NotNull final String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this.self();
    }

    @NotNull
    @Override
    public final X guard(@NotNull final Guard guard) {
        this.guards.add(guard);
        return this.self();
    }

    @NotNull
    @Override
    public final X guard(@NotNull final String guardid) {

        return this.self();
    }

    @NotNull
    @Override
    public final X execute(final @NotNull Execute execute) {
        this.executes.add(execute);
        return this.self();
    }

    @NotNull
    @Override
    public final X executePrevious() {
        this.executePrevious = true;
        return this.self();
    }

}
