package com.hunterz103.rsbot.scripts.scaleGrinder.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.scaleGrinder.ScaleGrinder;

/**
 * Created by Brian on 2/10/14.
 */
public class Bank extends Task<ScaleGrinder> {

    public Bank(ScaleGrinder scaleGrinder) {
        super(scaleGrinder);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(script.SCALE_ID).isEmpty() || ctx.bank.isOpen();
    }

    @Override
    public void execute() {
        if (ctx.bank.open()) {
            script.log("Depositing inventory");
            ctx.bank.depositInventory();

            script.log("Withdrawing scales");
            ctx.bank.withdraw(script.SCALE_ID, org.powerbot.script.methods.Bank.Amount.ALL);

            if (!ctx.backpack.select().id(243).isEmpty()) {
                script.log("Closing bank");
                ctx.bank.close();
            }
        }
    }
}
