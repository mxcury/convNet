package network.layer;

import network.grid.Grid;

import java.util.ArrayList;

public class TanHLayer extends Layer {


    @Override
    public int[] computeOutputSize() {
        return new int[]{getInput().getWidth(), getInput().getHeight(), getInput().getDepth()};
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
    public void compute() {
        for(int i=0;i<getInput().size();i++) output.set(i, (Math.tanh(getInput().get(i))));
    }

    public void backProp(){
        if(getInput().getGradients() == null) return;
        for(int i=0;i<output.size();i++) {
            getInput().getGradients().set(i,(1-(Math.tanh(output.get(i)))*Math.tanh(output.get(i))) * output.getGradients().get(i));
        }
    }

    @Override
    public int countParams() {
        return 0;
    }

    @Override
    public int countConns() {
        return getInput().size();
    }
}
