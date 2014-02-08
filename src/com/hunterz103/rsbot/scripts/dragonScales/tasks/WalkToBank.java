package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.util.Pathing;

/**
 * Created by Brian on 2/5/14.
 */
public class WalkToBank extends Task {

    Pathing pathing = new Pathing(ctx);
    TilePath pathToBank = ctx.movement.newTilePath(new Tile(2950, 3377, 0), new Tile(2945, 3369, 0));
    public WalkToBank(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean activate() {
        return Place.FALADOR.area.contains(ctx.players.local()) && ctx.backpack.select().count() == 28 &&
                !(Place.BANK.area.contains(ctx.players.local()) && Place.BANK_2.area.contains(ctx.players.local()));
    }

    @Override
    public void execute() {
        pathing.walkPath(pathToBank, 1, 3, 2);
    }
}
