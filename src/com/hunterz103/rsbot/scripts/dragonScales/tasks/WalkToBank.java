package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.util.Pathing;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;

/**
 * Created by Brian on 2/5/14.
 */
public class WalkToBank extends Task<BlueDragonScalePicker> {

    Pathing pathing = new Pathing(ctx);
    TilePath pathToBank = ctx.movement.newTilePath(new Tile(2950, 3377, 0), new Tile(2945, 3369, 0));
    public WalkToBank(BlueDragonScalePicker arg0) {
        super(arg0);
    }

    @Override
    public int getPriority() {
        return 3;
    }

    @Override
    public boolean activate() {
        return Place.FALADOR.area.contains(ctx.players.local()) && ctx.backpack.select().count() == 28 &&
                (!Place.BANK.area.contains(ctx.players.local()) && !Place.BANK_2.area.contains(ctx.players.local()));
    }

    @Override
    public void execute() {
        script.log("Walking to bank");
        pathing.walkPath(pathToBank, 1, 3, 2);
    }
}
