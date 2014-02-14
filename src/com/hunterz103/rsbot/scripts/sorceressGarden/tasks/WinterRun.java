package com.hunterz103.rsbot.scripts.sorceressGarden.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.sorceressGarden.Garden;
import com.hunterz103.rsbot.scripts.sorceressGarden.SorceressGarden;
import org.powerbot.script.methods.MethodContext;

/**
 * Created by Brian on 2/3/14.
 */
public class WinterRun extends Task {

    public WinterRun(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean activate() {
        return SorceressGarden.chosenGarden == Garden.WINTER && SorceressGarden.chosenGarden.area.contains(ctx.players.local());
    }

    @Override
    public void execute() {

    }
}
