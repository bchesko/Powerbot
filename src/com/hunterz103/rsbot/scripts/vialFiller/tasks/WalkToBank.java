package com.hunterz103.rsbot.scripts.vialFiller.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.vialFiller.VialFiller;
import com.hunterz103.rsbot.wrappers.Area;
import org.powerbot.script.wrappers.Tile;

/**
 * Created by Brian on 2/10/14.
 */
public class WalkToBank extends Task<VialFiller> {

    Area bankArea = new Area(new Tile(3152, 3483, 0), new Tile(3145, 3475, 0));
    Tile bankTile = new Tile(3152, 3481, 0);

    public WalkToBank(VialFiller vialFiller) {
        super(vialFiller);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().id(229).isEmpty() && !bankArea.containsPlayer(ctx) && !ctx.players.local().isInMotion();
    }

    @Override
    public void execute() {
        ctx.movement.findPath(bankTile).traverse();
        //new Pathing(ctx).walkPath(new TilePath(ctx, ), 2, 2, 2);
    }

}
