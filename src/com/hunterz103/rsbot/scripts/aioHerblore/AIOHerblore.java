package com.hunterz103.rsbot.scripts.aioHerblore;

import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.scripts.aioHerblore.tasks.Bank;
import com.hunterz103.rsbot.scripts.aioHerblore.tasks.Clean;
import com.hunterz103.rsbot.scripts.aioHerblore.tasks.MakePotion;
import com.hunterz103.rsbot.util.Calculations;
import com.hunterz103.rsbot.util.Util;
import com.hunterz103.rsbot.util.inventory.InventoryEvent;
import com.hunterz103.rsbot.util.inventory.InventoryListener;
import com.hunterz103.rsbot.util.inventory.InventoryWatcher;
import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;
import org.powerbot.script.methods.Skills;
import org.powerbot.script.util.GeItem;
import org.powerbot.script.wrappers.Item;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Manifest(authors = "hunterz103", description = "Makes potions and cleans herbs", name = "HHerblore", version = 0.9)
/**
 * Created by Brian on 2/10/14.
 */
public class AIOHerblore extends TaskScript implements PaintListener, InventoryListener{

    Util util = new Util(ctx);
    BufferedImage mX, mY, cX, cY; //Mouse X, Mouse Y, Click X, Click Y
    private final int START_EXP = ctx.skills.getExperience(Skills.HERBLORE);
    private final int START_LEVEL = ctx.skills.getLevel(Skills.HERBLORE);
    private int productsDone;
    private int item1Price, item2Price, productPrice;
    private InventoryWatcher watcher = new InventoryWatcher(ctx);
    private GUI gui;

    public Product product = Product.GUAM_CLEAN;

    @Override
    public void start() {
        final AIOHerblore aioHerblore = this;

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                gui = new GUI(aioHerblore);
            }
        });

        watcher.addListener(this);
        watcher.start();

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
        if (gui != null) gui.frame.dispose();
        watcher.stop();
    }

    @Override
    public void repaint(Graphics graphics) {
        util.drawMouse(graphics, mX, mY, cX, cY);
        Util.drawStrings(graphics,
                "Runtime: " + Util.longToTimeString(getRuntime()),
                "Making: " + product.name(),
                "Made: " + productsDone + " (" + Calculations.toHour(getRuntime(), productsDone) + ")",
                "Exp gained: " + expGained() + " (" + Calculations.toHour(getRuntime(), expGained()) + ")",
                "Profit: " + profit() + " (" + Calculations.toHour(getRuntime(), profit()) + ")",
                "Level: " + ctx.skills.getLevel(Skills.HERBLORE)  + " (+" + levelsGained() + ")",
                "Time to Next Level: " + Util.longToTimeString(Calculations.timeUntilLevel(ctx, getRuntime(), expGained(), Skills.HERBLORE)));
    }

    public int expGained(){
        return ctx.skills.getExperience(Skills.HERBLORE) - START_EXP;
    }

    public int levelsGained(){
        return ctx.skills.getLevel(Skills.HERBLORE) - START_LEVEL;
    }

    public int profit(){
        if (item2Price == -1) {
            return (productsDone) * (productPrice - item1Price);
        } else {
            return (productsDone) * (productPrice - (item1Price + item2Price));
        }

    }

    @Override
    public void onChange(InventoryEvent event) {
        if (product == null) return;

        for (Item item : event.getChange()) {
            if (product.PRODUCT_ID == item.getId()) {
                productsDone += item.getStackSize();
            }
        }
    }

    class GUI extends JPanel {

        final AIOHerblore script;

        public GUI(AIOHerblore script){
            this.script = script;
            init();
        }

        private void init(){
            frame = new JFrame("Herblore4lyfe");
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            comboBox = new JComboBox<>(Product.values());
            add(comboBox);

            startButton = new JButton("Start");
            startButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    product = comboBox.getItemAt(comboBox.getSelectedIndex());

                    item1Price = GeItem.getPrice(product.ITEM_1_ID);
                    item2Price = (product.ITEM_2_ID != -1) ? GeItem.getPrice(product.ITEM_2_ID) : 0;
                    productPrice = GeItem.getPrice(product.PRODUCT_ID);

                    if (product.type == Product.Type.HERB) tasks.add(new Clean(script));
                    else tasks.add(new MakePotion(script));
                    tasks.add(new Bank(script));

                    frame.dispose();
                }
            });
            add(startButton);

            frame.add(this);
            frame.pack();
            frame.setVisible(true);
        }

        private JFrame frame;
        private JComboBox<Product> comboBox;
        private JButton startButton;

    }
}
