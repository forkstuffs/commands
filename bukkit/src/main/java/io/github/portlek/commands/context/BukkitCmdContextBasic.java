package io.github.portlek.commands.context;

import io.github.portlek.commands.*;
import java.util.LinkedList;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class BukkitCmdContextBasic implements BukkitCmdContext {

    @NotNull
    private final CmdContext parent;

    @NotNull
    @Override
    public CmdSender sender() {
        return this.parent.sender();
    }

    @NotNull
    @Override
    public Cmd cmd() {
        return this.parent.cmd();
    }

    @NotNull
    @Override
    public LinkedList<Arg> args() {
        return this.parent.args();
    }

}
