package com.hunterz103.rsbot.scripts.sorceressGarden.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.sorceressGarden.Garden;
import com.hunterz103.rsbot.scripts.sorceressGarden.SorceressGarden;
import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Item;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/3/14.
 */
public class TeleportToGarden extends Task {

    public TeleportToGarden(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return !SorceressGarden.chosenGarden.area.contains(ctx.players.local()) &&
                !Garden.HUB.area.contains(ctx.players.local()) &&
                ctx.backpack.select().id(23643).count() == 1 &&
                ctx.backpack.select().id(14057).count() == 1 &&
                ctx.players.local().getAnimation() == -1;
    }

    @Override
    public void execute() {
        final Item broom = ctx.backpack.select().id(14057).poll();

        ctx.hud.view(Hud.Window.BACKPACK);
        sleep(200);

        if (broom.isValid()) {
            Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    if (broom.interact("Teleport")) sleep(2500, 3000);
                    sleep(400, 600);
                    return Garden.HUB.area.contains(ctx.players.local());
                }
            }, Random.nextInt(2000, 3000), 5);
        }
    }
}
