package com.hunterz103.rsbot.util;

import org.powerbot.script.methods.MethodContext;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Brian on 2/1/14.
 */
public class Util {

    private final MethodContext ctx;

    public Util(MethodContext ctx){
        this.ctx = ctx;
    }

    public void drawMouse(Graphics graphics, BufferedImage mX, BufferedImage mY, BufferedImage cX, BufferedImage cY){
        Graphics2D g = (Graphics2D) graphics;
        final long mpt = System.currentTimeMillis() - ctx.mouse.getPressTime();
        int x = (int) ctx.mouse.getLocation().getX();
        int y = (int) ctx.mouse.getLocation().getY();
        g.drawImage(mX, 0, y, null);
        g.drawImage(mY, x, 0, null);

        if (mpt < 1000) {
            int x1 = (int) ctx.mouse.getPressLocation().getX();
            int y1 = (int) ctx.mouse.getPressLocation().getY();
            g.drawImage(cX, 0, y1, null);
            g.drawImage(cY, x1, 0, null);
        }
    }

    public static void drawStrings(Graphics g, String... strs){
        //This is what we call the "old fashioned temporary lazy paint"
        g.setColor(Color.BLACK);
        for (int i = 0; i < strs.length; i++){
            g.drawString(strs[i], 19, 299 - (strs.length * 16) + (16 * i));
        }

        g.setColor(Color.RED);
        for (int i = 0; i < strs.length; i++){
            g.drawString(strs[i], 20, 300 - (strs.length * 16) + (16 * i));
        }
    }

}
