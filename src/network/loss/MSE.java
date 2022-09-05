package network.loss;

import network.grid.Grid;

import java.io.Serializable;

public class MSE extends Loss implements Serializable {
    public double computeLoss(Grid output, Grid expectedValue){
        double sum = 0;
        for(int i = 0; i < output.size(); i++) {
            output.getGradients().set(i, 2 * (output.get(i) - expectedValue.get(i)));
            sum+=(output.get(i) - expectedValue.get(i))*(output.get(i) - expectedValue.get(i));
        }
        return sum/output.size();
    }
}
