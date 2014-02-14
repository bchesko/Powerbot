package com.hunterz103.rsbot.scripts.unicows;

import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.scripts.unicows.tasks.*;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import com.hunterz103.rsbot.util.inventory.InventoryEvent;
import com.hunterz103.rsbot.util.inventory.InventoryListener;
import com.hunterz103.rsbot.util.inventory.InventoryWatcher;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Manifest(authors = "Hunterz103", name = "HUnicowKiller", description = "Kills unicows using magic notepaper and other stuff")
/**
 * Created by Brian on 2/13/14.
 * TODO: Add proper familiar support (not just withdraw without summoning, choices, etc)
 * VERY W.I.P. - DO NOT JUDGE THIS, IT'S A QUICK MOCK-UP BECAUSE I NEEDED HORNS
 */
public class UnicowKiller extends TaskScript  implements PaintListener, InventoryListener {

    public InventoryWatcher watcher = new InventoryWatcher(ctx);
    public final int ID_HORN = 237;
    public final int ID_COWHIDE = 1739;
    public final int ID_UNICOW = 5603;
    public final int ID_NOTEPAPER = 30372;
    public final Tile TILE_ALTAR = new Tile(3019, 4410, 0);
    public List<Item> familiarInventoryCache = new ArrayList<>();
    private int hornPrice = 0;
    private int cowhidePrice = 0;
    private int hornsPickedUp;
    private int killed;

    @Override
    public void start() {
        tasks.add(new AttackUnicow(this));
        tasks.add(new PickupLoot(this));
        tasks.add(new SummonUnicow(this));
        tasks.add(new NoteHorns(this));
        tasks.add(new TakeFromFamiliar(this));

        watcher.addListener(this);
        watcher.start();
    }

    @Override
    public void onChange(InventoryEvent event) {
        for (Item item : event.getChange()) {
            if (item.getId() == ID_HORN) hornsPickedUp += item.getStackSize();
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        Util.drawStrings(graphics,
                "Runtime: " + Util.longToTimeString(getRuntime()),
                "Killed: " + killed + " (" + Calculations.toHour(getRuntime(), killed) + ")",
                "Horns " + (hornsPickedUp - killed) + " (" + Calculations.toHour(getRuntime(), hornsPickedUp - killed) + ")",
                "Profit: " + profit() + " (" + Calculations.toHour(getRuntime(), profit()) + ")");
    }

    private int profit(){
        return (hornPrice * (hornsPickedUp - killed)) - (cowhidePrice * killed);
    }

}

