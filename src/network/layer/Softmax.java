package network.layer;

import network.grid.Grid;

import java.util.ArrayList;

public class Softmax extends Layer {


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
        double sum = sumExp(getInput());
        for(int i=0;i<getInput().size();i++){
            output.set(i, Math.exp(getInput().get(i))/sum);
        }

    }

    public void backProp(){
        if(getInput().getGradients() == null) return;
        /** if i = j : O(j) * (1-O(j))
         * where i is the current value and j is the value it has respect to
         *
         *  if i != j : -O(j) * O(j)
         */
        for(int i=0;i<getInput().getGradients().size();i++){
            double gradientsSum = 0;
            for(int j=0;j<getInput().getGradients().size();j++){
                if(i == j){
                    gradientsSum += (output.get(j) * (1d - output.get(j)))*output.getGradients().get(j);
                }else {
                    gradientsSum += (output.get(j) * -output.get(i))*output.getGradients().get(j);                }
            }
            getInput().getGradients().set(i,gradientsSum);

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

    private double sumExp(Grid g){
        double sum=0;
        for(int i=0;i<g.size();i++){
            sum+=Math.exp(g.get(i));
        }
        return sum;

    }
}
