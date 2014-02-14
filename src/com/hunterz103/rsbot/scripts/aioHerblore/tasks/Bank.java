package com.hunterz103.rsbot.scripts.aioHerblore.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.aioHerblore.AIOHerblore;
import org.powerbot.script.util.Condition;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/10/14.
 */
public class Bank extends Task<AIOHerblore> {

    private final int ITEM_1;
    private final int ITEM_2;

    public Bank(AIOHerblore potionMaker) {
        super(potionMaker);

        ITEM_1 = script.product.ITEM_1_ID;
        ITEM_2 = script.product.ITEM_2_ID;
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(ITEM_1).isEmpty() ||
              (ITEM_2 != -1 ? ctx.backpack.select().id(ITEM_2).isEmpty() : false) ||
               ctx.bank.isOpen();
    }

    @Override
    public void execute() {
        if (ITEM_2 != -1) {
            bankPotions();
        } else {
            bankHerbs();
        }
    }

    private void bankHerbs(){
        if (ctx.bank.open()) {
            script.log("Depositing inventory");
            ctx.bank.depositInventory();

            if (!ctx.bank.select().id(ITEM_1).isEmpty()) {
                script.log("Withdrawing herb");
                if (ctx.bank.withdraw(ITEM_1, org.powerbot.script.methods.Bank.Amount.ALL)) {
                    Condition.wait(new Callable<Boolean>(){
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.backpack.select().id(ITEM_1).isEmpty();
                        }
                    }, 100, 10);
                }


            } else {
                script.stop();
            }

            if (!ctx.backpack.select().id(ITEM_1).isEmpty() || ctx.bank.select().id(ITEM_1).isEmpty()) {
                script.log("Closing bank");
                ctx.bank.close();
                script.stop();
            }
        }
    }

    private void bankPotions(){
        if (ctx.bank.open()) {
            script.log("Depositing inventory");
            ctx.bank.depositInventory();

            if (!ctx.bank.select().id(ITEM_1).isEmpty() && !ctx.bank.select().id(ITEM_2).isEmpty()) {

                script.log("Withdrawing ingredients");
                if (ctx.bank.withdraw(ITEM_1, 14))
                    Condition.wait(new Callable<Boolean>(){
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.backpack.select().id(ITEM_1).isEmpty();
                        }
                    }, 100, 10);
                if (ctx.bank.withdraw(ITEM_2, 14))
                    Condition.wait(new Callable<Boolean>(){
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.backpack.select().id(ITEM_2).isEmpty();
                        }
                    }, 100, 10);

            } else {
                script.stop();
            }

            if (!ctx.backpack.select().id(ITEM_1).isEmpty() && !ctx.backpack.select().id(ITEM_2).isEmpty()) {
                script.log("Closing bank");
                ctx.bank.close();
            }
        }
    }
}
