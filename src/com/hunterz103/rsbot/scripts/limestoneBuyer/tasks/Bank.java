package com.hunterz103.rsbot.scripts.limestoneBuyer.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.limestoneBuyer.Area;
import com.hunterz103.rsbot.util.Util;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;

import java.util.concurrent.Callable;
/**
 * Created by Brian on 2/1/14.
 */
public class Bank extends Task {

    private final Util util = new Util(ctx);
    public int limestoneOrig = -1;
    public int limestoneLast = -1;

    public Bank(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return (Area.BANK.containsPlayer(ctx) && ctx.backpack.select().count() > 0) || ctx.bank.isOpen();
    }

    @Override
    public void execute() {
        GameObject booth = (GameObject) ctx.bank.getNearest();

        if (!ctx.bank.isOpen()) {
            booth.interact("Bank");
            Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    return ctx.bank.isOpen();
                }
            });
        }

        if (ctx.bank.isOpen()) {
            sleep(300);
            if (limestoneOrig == -1) limestoneOrig = ctx.bank.select().id(3211).poll().getStackSize();
            System.out.println(ctx.bank.select().id(3211).poll().getStackSize());
            if (ctx.backpack.select().count() > 0) ctx.bank.depositInventory();
                Condition.wait(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return ctx.backpack.select().count() == 0;
                    }
                });

            sleep(400);
            limestoneLast = ctx.bank.select().id(3211).poll().getStackSize();
            System.out.println(limestoneLast + " in bank");
            if (ctx.backpack.select().count() == 0) {
                ctx.bank.close();
                Condition.wait(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return !ctx.bank.isOpen();
                    }
                });
            }

        }
    }


}
