package com.hunterz103.rsbot.util;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;
import org.powerbot.script.util.Random;

import java.util.concurrent.Callable;


/**
 * Created by Brian on 2/1/14.
 */
public class Pathing extends MethodProvider {

    public Pathing(MethodContext ctx) {
        super(ctx);
    }

    public boolean walkPath(TilePath tp, int randomizeX, int randomizeY, int maxDistFrom) {
        tp.randomize(randomizeX, randomizeY);
        Tile[] tpa = tp.toArray();
        int startingIndex = 0;

        if (tp.getNext() == null) return true;

        for (int i = 0; i < tpa.length; i++) {
            if (tpa[i].toString().equals(tp.getNext().toString())) {
                startingIndex = i;
                break;
            }
        }

        for (int i = startingIndex; i < tpa.length; i++){
            final Tile t = tpa[i];
            if (ctx.movement.stepTowards(t)) {
                Condition.wait(new Callable<Boolean>(){
                    @Override
                    public Boolean call() throws Exception {
                        //while (ctx.players.local().isInMotion() && t.distanceTo(ctx.players.local()) > 4) sleep(100);
                        return t.distanceTo(ctx.players.local()) < 4;
                    }
                }, 800, 15);
            }
        }

        return tp.getEnd().distanceTo(ctx.players.local()) < maxDistFrom;
    }

}
