package com.hunterz103.rsbot.scripts.sorceressGarden.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.Hud;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Tile;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/3/14.
 */
public class Bank extends Task {

    public Bank(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28 || ctx.backpack.select().id(23643).count() == 0 || ctx.backpack.select().id(14057).count() == 0;
    }

    @Override
    public void execute() {
        if (ctx.bank.getNearest().getLocation().equals(new Tile(-1, -1, -1))) {
            final Item ring = ctx.backpack.select().id(23643).poll();

            ctx.hud.view(Hud.Window.BACKPACK);
            sleep(200);

            if (ring.isValid()) {
                Condition.wait(new Callable(){
                    @Override
                    public Object call() throws Exception {
                        ring.interact("Teleport");
                        sleep(800,1000);
                        ctx.keyboard.send("3");
                        sleep(1500, 2000);
                        return ctx.players.local().getAnimation() != -1;
                    }
                }, Random.nextInt(2000, 3000), 5);
            }
        } else {
            Condition.wait(new Callable(){
                @Override
                public Object call() throws Exception {
                    if (!ctx.bank.isOpen()) ((Npc) ctx.bank.getNearest()).interact("Bank");
                    return ctx.bank.isOpen();
                }
            }, Random.nextInt(2000, 3000), 5);
        }

        if (ctx.bank.isOpen()) {
            if (ctx.backpack.select().count() > 2) {
                ctx.bank.depositInventory();
            } else {
                if (ctx.backpack.select().id(23643).count() == 0) {
                    ctx.bank.withdraw(23643, 1);
                    sleep(700,900);
                }
                if (ctx.backpack.select().id(14057).count() == 0) {
                    ctx.bank.withdraw(14057, 1);
                    sleep(700, 900);
                }
                ctx.bank.close();
            }
        }
    }

    /*
            for (Item item : ctx.backpack.getAllItems()) {
                if (item.getId() != 14057 && item.getId() != 23643) {
                    ctx.bank.deposit(item.getId(), org.powerbot.script.methods.Bank.Amount.ALL);
                    sleep(400, 600);
                }
            }
     */

}

