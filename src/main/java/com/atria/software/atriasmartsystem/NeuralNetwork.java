package com.atria.software.atriasmartsystem;

import static com.atria.software.atriasmartsystem.Methods.arraytomat;

public class NeuralNetwork{
    int input_nodes;
    int hidden_nodes;
    int output_nodes;
    Matrix weights_ih;
    Matrix weights_ho;
    Matrix Vinputs_ih;
    Matrix Vhidden_ho;
    Matrix Voutput;
    Matrix hidden_bias;
    Matrix output_bias;
    double learningrate;


    public NeuralNetwork(int input_nodes , int hidden_nodes,int output_nodes){
        this.input_nodes = input_nodes;
        this.hidden_nodes = hidden_nodes;
        this.output_nodes = output_nodes;

        this.weights_ih = new Matrix(hidden_nodes,input_nodes);
        this.weights_ho = new Matrix(output_nodes,hidden_nodes);
        this.weights_ih.randomize();
        this.weights_ho.randomize();

        this.hidden_bias = new Matrix(hidden_nodes,1);
        this.output_bias = new Matrix(output_nodes,1);
        this.hidden_bias.randomize();
        this.output_bias.randomize();
        this.learningrate=0.006;


    }

    double[] guess(double[] inputs){
        return feedforward(inputs);
    }

    double[] feedforward(double[]inputs){

        double[]guess;

        Vinputs_ih = new Matrix(inputs.length,1);
        arraytomat(Vinputs_ih,inputs);

        Matrix converted = new Matrix(inputs.length,1);
        arraytomat(converted,inputs);

        //converted.matrixsigmoid();
        //getting the weighted sum of the hidden
        Vhidden_ho = weights_ih.dotproduct(converted);
        Vhidden_ho.add(this.hidden_bias);
        Vhidden_ho.matrixsigmoid();


        //getting the output
        Voutput = weights_ho.dotproduct(Vhidden_ho);
        Voutput.add(this.output_bias);
        Voutput.matrixsigmoid();


        guess=Methods.mattoarray(Voutput);
        return guess;

    }
    NeuralNetwork copy(){
        NeuralNetwork clone = new NeuralNetwork(this.input_nodes,this.hidden_nodes,this.output_nodes);

        clone.weights_ih  = this.weights_ih.copy() ;
        clone.weights_ho  = this.weights_ho.copy() ;
        clone.hidden_bias = this.hidden_bias.copy();
        clone.output_bias = this.output_bias.copy();

        return clone;

    }
    void mutate(float rate){
        this.weights_ih.mutate(rate);
        this.weights_ho.mutate(rate);
        this.hidden_bias.mutate(rate);
        this.output_bias.mutate(rate);
    }

    void train(double[]inputsa,double[]targetsa){
        double[] guesseda = this.feedforward(inputsa);
        Matrix guess = new Matrix(guesseda.length,1);
        arraytomat(guess,guesseda);

        Matrix inputs =new Matrix(inputsa.length,1);
        arraytomat(inputs,inputsa);

        Matrix targets =new Matrix(targetsa.length,1);
        arraytomat(targets,targetsa);

        Matrix output_error = targets.subtract(guess);

        // lets calculate the gradient
        Matrix hidden_gradient = guess.matrixdsigmoid();
        hidden_gradient.multiply(output_error);
        hidden_gradient.multiply(learningrate);
        Matrix voutput_t = Vhidden_ho.transpose();

        //calculate the delat weights for hidden inputs and make changes
        Matrix delta_weights_ho = hidden_gradient.dotproduct(voutput_t);
        weights_ho.add(delta_weights_ho);// hidden weights are changed
        this.output_bias.add(hidden_gradient);


        // lets make input weights to change first the error
        // error = tansposeofweightedsum * erroronout
        Matrix weights_ho_t =weights_ho.transpose();;
        Matrix v_error_ih =weights_ho_t.dotproduct(output_error);

        //calculate the gradient
        Matrix input_gradient = Vhidden_ho.matrixdsigmoid();
        input_gradient.multiply(v_error_ih);
        input_gradient.multiply(learningrate);
        Matrix input_transpose = Vinputs_ih.transpose();

        // lets calculate the delta wieghts and make changes
        Matrix delta_inputweights_t = input_gradient.dotproduct(input_transpose);
        weights_ih.add(delta_inputweights_t);
        this.hidden_bias.add(input_gradient);

    }


    void setLearningRate(float lr){
        this.learningrate = lr;
    }

}

