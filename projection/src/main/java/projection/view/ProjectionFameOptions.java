/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProjectionFameOptions.java
 *
 * Created on 23/06/2009, 11:12:03
 */
package projection.view;

import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import visualizationbasics.color.ColorScalePanel;
import projection.model.ProjectionModel;
import visualizationbasics.color.ColorScaleFactory.ColorScaleType;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class ProjectionFameOptions extends javax.swing.JDialog {

    /** Creates new form ProjectionFameOptions */
    private ProjectionFameOptions(java.awt.Frame parent) {
        super(parent);
        initComponents();

        for (ColorScaleType cst : ColorScaleType.values()) {
            this.scaleComboBox.addItem(cst);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        renderQualitybuttonGroup = new javax.swing.ButtonGroup();
        adjustsPanel = new javax.swing.JPanel();
        paremetersPanel = new javax.swing.JPanel();
        backGroundButton = new javax.swing.JButton();
        qualityPanel = new javax.swing.JPanel();
        highRadioButton = new javax.swing.JRadioButton();
        poorRadioButton = new javax.swing.JRadioButton();
        alphaSlider = new javax.swing.JSlider();
        colorPanel = new javax.swing.JPanel();
        scaleRangePanel = new javax.swing.JPanel();
        minSlider = new javax.swing.JSlider();
        maxSlider = new javax.swing.JSlider();
        coloScalePanel = new ColorScalePanel(null);
        reverseScaleCheckBox = new javax.swing.JCheckBox();
        colorChosePanel = new javax.swing.JPanel();
        scaleComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        buttonPanel = new javax.swing.JPanel();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Projection Options");
        setModal(true);

        adjustsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Adjustments "));
        adjustsPanel.setLayout(new java.awt.GridBagLayout());

        paremetersPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Graphics Parameters"));
        paremetersPanel.setLayout(new java.awt.GridBagLayout());

        backGroundButton.setText("BackGround Color");
        backGroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backGroundButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        paremetersPanel.add(backGroundButton, gridBagConstraints);

        qualityPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Render Quality"));
        qualityPanel.setLayout(new java.awt.GridBagLayout());

        renderQualitybuttonGroup.add(highRadioButton);
        highRadioButton.setSelected(true);
        highRadioButton.setText("High quality (slow)");
        highRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        highRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                highRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        qualityPanel.add(highRadioButton, gridBagConstraints);

        renderQualitybuttonGroup.add(poorRadioButton);
        poorRadioButton.setText("Poor quality (fast)");
        poorRadioButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        poorRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                poorRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        qualityPanel.add(poorRadioButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        paremetersPanel.add(qualityPanel, gridBagConstraints);

        alphaSlider.setPaintLabels(true);
        alphaSlider.setPaintTicks(true);
        alphaSlider.setSnapToTicks(true);
        alphaSlider.setBorder(javax.swing.BorderFactory.createTitledBorder("Alpha"));
        alphaSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                alphaSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        paremetersPanel.add(alphaSlider, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        adjustsPanel.add(paremetersPanel, gridBagConstraints);

        colorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Color Scale"));
        colorPanel.setLayout(new java.awt.BorderLayout(3, 3));

        scaleRangePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Scale Range"));
        scaleRangePanel.setLayout(new java.awt.GridBagLayout());

        minSlider.setMaximum(50);
        minSlider.setPaintLabels(true);
        minSlider.setPaintTicks(true);
        minSlider.setInverted(true);
        minSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                minSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        scaleRangePanel.add(minSlider, gridBagConstraints);

        maxSlider.setMaximum(50);
        maxSlider.setPaintLabels(true);
        maxSlider.setPaintTicks(true);
        maxSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                maxSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        scaleRangePanel.add(maxSlider, gridBagConstraints);

        coloScalePanel.setMinimumSize(new java.awt.Dimension(20, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        scaleRangePanel.add(coloScalePanel, gridBagConstraints);

        reverseScaleCheckBox.setText("Reverse Scale");
        reverseScaleCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reverseScaleCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        scaleRangePanel.add(reverseScaleCheckBox, gridBagConstraints);

        colorPanel.add(scaleRangePanel, java.awt.BorderLayout.SOUTH);

        colorChosePanel.setLayout(new java.awt.GridBagLayout());

        scaleComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                scaleComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorChosePanel.add(scaleComboBox, gridBagConstraints);

        jLabel1.setText("Scale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorChosePanel.add(jLabel1, gridBagConstraints);

        colorPanel.add(colorChosePanel, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        adjustsPanel.add(colorPanel, gridBagConstraints);

        getContentPane().add(adjustsPanel, java.awt.BorderLayout.CENTER);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backGroundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backGroundButtonActionPerformed
        if (viewer != null) {
            java.awt.Color color = javax.swing.JColorChooser.showDialog(this,
                    "Choose the Backgroud Color", java.awt.Color.BLACK);
            if (color != null) {
                viewer.setViewerBackground(color);
            }
        }
}//GEN-LAST:event_backGroundButtonActionPerformed

    private void highRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_highRadioButtonActionPerformed
        if (viewer != null) {
            viewer.setHighQualityRender(true);
        }
}//GEN-LAST:event_highRadioButtonActionPerformed

    private void poorRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_poorRadioButtonActionPerformed
        if (viewer != null) {
            viewer.setHighQualityRender(false);
        }
}//GEN-LAST:event_poorRadioButtonActionPerformed

    private void alphaSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_alphaSliderStateChanged
        if (viewer != null) {
            ProjectionModel model = (ProjectionModel) viewer.getModel();

            if (model != null) {
                javax.swing.JSlider slider = (javax.swing.JSlider) evt.getSource();
                model.setAlpha((float) Math.pow(slider.getValue() / 100.0f, 2));
                model.notifyObservers();
            }
        }
}//GEN-LAST:event_alphaSliderStateChanged

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.viewer = null;
        this.setVisible(false);
}//GEN-LAST:event_closeButtonActionPerformed

    private void minSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_minSliderStateChanged
        if (viewer != null) {
            try {
                if (viewer.getModel() != null) {
                    float min = ((float) minSlider.getValue() / (float) minSlider.getMaximum()) / 2.0f;
                    float max = ((float) maxSlider.getValue() / (float) maxSlider.getMaximum()) / 2.0f;
                    ((ProjectionModel) viewer.getModel()).getColorTable().getColorScale().setMinMax(0.5f - min, max + 0.5f);
                }

                if (viewer.getView() != null) {
                    viewer.getView().colorAs(viewer.getCurrentScalar());
                }

                coloScalePanel.repaint();

            } catch (IOException ex) {
                Logger.getLogger(ProjectionFameOptions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}//GEN-LAST:event_minSliderStateChanged

    private void maxSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxSliderStateChanged
        if (viewer != null) {
            try {
                if (viewer.getModel() != null) {
                    float min = ((float) minSlider.getValue() / (float) minSlider.getMaximum()) / 2.0f;
                    float max = ((float) maxSlider.getValue() / (float) maxSlider.getMaximum()) / 2.0f;
                    ((ProjectionModel) viewer.getModel()).getColorTable().getColorScale().setMinMax(0.5f - min, max + 0.5f);
                }

                if (viewer.getView() != null) {
                    viewer.getView().colorAs(viewer.getCurrentScalar());
                }

                coloScalePanel.repaint();
            } catch (IOException ex) {
                Logger.getLogger(ProjectionFameOptions.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}//GEN-LAST:event_maxSliderStateChanged

    private void reverseScaleCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reverseScaleCheckBoxActionPerformed
        if (viewer != null) {
            ProjectionModel model = (ProjectionModel) viewer.getModel();

            if (model != null) {
                model.getColorTable().getColorScale().setReverse(reverseScaleCheckBox.isSelected());
            }

            if (viewer.getView() != null) {
                viewer.getView().colorAs(viewer.getCurrentScalar());
            }

            coloScalePanel.repaint();
        }
    }//GEN-LAST:event_reverseScaleCheckBoxActionPerformed

    private void scaleComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_scaleComboBoxItemStateChanged
        if (viewer != null && evt.getStateChange() == ItemEvent.DESELECTED) {
            ColorScaleType type = (ColorScaleType) scaleComboBox.getSelectedItem();

            if (type != null) {
                try {
                    changeColorScale(type);
                } catch (IOException ex) {
                    Logger.getLogger(ProjectionFameOptions.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_scaleComboBoxItemStateChanged

    public static ProjectionFameOptions getInstance(javax.swing.JFrame parent) {
        return new ProjectionFameOptions(parent);
    }

    public void display(ProjectionFrame viewer) {
        if (viewer != null) {
            if (viewer.isHighQualityRender()) {
                highRadioButton.setSelected(true);
                poorRadioButton.setSelected(false);
            } else {
                highRadioButton.setSelected(false);
                poorRadioButton.setSelected(true);
            }

            ProjectionModel model = (ProjectionModel) viewer.getModel();

            if (model != null) {
                alphaSlider.setValue((int) (Math.sqrt(model.getAlpha()) * 100));
                scaleComboBox.setSelectedItem(model.getColorTable().getColorScaleType());
                ((ColorScalePanel) coloScalePanel).setColorTable(model.getColorTable());
                reverseScaleCheckBox.setSelected(model.getColorTable().getColorScale().isReverse());
                maxSlider.setValue((int) (model.getColorTable().getColorScale().getMax() * maxSlider.getMaximum()));
                minSlider.setValue((int) ((1.0f - model.getColorTable().getColorScale().getMin() * 2) * minSlider.getMaximum()));
            }
        }

        this.viewer = viewer;
        pack();
        setLocationRelativeTo(this.getParent());
        setVisible(true);
    }

    private void changeColorScale(ColorScaleType scaletype) throws IOException {
        if (viewer != null && viewer.getModel() != null) {
            ProjectionModel model = (ProjectionModel) viewer.getModel();

            model.getColorTable().setColorScaleType(scaletype);
            float min = ((float) minSlider.getValue() / (float) minSlider.getMaximum()) / 2.0f;
            float max = ((float) maxSlider.getValue() / (float) maxSlider.getMaximum()) / 2.0f;
            model.getColorTable().getColorScale().setMinMax(0.5f - min, max + 0.5f);
            model.getColorTable().getColorScale().setReverse(reverseScaleCheckBox.isSelected());

            if (viewer != null && viewer.getView() != null) {
                viewer.getView().colorAs(viewer.getCurrentScalar());
            }

            coloScalePanel.repaint();
        }

    }

    private ProjectionFrame viewer;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adjustsPanel;
    private javax.swing.JSlider alphaSlider;
    private javax.swing.JButton backGroundButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel coloScalePanel;
    private javax.swing.JPanel colorChosePanel;
    private javax.swing.JPanel colorPanel;
    private javax.swing.JRadioButton highRadioButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider maxSlider;
    private javax.swing.JSlider minSlider;
    private javax.swing.JPanel paremetersPanel;
    private javax.swing.JRadioButton poorRadioButton;
    private javax.swing.JPanel qualityPanel;
    private javax.swing.ButtonGroup renderQualitybuttonGroup;
    private javax.swing.JCheckBox reverseScaleCheckBox;
    private javax.swing.JComboBox scaleComboBox;
    private javax.swing.JPanel scaleRangePanel;
    // End of variables declaration//GEN-END:variables
}
