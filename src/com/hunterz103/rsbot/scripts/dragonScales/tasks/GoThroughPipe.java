package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class GoThroughPipe extends Task<BlueDragonScalePicker> {

    public GoThroughPipe(BlueDragonScalePicker ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return Place.INNER_DUNGEON.area.containsPlayer(ctx) && !Place.DRAGONS.area.containsPlayer(ctx) && ctx.backpack.select().count() != 28 && !ctx.players.local().isInMotion();
    }

    @Override
    public void execute() {
        final GameObject pipe = ctx.objects.select().id(9293).poll();
        ctx.camera.turnTo(pipe);
        sleep(200, 400);

        if (pipe.interact("Squeeze-through")) {
            script.log("Squeezing through pipe to dragons");
            if (Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.players.local().getAnimation() == 10580;
                    }
                }, 500, 10)) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return Place.DRAGONS.area.containsPlayer(ctx);
                    }
                }, 500, 10);
            }

        }
    }
}
