package com.hunterz103.rsbot.scripts.unicows.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.unicows.UnicowKiller;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Component;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Widget;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/13/14.
 */
public class SummonUnicow extends Task<UnicowKiller> {

    private final int ID_ALTAR = 21893;

    public SummonUnicow(UnicowKiller unicowKiller) {
        super(unicowKiller);
    }

    @Override
    public int getPriority() {
        return 4;
    }

    @Override
    public boolean activate() {
        return ctx.npcs.select().id(script.ID_UNICOW).isEmpty() && ((!ctx.backpack.select().id(script.ID_COWHIDE).isEmpty() && !ctx.backpack.select().id(script.ID_HORN).isEmpty()) || ctx.widgets.get(1189).isValid());
    }

    @Override
    public void execute() {
        final GameObject spawner = ctx.objects.select().at(script.TILE_ALTAR).id(ID_ALTAR).poll();
        final Widget confirmationWidget = ctx.widgets.get(1189);

        if (spawner.isValid()) {
            ctx.camera.turnTo(spawner);

            if (spawner.isInViewport()) {
                if (confirmationWidget.isValid() || spawner.interact("Activate")) {
                    if (Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return confirmationWidget.isValid();
                        }
                    }, 50, 10)) {
                        Component nextButton = confirmationWidget.getComponent(3);

                        if (nextButton.isValid() && nextButton.click(true)) {
                            Condition.wait(new Callable<Boolean>(){
                                @Override
                                public Boolean call() throws Exception {
                                    return !ctx.npcs.select().id(script.ID_UNICOW).isEmpty();
                                }
                            }, 50, 10);
                        }
                    }
                }
            }
        }
    }

}