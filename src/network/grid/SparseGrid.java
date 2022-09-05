package network.grid;

import java.util.ArrayList;

public class SparseGrid extends Grid<SparseGrid> {

    private ArrayList<Double> values = new ArrayList<>();
    private ArrayList<Integer> indices = new ArrayList<>();

    public SparseGrid(int width) {
        super(width);
    }

    public SparseGrid(int width, int height, int depth) {
        super(width, height, depth);
    }

    public SparseGrid(int width, boolean createGradients) {
        super(width, createGradients);
    }

    public SparseGrid(int width, int height, int depth, boolean createGradients) {
        super(width, height, depth, createGradients);
    }

    @Deprecated
    public ArrayList<Double> getValues() {
        return values;
    }

    @Deprecated
    public ArrayList<Integer> getIndices() {
        return indices;
    }

    @Override
    public int memUsage() {
        return values.size() * 8 + indices.size() * 4 + 2 * 4 + super.memUsage();
    }

    @Override
    public SparseGrid copy() {
        SparseGrid outgrid = new SparseGrid(width, height, depth);
        for(int i = 0; i < values.size(); i++){
            outgrid.values.add(values.get(i));
            outgrid.indices.add(indices.get(i));
        }
        if(gradients != null){
            outgrid.gradients = gradients.copy();
        }
        return outgrid;
    }

    @Override
    public void init() {

    }

    @Override
    public double get(int w, int h, int d) {
        int index = index(w,h,d);
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == index){
                return values.get(i);
            }
        }
        return 0;
    }

    @Override
    public double get(int index) {
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == index){
                return values.get(i);
            }
        }
        return 0;
    }

    @Override
    public void add(int w, int h, int d, double val) {
        if(val == 0) return;
        int index = index(w,h,d);
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == index){
                values.set(i, values.get(i) + val);
                return;
            }
        }
        values.add(val);
        indices.add(index);
    }

    @Override
    public void add(int index, double val) {
        if(val == 0) return;
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == index){
                values.set(i, values.get(i) + val);
                return;
            }
        }
        values.add(val);
        indices.add(index);
    }

    @Override
    public void set(int w, int h, int d, double val) {
        if(val == 0) return;
        int index = index(w,h,d);
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == index){
                values.set(i, val);
                return;
            }
        }
        values.add(val);
        indices.add(index);
    }

    @Override
    public void set(int index, double val) {
        if(val == 0) return;
        for(int i = 0; i < indices.size(); i++){
            if(indices.get(i) == index){
                values.set(i, val);
                return;
            }
        }
        values.add(val);
        indices.add(index);
    }

    @Override
    public void reset() {
        values.clear();
        indices.clear();
    }

    @Override
    public void normalise() {
        double min = findMin();
        double max = findMax();

        for(int i = 0; i < values.size();i ++){
            double oldV = values.get(i);
            double newV = (oldV-min)/(max-min);
            values.set(i, newV);
        }
    }

    @Override
    public double findMax() {
        int best = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) < values.get(best)) {
                best = i;
            }
        }
        return values.get(best);
    }

    @Override
    public double findMin() {
        int best = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) < values.get(best)) {
                best = i;
            }
        }
        return values.get(best);
    }

    public void randomise(double lower, double upper) {
        values.clear();
        indices.clear();

        for (int i = 0; i < this.size(); i++) {
            indices.add(i);
            values.add(lower + Math.random() * (upper - lower));
        }

    }

    public int indexOfMax() {
        int best = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) > values.get(best)) {
                best = i;
            }
        }
        return indices.get(best);
    }

    public int indexOfMin() {
        int best = 0;
        for (int i = 0; i < values.size(); i++) {
            if (values.get(i) < values.get(best)) {
                best = i;
            }
        }
        return indices.get(best);
    }
}
