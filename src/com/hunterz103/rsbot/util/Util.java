package com.hunterz103.rsbot.util;

import org.powerbot.script.methods.MethodContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

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

    final static int STRINGS_START_X = 20;
    final static int STRINGS_END_Y = 300;
    final static int SHADOW_DIST = 1;

    public static void drawStrings(Graphics g, String... strs){
        String longestString = "";

        for (String str : strs) {
            if (g.getFontMetrics().stringWidth(str) > g.getFontMetrics().stringWidth(longestString)) longestString = str;
        }

        //This is what we call the "old fashioned temporary lazy paint"
        //I actually found this in an old file from ~2012, and had a better version on an SDN script which I can't find
        if (!g.getFont().equals(new Font("Tahoma", Font.PLAIN, 12))) {
            g.setFont(new Font("Tahoma", Font.PLAIN, 12));
        }

        g.setColor(Color.BLACK);
        g.fillRect( STRINGS_START_X - 6,
                    STRINGS_END_Y - ((strs.length) * 16),
                    g.getFontMetrics().stringWidth(longestString) + 12,
                    strs.length * 16 + 6);

        //Prince fan purple (RGB: 102, 0, 187)
        g.setColor(new Color(102, 0, 187));
        g.drawRect( STRINGS_START_X - 5,
                    STRINGS_END_Y - ((strs.length) * 16) + 1,
                    g.getFontMetrics().stringWidth(longestString) + 9,
                    strs.length * 16 + 3);


        g.setColor(Color.BLACK);
        for (int i = strs.length - 1; i >= 0; i--) {
            g.drawString(strs[i], STRINGS_START_X - SHADOW_DIST, STRINGS_END_Y - SHADOW_DIST - (i * 16));
        }

        g.setColor(Color.RED);
        for (int i = strs.length - 1; i >= 0; i--) {
            g.drawString(strs[i], STRINGS_START_X, STRINGS_END_Y - (i * 16));
        }
    }

    @Deprecated
    /**
     * @deprecated I'm going to replace the usage of Robot, but it was a quick fix from the loss of Environment#captureScreen
     */
    public void screenshot(String file){
        final File path = new File(file, System.currentTimeMillis() + ".png");
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
    }

}
