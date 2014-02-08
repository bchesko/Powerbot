package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Item;
import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class Bank extends Task {

    public Bank(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return ctx.bank.isOpen() ||
                (ctx.backpack.select().count() == 28 &&
                (Place.BANK.area.contains(ctx.players.local()) ||
                 Place.BANK_2.area.contains(ctx.players.local())));
    }

    @Override
    public void execute() {
        if (!ctx.bank.isOpen()) {
            ctx.bank.open();
            sleep(300, 400);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    while (ctx.players.local().isInMotion());
                    return ctx.bank.isOpen();
                }
            }, 300, 5);
        } else {
            if (BlueDragonScalePicker.scalesOrig == -1) {
                BlueDragonScalePicker.scalesOrig = ctx.bank.select().id(243).poll().getStackSize();
                BlueDragonScalePicker.getInstance().log("Original amount of scales in bank: " + BlueDragonScalePicker.scalesOrig + ". (That's " + new DecimalFormat("#,###").format(BlueDragonScalePicker.getNetProfit(BlueDragonScalePicker.scalesOrig)) + " gp)");
            }
             for (Item item : ctx.backpack.getAllItems()) {
                if (item.getId() != -1 && !item.getName().equalsIgnoreCase("falador teleport") && ctx.backpack.select().contains(item)) {
                    ctx.bank.deposit(item.getId(), org.powerbot.script.methods.Bank.Amount.ALL);
                    sleep(700, 900);
                }
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().count() == 1;
                }
            }, 200, 5);

            if (ctx.backpack.select().id(243).count() == 0) {
                BlueDragonScalePicker.scales = ctx.bank.select().id(243).poll().getStackSize();
                BlueDragonScalePicker.getInstance().log(BlueDragonScalePicker.scales + " scale(s) are in the bank now. (That's " + new DecimalFormat("#,###").format(BlueDragonScalePicker.getNetProfit(BlueDragonScalePicker.scales)) + " gp)");
                ctx.bank.close();
            }
        }
    }
}
