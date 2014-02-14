package com.hunterz103.rsbot.scripts.limestoneBuyer;

import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.scripts.limestoneBuyer.tasks.*;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.util.Timer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

@Manifest(name = "HLimestoneBuyer", authors = "Hunterz103", description = "Purchases limestone in Burgh de Rott")
/**
 * Created by Brian on 2/1/14.
 */
public class LimestoneBuyer extends TaskScript implements PaintListener {

    Util util = new Util(ctx);
    Bank doBank = new Bank(ctx);
    Buy doBuy = new Buy(ctx);
    WalkToBank toBank = new WalkToBank(ctx);
    WalkToStore toStore = new WalkToStore(ctx);
    SwapWorlds swapWorlds = new SwapWorlds(ctx);
    BufferedImage mX, mY, cX, cY; //Mouse X, Mouse Y, Click X, Click Y

    Timer runTime = new Timer(0);
    public static ArrayList<Integer> worldsFinished = new ArrayList<Integer>();
    int limestoneBought;
    int limestonePrice;

    @Override
    public void repaint(Graphics graphics) {
        Graphics2D g = (Graphics2D) graphics;

        limestoneBought = doBank.limestoneLast - doBank.limestoneOrig;
        String[] strings = {
                "Runtime: " + runTime.toElapsedString(),
                "Limestone bought: " + limestoneBought,
                "GP made: " + limestoneBought * (limestonePrice - 10),
                "GP/hour: " + Calculations.toHour(getRuntime(), limestoneBought * (limestonePrice - 10))
        };

        g.setFont(new Font("Dialog", Font.BOLD, 12));
        util.drawStrings(g, strings);
        util.drawMouse(g, mX, mY, cX, cY);
    }

    @Override
    public void start(){
        System.out.println("Starting LimestoneBuyer");
        limestonePrice = GeItem.getPrice(3211);
        System.out.println("Limestone price: " + limestonePrice);

        mX = downloadImage("http://i.imgur.com/SfFWM.png");
        cX = downloadImage("http://i.imgur.com/PX1IA.png");
        mY = downloadImage("http://i.imgur.com/XfZTz.png");
        cY = downloadImage("http://i.imgur.com/1fGZc.png");

        tasks = new ArrayList<>();
        tasks.add(doBank);
        tasks.add(doBuy);
        tasks.add(toBank);
        tasks.add(toStore);
        tasks.add(swapWorlds);
    }

    public final boolean needToSwap(){
        String worldString = ctx.widgets.get(550).getComponent(38).getText();
        String worldSub = worldString.replace("Friends List<br>RuneScape ", "");

        return ctx.game.isLoggedIn() && worldsFinished.contains((Integer.parseInt(worldSub)));
    }

    public static LimestoneBuyer getInstance(){
        return (LimestoneBuyer) instance;
    }

}
