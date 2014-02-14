package com.hunterz103.rsbot.scripts.framework;

import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.MethodContext;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Brian on 2/1/14.
 */
public abstract class TaskScript extends PollingScript {

    public MethodContext ctx;
    protected static TaskScript instance;
    public List<Task> tasks = new ArrayList<>();
    //private Task currentTask = null;
    private GUILogger guiLogger;
    private boolean guiVisible = false;
    private DefaultListModel<String> dlm = new DefaultListModel<>();
    private DefaultListModel<String> dlmDev = new DefaultListModel<>();

    public TaskScript(){
        this.instance = this;
        this.ctx = super.ctx;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                guiLogger = new GUILogger(dlm, dlmDev);
                guiLogger.setVisible(true);
                guiVisible = true;
            }
        });

        sortTasksByPriority();
    }

    @Override
    public abstract void start();

    private final void sortTasksByPriority() {
        if (tasks.size() == 0) return;

        Collections.sort(tasks);
    }

    public void log(String str) {
        dlm.addElement(runtimeToString() + " - " + str);
        for (int i = 0; i < dlm.size(); i++) System.out.println(dlm.getElementAt(i));
    }

    public void logDev(String str) {
        dlmDev.addElement(runtimeToString() + " - " + str);
    }

    protected String runtimeToString(){
        long runtime = getRuntime();
        long seconds = runtime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        return new StringBuilder().append(hours).append(':').append(minutes % 60).append(':').append(seconds % 60).toString();
    }

    @Override
    public int poll() {
        if (!guiVisible) return 30;
        int returnTime = 50;
        /*
        //Reenable this after adding overridability
        if (currentTask != null){
            if (!currentTask.isInProgress()) currentTask = null;
        }
        */

        if (tasks != null) {
            for (Task task : tasks){
                iteration:
                if (task.activate()) {
                    /*
                    if (currentTask != null && task.priority() < currentTask.priority()) {
                        //If we have a task and the task being checked is more important (remember, higher integer priority means lower actual priority)
                        logDev("Overriding task " + currentTask.toString() + " with " + task.toString());
                    } else if (currentTask == null) {
                        logDev("Starting task " + task.toString());
                    } else {
                        break iteration;
                    }
                    */
                    logDev("Starting task: " + task.getClass().getCanonicalName());
                    //currentTask = task;
                    task.execute();
                    returnTime = 200;
                }
            }
        }

        return returnTime;
    }

    public static TaskScript getInstance() {
        return instance;
    }

}
