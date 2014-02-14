package com.hunterz103.rsbot.scripts.limestoneBuyer.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.limestoneBuyer.LimestoneBuyer;
import org.powerbot.script.methods.MethodContext;

/**
 * Created by Brian on 2/2/14.
 */
public class SwapWorlds extends Task {

    public SwapWorlds(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return LimestoneBuyer.getInstance().needToSwap();
    }

    @Override
    public void execute() {
        //TODO: Ya know, all of it
    }
}
