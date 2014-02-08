package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.Menu;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GroundItem;
import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import org.powerbot.script.util.Random;


import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class PickupScales extends Task {

    public PickupScales(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return Place.DRAGONS.area.contains(ctx.players.local()) &&
                ctx.backpack.select().count() != 28 &&
                !ctx.players.local().isInMotion() &&
                ctx.players.local().getAnimation() == -1 &&
                !BlueDragonScalePicker.needToTeleport;
    }

    @Override
    public void execute() {
        final GroundItem scale = ctx.groundItems.select().id(243).nearest().poll();

        if (scale != null) {
            BlueDragonScalePicker.getInstance().log("Gonna pick up a scale");
            if (scale.getLocation().distanceTo(ctx.players.local()) >= 5) {
                if (ctx.movement.stepTowards(scale)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            while (ctx.players.local().isInMotion());
                            return scale.getLocation().distanceTo(ctx.players.local()) < 5;
                        }
                    }, 200, 10);
                }
            }
            if (!scale.isInViewport()) {
                ctx.camera.turnTo(scale);
                if (!scale.isInViewport()) {
                    ctx.camera.setPitch(Random.nextInt(30, 60));
                }
            } else {
                if (takeScale(scale)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            while (ctx.players.local().isInMotion());
                            return !scale.isValid() || !ctx.groundItems.select().id(243).nearest().poll().equals(scale);
                        }
                    }, Random.nextInt(200, 300), 10);
                }
            }
        }


    }

    private boolean takeScale(final GroundItem scale){
        scale.click(false);
        Condition.wait(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return ctx.menu.isOpen();
            }
        }, Random.nextInt(20, 30), 10);

        if (ctx.menu.isOpen()) {
            if (ctx.menu.indexOf(takeFilter) == -1) {
                ctx.menu.close();
                ctx.camera.setPitch(Random.nextInt(60, 80));
            } else {
                int takeIndex = ctx.menu.indexOf(takeFilter);

                if (takeIndex != -1) {
                    ctx.menu.click(takeFilter);
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.menu.isOpen();
                        }
                    }, 50, 10);
                    return true;
                }
            }
        }

        return false;

    }

    Filter takeFilter = new Filter<Menu.Entry>(){
        @Override
        public boolean accept(Menu.Entry entry) {
            return entry.action.equalsIgnoreCase("take") && entry.option.equalsIgnoreCase("blue dragon scale");
        }
    };

}
