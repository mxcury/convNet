package network.grid;

public class DenseGrid extends Grid<DenseGrid> {
    private double values[];

    public DenseGrid(int width) {
        super(width);
    }

    public DenseGrid(int width, int height, int depth) {
        super(width, height, depth);
    }

    public DenseGrid(int width, boolean createGradients) {
        super(width, createGradients);
    }

    public DenseGrid(int width, int height, int depth, boolean createGradients) {
        super(width, height, depth, createGradients);
    }


    @Override
    public int memUsage() {
        return values.length * 8 + super.memUsage();
    }

    @Override
    public DenseGrid copy() {
        DenseGrid outgrid = new DenseGrid(width, height, depth);
        for(int i = 0; i < size(); i++){
            outgrid.values[i] = values[i];
        }
        if(gradients != null){
            outgrid.gradients = gradients.copy();
        }
        return outgrid;
    }

    @Override
    public void init() {
        values = new double[this.size()];
    }

    public double get(int w, int h, int d) {
        int index = index(w,h,d);
        return this.values[index];
    }

    public double get(int index) {
        return this.values[index];
    }

    @Override
    public void add(int w, int h, int d, double val) {
        int index = index(w,h,d);
        this.values[index] += val;
    }

    @Override
    public void add(int index, double val) {
        this.values[index] += val;
    }

    public void set(int w, int h, int d, double val) {
        int index = index(w,h,d);
        this.values[index] = val;
    }

    public void set(int index, double val) {
        this.values[index] = val;
    }

    public double[] raw() {
        return values;
    }

    public void reset() {
        for (int i = 0; i < size(); i++) {
            set(i, 0);
        }
    }


    public void normalise() {
        double max = findMax();
        double min = findMin();
        for (int i = 0; i < values.length; i++) {
            values[i] = (values[i] - min) / (max - min);
        }
    }

    public double findMax() {
        return values[indexOfMax()];
    }

    public double findMin() {
        return values[indexOfMin()];
    }


    public void randomise(double lower, double upper) {
        for (int i = 0; i < values.length; i++) {
            double a = lower + Math.random() * (upper - lower);
            values[i] = a;
        }
    }

    public int indexOfMax() {
        int best = 0;
        for (int i = 0; i < this.size(); i++) {
            if (values[i] > values[best]) {
                best = i;
            }
        }
        return best;
    }

    public int indexOfMin() {
        int best = 0;
        for (int i = 0; i < this.size(); i++) {
            if (values[i] < values[best]) {
                best = i;
            }
        }
        return best;
    }

}
