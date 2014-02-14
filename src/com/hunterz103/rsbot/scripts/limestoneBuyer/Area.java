package com.hunterz103.rsbot.scripts.limestoneBuyer;

import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.Tile;

/**
 * Created by Brian on 2/1/14.
 */
public enum Area {

    BANK(new com.hunterz103.rsbot.wrappers.Area(new Tile(3495, 3210, 0), new Tile(3501, 3214, 0))),
    GATE_BURGH_SIDE(new com.hunterz103.rsbot.wrappers.Area(new Tile(3482, 3239, 0), new Tile(3486, 3243, 0))),
    GATE_MORT_SIDE(new com.hunterz103.rsbot.wrappers.Area(new Tile(3483, 3244, 0), new Tile(3487, 3246, 0))),
    STORE_OUTSIDE(new com.hunterz103.rsbot.wrappers.Area(new Tile(3485, 3296, 0), new Tile(3492, 3293, 0))),
    STORE_INSIDE(new com.hunterz103.rsbot.wrappers.Area(new Tile(3487, 3295, 0), new Tile(3488, 3297, 0)));

    public final com.hunterz103.rsbot.wrappers.Area area;

    Area(com.hunterz103.rsbot.wrappers.Area area) {
     this.area = area;
    }

    public boolean walkTo(MethodContext ctx, int maxDistFrom) {
        return area.contains(ctx.players.local());
    }

    public boolean containsPlayer(MethodContext ctx){
        //System.out.println(area.contains(ctx.players.local()));
        return area.contains(ctx.players.local());
    }


}
