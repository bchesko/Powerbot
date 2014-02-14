package com.hunterz103.rsbot.scripts.clayHumidifier.tasks;

import com.hunterz103.rsbot.scripts.clayHumidifier.ClayHumidifier;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.util.Condition;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/13/14.
 */
public class Humidify extends Task<ClayHumidifier> {

    public Humidify(ClayHumidifier clayHumidifier) {
        super(clayHumidifier);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return ctx.bank.isOpen() && !ctx.backpack.select().id(script.ID_CLAY).isEmpty() && ctx.backpack.select().id(script.ID_SOFT_CLAY).isEmpty() && ctx.players.local().getAnimation() == -1;
    }

    @Override
    public void execute() {
        if (ctx.combatBar.getActionAt(ctx.combatBar.select().id(1766).poll().getSlot()).select(true)) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().getAnimation() == -1 || !ctx.backpack.select().id(script.ID_CLAY).isEmpty();
                }
            }, 400, 10);
        }
    }
}
