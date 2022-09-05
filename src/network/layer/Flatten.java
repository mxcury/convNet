package network.layer;

import network.grid.DenseGrid;
import network.grid.Grid;
import network.grid.SparseGrid;

import java.util.ArrayList;

public class Flatten extends Layer {
    @Override
    public int[] computeOutputSize() {
        return new int[]{getInput().getWidth() * getInput().getHeight()* getInput().getDepth(),1,1};
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

        Grid input = getInput();

        if(input instanceof SparseGrid){
            if(output instanceof DenseGrid){
                output = new SparseGrid(output.size(),1,1,true);
            }

            ((SparseGrid)output).getValues().clear();
            ((SparseGrid)output).getIndices().clear();

            ((SparseGrid)output).getValues().addAll(((SparseGrid) input).getValues());
            ((SparseGrid)output).getIndices().addAll(((SparseGrid) input).getIndices());

        }
        else if(input instanceof DenseGrid){
            if(output instanceof SparseGrid){
                output = new DenseGrid(output.size(),1,1,true);
            }

            for(int i = 0; i < getInput().size(); i++){
                output.set(i,getInput().get(i));
            }

        }




    }

    @Override
    public void backProp() {

        if(getInput().getGradients() != null){
            for(int i = 0; i < getInput().size(); i++){
                getInput().getGradients().set(i,output.getGradients().get(i));
            }
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
