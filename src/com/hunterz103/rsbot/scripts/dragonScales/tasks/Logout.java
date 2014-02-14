package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.framework.TaskScript;
import org.powerbot.script.util.Condition;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/10/14.
 */
public class Logout extends Task {

    public Logout(TaskScript taskScript) {
        super(taskScript);
    }

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(8009).isEmpty();
    }

    @Override
    public void execute() {
        if (ctx.game.logout(true)) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.game.isLoggedIn();
                }
            }, 500, 10);
        }
    }
}
