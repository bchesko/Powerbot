package com.hunterz103.rsbot.scripts.framework;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;

/**
 * Created by Brian on 2/1/14.
 */
public abstract class Task extends MethodProvider {

    private boolean inProgress = false;

    public Task(final MethodContext ctx) {
        super(ctx);
    }

    /**
     * @return importance ascending (1 is higher priority than 3)
     */
    public abstract int priority();
    public abstract boolean activate();
    public final void executeAndSet(){
        inProgress = true;
        execute();
        inProgress = false;
    }
    public abstract void execute();

    public boolean isInProgress(){
        return inProgress;
    }

}
