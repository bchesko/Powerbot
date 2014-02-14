package com.hunterz103.rsbot.scripts.framework;

import org.powerbot.script.methods.MethodContext;

/**
 * Created by Brian on 2/9/14.
 */
public abstract class LoopTask {
    private Thread thread = new Thread(new Runnable(){
        @Override
        public void run() {
            execute();
        }
    });

    private boolean interrupt = false;
    protected MethodContext ctx;

    public LoopTask(MethodContext ctx){
        this.ctx = ctx;
    }

    public abstract int loop();

    private void execute() {
        int delay;
        while (!interrupt && (delay = loop()) > 0) {
            try {
                thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (thread.isAlive() && !thread.isInterrupted()) thread.interrupt();
    }

    public void start(){
        thread.start();
    }

    public void stop(){
        interrupt = true;
    }

}
