package com.hunterz103.rsbot.scripts.dragonScales;

import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.dragonScales.tasks.*;
import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import com.hunterz103.rsbot.util.inventory.InventoryEvent;
import com.hunterz103.rsbot.util.inventory.InventoryListener;
import com.hunterz103.rsbot.util.inventory.InventoryWatcher;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.wrappers.Item;
import org.powerbot.script.wrappers.Npc;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Manifest(authors = "Hunterz103", name = "Blue Dragon Scale Picker v0.1", description = "Picks up blue dragon scales in the Taverley dungeon")
/**
 * Created by Brian on 2/5/14.
 */
public class BlueDragonScalePicker extends TaskScript implements PaintListener, InventoryListener {

    BufferedImage mX, mY, cX, cY; //Mouse X, Mouse Y, Click X, Click Y
    Util util = new Util(ctx);
    public static int scalePrice = 0;
    int tabPrice = 0;
    public int scales = 0;
    public int tabsUsed = 0;
    InventoryWatcher watcher = new InventoryWatcher(ctx);

    @Override
    public void start() {
        watcher.addListener(this);
        watcher.start();

        tasks = new ArrayList<>();
        tasks.add(new Bank(this));
        tasks.add(new PickupScales(this));
        tasks.add(new WalkToBank(this));
        tasks.add(new WalkToDungeon(this));
        tasks.add(new TeleportToBank(this));
        tasks.add((ctx.skills.getLevel(Skills.AGILITY) >= 70) ? new GoThroughPipe(this) : new WalkAroundIntoDragons(this));

        log("Based on your agility level, we will " + ((ctx.skills.getLevel(Skills.AGILITY) >= 70) ? "use the pipe shortcut." : "run around and use the dusty key."));

        scalePrice = GeItem.getPrice(243);
        tabPrice = GeItem.getPrice(8009);

        log("G.E. price of scales: " + scalePrice + " each.");

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
    public void stop(){
        watcher.stop();
        /*
        final BlueDragonScalePicker bdsp = new BlueDragonScalePicker();
        bdsp.start();
        new LoopTask(ctx){
            @Override
            public int loop() {
                return bdsp.poll();
            }
        }.start();
        */
    }

    @Override
    public void repaint(Graphics graphics) {
        util.drawStrings(
                graphics,
                "Runtime: " + String.format("%d hours, %d min, %d sec",
                    TimeUnit.MILLISECONDS.toHours(getRuntime()),
                    TimeUnit.MILLISECONDS.toMinutes(getRuntime()) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(getRuntime()) % 60),
                "Scales: " + scales + " (" + Calculations.toHour(getRuntime(), scales) + ")",
                "GP made: " + (scalePrice * scales - tabPrice * tabsUsed) + " (" + Calculations.toHour(getRuntime(), (scalePrice * scales - tabPrice * tabsUsed)) + ")"
        );

        util.drawMouse(graphics, mX, mY, cX, cY);
    }

    public static int getNetProfit(int scales){
        return scales * scalePrice;
    }

    public boolean shouldTeleport(){
        return  (!Place.FALADOR.area.contains(ctx.players.local()) && ctx.backpack.select().count() == 28) ||
                (beingAttacked() && (Place.DRAGONS.area.containsPlayer(ctx) ? true : ctx.combatBar.getMaximumHealth() / 2 > ctx.combatBar.getHealth()));
    }

    private boolean beingAttacked() {
        return !ctx.npcs.select(interactFilter).isEmpty() && ctx.players.local().isInCombat();
    }

    private Filter interactFilter = new Filter<Npc>(){
        @Override
        public boolean accept(Npc npc) {
            return npc.getInteracting().equals(ctx.players.local());
        }
    };

    @Override
    public void onChange(InventoryEvent event) {
        for (Item item : event.getChange()) {
            switch (item.getId()) {
                case 243:
                    scales += item.getStackSize();
                    break;
                case 8009:
                    tabsUsed -= item.getStackSize();
                    break;
            }
        }
    }

    /*
    class BlueDragonScalesGui extends JPanel {

        private JFrame frame;
        private JCheckBox useShortcutBox;
        private JButton startButton;

        BlueDragonScalesGui(){
            init();
        }

        public void init(){
            frame = new JFrame("Hunterz103's Scales");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            useShortcutBox = new JCheckBox("Use level 70 shortcut");

            startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    startButtonAction();
                }
            });

            this.add(useShortcutBox);
            this.add(startButton);

            setSize(300, 500);
            frame.setContentPane(this);
            frame.pack();
            frame.setVisible(true);
        }

        private void startButtonAction(){
            mayBegin = true;
            frame.dispose();
        }

    }
    */

}
