package com.hunterz103.rsbot.scripts.dragonScales.enums;

import org.powerbot.script.wrappers.Tile;
import com.hunterz103.rsbot.wrappers.Area;

/**
 * Created by Brian on 2/5/14.
 */
public enum Place {

    BANK(new Area(new Tile(2943, 3373, 0), new Tile(2947, 3368, 0))),
    BANK_2(new Area(new Tile(2947, 3368, 0), new Tile(2949, 3368, 0))),
    OUT_OF_DUNGEON(new Area(new Tile(2881, 3391, 0), new Tile(2889, 3402, 0))),
    INNER_DUNGEON(new Area(new Tile(2882, 9795, 0), new Tile(2887, 9811, 0))),
    DRAGONS(new Area(new Tile(2890, 9789, 0), new Tile(2923, 9813, 0))),
    FALADOR(new Area(new Tile(2981, 3391, 0), new Tile(2936, 3354, 0)));

    public final Area area;

    Place(Area area){
        this.area = area;
    }

}
