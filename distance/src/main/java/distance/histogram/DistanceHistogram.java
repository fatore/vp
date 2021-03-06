/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DistanceHistogram.java
 *
 * Created on 21/07/2009, 21:09:12
 */
package distance.histogram;

import distance.util.DistanceConstants;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import distance.DistanceMatrix;
import vispipelinecommon.filefilter.PNGFilter;
import vispipelinecommon.util.PropertiesManager;
import vispipelinecommon.util.SaveDialog;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class DistanceHistogram extends javax.swing.JFrame {

    /** Creates new form DistanceHistogram */
    public DistanceHistogram() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        saveImageButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        saveImageButton.setText("Save Image");
        saveImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImageButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(saveImageButton);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(closeButton);

        getContentPane().add(buttonPanel, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveImageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImageButtonActionPerformed
        try {
            PropertiesManager spm = PropertiesManager.getInstance(DistanceConstants.PROPFILENAME);
            int result = SaveDialog.showSaveDialog(spm, new PNGFilter(), this, "image.png");

            if (result == JFileChooser.APPROVE_OPTION) {
                String filename = SaveDialog.getFilename();

                try {
                    BufferedImage image = freechart.createBufferedImage(panel.getWidth(), panel.getHeight());
                    ImageIO.write(image, "png", new File(filename));
                } catch (IOException ex) {
                    Logger.getLogger(DistanceHistogram.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(this, ex.getMessage(),
                            "Problems saving the file", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DistanceHistogram.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_saveImageButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        setVisible(false);
}//GEN-LAST:event_closeButtonActionPerformed

    public void display(DistanceMatrix dmat) {
        freechart = createChart(createDataset(dmat));
        freechart.removeLegend();

        panel = new ChartPanel(freechart);
        getContentPane().add(panel, BorderLayout.CENTER);

        setPreferredSize(new Dimension(500, 300));
        setSize(new Dimension(500, 300));
        setLocationRelativeTo(this.getParent());
        setVisible(true);
    }

    private IntervalXYDataset createDataset(DistanceMatrix dmat) {
        HistogramDataset histogramdataset = new HistogramDataset();
        int nrDistances = ((dmat.getElementCount() * dmat.getElementCount()) - dmat.getElementCount()) / 2;

        double[] ad = new double[nrDistances];
        int index = 0;

        for (int i = 0; i < dmat.getElementCount() - 1; i++) {
            for (int j = dmat.getElementCount() - 1; j > i; j--) {
                ad[index] = (dmat.getDistance(i, j) - dmat.getMinDistance()) /
                        (dmat.getMaxDistance() - dmat.getMinDistance());
                index++;
            }
        }

        histogramdataset.addSeries("", ad, 200, 0, 1);

        return histogramdataset;
    }

    private JFreeChart createChart(IntervalXYDataset intervalxydataset) {
        JFreeChart chart = ChartFactory.createHistogram("Distance Histogram",
                "Distances Values", "Occurences", intervalxydataset,
                PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.WHITE);

        XYPlot xyplot = (XYPlot) chart.getPlot();

        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setAutoRangeIncludesZero(false);

        xyplot.setDomainGridlinePaint(Color.BLACK);
        xyplot.setRangeGridlinePaint(Color.BLACK);

        xyplot.setOutlinePaint(Color.BLACK);
        xyplot.setOutlineStroke(new BasicStroke(1.0f));
        xyplot.setBackgroundPaint(Color.white);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);

        XYBarRenderer xybarrenderer = (XYBarRenderer) xyplot.getRenderer();
        xybarrenderer.setDrawBarOutline(false);

        return chart;
    }

    private JFreeChart freechart;
    private JPanel panel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton saveImageButton;
    // End of variables declaration//GEN-END:variables
}
