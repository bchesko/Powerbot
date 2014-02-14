package com.hunterz103.rsbot.scripts.vialFiller;

import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.scripts.vialFiller.tasks.Bank;
import com.hunterz103.rsbot.scripts.vialFiller.tasks.FillVials;
import com.hunterz103.rsbot.scripts.vialFiller.tasks.WalkToBank;
import com.hunterz103.rsbot.scripts.vialFiller.tasks.WalkToFountain;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import com.hunterz103.rsbot.util.inventory.InventoryEvent;
import com.hunterz103.rsbot.util.inventory.InventoryListener;
import com.hunterz103.rsbot.util.inventory.InventoryWatcher;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.wrappers.Item;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Manifest(authors = "Hunterz103", description = "Fills vials at GE", name = "HVialFiller")
/**
 * Created by Brian on 2/10/14.
 */
public class VialFiller extends TaskScript implements PaintListener, InventoryListener {

    InventoryWatcher watcher = new InventoryWatcher(ctx);
    BufferedImage mX, mY, cX, cY; //Mouse X, Mouse Y, Click X, Click Y
    Util util = new Util(ctx);
    public int vialsFilled;
    int filledPrice;
    int emptyPrice;

    @Override
    public void start() {
        tasks.add(new Bank(this));
        tasks.add(new FillVials(this));
        tasks.add(new WalkToFountain(this));
        tasks.add(new WalkToBank(this));

        watcher.addListener(this);
        watcher.start();

        filledPrice = GeItem.getPrice(227);
        emptyPrice = GeItem.getPrice(229);

        getStorageDirectory().mkdirs();

        File mXFile = new File(getStorageDirectory() + "/mX.png");
        File cXFile = new File(getStorageDirectory() + "/cX.png");
        File mYFile = new File(getStorageDirectory() + "/mY.png");
        File cYFile = new File(getStorageDirectory() + "/cY.png");

        if (!mXFile.exists()) download("http://i.imgur.com/SfFWM.png", "mX.png");
        if (!cXFile.exists()) download("http://i.imgur.com/PX1IA.png", "cX.png");
        if (!mYFile.exists()) download("http://i.imgur.com/XfZTz.png", "mY.png");
        if (!cYFile.exists()) download("http://i.imgur.com/1fGZc.png", "cY.png");

        try {
            mX = ImageIO.read(mXFile);
            cX = ImageIO.read(cXFile);
            mY = ImageIO.read(mYFile);
            cY = ImageIO.read(cYFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void repaint(Graphics graphics) {
        util.drawMouse(graphics, mX, mY, cX, cY);
        util.drawStrings(graphics,
                "Runtime: " + String.format("%d hours, %d min, %d sec",
                    TimeUnit.MILLISECONDS.toHours(getRuntime()),
                    TimeUnit.MILLISECONDS.toMinutes(getRuntime()) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(getRuntime()) % 60),
                "Vials filled: " + vialsFilled + " (" + Calculations.toHour(getRuntime(), vialsFilled) + ")",
                "GP made: " + gpMade() + " (" + Calculations.toHour(getRuntime(), gpMade()) + ")");
    }

    @Override
    public void stop(){
        /*
        final VialFiller vf = new VialFiller();
        vf.start();
        new LoopTask(vf.ctx) {
            @Override
            public int loop() {
                return vf.poll();
            }
        }.start();
        */
    }

    public int gpMade(){
        return filledPrice * vialsFilled - emptyPrice * vialsFilled;
    }

    @Override
    public void onChange(InventoryEvent event) {
        for (Item item : event.getChange()) {
            switch (item.getId()) {
                case 227:
                    vialsFilled += item.getStackSize();
                    break;
            }
        }
    }
}
