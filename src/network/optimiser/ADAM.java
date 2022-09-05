package network.optimiser;

import network.grid.DenseGrid;
import network.grid.Grid;

import java.util.ArrayList;

public class ADAM extends Optimiser{
    private double alpha = 0.001, beta1 = 0.9, beta2 =0.999, epsilon = 1e-8;
    private ArrayList<Grid> FMV = new ArrayList<>();
    private ArrayList<Grid> SMV = new ArrayList<>();
    private int t = 0;

    public ADAM(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public void optimise() {
        t++;
        for(int i=0; i < grids.size();i++){
            Grid w = grids.get(i);
            Grid m = FMV.get(i);
            Grid v = SMV.get(i);
            for(int j =0; j< w.size();j++){
                double gradient = w.getGradients().get(j);
                m.set(j,m.get(j)*beta1+(1-beta1)*gradient);
                v.set(j,v.get(j)*beta2+(1-beta2)*(gradient*gradient));
                double m_corrected = m.get(j)/(1-(Math.pow(beta1,t)));
                double v_corrected = v.get(j)/(1-(Math.pow(beta2,t)));
                w.add(j,-(alpha*m_corrected)/(Math.sqrt(v_corrected)+epsilon));
                w.getGradients().set(j,0);

            }
        }

    }

    @Override
    public void reset() {
        for(int i=0; i < grids.size();i++){
            this.FMV.add(new DenseGrid(grids.get(i).size()));
            this.SMV.add(new DenseGrid(grids.get(i).size()));
        }

    }
}
