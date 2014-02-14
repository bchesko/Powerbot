package com.hunterz103.rsbot.scripts.sorceressGarden;

import com.hunterz103.rsbot.wrappers.Area;
import org.powerbot.script.wrappers.Tile;

/**
 * Created by Brian on 2/3/14.
 */
public enum Garden {

    HUB(new Area(new Tile(2903, 5480, 0), new Tile(2920, 5463, 0)), null, -1),
    WINTER(null, new Tile(2903, 5470, 0), 5),
    SPRING(null, new Tile(2920, 5473, 0), 4),
    AUTUMN(null, new Tile(2913, 5463, 0), 3),
    SUMMER(new Area(new Tile(2905, 5481, 0), new Tile(2925, 5495, 0)), new Tile(2910, 5480, 0), 2);
    //So each is 20 long, 14 tall

    public final Area area;
    public final Tile gateTile;
    public final int sqirksRequired;

    Garden(Area area, Tile gateTile , int sqirksRequired){
        this.area = area;
        this.gateTile = gateTile;
        this.sqirksRequired = sqirksRequired;
    }

}
