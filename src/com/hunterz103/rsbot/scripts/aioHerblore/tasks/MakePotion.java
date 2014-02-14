package com.hunterz103.rsbot.scripts.aioHerblore.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.aioHerblore.AIOHerblore;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Widget;

import java.awt.*;
import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/10/14.
 */
public class MakePotion extends Task<AIOHerblore> {

    private final int ITEM_1;
    private final int ITEM_2;
    
    public MakePotion(AIOHerblore potionMaker) {
        super(potionMaker);

        ITEM_1 = script.product.ITEM_1_ID;
        ITEM_2 = script.product.ITEM_2_ID;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(ITEM_1).isEmpty() && !ctx.backpack.select().id(ITEM_2).isEmpty() && !ctx.widgets.get(1251).isValid() && !ctx.bank.isOpen();
    }

    @Override
    public void execute() {
        final Component fillComp = ctx.widgets.get(1370).getComponent(38);
        final Widget inProgressWidget = ctx.widgets.get(1251);

        if (fillComp.isValid()) {
            if (fillComp.click(true)) {
                if (Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.backpack.select().id(ITEM_1).isEmpty() || ctx.backpack.select().id(ITEM_2).isEmpty() || !inProgressWidget.isValid();
                    }
                }, 1000, 16)) {
                    return;
                }
            }
        }

        if (ctx.backpack.getSelectedItem().getId() == ITEM_1) {
            if (getClosestToPoint(new Point(ctx.backpack.getSelectedItem().getCenterPoint()), ITEM_2).interact("Use")) {
                if (Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return fillComp.isValid();
                    }
                }, 200, 10)) {
                    return;
                }
            }
        }

        script.log("Using item 1 on item 2");
        if (getClosestToPoint(new Point(470, 440), ITEM_1).interact("Use")) {
            if (Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.getSelectedItem().getId() == ITEM_1;
                }
            }, 50, 5)) {
                return;
            }
        }
    }


    private Item getClosestToPoint(Point p, int id){
        Item closest = null;
        for (Item item : ctx.backpack.select().id(id)) {
            if (closest == null) closest = item;
            if (item.getInteractPoint().distance(p) < closest.getInteractPoint().distance(p)) {
                closest = item;
            }

        }
        return closest;
    }
}
