package com.hunterz103.rsbot.scripts.aioHerblore.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.aioHerblore.AIOHerblore;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/12/14.
 */
public class Clean extends Task<AIOHerblore> {

    private final int ID;

    public Clean(AIOHerblore potionMaker) {
        super(potionMaker);

        ID = script.product.ITEM_1_ID;
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(ID).isEmpty()&& !ctx.widgets.get(1251).isValid() && !ctx.bank.isOpen();
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
                        return ctx.backpack.select().id(ID).isEmpty() || !inProgressWidget.isValid();
                    }
                }, 500, 64)) {
                    return;
                }
            }
        }

        script.log("Clicking on herb");
        if (ctx.backpack.select().id(ID).poll().interact("Clean")) {
            if (Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.backpack.getSelectedItem().getId() == ID;
                }
            }, 100, 5)) {
                return;
            }
        }
    }
}
