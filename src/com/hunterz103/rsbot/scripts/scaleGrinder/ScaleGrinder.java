package com.hunterz103.rsbot.scripts.scaleGrinder;

import com.hunterz103.rsbot.scripts.framework.TaskScript;
import com.hunterz103.rsbot.scripts.scaleGrinder.tasks.Bank;
import com.hunterz103.rsbot.scripts.scaleGrinder.tasks.Grind;
import org.powerbot.script.Manifest;

@Manifest(authors = "Hunterz103", name = "HScaleGrinder", description = "Grinds dragon scales. Not that hard.")
/**
 * Created by Brian on 2/10/14.
 * I didn't even try. I just needed scales to be ground.
 */
public class ScaleGrinder extends TaskScript {

    public final int SCALE_ID = 243;

    @Override
    public void start() {
        tasks.add(new Bank(this));
        tasks.add(new Grind(this));
    }

}
