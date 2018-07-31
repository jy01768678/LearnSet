package com.lorin.patterns.command;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by lorin on 2018/4/27.
 */
public class Wizard {

    private Deque<Command> undoStack = new LinkedList<>();

    private Deque<Command> redoStack = new LinkedList<>();

    public Wizard(){

    }

    public void castSpell(Command command,Target target) {
        command.execute(target);
        undoStack.offerLast(command);
    }

    /**
     * Undo last spell
     */
    public void undoLastSpell() {
        if (!undoStack.isEmpty()) {
            Command previousSpell = undoStack.pollLast();
            redoStack.offerLast(previousSpell);
            previousSpell.undo();
        }
    }

    /**
     * Redo last spell
     */
    public void redoLastSpell() {
        if (!redoStack.isEmpty()) {
            Command previousSpell = redoStack.pollLast();
            undoStack.offerLast(previousSpell);
            previousSpell.redo();
        }
    }

    @Override
    public String toString() {
        return "Wizard";
    }

}
