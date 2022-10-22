package com.atria.software.atriasmartsystem;

import java.util.Random;

public class Methods {

    static double random(double d1 , double d2){
        Random r = new Random();
        double randomValue = d1 + (d2 - d1) * r.nextDouble();
        return randomValue;
    }

    static double randomGaussian(){
        Random r= new Random();
        return r.nextGaussian();
    }

    static double sigmoid(double x){
        return 1/(1+Math.exp(-x));
    }

    static double dsigmoid(double x){
        return x*(1-x);
    }


    static void arraytomat(Matrix output, double[] array){
        for(int i = 0;i<array.length;i++){
            output.matrix[i][0]=array[i];
        }
    }

    static double[] mattoarray(Matrix mat){

        double[] output = new double[mat.matrix.length];
        for(int i = 0;i<mat.matrix.length;i++){
            for(int j=0;j<mat.matrix[0].length;j++){
                output[i]=mat.matrix[i][j];

            }
        }

        return output;
    }
}
