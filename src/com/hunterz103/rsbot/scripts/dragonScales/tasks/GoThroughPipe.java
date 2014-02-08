package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import org.powerbot.script.util.Random;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class GoThroughPipe extends Task {

    public GoThroughPipe(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int priority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return Place.INNER_DUNGEON.area.contains(ctx.players.local()) && ctx.backpack.select().count() != 28 && ctx.players.local().getAnimation() == -1;
    }

    @Override
    public void execute() {
        final GameObject pipe = ctx.objects.select().id(9293).poll();

        if (!pipe.isInViewport()) {
            ctx.camera.turnTo(pipe);
            sleep(300,500);
        } else {
            Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    pipe.interact("Squeeze-through");
                    sleep(300, 400);
                    while (ctx.players.local().getAnimation() != -1) ;
                    sleep(300, 400);
                    return ctx.players.local().getLocation().getX() <= 2935;
                }
            }, Random.nextInt(500, 700), 5);
        }
    }
}
