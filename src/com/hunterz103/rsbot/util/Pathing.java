package com.hunterz103.rsbot.util;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;
import org.powerbot.script.util.Random;


/**
 * Created by Brian on 2/1/14.
 */
public class Pathing {
    private final MethodContext ctx;

    public Pathing(MethodContext ctx) {
        this.ctx = ctx;
    }

    public boolean walkPath(TilePath tp, int randomizeX, int randomizeY, int maxDistFrom) {
        tp = randomize(tp, randomizeX, randomizeY);
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
            Tile t = tpa[i];
            ctx.movement.stepTowards(t);
            while (ctx.players.local().isInMotion() && t.distanceTo(ctx.players.local()) > 4) new MethodProvider(ctx).sleep(300);
        }

        return tp.getEnd().distanceTo(ctx.players.local()) < maxDistFrom;
    }

    private TilePath randomize(TilePath tilePath, int randomizeX, int randomizeY) {
        Tile[] result = new Tile[tilePath.toArray().length];

        for (int i = 0; i < tilePath.toArray().length; i++) {
            Tile t = tilePath.toArray()[i];

            int newX = t.getX() + Random.nextInt(-randomizeX, randomizeX);
            int newY = t.getY() + Random.nextInt(-randomizeY, randomizeY);

            result[i] = new Tile(newX, newY, t.getPlane());
        }

        return new TilePath(ctx, result);
    }

}
