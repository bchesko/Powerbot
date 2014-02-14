package com.hunterz103.rsbot.scripts.unicows.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.unicows.UnicowKiller;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GroundItem;
import org.powerbot.script.wrappers.Item;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/13/14.
 */
public class PickupLoot extends Task<UnicowKiller> {

    public PickupLoot(UnicowKiller unicowKiller) {
        super(unicowKiller);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return !ctx.groundItems.select().id(script.ID_HORN).isEmpty();
    }

    @Override
    public void execute() {
        for (int i = 0; i < 3; i++)
            for (GroundItem item : ctx.groundItems.select().id(script.ID_HORN)) {
                if (item.isValid() && !item.isInViewport()) {
                    ctx.camera.turnTo(item);
                }

                if (item.isValid() && item.isInViewport()) {
                    final int temp = getCount(script.ID_HORN);
                    if (item.interact("Take")) {
                        Condition.wait(new Callable<Boolean>(){
                            @Override
                            public Boolean call() throws Exception {
                                return ctx.players.local().isInMotion();
                            }
                        }, 500, 10);
                        Condition.wait(new Callable<Boolean>(){
                            @Override
                            public Boolean call() throws Exception {
                                return getCount(script.ID_HORN) > temp;
                            }
                        }, 50, 10);
                    }
                }
            }
    }

    private int getCount(int id) {
        int count = 0;

        for (Item item : ctx.backpack.select().id(id)) {
            count += item.getStackSize();
        }

        return count;
    }
}