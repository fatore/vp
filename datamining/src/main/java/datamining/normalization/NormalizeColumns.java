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

package datamining.normalization;

import matrix.AbstractMatrix;
import matrix.AbstractVector;
import matrix.dense.DenseMatrix;
import matrix.sparse.SparseMatrix;
import matrix.sparse.SparseVector;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public class NormalizeColumns extends AbstractNormalization {

    @Override
    public AbstractMatrix execute(AbstractMatrix matrix) {
        assert (matrix.getRowCount() > 0) : "More than zero vectors must be used!";

        float[][] points = null;

        if (matrix instanceof DenseMatrix) {
            points = new float[matrix.getRowCount()][];

            for (int i = 0; i < points.length; i++) {
                points[i] = matrix.getRow(i).getValues();
                matrix.getRow(i).shouldUpdateNorm();
            }
        } else {
            points = matrix.toMatrix();
        }

        for (int j = 0; j < matrix.getDimensions(); j++) {
            float max = Float.NEGATIVE_INFINITY;
            float min = Float.POSITIVE_INFINITY;

            for (int i = 0; i < matrix.getRowCount(); i++) {
                if (max < points[i][j]) {
                    max = points[i][j];
                }

                if (min > points[i][j]) {
                    min = points[i][j];
                }
            }

            for (int i = 0; i < matrix.getRowCount(); i++) {
                if (max > min) {
                    points[i][j] = (points[i][j] - min) / (max - min);
                } else {
                    points[i][j] = 0.0f;
                }
            }
        }

        if (matrix instanceof SparseMatrix) {
            AbstractMatrix colmatrix = new SparseMatrix();
            colmatrix.setAttributes(matrix.getAttributes());

            for (int i = 0; i < matrix.getRowCount(); i++) {
                AbstractVector oldv = matrix.getRow(i);
                colmatrix.addRow(new SparseVector(points[i], oldv.getId(), oldv.getKlass()));
            }

            return colmatrix;
        } else {
            return matrix;
        }
    }

}
