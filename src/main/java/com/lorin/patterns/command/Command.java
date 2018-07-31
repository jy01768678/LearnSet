package com.lorin.patterns.command;

/**
 * Created by lorin on 2018/4/27.
 */
public abstract class Command {

    public abstract void execute(Target target);

    public abstract void undo();

    public abstract void redo();

    @Override
    public abstract String toString();
}
