package com.hunterz103.rsbot.scripts.vialFiller.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.vialFiller.VialFiller;
import com.hunterz103.rsbot.wrappers.Area;
import org.powerbot.script.wrappers.Tile;

/**
 * Created by Brian on 2/10/14.
 */
public class WalkToFountain extends Task<VialFiller> {

    private final Area fountainArea = new Area(new Tile(3158, 3494, 0), new Tile(3173, 3487, 0));
    Tile fountainTile = new Tile(3161, 3490, 0);

    public WalkToFountain(VialFiller vialFiller) {
        super(vialFiller);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return !ctx.backpack.select().id(229).isEmpty() && !fountainArea.containsPlayer(ctx) && !ctx.bank.isOpen() && !ctx.players.local().isInMotion();
    }

    @Override
    public void execute() {
        ctx.movement.findPath(fountainTile).traverse();
    }

}
