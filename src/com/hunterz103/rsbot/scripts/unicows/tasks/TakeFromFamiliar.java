package com.hunterz103.rsbot.scripts.unicows.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.unicows.UnicowKiller;
import org.powerbot.script.methods.Bank;
import org.powerbot.script.methods.Summoning;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/13/14.
 */
public class TakeFromFamiliar extends Task<UnicowKiller> {

    public TakeFromFamiliar(UnicowKiller unicowKiller) {
        super(unicowKiller);
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean activate() {
        return ctx.npcs.select().id(script.ID_UNICOW).isEmpty() && ctx.backpack.select().id(script.ID_COWHIDE).isEmpty() && ctx.summoning.isFamiliarSummoned() && cacheContainsHides();
    }

    @Override
    public void execute() {
        //Summoning.Familiar familiar = ctx.summoning.getFamiliar();
        final Widget bobInventory = ctx.widgets.get(671);
        final Component bobInvetoryItems = ctx.widgets.get(671, 25);
        Npc familiar = ctx.summoning.getNpc();

        ctx.camera.turnTo(familiar);

        if (familiar.interact("Store")) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.players.local().isInMotion();
                }
            }, 500, 15);
            if (Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return bobInventory.isValid();
                }
            }, 50, 10)) {
                updateCache();

                if (cacheContainsHides()) withdrawFromBoB(script.ID_COWHIDE, Bank.Amount.TEN);


            }
        }

        if (bobInventory.isValid() && !ctx.backpack.select().id(script.ID_COWHIDE).isEmpty()) {
            ctx.widgets.get(671,21).getChild(1).click(true);
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !bobInventory.isValid();
                }
            }, 70, 10);
        }

    }

    private boolean cacheContainsHides(){
        for (Item item : script.familiarInventoryCache) {
            if (item.getId() == script.ID_COWHIDE) return true;
        }

        return false;
    }

    private void updateCache() {
        Component comp = ctx.widgets.get(671, 25);

        script.familiarInventoryCache.clear();

        for (int i = 0; i < ctx.summoning.getFamiliar().getBoBSpace(); i++) {
            Component itemComp = comp.getChild(i);
            script.familiarInventoryCache.add(new Item(ctx, itemComp));
        }
    }

    private boolean withdrawFromBoB(int id, Bank.Amount amount) {
        Component comp = ctx.widgets.get(671, 25);

        for (int i = 0; i < ctx.summoning.getFamiliar().getBoBSpace(); i++) {
            Component itemComp = comp.getChild(i);
            if (itemComp.getId() == id) {
                if (itemComp.interact("Withdraw-" + amount)) {
                    return true;
                }
            }
        }

        updateCache();
        return false;
    }

}
