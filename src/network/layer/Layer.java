package network.layer;

import network.grid.DenseGrid;
import network.grid.Grid;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Layer implements Serializable {

    protected Grid output;

    protected Layer prevLayer;
    protected Layer nextLayer;

    public void connect(Layer prevLayer){


        if(prevLayer != null){
            this.prevLayer = prevLayer;
            this.prevLayer.nextLayer = this;
            if(!inputSizeIsOK()) {
                throw new RuntimeException("input size not working with this network.layer type");
            }
        }


        int[] sizes = computeOutputSize();
        this.output = new DenseGrid(sizes[0], sizes[1], sizes[2]);

        initWeightsArrays();
    }


    public abstract int[] computeOutputSize();

    public abstract void collectWeights(ArrayList<Grid> grids);

    public abstract void initWeightsArrays();

    public abstract boolean inputSizeIsOK();

    public abstract void compute();

    public abstract void backProp();

    public abstract int countParams();

    public abstract int countConns();

    public Grid getInput() {
        if(prevLayer == null) return null;
        return prevLayer.getOutput();
    }

    public void setInput(Grid input) {
        if(prevLayer == null) return;
        this.prevLayer.output = input;
    }

    public Grid getOutput() {
        return output;
    }

    public void setOutput(Grid output) {
        this.output = output;
    }

    public Layer getPrevLayer() {
        return prevLayer;
    }

    public void setPrevLayer(Layer prevLayer) {
        this.prevLayer = prevLayer;
    }

    public Layer getNextLayer() {
        return nextLayer;
    }

    public void setNextLayer(Layer nextLayer) {
        this.nextLayer = nextLayer;
    }



}
