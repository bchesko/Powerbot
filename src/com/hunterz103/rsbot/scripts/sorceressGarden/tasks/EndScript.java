package com.hunterz103.rsbot.scripts.sorceressGarden.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.sorceressGarden.SorceressGarden;
import org.powerbot.script.methods.MethodContext;

/**
 * Created by Brian on 2/4/14.
 */
public class EndScript extends Task {

    public EndScript(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean activate() {
        return SorceressGarden.failed >= 5&& (SorceressGarden.failed / 2 >= SorceressGarden.succeeded) ;
    }

    @Override
    public void execute() {
        ctx.getBot().stop();
    }
}
