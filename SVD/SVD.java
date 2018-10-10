package SVD;

import java.io.IOException;

import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;

import ExcelWrite.ExcelRead;
import GUIManager.mainScreen;
import charNetManager.CharNetList;

public class SVD {

	public SVD(int[][] charNet, int n) throws IOException{
		test(charNet, n);
	}
	 public void test(int[][]charNet, int n) throws IOException{
		 double[][]A = new double[charNet[0].length][charNet[0].length];
		 for(int i =0;i<charNet[0].length;i++){
			 for(int j =0;j<charNet[0].length;j++){
				 A[i][j]=(double)charNet[i][j];
			 }
		 }

			SimpleMatrix matA = new SimpleMatrix(A);
			SimpleSVD svd = matA.svd();
			//SimpleEVD evd = matA.eig();

			SimpleMatrix U = (SimpleMatrix) svd.getU();
			SimpleMatrix W = (SimpleMatrix) svd.getW();
			SimpleMatrix V = (SimpleMatrix) svd.getV();

			double[][] newU = new double[charNet[0].length][n];
			double[][] newW = new double[n][n];
			double[][] newV = new double[n][charNet[0].length];
			
			for(int i=0;i<charNet[0].length;i++){
				for(int j =0;j<n;j++){
					newU[i][j]=U.get(i,j);
				}
			}
			for(int i=0;i<n;i++){
				for(int j =0;j<n;j++){
					newW[i][j]=W.get(i,j);
				}
			}
			for(int i=0;i<n;i++){
				for(int j =0;j<charNet[0].length;j++){
					newV[i][j]=V.get(i,j);
				}
			}
			
			SimpleMatrix u=new SimpleMatrix(newU);
			SimpleMatrix w=new SimpleMatrix(newW);
			SimpleMatrix v=new SimpleMatrix(newV);

			double[][] result = multi(newU, newW);
			result = multi(result, newV);
			
			for(int i =0;i<result[0].length;i++){
				for(int j=0;j<result[0].length;j++){
					System.out.print(result[i][j]);
				}
				System.out.println();
			}

			
	 }
	 private double[][] multi(double[][] m1, double[][] m2)
	 {
	         double[][] result = new double[m1.length][m2[0].length];
	         for(int i=0;i<result.length;i++)
	         {
	             for(int j=0;j<result[0].length;j++)
	             {
	                 for(int k=0;k<m1[0].length;k++)
	                 {
	                     result[i][j]+=m1[i][k]*m2[k][j];
	                 }
	             }
	         }
	         return result;
	 }
}
