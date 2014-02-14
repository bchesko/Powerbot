package com.hunterz103.rsbot.scripts.vialFiller.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.vialFiller.VialFiller;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.wrappers.Area;
import org.powerbot.script.wrappers.Tile;

/**
 * Created by Brian on 2/10/14.
 */
public class Bank extends Task<VialFiller> {

    Area bankArea = new Area(new Tile(3152, 3483, 0), new Tile(3145, 3475, 0));

    public Bank(VialFiller vialFiller) {
        super(vialFiller);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return (bankArea.containsPlayer(ctx) && ctx.backpack.select().id(229).isEmpty()) || ctx.bank.isOpen();
    }

    @Override
    public void execute() {
        if (ctx.bank.open()) {
            if (ctx.backpack.select().count() > 0) {
                script.log("Depositing inventory");
                ctx.bank.depositInventory();
                script.log("Current info: Vials filled: " + script.vialsFilled + " (" + Calculations.toHour(script.getRuntime(), script.vialsFilled) +
                        " p/hr) GP made: " + script.gpMade() + " (" + Calculations.toHour(script.getRuntime(), script.gpMade()) + " p/hr)");
            }

            if (ctx.backpack.select().id(229).isEmpty()) {
                script.log("Withdrawing empty vials");
                ctx.bank.withdraw(229, org.powerbot.script.methods.Bank.Amount.ALL);
            }

            if (!ctx.backpack.select().id(229).isEmpty()) {
                script.log("Closing bank");
                ctx.bank.close();
            }
        }
    }
}
