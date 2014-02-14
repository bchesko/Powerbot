package com.hunterz103.rsbot.scripts.unicows.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.unicows.UnicowKiller;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Npc;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/13/14.
 */
public class AttackUnicow extends Task<UnicowKiller>  {

    public AttackUnicow(UnicowKiller unicowKiller) {
        super(unicowKiller);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return !ctx.npcs.select().id(script.ID_UNICOW).isEmpty() && !ctx.players.local().getInteracting().isValid();
    }

    @Override
    public void execute() {
        for (Npc npc : ctx.npcs.select().id(script.ID_UNICOW)) {
            if (npc.getInteracting().isValid() && npc.getInteracting().equals(ctx.players.local())) {
                ctx.camera.turnTo(npc);
                if (npc.interact("Attack")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.players.local().getInteracting().isValid();
                        }
                    }, 100, 10);
                }
                break;
            }
        }
    }
}