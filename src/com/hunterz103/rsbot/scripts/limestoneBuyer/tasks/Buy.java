package com.hunterz103.rsbot.scripts.limestoneBuyer.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.limestoneBuyer.Area;
import com.hunterz103.rsbot.scripts.limestoneBuyer.LimestoneBuyer;
import com.hunterz103.rsbot.util.Util;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/1/14.
 */
public class Buy extends Task {

    private final Util util = new Util(ctx);
    private final int STORE_ID = 7899;
    private final int STORE_WIDGET_ID = 1265;
    /*
        COMPONENT-20 = ITEMS, SUB-0 = LIMESTONE
        COMPONENT-26 = ITEMS(SMALLER), SUB-0 = LIMESTONE, CHILD-STACK-SIZE = COUNT
        COMPONENT-86 = EXIT STORE
     */

    public Buy(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return  ((isOutsideAndDoorOpen() || isInside()) && ctx.backpack.select().count() < 28)
                || ctx.widgets.get(1265).isValid();
    }

    private boolean isOutsideAndDoorOpen(){
        return Area.STORE_OUTSIDE.containsPlayer(ctx) && ctx.objects.select().at(new Tile(3488, 3295, 0)).id(1531).poll().isValid();
    }

    private boolean isInside(){
        return Area.STORE_INSIDE.containsPlayer(ctx);
    }

    @Override
    public void execute() {
        Npc store = ctx.npcs.select().id(STORE_ID).poll();
        final Widget storeWidget = ctx.widgets.get(STORE_WIDGET_ID);
        final Component limestoneComponent = ctx.widgets.get(STORE_WIDGET_ID, 26).getChild(0);
        final Component limestoneLargeComponent = ctx.widgets.get(STORE_WIDGET_ID, 20).getChild(0);

        if ((isInside() || isOutsideAndDoorOpen()) && ctx.backpack.select().count() < 28) {
            if (!storeWidget.isValid()) {
                store.interact("Trade-Builders-Store");
                Condition.wait(new Callable(){
                    @Override
                    public Object call() throws Exception {
                        return storeWidget.isValid();
                    }
                });
            }

            sleep(300,400);
            if (storeWidget.isValid() && limestoneComponent.getItemStackSize() > 0) {
                limestoneLargeComponent.interact("Buy 50");
                Condition.wait(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return ctx.backpack.count() == 28;
                    }
                });

                while (storeWidget.isValid()) {
                    storeWidget.getComponent(87).click();
                    sleep(500);
                }
            } else {
                String worldString = ctx.widgets.get(550).getComponent(38).getText();
                String worldSub = worldString.replace("Friends List<br>RuneScape ", "");
                LimestoneBuyer.worldsFinished.add(Integer.parseInt(worldSub));
            }
        } else if (storeWidget.isValid()) {
            storeWidget.getComponent(87).click();
            Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    return storeWidget.isValid();
                }
            });
        }
    }


}
