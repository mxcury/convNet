package network.optimiser;

import network.grid.Grid;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Optimiser implements Serializable {
    protected ArrayList<Grid> grids;
    public abstract void optimise();

    public abstract void reset();

    public void init(ArrayList<Grid> grids){
        this.grids = grids;
        reset();
    }
}
