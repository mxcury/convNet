package network.loss;

import network.grid.Grid;

import java.io.Serializable;

public abstract class Loss implements Serializable {
    public abstract double computeLoss(Grid output, Grid expectedValue);

}
