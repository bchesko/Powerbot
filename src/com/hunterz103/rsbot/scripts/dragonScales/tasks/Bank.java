package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Item;

import java.text.DecimalFormat;
import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class Bank extends Task<BlueDragonScalePicker> {

    private final int SCALE_ID = 243;
    private final int KEY_ID = 1590;
    private final int TAB_ID = 8009;

    public Bank(BlueDragonScalePicker arg0) {
        super(arg0);
    }

    @Override
    public int getPriority() {
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
        if (ctx.bank.open()) {
            if (script.scalesOrig == -1) {
                script.scalesOrig = ctx.bank.select().id(SCALE_ID).poll().getStackSize();
                script.log("Original amount of scales in bank: " + script.scalesOrig + ". (That's " + new DecimalFormat("#,###").format(script.getNetProfit(script.scalesOrig)) + " gp)");
            }
            for (Item item : ctx.backpack.getAllItems()) {
                if (item.getId() != -1 && shouldBeBanked(item) && ctx.backpack.select().contains(item)) {
                    ctx.bank.deposit(item.getId(), org.powerbot.script.methods.Bank.Amount.ALL);
                    sleep(300, 500);
                }
            }

            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.select().id(SCALE_ID).count() == 0;
                }
            }, 200, 5);

            if (ctx.backpack.select().id(SCALE_ID).count() == 0) {
                script.scales = ctx.bank.select().id(SCALE_ID).poll().getStackSize();
                script.log(script.scales + " scale(s) are in the bank now. (That's " + new DecimalFormat("#,###").format(BlueDragonScalePicker.getNetProfit(script.scales)) + " gp)");
                ctx.bank.close();
            }
        }
    }

    private boolean shouldBeBanked(Item item){
        for (int id : new int[]{KEY_ID, TAB_ID}) { //because I'll add familiars later
            if (item.getId() == id) return false;
        }
        return true;
    }
}
