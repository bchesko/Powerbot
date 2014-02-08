/*
 * Created by JFormDesigner on Fri Feb 07 16:27:12 EST 2014
 */

package com.hunterz103.rsbot.scripts.framework;

import java.awt.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Hunter 103
 */
public class GUILogger extends JFrame {
    public GUILogger() {
        initComponents();
    }


    public void addToScriptLog(String str){
        panel1ListModel.addElement(str);
    }

    public void addToDevLog(String str){
        panel2ListModel.addElement(str);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Hunter 103
        scrollPane1 = new JScrollPane();
        textPane1 = new JList();
        scrollPane2 = new JScrollPane();
        textPane2 = new JList();
        scriptLogLabel = new JLabel();
        label1 = new JLabel();
        panel1ListModel = new DefaultListModel<>();
        panel2ListModel = new DefaultListModel<>();

        //======== this ========
        setTitle("Hunterz103 Script Logger");
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {

            //---- textPane1 ----
            scrollPane1.setViewportView(textPane1);
            textPane1.setModel(panel1ListModel);
            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        }

        //======== scrollPane2 ========
        {

            //---- textPane2 ----
            scrollPane2.setViewportView(textPane2);
            textPane2.setModel(panel2ListModel);
            scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        }

        //---- scriptLogLabel ----
        scriptLogLabel.setText("Script Log");
        scriptLogLabel.setFont(new Font("Levenim MT", Font.PLAIN, 16));
        scriptLogLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //---- label1 ----
        label1.setText("Developer Log");
        label1.setFont(new Font("Levenim MT", Font.PLAIN, 16));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 177, Short.MAX_VALUE)
                                        .addComponent(label1)
                                        .addGap(0, 182, Short.MAX_VALUE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                        .addGap(0, 197, Short.MAX_VALUE)
                                        .addComponent(scriptLogLabel)
                                        .addGap(0, 200, Short.MAX_VALUE)))
                        .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scriptLogLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label1)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane2, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                        .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Hunter 103
    private JScrollPane scrollPane1;
    private JList textPane1;
    private JScrollPane scrollPane2;
    private JList textPane2;
    private JLabel scriptLogLabel;
    private JLabel label1;
    private DefaultListModel<String> panel1ListModel;
    private DefaultListModel<String> panel2ListModel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
