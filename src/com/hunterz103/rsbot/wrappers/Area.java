package com.hunterz103.rsbot.wrappers;

import org.powerbot.script.wrappers.Locatable;
import org.powerbot.script.wrappers.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian on 2/1/14.
 */
public class Area {
    public final List<Tile> tiles = new ArrayList<>();

    public Area(Tile c1, Tile c2){
        for (int x = c1.getX(); (c1.getX() <= c2.getX()) ? x <= c2.getX() : x >= c2.getX(); x += ((c1.getX() <= c2.getX()) ? 1 : -1)) {
            for (int y = c1.getY(); (c1.getY() <= c2.getY()) ? y <= c2.getY() : y >= c2.getY(); y += ((c1.getY() <= c2.getY()) ? 1 : -1)) {
                for (int z = c1.getPlane(); (c1.getPlane() <= c2.getPlane()) ? z <= c2.getPlane() : z >= c2.getPlane(); z += ((c1.getPlane() <= c2.getPlane()) ? 1 : -1)) {
                    tiles.add(new Tile(x,y,z));
                }
            }
        }
    }

    public Area(Tile... tiles) {
        for (Tile tile : tiles) this.tiles.add(tile);
    }

    public boolean contains(Locatable arg0){
        for (Tile tile : tiles) {
            if (arg0.getLocation().equals(tile)) {
                return true;
            }
        }
        return false;
    }


}
