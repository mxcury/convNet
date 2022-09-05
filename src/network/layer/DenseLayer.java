package network.layer;


import network.grid.DenseGrid;
import network.grid.Grid;
import network.grid.SparseGrid;

import java.util.ArrayList;

public class DenseLayer extends Layer {


    Grid weights;
    Grid bias;

    
    private int outputSize;
    
    public DenseLayer(int outputSize) {
        this.outputSize = outputSize;
    }

    @Override
    public int[] computeOutputSize() {
        return new int[]{outputSize,1,1};
    }

    @Override
    public void collectWeights(ArrayList<Grid> grids) {
        grids.add(weights);
        grids.add(bias);
    }


    @Override
    public void initWeightsArrays() {
        bias = new DenseGrid(getOutput().getWidth(), 1, 1);
        weights = new DenseGrid(getOutput().getWidth(), getInput().getWidth(), 1);
        double factor = 1d / Math.sqrt(getInput().size());
        bias.randomise(-factor, factor);
        weights.randomise(-factor, factor);
    }

    @Override
    public boolean inputSizeIsOK() {
        return getInput().getHeight() == 1 && getInput().getDepth() == 1;
    }

    @Override
    public void compute() {

        Grid input = getInput();
        Grid output = getOutput();

        if (input instanceof SparseGrid){


            for (int o = 0; o < output.size(); o++) {
                double sum = 0;
                for (int i = 0; i < ((SparseGrid) input).getIndices().size(); i++) {
                    int index = ((SparseGrid) input).getIndices().get(i);
                    sum += weights.get(o, index, 0) * ((SparseGrid) input).getValues().get(i);
                    //System.out.println(input.get(i));
                }
                sum += bias.get(o, 0, 0);
                //System.out.println(sum);
                output.set(o, 0, 0, sum);
            }


        }else if (input instanceof DenseGrid){
            for (int o = 0; o < output.size(); o++) {
                double sum = 0;
                for (int i = 0; i < input.size(); i++) {
                    sum += weights.get(o, i, 0) * input.get(i, 0, 0);
                    //System.out.println(input.get(i));
                }
                sum += bias.get(o, 0, 0);
                //System.out.println(sum);
                output.set(o, 0, 0, sum);
            }
        }


    }
    
    @Override
    public void backProp() {
        for (int i = 0; i < output.size(); i++) {
            bias.getGradients().add(i,0,0, output.getGradients().get(i));
        }

        Grid input = getInput();
        // handle sparse data differently (very fast)
        if (input instanceof SparseGrid){
            int index = 0;
            for (int i:((SparseGrid) input).getIndices()) {
                if(input.getGradients() != null){
                    double sum = 0;
                    for (int j = 0; j < output.size(); j++) {
                        sum += weights.get(j, i, 0) * output.getGradients().get(j);
                        weights.getGradients().add(j, i, 0, ((SparseGrid) input).getValues().get(index) * output.getGradients().get(j));
                    }
                    input.getGradients().set(i, sum);
                }else{

                }
                for (int j = 0; j < output.size(); j++) {
                    weights.getGradients().add(j, i, 0, ((SparseGrid) input).getValues().get(index) * output.getGradients().get(j));
                }
                index ++;
            }
        }else if (input instanceof DenseGrid){
            for (int i = 0; i < input.size(); i++) {
                if(input.getGradients() != null){
                    double sum = 0;
                    for (int j = 0; j < output.size(); j++) {
                        sum += weights.get(j, i, 0) * output.getGradients().get(j);
                        weights.getGradients().add(j, i, 0, input.get(i) * output.getGradients().get(j));
                    }
                    input.getGradients().set(i, sum);
                }else{
                    for (int j = 0; j < output.size(); j++) {
                        weights.getGradients().add(j, i, 0, input.get(i) * output.getGradients().get(j));
                    }
                }

            }
        }

    }

    @Override
    public int countParams() {
        return (getInput().size()*output.size())+outputSize;
    }

    @Override
    public int countConns() {
        return (getInput().size()*output.size())+outputSize;
    }

    public Grid getWeights() {
        return this.weights;
    }

    public void setWeights(Grid weights) {
        this.weights = weights;
    }

    public Grid getBias() {
        return bias;
    }

    public void setBias(Grid bias) {
        this.bias = bias;
    }

}
