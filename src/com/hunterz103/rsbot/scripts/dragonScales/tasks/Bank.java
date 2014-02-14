package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.wrappers.Item;

import java.text.DecimalFormat;

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
        ctx.camera.turnTo(ctx.bank.getNearest());
        if (ctx.bank.open()) {
            for (Item item : ctx.backpack.getAllItems()) {
                if (item.getId() != -1 && shouldBeBanked(item) && ctx.backpack.select().contains(item)) {
                    ctx.bank.deposit(item.getId(), org.powerbot.script.methods.Bank.Amount.ALL);
                    sleep(300, 500);
                }
            }

            int inBank = ctx.bank.select().id(243).poll().getStackSize();
            script.log(script.scales + " picked up so far. (That's " + new DecimalFormat("#,###").format(BlueDragonScalePicker.getNetProfit(script.scales)) + " gp)");
            script.log(inBank + " scale(s) in the bank. (That's " + new DecimalFormat("#,###").format(BlueDragonScalePicker.getNetProfit(inBank)) + " gp)");
            ctx.bank.close();
        }
    }

    private boolean shouldBeBanked(Item item){
        for (int id : new int[]{KEY_ID, TAB_ID}) { //because I'll add familiars later
            if (item.getId() == id) return false;
        }
        return true;
    }
}
