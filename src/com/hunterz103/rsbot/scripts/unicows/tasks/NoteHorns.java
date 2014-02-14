package com.hunterz103.rsbot.scripts.unicows.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.unicows.UnicowKiller;
import org.powerbot.script.util.Condition;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/13/14.
 */
public class NoteHorns extends Task<UnicowKiller> {

    public NoteHorns(UnicowKiller unicowKiller) {
        super(unicowKiller);
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean activate() {
        return !ctx.npcs.select().id(script.ID_UNICOW).isEmpty()
                && ctx.players.local().getInteracting().isValid()
                && !ctx.backpack.select().id(script.ID_HORN).isEmpty()
                && !ctx.backpack.select().id(script.ID_NOTEPAPER).isEmpty();
    }

    @Override
    public void execute() {
        if (ctx.backpack.getSelectedItem().getId() == script.ID_HORN) {
            final int noteCount = ctx.backpack.select().id(script.ID_NOTEPAPER).poll().getStackSize();
            if (ctx.backpack.select().id(script.ID_NOTEPAPER).poll().interact("Use")) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.backpack.select().id(script.ID_NOTEPAPER).poll().getStackSize() < noteCount;
                    }
                }, 50, 10);
                return;
            }
        } else {
            if (ctx.backpack.select().id(script.ID_HORN).poll().interact("Use")) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.backpack.getSelectedItem().getId() == script.ID_HORN;
                    }
                }, 50, 10);
                return;
            }
        }
    }
}