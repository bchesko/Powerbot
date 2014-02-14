package com.hunterz103.rsbot.scripts.framework;

import org.powerbot.script.methods.MethodProvider;

/**
 * Created by Brian on 2/1/14.
 */
public abstract class Task<T extends TaskScript> extends MethodProvider implements Comparable<Task> {

    protected T script;

     public Task(T t) {
         super(t.ctx);
         this.script = t;
         this.ctx = script.ctx;
    }



    /**
     * @return importance ascending (1 is higher priority than 3)
     */
    public abstract int getPriority();

    public abstract boolean activate();

    public abstract void execute();

    @Override
    public int compareTo(Task otherTask){
        return Integer.compare(getPriority(), otherTask.getPriority());
    }
}
