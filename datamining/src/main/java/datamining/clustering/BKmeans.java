/* ***** BEGIN LICENSE BLOCK *****
 *
 * Copyright (c) 2005-2007 Universidade de Sao Paulo, Sao Carlos/SP, Brazil.
 * All Rights Reserved.
 *
 * This file is part of Projection Explorer (PEx).
 *
 * How to cite this work:
 * 
@inproceedings{paulovich2007pex,
author = {Fernando V. Paulovich and Maria Cristina F. Oliveira and Rosane 
Minghim},
title = {The Projection Explorer: A Flexible Tool for Projection-based 
Multidimensional Visualization},
booktitle = {SIBGRAPI '07: Proceedings of the XX Brazilian Symposium on 
Computer Graphics and Image Processing (SIBGRAPI 2007)},
year = {2007},
isbn = {0-7695-2996-8},
pages = {27--34},
doi = {http://dx.doi.org/10.1109/SIBGRAPI.2007.39},
publisher = {IEEE Computer Society},
address = {Washington, DC, USA},
}
 * 
 * PEx is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free 
 * Software Foundation, either version 3 of the License, or (at your option) 
 * any later version.
 *
 * PEx is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details.
 *
 * This code was developed by members of Computer Graphics and Image
 * Processing Group (http://www.lcad.icmc.usp.br) at Instituto de Ciencias
 * Matematicas e de Computacao - ICMC - (http://www.icmc.usp.br) of 
 * Universidade de Sao Paulo, Sao Carlos/SP, Brazil. The initial developer 
 * of the original code is Fernando Vieira Paulovich <fpaulovich@gmail.com>.
 *
 * Contributor(s): Rosane Minghim <rminghim@icmc.usp.br>
 *
 * You should have received a copy of the GNU General Public License along 
 * with PEx. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */
package datamining.clustering;

import distance.DistanceMatrix;
import distance.dissimilarity.AbstractDissimilarity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.MatrixFactory;
import matrix.util.MatrixUtils;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class BKmeans extends Clustering {

    /** Creates a new instance of BKmeans
     * @param nrclusters 
     */
    public BKmeans(int nrclusters) {
        super(nrclusters);
    }

    @Override
    public ArrayList<ArrayList<Integer>> execute(AbstractDissimilarity diss, AbstractMatrix matrix) {
        try {
            long start = System.currentTimeMillis();
            this.diss = diss;
            this.clusters = new ArrayList<ArrayList<Integer>>();
            this.centroids = MatrixFactory.getInstance(matrix.getClass());

            //initially the gCluster has all elements
            ArrayList<Integer> gCluster = new ArrayList<Integer>();
            int size1 = matrix.getRowCount();
            for (int i = 0; i < size1; i++) {
                gCluster.add(i);
            }

            this.clusters.add(gCluster);

            //considering just one element as the centroid
            this.centroids.addRow((AbstractVector) matrix.getRow(0).clone());

            for (int j = 0; j < this.nrclusters - 1; j++) {
                //Search the cluster with the bigger number of elements
                gCluster = this.getClusterToSplit(this.clusters);

                //split the greatest cluster into two clusters
                if (gCluster.size() > 1) {
                    this.splitCluster(matrix, diss, gCluster);
                }
            }

            //removing possible empty clusters
            for (int i = this.clusters.size() - 1; i >= 0; i--) {
                if (this.clusters.get(i).size() <= 0) {
                    this.clusters.remove(i);

                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                            "The Bissection k-means algorithm is returning an " + 
                            "empty cluster. Number of requested clusters : {0}", this.nrclusters);
                }
            }

            int removed = 0;

            for (int i = this.clusters.size() - 1; i >= 0; i--) {
                if (this.clusters.get(i).isEmpty()) {
                    this.clusters.remove(i);
                    this.centroids.removeRow(i);
                    removed++;
                }
            }

            if (removed > 0) {
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING,
                        "The Bissection k-means algorithm is returning " + 
                        "empty clusters. Removed: {0}", removed);
            }

            long finish = System.currentTimeMillis();

            Logger.getLogger(this.getClass().getName()).log(Level.INFO,
                    "Bisecting K-Means time: {0}s", (finish - start) / 1000.0f);

            String tmp = "Clusters sizes: ";
            for (int i = 0; i < this.clusters.size(); i++) {
                tmp += this.clusters.get(i).size() + " ";
            }

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, tmp.trim());


        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(BKmeans.class.getName()).log(Level.SEVERE, null, ex);
        }

        return this.clusters;
    }

    @Override
    public ArrayList<ArrayList<Integer>> execute(DistanceMatrix dmat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public AbstractMatrix getCentroids() {
        return this.centroids;
    }

    public int[] getMedoids(AbstractMatrix matrix) {
        int[] m = new int[this.centroids.getRowCount()];

        for (int i = 0; i < m.length; i++) {
            int point = -1;
            float distance = Float.MAX_VALUE;

            for (int j = 0; j < this.clusters.get(i).size(); j++) {
                float distance2 = this.diss.calculate(this.centroids.getRow(i),
                        matrix.getRow(this.clusters.get(i).get(j)));

                if (distance > distance2) {
                    point = this.clusters.get(i).get(j);
                    distance = distance2;
                }
            }

            m[i] = point;
        }

        return m;
    }

    protected ArrayList<Integer> getClusterToSplit(ArrayList<ArrayList<Integer>> clusters) {
        ArrayList<Integer> gCluster = clusters.get(0);

        for (int i = 0; i < clusters.size(); i++) {
            if (clusters.get(i).size() > gCluster.size()) {
                gCluster = clusters.get(i);
            }
        }

        return gCluster;
    }

    protected void splitCluster(AbstractMatrix matrix, AbstractDissimilarity diss, ArrayList<Integer> gCluster) {
        try {
            this.centroids.removeRow(clusters.indexOf(gCluster));
            this.clusters.remove(gCluster);

            //getting the two pivots
            int[] pivots = this.getPivots(matrix, diss, gCluster);

            //Create two new clusters
            ArrayList<Integer> cluster_1 = new ArrayList<Integer>();
            cluster_1.add(pivots[0]);
            AbstractVector centroid_1 = (AbstractVector) matrix.getRow(pivots[0]).clone();

            ArrayList<Integer> cluster_2 = new ArrayList<Integer>();
            cluster_2.add(pivots[1]);
            AbstractVector centroid_2 = (AbstractVector) matrix.getRow(pivots[1]).clone();

            int iterations = 0;

            do {
                centroid_1 = this.calculateMean(matrix, cluster_1);
                centroid_2 = this.calculateMean(matrix, cluster_2);

                cluster_1.clear();
                cluster_2.clear();

                //For each cluster
                int size = gCluster.size();
                for (int i = 0; i < size; i++) {
                    float distCentr_1 = diss.calculate(matrix.getRow(gCluster.get(i)), centroid_1);
                    float distCentr_2 = diss.calculate(matrix.getRow(gCluster.get(i)), centroid_2);

                    if (distCentr_1 < distCentr_2) {
                        cluster_1.add(gCluster.get(i));
                    } else if (distCentr_2 < distCentr_1) {
                        cluster_2.add(gCluster.get(i));
                    } else {
                        if (cluster_1.size() > cluster_2.size()) {
                            cluster_2.add(gCluster.get(i));
                        } else {
                            cluster_1.add(gCluster.get(i));
                        }
                    }
                }

                if (cluster_1.size() < 1) {
                    cluster_1.add(cluster_2.get(0));
                    cluster_2.remove(cluster_2.get(0));
                } else if (cluster_2.size() < 1) {
                    cluster_2.add(cluster_1.get(0));
                    cluster_1.remove(cluster_1.get(0));
                }

            } while (++iterations < this.nrIterations);

            //Add the two new clusters
            this.clusters.add(cluster_1);
            this.clusters.add(cluster_2);

            //add the new centroids
            this.centroids.addRow(centroid_1);
            this.centroids.addRow(centroid_2);

        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(BKmeans.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected int[] getPivots(AbstractMatrix matrix, AbstractDissimilarity diss, ArrayList<Integer> gCluster) {
        int[] pivots = new int[2];

//        Random random = new Random(System.currentTimeMillis());
//        pivots[0] = (int) (random.nextFloat() * gCluster.size());
//        pivots[1] = (int) (random.nextFloat() * gCluster.size());

        ArrayList<Pivot> pivots_aux = new ArrayList<Pivot>();

        //choosing the first pivot
        AbstractVector mean = this.calculateMean(matrix, gCluster);

        float size = 1 + (gCluster.size() / 10);
        for (int i = 0; i < size; i++) {
            int el = (int) ((gCluster.size() / size) * i);
            int aux = gCluster.get(el);
            float distance = diss.calculate(mean, matrix.getRow(aux));
            pivots_aux.add(new Pivot(distance, aux));
        }

        Collections.sort(pivots_aux);

        pivots[0] = pivots_aux.get((int) (pivots_aux.size() * 0.75f)).id;

        //choosing the second pivot
        pivots_aux.clear();

        for (int i = 0; i < size; i++) {
            int el = (int) ((gCluster.size() / size) * i);
            int aux = gCluster.get(el);
            float distance = diss.calculate(matrix.getRow(pivots[0]), matrix.getRow(aux));
            pivots_aux.add(new Pivot(distance, aux));
        }

        Collections.sort(pivots_aux);

        pivots[1] = pivots_aux.get((int) (pivots_aux.size() * 0.75f)).id;

        return pivots;
    }
    
//    protected int[] getPivots(AbstractMatrix matrix, AbstractDissimilarity diss, ArrayList<Integer> gCluster) throws IOException {
//        int[] pivots = new int[2];
//
//        //choosing the first pivot
//        Random random = new Random(System.currentTimeMillis());
//        AbstractVector fpivot = matrix.getRow(gCluster.get(random.nextInt(gCluster.size())));
//
//        int pivot = 0;
//        float maxdist = Float.NEGATIVE_INFINITY;
//
//        for (int i = 0; i < gCluster.size(); i++) {
//            float dist = diss.calculate(fpivot, matrix.getRow(gCluster.get(i)));
//
//            if (dist > maxdist) {
//                maxdist = dist;
//                pivot = gCluster.get(i);
//            }
//        }
//
//        pivots[0] = pivot;
//
//        //choosing the second pivot
//        fpivot = matrix.getRow(pivot);
//        pivot = 0;
//        maxdist = Float.NEGATIVE_INFINITY;
//
//        for (int i = 0; i < gCluster.size(); i++) {
//            float dist = diss.calculate(fpivot, matrix.getRow(gCluster.get(i)));
//
//            if (dist > maxdist) {
//                maxdist = dist;
//                pivot = gCluster.get(i);
//            }
//        }
//
//        pivots[1] = pivot;
//
//        return pivots;
//    }

    protected AbstractVector calculateMean(AbstractMatrix matrix, ArrayList<Integer> cluster) {
        AbstractMatrix mean = MatrixFactory.getInstance(matrix.getClass());

        int size = cluster.size();
        for (int i = 0; i < size; i++) {
            mean.addRow(matrix.getRow(cluster.get(i)));
        }

        return MatrixUtils.mean(mean);
    }

    public class Pivot implements Comparable<Pivot> {

        public Pivot(float distance, int id) {
            this.distance = distance;
            this.id = id;
        }

        @Override
        public int compareTo(Pivot o) {
            if (Math.abs(this.distance - o.distance) < EPSILON) {
                return 0;
            } else if ((this.distance - o.distance) > EPSILON) {
                return 1;
            } else {
                return -1;
            }
        }

        public float distance;
        public int id;
    }

    protected ArrayList<ArrayList<Integer>> clusters;
    protected AbstractMatrix centroids;
    protected AbstractDissimilarity diss;
    protected static final float EPSILON = 0.00001f;
    protected int nrIterations = 15;
}
