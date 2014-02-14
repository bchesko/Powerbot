package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Item;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class TeleportToBank extends Task<BlueDragonScalePicker> {

    public TeleportToBank(BlueDragonScalePicker arg0) {
        super(arg0);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean activate() {
        return script.shouldTeleport();
    }

    @Override
    public void execute() {
        if (!ctx.backpack.select().id(8009).isEmpty()) {
            final Item tab = ctx.backpack.select().id(8009).poll();
            int actionSlot = ctx.combatBar.select().id(8009).first().poll().getSlot();

            if (actionSlot != -1) {
                if (ctx.combatBar.getActionAt(actionSlot).select()) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return Place.FALADOR.area.contains(ctx.players.local());
                        }
                    }, 400, 10);
                }
            } else {
                if (tab.interact("Break")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return Place.FALADOR.area.contains(ctx.players.local());
                        }
                    }, 400, 10);
                }
            }
        } else {
            script.log("Uh oh... we're out of tabs!!!");
        }
    }

}
