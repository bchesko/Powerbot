package com.hunterz103.rsbot.scripts.dragonScales;

import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.framework.TaskScript;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.wrappers.Npc;
import com.hunterz103.rsbot.scripts.dragonScales.tasks.*;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Manifest(authors = "Hunterz103", name = "Blue Dragon Scale Picker v0.1", description = "Picks up blue dragon scales in the Taverley dungeon")
/**
 * Created by Brian on 2/5/14.
 */
public class BlueDragonScalePicker extends TaskScript implements PaintListener {

    BufferedImage mX, mY, cX, cY; //Mouse X, Mouse Y, Click X, Click Y
    Util util = new Util(ctx);
    public static int scalePrice = 0;
    int tabPrice = 0;
    public static int scalesOrig = -1;
    public static int scales = -1;
    public static int tabsOrig = -1;
    public static int tabs = -1;
    public static boolean needToTeleport = false;
    Thread t = new Thread(new Runnable(){
        @Override
        public void run() {
            checkPlayerIsAttacked();
        }
    });;

    @Override
    public void start() {
        tasks = new ArrayList<>();
        tasks.add(new Bank(ctx));
        tasks.add(new PickupScales(ctx));
        tasks.add(new WalkToBank(ctx));
        tasks.add(new WalkToDungeon(ctx));
        tasks.add(new TeleportToBank(ctx));
        tasks.add(new GoThroughPipe(ctx));

        scalePrice = GeItem.getPrice(243);
        tabPrice = GeItem.getPrice(8009);

        mX = downloadImage("http://i.imgur.com/SfFWM.png");
        cX = downloadImage("http://i.imgur.com/PX1IA.png");
        mY = downloadImage("http://i.imgur.com/XfZTz.png");
        cY = downloadImage("http://i.imgur.com/1fGZc.png");

        tabsOrig = ctx.backpack.select().id(8009).poll().getStackSize();

        t.start();
    }

    private void checkPlayerIsAttacked(){
        while (!t.isInterrupted()) {
            for (Npc npc : ctx.npcs.select()) {
                if (npc.getInteracting().equals(ctx.players.local()) && ctx.players.local().isInCombat()) {
                    log("We're being attacked! Time to go!");
                    needToTeleport = true;
                }
            }

            if (needToTeleport && Place.FALADOR.area.contains(ctx.players.local())) {
                needToTeleport = false;
            }

            try {
                t.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void stop(){
        final File path = new File(getStorageDirectory().getPath(), System.currentTimeMillis() + ".png");
        log("Picture saved to " + path.toString());
        BufferedImage img = null;
        try {
            img = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        try {
            ImageIO.write(img, "png", path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        t.interrupt();
    }

    @Override
    public void repaint(Graphics graphics) {
        tabs = ctx.backpack.select().id(8009).poll().getStackSize();

        util.drawStrings(
                graphics,
                "Runtime: " + String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(getRuntime()),
                    TimeUnit.MILLISECONDS.toSeconds(getRuntime()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(getRuntime()))),
                "Scales: " + ((scales + ctx.backpack.select().id(243).count()) - scalesOrig) + " (" + Calculations.toHour(getRuntime(), ((scales + ctx.backpack.select().id(243).count()) - scalesOrig)) + ")",
                "GP made: " + (scalePrice * ((scales + ctx.backpack.select().id(243).count()) - scalesOrig) - (tabPrice * (tabs - tabsOrig)))+ " (" + Calculations.toHour(getRuntime(), (scalePrice * ((scales + ctx.backpack.select().id(243).count()) - scalesOrig) - (tabPrice * (tabs - tabsOrig)))) + ")"
        );

        util.drawMouse(graphics, mX, mY, cX, cY);
    }

    public static int getNetProfit(int scales){
        return scales * scalePrice;
    }


}
