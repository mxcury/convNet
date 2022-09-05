package network.layer;

import network.grid.DenseGrid;
import network.grid.Grid;
import network.grid.SparseGrid;

import java.util.ArrayList;

public class InputLayer extends Layer {


    private int w,h,d;

    public InputLayer(int w, int h, int d) {
        this.w = w;
        this.h = h;
        this.d = d;
    }

    @Override
    public int[] computeOutputSize() {
        return new int[]{w,h,d};
    }

    @Override
    public void collectWeights(ArrayList<Grid> grids) {

    }

    @Override
    public void initWeightsArrays() {
    }

    @Override
    public boolean inputSizeIsOK() {
        return true;
    }


    @Override
    public void setOutput(Grid output) {
        this.output = output;
    }

    @Override
    public void compute() {

    }

    @Override
    public void backProp() {

    }

    @Override
    public int countParams() {
        return 0;
    }

    @Override
    public int countConns() {
        return output.size();
    }

    public int randInt(){
        return (int) (Math.random()*10);
    }

}
