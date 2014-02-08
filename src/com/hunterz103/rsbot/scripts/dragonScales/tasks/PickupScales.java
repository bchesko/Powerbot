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
            if (scale.getLocation().distanceTo(ctx.players.local()) >= 5) {
                Condition.wait(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        if (ctx.movement.stepTowards(scale) && !ctx.players.local().isInMotion()) sleep(200, 300);
                        return scale.getLocation().distanceTo(ctx.players.local()) < 5;
                    }
                });
            }
            if (!scale.isInViewport()) {
                ctx.camera.turnTo(scale);
                if (!scale.isInViewport()) {
                    ctx.camera.setPitch(Random.nextInt(30, 60));
                }
            } else {
                Condition.wait(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        if (takeScale(scale)) {
                            sleep(600, 700);
                            while (ctx.players.local().isInMotion());
                        }
                        return !scale.isValid() || !ctx.groundItems.select().id(243).nearest().poll().equals(scale);
                    }
                });
            }
        }


    }

    private boolean takeScale(final GroundItem scale){
        Condition.wait(new Callable() {
            @Override
            public Object call() throws Exception {
                scale.click(false);
                sleep(200);
                return ctx.menu.isOpen();
            }
        }, Random.nextInt(200, 300), 5);

        if (ctx.menu.isOpen()) {
            if (ctx.menu.indexOf(takeFilter) == -1) {
                ctx.menu.close();
                ctx.camera.setPitch(Random.nextInt(60, 80));
            } else {
                int takeIndex = ctx.menu.indexOf(takeFilter);

                if (takeIndex != -1) {
                    ctx.menu.click(takeFilter);
                    sleep(300, 500);
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
