package com.hunterz103.rsbot.scripts.dragonScales.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.framework.Task;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;
import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.util.Pathing;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/5/14.
 */
public class WalkToDungeon extends Task {

    Pathing pathing = new Pathing(ctx);
    TilePath pathToWall = ctx.movement.newTilePath(new Tile(2952, 3382, 0), new Tile(2941, 3372, 0), new Tile(2940, 3356, 0));
    TilePath pathToDung = ctx.movement.newTilePath(new Tile(2931, 3372, 0), new Tile(2921, 3378, 0), new Tile(2906, 3384, 0), new Tile(2892, 3388, 0), new Tile(2886, 3394, 0));

    public WalkToDungeon(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int priority() {
        return 3;
    }

    @Override
    public boolean activate() {
        return !Place.DRAGONS.area.contains(ctx.players.local()) &&!Place.INNER_DUNGEON.area.contains(ctx.players.local()) && !ctx.bank.isOpen() && ctx.backpack.select().count() != 28;
    }

    @Override
    public void execute() {
        if (ctx.players.local().getLocation().getX() >= 2936) { //Before the wall jump
            if (pathToWall.getEnd().distanceTo(ctx.players.local()) > 7) {
                pathing.walkPath(pathToWall, 3, 3, 3);
            } else {
                final GameObject wall = ctx.objects.select().id(11844).poll();

                if (wall.isValid()) {
                    if (!wall.isInViewport()) {
                        ctx.camera.turnTo(wall);
                        sleep(300, 500);
                    }

                    if (wall.interact("Climb-over")) {
                        BlueDragonScalePicker.getInstance().log("Climbing over wall.");
                        Condition.wait(new Callable<Boolean>() {
                            @Override
                            public Boolean call() throws Exception {
                                while (ctx.players.local().getAnimation() != -1) ;
                                return ctx.players.local().getLocation().getX() <= 2935;
                            }
                        }, 500, 5);
                    }
                }
            }
        } else { //Post wall jump
            if (Place.OUT_OF_DUNGEON.area.contains(ctx.players.local())) { //NEXT TO STEPS
                final GameObject dungeon = ctx.objects.select().id(66991).poll();

                if (!dungeon.isInViewport()) {
                    ctx.camera.turnTo(dungeon);
                    sleep(300, 500);
                }

                if (dungeon.interact("Climb-down")) {
                    BlueDragonScalePicker.getInstance().log("Going into dungeon.");
                    sleep(300, 400);
                    Condition.wait(new Callable() {
                        @Override
                        public Object call() throws Exception {
                            while (ctx.players.local().getAnimation() != -1 && ctx.players.local().isInMotion());
                            return Place.INNER_DUNGEON.area.contains(ctx.players.local());
                        }
                    }, 200, 10);
                }
            } else { //AWAY FROM STEPS
                pathing.walkPath(pathToDung, 3, 3, 3);
            }
        }
    }
}
