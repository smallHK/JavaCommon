package com.hk.apache.math;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class Linear {

    private void testMatrix() {
        double[][] matrixData = {{1d, 2d, 3d}, {2d, 5d, 3d}};
        RealMatrix m = MatrixUtils.createRealMatrix(matrixData);

        double [][] matrixData2 = {{1d, 2d}, {2d, 5d}, {1d, 7d}};
        RealMatrix n = new Array2DRowRealMatrix(matrixData2);


        //矩阵相乘
        RealMatrix p = m.multiply(n);
        System.out.println(p.getRowDimension());
        System.out.println(p.getColumnDimension());

        System.out.println(p);

        RealMatrix pInverse = new LUDecomposition(p).getSolver().getInverse();

    }

    public static void main(String[] args) {
        var test = new Linear();
        test.testMatrix();
    }
}
