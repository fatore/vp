/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NormalizationParamView.java
 *
 * Created on 20/06/2009, 11:22:01
 */
package datamining.weighing;

import java.io.IOException;
import javax.swing.table.DefaultTableModel;
import vispipelinebasics.interfaces.AbstractParametersView;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class AttributeWeighingParamView extends AbstractParametersView {

    /** Creates new form NormalizationParamView */
    public AttributeWeighingParamView(AttributeWeighingComp comp) {
        initComponents();
        this.attributeModel = (DefaultTableModel) this.features_jTable.getModel();

        this.comp = comp;
        reset();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selectedFeaturesjScrollPane = new javax.swing.JScrollPane();
        features_jTable = new javax.swing.JTable();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Attribute Weighing Parameters"));
        setLayout(new java.awt.GridBagLayout());

        selectedFeaturesjScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected Features"));
        selectedFeaturesjScrollPane.setPreferredSize(new java.awt.Dimension(250, 200));

        features_jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Attribute", "Weight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        features_jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                features_jTableMouseClicked(evt);
            }
        });
        selectedFeaturesjScrollPane.setViewportView(features_jTable);

        add(selectedFeaturesjScrollPane, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void features_jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_features_jTableMouseClicked
}//GEN-LAST:event_features_jTableMouseClicked

    @Override
    public void reset() {
        //normalizationComboBox.setSelectedItem(comp.getNormalizationType());
        while (this.attributeModel.getRowCount() > 0) {
            this.attributeModel.removeRow(this.attributeModel.getRowCount() - 1);
        }

        /*
        FIXME - XXX
        for (int i = 0; i < this.comp.getInput().getDimensions(); i++) {
        String[] row = new String[2];
        row[0] = i + " - " +  this.comp.getInput().getAttributes().get(i);
        row[1] = "1.0";
        this.attributeModel.addRow(row);
        }
        */
    }

    @Override
    public void finished() throws IOException {
        // FIXME - XXX
        /*//comp.setNormalizationType((NormalizationType) normalizationComboBox.getSelectedItem());
        ArrayList<Float> weights = new ArrayList<Float>();
        for (int i = 0; i < this.comp.getInput().getAttributes().size(); i++){
        weights.add( Float.parseFloat( (String)this.attributeModel.getValueAt(i, 1) ) );
        }
        this.comp.setWeights(weights);*/
    }

    public DefaultTableModel getAttributeModel() {
        return attributeModel;
    }
    private AttributeWeighingComp comp;
    private DefaultTableModel attributeModel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable features_jTable;
    private javax.swing.JScrollPane selectedFeaturesjScrollPane;
    // End of variables declaration//GEN-END:variables
}
