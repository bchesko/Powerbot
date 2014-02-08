package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Item;
import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class TeleportToBank extends Task {

    public TeleportToBank(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return (!Place.FALADOR.area.contains(ctx.players.local()) && ctx.backpack.select().count() == 28) || BlueDragonScalePicker.needToTeleport;
    }

    @Override
    public void execute() {
        final Item tab = ctx.backpack.select().id(8009).poll();

        if (tab != null) {
            Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    if (tab.interact("Break")) sleep(1000, 1200);
                    while (ctx.players.local().getAnimation() != -1);
                    sleep(300, 400);
                    return Place.FALADOR.area.contains(ctx.players.local());
                }
            }, Random.nextInt(500, 700), 10);
        }
    }
}
