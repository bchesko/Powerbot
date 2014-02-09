package com.hunterz103.rsbot.scripts.dragonScales;

import com.hunterz103.rsbot.scripts.dragonScales.enums.Place;
import com.hunterz103.rsbot.scripts.dragonScales.tasks.*;
import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.lang.Filter;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.wrappers.Npc;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    public int scalesOrig = -1;
    public int scales = -1;
    public int tabsOrig = -1;
    public int tabs = -1;

    @Override
    public void start() {
        tasks = new ArrayList<>();
        tasks.add(new Bank(this));
        tasks.add(new PickupScales(this));
        tasks.add(new WalkToBank(this));
        tasks.add(new WalkToDungeon(this));
        tasks.add(new TeleportToBank(this));
        tasks.add((ctx.skills.getLevel(Skills.AGILITY) >= 70) ? new GoThroughPipe(this) : new WalkAroundIntoDragons(this));

        log("Based on your agility level, we will " + ((ctx.skills.getLevel(Skills.AGILITY) >= 70) ? "use the pipe shortcut." : "run around and use the dusty key."));
        log(ctx.combatBar.getActions().length + " length combat bar");

        scalePrice = GeItem.getPrice(243);
        tabPrice = GeItem.getPrice(8009);

        mX = downloadImage("http://i.imgur.com/SfFWM.png");
        cX = downloadImage("http://i.imgur.com/PX1IA.png");
        mY = downloadImage("http://i.imgur.com/XfZTz.png");
        cY = downloadImage("http://i.imgur.com/1fGZc.png");

        tabsOrig = ctx.backpack.select().id(8009).poll().getStackSize();
    }

    @Override
    public void stop(){
        String path = getStorageDirectory().getPath();
        util.screenshot(path);
        log("Picture saved to " + path.toString());
    }

    @Override
    public void repaint(Graphics graphics) {
        tabs = ctx.backpack.select().id(8009).poll().getStackSize();

        util.drawStrings(
                graphics,
                "Runtime: " + String.format("%d hours, %d min, %d sec",
                    TimeUnit.MILLISECONDS.toHours(getRuntime()),
                    TimeUnit.MILLISECONDS.toMinutes(getRuntime()) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(getRuntime()) % 60),
                "Scales: " + ((scales + ctx.backpack.select().id(243).count()) - scalesOrig) + " (" + Calculations.toHour(getRuntime(), ((scales + ctx.backpack.select().id(243).count()) - scalesOrig)) + ")",
                "GP made: " + (scalePrice * ((scales + ctx.backpack.select().id(243).count()) - scalesOrig) - (tabPrice * (tabs - tabsOrig)))+ " (" + Calculations.toHour(getRuntime(), (scalePrice * ((scales + ctx.backpack.select().id(243).count()) - scalesOrig) - (tabPrice * (tabs - tabsOrig)))) + ")"
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
