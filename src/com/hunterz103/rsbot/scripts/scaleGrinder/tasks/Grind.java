package com.hunterz103.rsbot.scripts.scaleGrinder.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.scaleGrinder.ScaleGrinder;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/10/14.
 */
public class Grind extends Task<ScaleGrinder> {

    public Grind(ScaleGrinder scaleGrinder) {
        super(scaleGrinder);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(script.SCALE_ID).isEmpty() && !ctx.widgets.get(1251).isValid() && !ctx.bank.isOpen();
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
                        return ctx.backpack.select().id(script.SCALE_ID).isEmpty() || !inProgressWidget.isValid();
                    }
                }, 1000, 16)) {
                    return;
                }
            }
        }

        if (ctx.backpack.select().id(script.SCALE_ID).poll().interact("Grind")) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return fillComp.isValid();
                }
            }, 30, 10);
        }
    }
}
