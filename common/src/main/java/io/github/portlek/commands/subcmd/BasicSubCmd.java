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
import io.github.portlek.commands.CmdContext;
import io.github.portlek.commands.SubCmd;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BasicSubCmd implements SubCmd {

    private final Map<String, SubCmd> subCommands = new HashMap<>();

    private final List<String> aliases = new ArrayList<>();

    private final List<Predicate<CmdContext>> guards = new ArrayList<>();

    private final List<Consumer<CmdContext>> executes = new ArrayList<>();

    @NotNull
    private final String name;

    @Nullable
    private String permission;

    @Nullable
    private String description;

    @Nullable
    private ArgType type;

    public BasicSubCmd(@NotNull final String name) {
        this.name = name.toLowerCase(Locale.ENGLISH);
    }

    @NotNull
    @Override
    public final String getName() {
        return this.name;
    }

    @NotNull
    @Override
    public final SubCmd description(@Nullable final String description) {
        this.description = description;
        return this;
    }

    @NotNull
    @Override
    public final SubCmd permission(@Nullable final String permission) {
        this.permission = permission;
        return this;
    }

    @NotNull
    @Override
    public final SubCmd createSub(@NotNull final String subcommand, @NotNull final Function<SubCmd, SubCmd> sub) {
        return this.createSub(new BasicSubCmd(subcommand), sub);
    }

    @NotNull
    @Override
    public final SubCmd createSub(@NotNull final SubCmd subcommand, @NotNull final Function<SubCmd, SubCmd> sub) {
        return this.createSub(sub.apply(subcommand));
    }

    @NotNull
    @Override
    public final SubCmd createSub(@NotNull final SubCmd subcommand) {
        this.subCommands.put(subcommand.getName(), subcommand);
        return this;
    }

    @NotNull
    @Override
    public final SubCmd aliases(@NotNull final String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }

    @NotNull
    @Override
    public final SubCmd guard(@NotNull final Predicate<CmdContext> guard) {
        this.guards.add(guard);
        return this;
    }

    @NotNull
    @Override
    public final SubCmd execute(final @NotNull Consumer<CmdContext> execute) {
        this.executes.add(execute);
        return this;
    }

    @NotNull
    @Override
    public SubCmd type(@NotNull final ArgType type) {
        this.type = type;
        return this;
    }

}
