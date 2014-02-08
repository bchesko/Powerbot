package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.util.Pathing;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/8/14.
 */
public class WalkAroundIntoDragons extends Task {

    Pathing pathing = new Pathing(ctx);
    TilePath pathToFirstDoor = ctx.movement.newTilePath(new Tile(2883, 9818, 0), new Tile(2885, 9831, 0));
    Tile firstDoorTile = new Tile(2888, 9830, 0);
    Tile secondDoorTile = new Tile(2892, 9825, 0);
    TilePath pathToFinalDoor = ctx.movement.newTilePath(new Tile(2905, 9819, 0), new Tile(2922, 9818, 0),
            new Tile(2938, 9822, 0), new Tile(2939, 9809, 0), new Tile(2946, 9797, 0), new Tile(2955, 9789, 0),
            new Tile(2952, 9775, 0), new Tile(2936, 9776, 0), new Tile(2927, 9756, 0), new Tile(2927, 9773, 0),
            new Tile(2933, 9796, 0), new Tile(2925, 9803, 0));
    Tile lastDoorTile = new Tile(2924, 9803, 0);
    private final int KEY_ID = 1590;


    public WalkAroundIntoDragons(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return Place.INNER_DUNGEON.area.containsPlayer(ctx) && !Place.DRAGONS.area.containsPlayer(ctx) && !BlueDragonScalePicker.usingAgilityShortcut && ctx.backpack.select().count() != 28;
    }

    @Override
    public void execute() {


        if (ctx.players.local().getLocation().getX() <= 2888) { //Pre-first-door
            if (pathToFirstDoor.getEnd().distanceTo(ctx.players.local()) > 5) {
                BlueDragonScalePicker.getInstance().log("Walking to first door");
                pathing.walkPath(pathToFirstDoor, 2, 2, 3);
            } else {
                openDoor(firstDoorTile);
            }
        } else if (Place.CAULDRON_ROOM.area.containsPlayer(ctx)) { //Cauldron room
            openDoor(secondDoorTile);
        } else if (ctx.players.local().getLocation().getY() < 9826 && ctx.players.local().getLocation().getX() > 2888) { //from 2nd to last door
            if (pathToFinalDoor.getEnd().distanceTo(ctx.players.local()) > 5) {
                BlueDragonScalePicker.getInstance().log("Walking to last door");
                pathing.walkPath(pathToFinalDoor, 2, 2, 3);
            } else {
                openDoor(lastDoorTile);
            }
        }
    }

    private boolean openDoor(final Tile tile) {
        GameObject door = ctx.objects.select().at(tile).peek();
        if (door != null && door.isValid()) {
            if (!door.isInViewport()) ctx.camera.turnTo(door);
            if (tile.toString().equals(lastDoorTile.toString())) {
                if (ctx.backpack.select().id(KEY_ID).poll().interact("Use")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.backpack.getSelectedItem().getId() == KEY_ID;
                        }
                    }, 30, 10);
                    if (ctx.mouse.click(door.getInteractPoint(), true)) {
                        BlueDragonScalePicker.getInstance().log("Opening last door");
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                while (ctx.players.local().getAnimation() != -1) ;
                                return Place.DRAGONS.area.containsPlayer(ctx);
                            }
                        }, 500, 7);
                    }
                }
            } else {
                if (door.interact("Open")) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            while (ctx.players.local().getAnimation() != -1) ;
                            return (tile.toString().equals(secondDoorTile.toString())) ? ctx.players.local().getLocation().getY() < 9826 : Place.CAULDRON_ROOM.area.containsPlayer(ctx);
                        }
                    }, 500, 7);
                }
            }
        }
        return false;
    }
}
