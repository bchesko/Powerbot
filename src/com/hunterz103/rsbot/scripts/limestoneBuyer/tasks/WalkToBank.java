package com.hunterz103.rsbot.scripts.limestoneBuyer.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.limestoneBuyer.Area;
import com.hunterz103.rsbot.scripts.limestoneBuyer.LimestoneBuyer;
import com.hunterz103.rsbot.util.Pathing;
import com.hunterz103.rsbot.util.Util;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/1/14.
 */
public class WalkToBank extends Task {

    private final Util util = new Util(ctx);
    private final Pathing pathing = new Pathing(ctx);
    private final TilePath PATH_TO_GATE = ctx.movement.newTilePath(new Tile(3486, 3287, 0), new Tile(3486, 3279, 0), new Tile(3488, 3268, 0), new Tile(3486, 3250, 0), new Tile(3485, 3245, 0));
    private final TilePath PATH_TO_BANK = ctx.movement.newTilePath(new Tile(3489, 3226, 0), new Tile(3496, 3212, 0));

    public WalkToBank(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return !LimestoneBuyer.getInstance().needToSwap() && (ctx.backpack.select().count() == 28 && !Area.BANK.containsPlayer(ctx));
    }

    @Override
    public void execute() {
        final GameObject door = ctx.objects.select().at(new Tile(3488, 3294, 0)).id(1530).poll();
        final GameObject fence = ctx.objects.select().at(new Tile(3485, 3244, 0)).id(17757).poll();

        if (Area.STORE_INSIDE.containsPlayer(ctx)){

            if (door.isValid()) {
                door.interact("Open");
            }

            org.powerbot.script.util.Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    return !door.isValid();
                }
            });
        }

        if ((!door.isValid() || !Area.STORE_INSIDE.containsPlayer(ctx)) && ctx.players.local().getLocation().getY() > 3245 && fence.getLocation().distanceTo(ctx.players.local()) > 5) {
            System.out.println("Traversing path to gate from store side");
            pathing.walkPath(PATH_TO_GATE, 2, 2, 3);
        }

        if (fence.isOnScreen() && ctx.players.local().getLocation().getY() >= 3244) {
            System.out.println("Attempting to jump fence");
            if (fence.interact("Jump"))
                org.powerbot.script.util.Condition.wait(new Callable() {
                    @Override
                    public Object call() throws Exception {
                        return Area.GATE_BURGH_SIDE.containsPlayer(ctx);
                    }
                });
        } else {
            ctx.camera.turnTo(fence);
        }

        if (ctx.players.local().getLocation().getY() < 3244) {
            System.out.println("Traversing path to bank");
            pathing.walkPath(PATH_TO_BANK, 2, 2, 3);
        }


    }
}
