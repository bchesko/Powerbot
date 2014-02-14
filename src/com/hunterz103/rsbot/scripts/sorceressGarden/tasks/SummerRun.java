package com.hunterz103.rsbot.scripts.sorceressGarden.tasks;

import com.hunterz103.rsbot.scripts.framework.Task;
import com.hunterz103.rsbot.scripts.sorceressGarden.Garden;
import com.hunterz103.rsbot.scripts.sorceressGarden.SorceressGarden;
import com.hunterz103.rsbot.wrappers.Area;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.Menu;
import org.powerbot.script.methods.MethodContext;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Tile;

import java.util.concurrent.Callable;

/**
 * Created by Brian on 2/3/14.
 */
public class SummerRun extends Task {

    int herbID = 21669;
    int treeID = 21766;

    Area areaPre1 = new Area(new Tile(2910, 5481, 0), new Tile(2906, 5483, 0));
    Area treeArea = new Area(new Tile(2919, 5487, 0), new Tile(2912, 5495, 0));
    Area herbArea = new Area(new Tile(2925, 5484, 0), new Tile(2923, 5483, 0));
    Tile tilePost1 = new Tile(2906, 5489, 0);
    Tile tilePost2 = new Tile(2909, 5490, 0);
    Area areaPost2 = new Area(new Tile(2909, 5490, 0), new Tile(2909, 5494, 0));
    Tile tilePost3 = new Tile(2911, 5485, 0);
    Tile tilePost4 = new Tile(2921, 5485, 0);
    Tile tilePost5 = new Tile(2924, 5487, 0);
    Tile elemental1Loc = new Tile(2907, 5484, 0);
    Tile elemental2Loc = new Tile(2907, 5491, 0);
    Tile elemental3Loc = new Tile(2910, 5489, 0);
    Tile elemental4Loc = new Tile(2914, 5485, 0);
    Tile elemental4Loc2 = new Tile(2915, 5484, 0);
    Tile elemental5Loc = new Tile(2922, 5486, 0);
    Tile elemental5Loc2 = new Tile(2923, 5486, 0);
    Tile herbTile = new Tile(2923, 5484, 0);
    Tile treeTile = new Tile(2918, 5489, 0);

    public SummerRun(MethodContext ctx) {
        super(ctx);
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean activate() {
        return SorceressGarden.chosenGarden.equals(Garden.SUMMER) && SorceressGarden.chosenGarden.area.contains(ctx.players.local());
    }

    @Override
    public void execute() {
        while (ctx.players.local().isInMotion()) sleep(200);

        sleep(500);
        ctx.menu.close();

        //Orientation: East = 0, North = 90, West = 180, South = 270

        if (areaPre1.contains(ctx.players.local())) {
            hoverThenMove(tilePost1, elemental1Loc, 270);
        } else if (tilePost1.equals(ctx.players.local().getLocation())) {
            hoverThenMove(tilePost2, elemental2Loc, 90);
        } else if (areaPost2.contains(ctx.players.local().getLocation())) {
            hoverThenMove(tilePost3, elemental3Loc, 90);
        } else if (tilePost3.equals(ctx.players.local().getLocation())) {
            hoverThenMove(tilePost4, new Tile[]{elemental4Loc, elemental4Loc2});
        } else if (tilePost4.equals(ctx.players.local().getLocation())) {
            if (SorceressGarden.gettingHerbs) {
                hoverThenMove(herbTile, new Tile[]{elemental5Loc, new Tile(2922, 5487, 0), new Tile(2922, 5488, 0)}, 0);
            } else {
                hoverThenMove(tilePost5, elemental5Loc, 0);
            }
        } else if (herbArea.contains(ctx.players.local()) && SorceressGarden.gettingHerbs) {
            if (ctx.camera.getPitch() < 60) ctx.camera.setPitch(Random.nextInt(60, 90));
            ctx.objects.select().id(herbID).nearest().poll().interact("Pick");
            Condition.wait(new Callable() {
                @Override
                public Object call() throws Exception {
                    return Garden.HUB.area.contains(ctx.players.local());
                }
            }, Random.nextInt(400, 700), 5);
        } else if (tilePost5.equals(ctx.players.local().getLocation()) && !SorceressGarden.gettingHerbs) {
            hoverThenMove(treeTile, elemental5Loc2, 180);
            hoverThenMove(treeTile, elemental5Loc2, 270);
        } else if (treeArea.contains(ctx.players.local()) && !SorceressGarden.gettingHerbs) {
            ctx.objects.select().id(treeID).nearest().poll().interact("Pick-fruit");
            Condition.wait(new Callable(){
                @Override
                public Object call() throws Exception {
                    return Garden.HUB.area.contains(ctx.players.local());
                }
            }, Random.nextInt(400, 700), 5);
        }

    }

    private boolean hoverThenMove(Tile tileToMoveTo, Tile tileToCheck) {
        return hoverThenMove(tileToMoveTo, new Tile[]{tileToCheck}, -1);
    }

    private boolean hoverThenMove(Tile tileToMoveTo, Tile[] tilesToCheck) {
        return hoverThenMove(tileToMoveTo, tilesToCheck, -1);
    }

    private boolean hoverThenMove(Tile tileToMoveTo, Tile tileToCheck, int orientation) {
        return hoverThenMove(tileToMoveTo, new Tile[]{tileToCheck}, orientation);
    }

    private boolean hoverThenMove(Tile tileToMoveTo, Tile[] tilesToCheck, int orientation) {
        while (ctx.players.local().isInMotion()) sleep(100);
        if (!tileToMoveTo.getMatrix(ctx).isOnScreen()) {
            ctx.camera.turnTo(tileToMoveTo);
            sleep(200,400);
            if (!tileToMoveTo.getMatrix(ctx).isOnScreen()) ctx.camera.setPitch(Random.nextInt(30, 50));
        }

        if (!ctx.menu.isOpen() || noMenuOptions()) {
            ctx.mouse.click(tileToMoveTo.getMatrix(ctx).getInteractPoint(), false);
        }
        Npc npc = null;

        while (ctx.menu.isOpen() && !noMenuOptions()) {
            for (Tile t : tilesToCheck) {
                if (ctx.npcs.select().at(t).poll().isValid()) npc = ctx.npcs.select().at(t).poll();
            }

            if (npc != null && npc.isValid() && npc.isInMotion() && ((orientation != -1) ? npc.getOrientation() == orientation : true)) {
                sleep(400);  //TODO:Make this based on ping
                ctx.menu.click(new Filter<Menu.Entry>(){
                    @Override
                    public boolean accept(Menu.Entry entry) {
                        return entry.action.equalsIgnoreCase("walk here");
                    }
                });

                sleep(1000);
            }
        }

        while (ctx.players.local().isInMotion()) sleep(100);
        return ctx.players.local().getLocation().equals(tileToMoveTo);
    }

    private boolean noMenuOptions(){
        return (ctx.menu.indexOf(new Filter<Menu.Entry>(){
            @Override
            public boolean accept(Menu.Entry entry) {
                return entry.action.equals("Cancel");
            }
        }) == 0);
    }
}
