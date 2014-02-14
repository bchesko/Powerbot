package com.hunterz103.rsbot.scripts.limestoneBuyer.tasks;

import com.hunterz103.rsbot.scripts.dragonScales.BlueDragonScalePicker;
import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.limestoneBuyer.Area;
import com.hunterz103.rsbot.scripts.limestoneBuyer.LimestoneBuyer;
import com.hunterz103.rsbot.util.Pathing;
import com.hunterz103.rsbot.util.Util;
import org.powerbot.script.util.Condition;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Tile;
import org.powerbot.script.wrappers.TilePath;

import java.util.concurrent.Callable;


/**
 * Created by Brian on 2/1/14.
 */
public class WalkToStore extends Task {

    private final Util util = new Util(ctx);
    private final Pathing pathing = new Pathing(ctx);
    private final TilePath PATH_TO_GATE = ctx.movement.newTilePath(new Tile(3496, 3212, 0), new Tile(3489, 3226, 0), new Tile(3485, 3241, 0));
    private final TilePath PATH_TO_STORE = ctx.movement.newTilePath(new Tile(3486, 3246, 0), new Tile(3488, 3258, 0), new Tile(3487, 3265, 0) , new Tile(3487, 3283, 0), new Tile(3487, 3293, 0));

    public WalkToStore(BlueDragonScalePicker arg0) {
        super(arg0);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return !LimestoneBuyer.getInstance().needToSwap() && (!ctx.bank.isOpen() && ctx.backpack.select().count() < 28 && !Area.STORE_INSIDE.containsPlayer(ctx));
    }

    @Override
    public void execute() {
        if (ctx.players.local().getLocation().getY() <= 3243 && !Area.GATE_BURGH_SIDE.containsPlayer(ctx)) {
            //System.out.println("Marker 0");
            System.out.println("Traversing path to gate from bank side");
            PATH_TO_GATE.traverse();
            sleep(300);
        }

        final GameObject fence = ctx.objects.select().at(new Tile(3485, 3244, 0)).id(17757).poll();
        if (fence.isOnScreen() && ctx.players.local().getLocation().getY() <= 3243) {
            //System.out.println("Marker 1");
            System.out.println("Attempting to jump fence");
            if (fence.interact("Jump"))
                Condition.wait(new Callable(){
                    @Override
                    public Object call() throws Exception {
                        return Area.GATE_MORT_SIDE.containsPlayer(ctx);
                    }
                });
        }

        if (ctx.players.local().getLocation().getY() >= 3244 && Area.STORE_INSIDE.area.tiles.get(1).distanceTo(ctx.players.local()) > 5) {
            //System.out.println("Marker 2");
            pathing.walkPath(PATH_TO_STORE, 2, 2, 3);
            System.out.println("Traversing path to store");
        }
        final GameObject door = ctx.objects.select().at(new Tile(3488, 3294, 0)).id(1530).poll();
        if (!Area.STORE_INSIDE.containsPlayer(ctx)){
            //System.out.println("Marker 4");

            if (door.isValid() && ctx.players.local().getLocation().distanceTo(door.getLocation()) < 6) {
                //System.out.println("Marker 5");
                door.interact("Open", "Door");
                System.out.println("Opening door");
            }

            Condition.wait(new Callable(){
                @Override
                public Object call() throws Exception {
                    return !door.isValid();
                }
            });
        }
        /*
        if (!Area.STORE_INSIDE.containsPlayer(ctx) && !door.isValid() && Area.STORE_INSIDE.area.tiles.get(1).distanceTo(ctx.players.local()) < 5){
            //System.out.println("Marker 6");
            System.out.println("Traversing ")
            ctx.movement.findPath(Area.STORE_INSIDE.area.tiles.get(1)).traverse();
        }           */
    }
}
