package com.atria.software.atriasmartsystem;

import static com.atria.software.momclock.Methods.dsigmoid;
import static com.atria.software.momclock.Methods.random;
import static com.atria.software.momclock.Methods.randomGaussian;
import static com.atria.software.momclock.Methods.sigmoid;

class Matrix{

    int rows;
    int cols;
    double[][] matrix;
    public Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j] = 0;
            }
        }

    }

    public void randomize(){

        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                this.matrix[i][j]= random(-1.1,1.1);
            }
        }
    }

    Matrix transpose(){
        Matrix transposed = new Matrix(this.cols,this.rows);

        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                transposed.matrix[j][i]=this.matrix[i][j];
            }
        }

        return transposed;
    }

    public void add(int value){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]+=value;
            }
        }
    }

    public void difference(int value){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]-=value;
            }
        }
    }

    public void divide(int value){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]/=value;
            }
        }
    }

    void mutate(float rate){

        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                if(random(0,1)<rate){
                    this.matrix[i][j] += randomGaussian();

                }
            }
        }

    }


    public void multiply(double value){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]*=value;
            }
        }
    }

    public void add(Matrix m){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]+=m.matrix[i][j];
            }
        }
    }
    Matrix subtract(Matrix m){
        Matrix output = new Matrix(this.matrix.length,this.matrix[0].length);

        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                output.matrix[i][j]=this.matrix[i][j] -m.matrix[i][j];
            }
        }
        return output;

    }

    public void multiply(Matrix m){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]*=m.matrix[i][j];
            }
        }
    }

    public void difference(Matrix m){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]-=m.matrix[i][j];
            }
        }
    }

    public void divide(Matrix m){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j<cols ; j++){
                this.matrix[i][j]/=m.matrix[i][j];
            }
        }
    }


    void matrixsigmoid(){

        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                double temp = this.matrix[i][j];
                double sign = sigmoid(temp);
                this.matrix[i][j] = sign;

            }
        }

    }

    Matrix matrixdsigmoid(){

        Matrix output = new Matrix(this.rows,this.cols);

        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                output.matrix[i][j]= dsigmoid(this.matrix[i][j]);
            }
        }
        return output;

    }

    // now we will have dot multiplication
    Matrix dotproduct(Matrix m){

        if(this.cols == m.rows){
            Matrix product = new Matrix(this.rows,m.cols);

            for(int i = 0 ; i<product.matrix.length;i++){
                for(int j = 0;j<product.matrix[0].length;j++){

                    double sum =0;
                    for(int k = 0 ;k<this.cols;k++){
                        sum += this.matrix[i][k]*m.matrix[k][j];
                    }
                    product.matrix[i][j]=sum;
                }
            }

            return product;

        }else{
            // error:
            System.out.println("Dot product rows and cols mismatch");
            return null;
        }

    }

    Matrix copy(){

        Matrix m = new Matrix(this.rows,this.cols);
        for(int i = 0 ; i<this.matrix.length;i++){
            for(int j = 0;j<this.matrix[i].length;j++){
                m.matrix[i][j]=this.matrix[i][j] ;
            }
        }
        return m;
    }
    void printmat(){

        for(int i = 0 ; i<this.matrix.length;i++){
            System.out.println("\n");
            for(int j = 0;j<this.matrix[0].length;j++){
                System.out.println(this.matrix[i][j]+" ");
            }
        }

        System.out.println("\n__________\n");

    }

}