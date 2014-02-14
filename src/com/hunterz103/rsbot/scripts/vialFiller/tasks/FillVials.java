package com.hunterz103.rsbot.scripts.vialFiller.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.vialFiller.VialFiller;
import com.hunterz103.rsbot.wrappers.Area;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/10/14.
 */
public class FillVials extends Task<VialFiller> {

    private final Area fountainArea = new Area(new Tile(3158, 3494, 0), new Tile(3173, 3487, 0));

    public FillVials(VialFiller vialFiller) {
        super(vialFiller);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return fountainArea.containsPlayer(ctx) && !ctx.backpack.select().id(229).isEmpty() && !ctx.widgets.get(1251).isValid();
    }


    @Override
    //Don't judge me on how I handle this - I feel like having it execute 3 times, checking in reverse is a lot simpler than
    //stacks on stacks of nested ifs and elses.
    public void execute() {
        final GameObject fountain = ctx.objects.select().id(47150).poll();
        final Component fillComp = ctx.widgets.get(1370).getComponent(38);
        final Widget inProgressWidget = ctx.widgets.get(1251);

        if (fillComp.isValid()) {
            if (fillComp.click(true)) {
                if (Condition.wait(new Callable<Boolean>(){
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.backpack.select().id(229).isEmpty() || !inProgressWidget.isValid();
                    }
                }, 1000, 16)) {
                    return;
                }
            }
        }

        if (ctx.backpack.getSelectedItem().getId() == 229) {
            if (fountain.interact("Use")) {
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

        if (fountain.isValid()) {
            if (!fountain.isInViewport()) {
                ctx.camera.turnTo(fountain);
            } else {
                if (ctx.backpack.select().id(229).poll().interact("Use")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.backpack.getSelectedItem().getId() == 229;
                        }
                    }, 30, 10);
                }
            }
        }
    }
}
