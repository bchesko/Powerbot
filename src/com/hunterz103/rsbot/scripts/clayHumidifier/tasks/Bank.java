package com.hunterz103.rsbot.scripts.clayHumidifier.tasks;

import com.hunterz103.rsbot.scripts.clayHumidifier.ClayHumidifier;
import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.wrappers.Item;

import java.text.DecimalFormat;

/**
 * Created by Brian on 2/13/14.
 */
public class Bank extends Task<ClayHumidifier> {

    public Bank(ClayHumidifier clayHumidifier) {
        super(clayHumidifier);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean activate() {
        return ctx.bank.isOpen() || ctx.backpack.id(script.ID_CLAY).isEmpty();
    }

    @Override
    public void execute() {
        if (ctx.bank.open()) {
            for (Item item : ctx.backpack.getAllItems()) {
                if (item.getId() != -1 && item.getId() != script.ID_CLAY && item.getId() != script.ID_ASTRAL && ctx.backpack.select().contains(item)) {
                    ctx.bank.deposit(item.getId(), org.powerbot.script.methods.Bank.Amount.ALL);
                    sleep(300, 500);
                }
            }

            if (ctx.backpack.select().count() != 28) {
                ctx.bank.withdraw(script.ID_CLAY, org.powerbot.script.methods.Bank.Amount.ALL);
            }

            ctx.bank.close();
        }
    }
}
