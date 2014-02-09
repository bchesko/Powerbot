package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.Menu;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.GroundItem;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class PickupScales extends Task<BlueDragonScalePicker> {

    public PickupScales(BlueDragonScalePicker arg0) {
        super(arg0);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return Place.DRAGONS.area.contains(ctx.players.local()) &&
                ctx.backpack.select().count() != 28 &&
                !ctx.players.local().isInMotion() &&
                ctx.players.local().getAnimation() == -1 &&
                !script.shouldTeleport();
    }

    @Override
    public void execute() {
        if (!ctx.groundItems.select().id(243).isEmpty()) {
            final GroundItem scale = ctx.groundItems.nearest().peek();
            script.log("Going to pick up a scale");
            if (scale.getLocation().distanceTo(ctx.players.local()) >= 5) {
                if (ctx.movement.stepTowards(scale)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return scale.getLocation().distanceTo(ctx.players.local()) < 5 || !ctx.players.local().isInMotion();
                        }
                    }, 300, 15);
                }
            }
            if (!scale.isInViewport()) {   //If we can't see the scale, turn to it
                ctx.camera.turnTo(scale);
                if (!scale.isInViewport()) { //if we STILL can't see the scale, adjust camera pitch
                    ctx.camera.setPitch(Random.nextInt(30, 60));
                }
            } else {
                if (takeScale(scale)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !scale.isValid();
                        }
                    }, 300, 10);
                }
            }
        }


    }

    private boolean takeScale(final GroundItem scale){
        if (scale.click(false)){
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.menu.isOpen();
                }
            }, Random.nextInt(20, 30), 10);
        }

        if (ctx.menu.isOpen()) {
            int takeIndex = ctx.menu.indexOf(takeFilter);
            if (takeIndex == -1) {
                ctx.menu.click(new Filter<Menu.Entry>(){
                    @Override
                    public boolean accept(Menu.Entry entry) {
                        return entry.action.equalsIgnoreCase("cancel");
                    }
                });
                //ctx.camera.setPitch(Random.nextInt(60, 80));
            } else {
                return ctx.menu.click(takeFilter);
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
