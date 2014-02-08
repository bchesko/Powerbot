package com.hunterz103.rsbot.scripts.utilScripts;

import org.powerbot.event.MessageEvent;
import org.powerbot.event.MessageListener;
import org.powerbot.script.AbstractScript;
import org.powerbot.script.Manifest;
import org.powerbot.script.wrappers.Tile;

import java.util.ArrayList;

@Manifest(name = "Area Finder", description = "Too lazy to type numbers and shit.")
/**
 * Created by Brian on 2/1/14.
 */
public class AreaFinder extends AbstractScript implements MessageListener {

    int areasMade = 0;
    int tilesMade = 0;
    int tilePathsMade = 0;
    Tile tile1, tile2;
    ArrayList<Tile> walkingTiles = new ArrayList<>();

    @Override
    public void messaged(MessageEvent messageEvent) {
        String sender = messageEvent.getSender();
        String message = messageEvent.getMessage();
        if (sender.equals(ctx.players.local().getName())) {
            if (message.equalsIgnoreCase("a") || message.equalsIgnoreCase("ea")) {
                if (tile1 == null) {
                    tile1 = ctx.players.local().getLocation();
                    if (message.equalsIgnoreCase("a")) {
                        System.out.println("Area area" + areasMade + " started at " + ctx.players.local().getLocation().toString());
                    } else {
                        System.out.println("AREA_" + areasMade + " started at " + ctx.players.local().getLocation().toString());
                    }
                } else {
                    tile2 = ctx.players.local().getLocation();
                    if (message.equalsIgnoreCase("a")) {
                        System.out.println("Area area" + areasMade + " = new Area(new Tile" + tile1.toString() +  ", new Tile" + tile2.toString() + ");");
                    } else {
                        System.out.println("AREA_" + areasMade + "(new org.powerbot.script.com.hunterz103.rsbot.wrappers.Area(new Tile" + tile1.toString() +  ", new Tile" + tile2.toString() + "));");
                    }
                    tile1 = null;
                    tile2 = null;
                    areasMade++;
                }
            } else if (message.equalsIgnoreCase("t")) {
                System.out.println("Tile tile" + tilesMade + " = new Tile" + ctx.players.local().getLocation().toString() + ";");
                tilesMade++;
            }  else if (message.equalsIgnoreCase("w") || message.equalsIgnoreCase("w done")) {
                walkingTiles.add(ctx.players.local().getLocation());
                System.out.println("Adding tile: " + ctx.players.local().getLocation().toString() + " to tile path.");
                if (message.equalsIgnoreCase("w done")) {
                    StringBuilder tilesString = new StringBuilder();
                    for (Tile tile : walkingTiles) {
                        tilesString.append("new Tile").append(tile.toString()).append(", ");
                    }
                    System.out.println("TilePath tilePath" + tilePathsMade + " = ctx.movement.newTilePath(" + tilesString.toString().substring(0, tilesString.length() - 2) + ");");
                    walkingTiles.clear();
                    tilePathsMade++;
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Running Area Finder.");
    }
}
