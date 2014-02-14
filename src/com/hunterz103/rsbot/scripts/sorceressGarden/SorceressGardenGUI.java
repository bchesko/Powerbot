/*
 * Created by JFormDesigner on Mon Feb 03 12:06:19 EST 2014
 */

package com.hunterz103.rsbot.scripts.sorceressGarden;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author unknown
 */
public class SorceressGardenGUI extends JFrame {

    ButtonGroup buttonGroup1 = new ButtonGroup();
    ButtonGroup buttonGroup2 = new ButtonGroup();

    public SorceressGardenGUI() {
        initComponents();
    }

    private void startButtonActionPerformed(ActionEvent e) {

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Hunter 103
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        winterButton = new JRadioButton();
        springButton = new JRadioButton();
        autumnButton = new JRadioButton();
        summerButton = new JRadioButton();
        herbsButton = new JRadioButton();
        sqirksButton = new JRadioButton();
        buttonBar = new JPanel();
        vSpacer1 = new JPanel(null);
        startButton = new JButton();

        //======== this ========
        setTitle("Sorceress's Garden by Hunterz103");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {

                //---- winterButton ----
                winterButton.setText("Winter");
                winterButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
                winterButton.setSelected(true);

                //---- springButton ----
                springButton.setText("Spring");
                springButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

                //---- autumnButton ----
                autumnButton.setText("Autumn");
                autumnButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

                //---- summerButton ----
                summerButton.setText("Summer");
                summerButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

                //---- herbsButton ----
                herbsButton.setText("Herbs");
                herbsButton.setFont(new Font("Tahoma", Font.PLAIN, 15));

                //---- sqirksButton ----
                sqirksButton.setText("Sq'irks");
                sqirksButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
                sqirksButton.setSelected(true);

                GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
                contentPanel.setLayout(contentPanelLayout);
                contentPanelLayout.setHorizontalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(contentPanelLayout.createParallelGroup()
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addComponent(winterButton)
                                                .addContainerGap(60, Short.MAX_VALUE))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addGroup(contentPanelLayout.createParallelGroup()
                                                        .addComponent(springButton)
                                                        .addComponent(autumnButton)
                                                        .addComponent(summerButton)
                                                        .addComponent(sqirksButton, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(herbsButton))
                                                .addGap(0, 14, Short.MAX_VALUE))))
                );
                contentPanelLayout.setVerticalGroup(
                    contentPanelLayout.createParallelGroup()
                        .addGroup(contentPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(winterButton)
                                .addGap(5, 5, 5)
                                .addComponent(springButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(autumnButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(summerButton)
                                .addGap(18, 18, 18)
                                .addComponent(sqirksButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(herbsButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};
                buttonBar.add(vSpacer1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- startButton ----
                startButton.setText("Start");
                startButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        startButtonActionPerformed(e);
                    }
                });
                buttonBar.add(startButton, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());

        //---- buttonGroup1 ----
        buttonGroup1.add(winterButton);
        buttonGroup1.add(springButton);
        buttonGroup1.add(autumnButton);
        buttonGroup1.add(summerButton);

        //---- buttonGroup2 ----
        buttonGroup2.add(herbsButton);
        buttonGroup2.add(sqirksButton);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Hunter 103
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JRadioButton winterButton;
    private JRadioButton springButton;
    private JRadioButton autumnButton;
    private JRadioButton summerButton;
    private JRadioButton herbsButton;
    private JRadioButton sqirksButton;
    private JPanel buttonBar;
    private JPanel vSpacer1;
    private JButton startButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
