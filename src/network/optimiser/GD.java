package network.optimiser;

import network.grid.Grid;

import java.io.Serializable;

public class GD extends Optimiser implements Serializable {

    public void setLearning_rate(double learning_rate) {
        this.learning_rate = learning_rate;
    }

    public double getLearning_rate() {
        return learning_rate;
    }

    public GD(double learning_rate) {
        this.learning_rate = learning_rate;
    }

    private double learning_rate;

    @Override
    public void optimise() {
        // assume that everything optimised will be dense.
        // if this is not the case, this will be significantly slower!
        for(int i=0;i<grids.size();i++){
            Grid G = grids.get(i);
            for(int j=0;j<G.size();j++){
                G.set(j,G.get(j)-G.getGradients().get(j)*learning_rate);
                G.getGradients().set(j,0);
            }
        }
    }

    @Override
    public void reset() {

    }
}
