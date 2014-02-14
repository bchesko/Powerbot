package com.hunterz103.rsbot.scripts.sorceressGarden.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.sorceressGarden.Garden;
import com.hunterz103.rsbot.scripts.sorceressGarden.SorceressGarden;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.GameObject;

/**
 * Created by Brian on 2/3/14.
 */
public class WalkInGarden extends Task {

    public WalkInGarden(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() != 28 && Garden.HUB.area.contains(ctx.players.local()) && !ctx.players.local().isInMotion();
    }

    @Override
    public void execute() {
        if (!SorceressGarden.chosenGarden.area.contains(ctx.players.local())) {
            final GameObject gate = ctx.objects.select().at(SorceressGarden.chosenGarden.gateTile).poll();

            if (!gate.isOnScreen()) {
                ctx.camera.turnTo(gate, 5);
                ctx.camera.setPitch(Random.nextInt(30, 60));
            }

            gate.interact("Open");
            sleep(Random.nextInt(600,800));
            while (ctx.players.local().isInMotion()) sleep(250);
            sleep(Random.nextInt(1700, 2200));
        }
    }
}
