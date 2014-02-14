package com.hunterz103.rsbot.scripts.clayHumidifier;

import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import com.hunterz103.rsbot.util.inventory.InventoryEvent;
import com.hunterz103.rsbot.util.inventory.InventoryListener;
import com.hunterz103.rsbot.util.inventory.InventoryWatcher;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.wrappers.Item;

import java.awt.*;

@Manifest(authors = "Hunterz103", name = "HClayHumidifier", description = "Humidifies clay next to any bank")
/**
 * Created by Brian on 2/13/14.
 */
public class ClayHumidifier extends TaskScript implements PaintListener, InventoryListener {

    InventoryWatcher watcher = new InventoryWatcher(ctx);
    public int humidified, softClayPrice, clayPrice = 0;
    public final int ID_CLAY = 434;
    public final int ID_SOFT_CLAY = 1761;
    public final int ID_ASTRAL = 9075;

    @Override
    public void start() {
        softClayPrice = GeItem.getPrice(ID_SOFT_CLAY);
        clayPrice = GeItem.getPrice(ID_CLAY);

        watcher.addListener(this);
        watcher.start();
    }

    @Override
    public void repaint(Graphics graphics) {
        Util.drawStrings(graphics,
                "Runtime: " + Util.longToTimeString(getRuntime()),
                "Humidified: " + humidified + " (" + Calculations.toHour(getRuntime(), humidified) + "/hr)",
                "Profit: " + profit() + " (" + Calculations.toHour(getRuntime(), profit()) + ")/hr");
    }

    @Override
    public void onChange(InventoryEvent event) {
        for (Item item : event.getChange()) if (item.getId() == ID_SOFT_CLAY) humidified++;
    }

    private int profit(){
        return humidified * (softClayPrice - clayPrice);
    }
}
