package network.grid;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public abstract class Grid<T extends Grid<T>> implements Serializable {


    protected int width, height, depth;
    protected T gradients;

    public Grid(int width) {
        this(width, true);
        init();
    }

    public Grid(int width, int height, int depth) {
        this(width, height, depth, true);
        init();
    }

    public Grid(int width, boolean createGradients) {
        this.width = width;
        this.height = 1;
        this.depth = 1;
        if (createGradients) {
            try {
                this.gradients = (T) this.getClass().getConstructor(int.class, boolean.class)
                        .newInstance(width, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        init();
    }

    public Grid(int width, int height, int depth, boolean createGradients) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        if (createGradients) {
            try {
                this.gradients = (T) this.getClass().getConstructor(int.class, int.class, int.class, boolean.class)
                        .newInstance(width, height, depth, false);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        init();
    }

    public T getGradients() {
        return gradients;
    }

    public void setGradients(T gradients) {
        this.gradients = gradients;
    }


    public int index(int w, int h, int d) {
        return w + h * this.width + d * this.width * this.height;
    }

    public int size() {
        return this.width * this.height * this.depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public String getDimensions() {
        String str = "[" + width + " " + height + " " + depth + "]";
        return str;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < this.getDepth(); i++) {
            for (int n = 0; n < this.getHeight(); n++) {
                for (int j = 0; j < this.getWidth(); j++) {
                    builder.append((String.format("%-+12.3E", this.get(j, n, i))));


//                    builder.append((this.get(j, n, i) + "             ").substring(0, 10) + "  ");
                }
                builder.append("\n");
            }
            builder.append("\n");
            builder.append("\n");
        }
        return builder.toString();
    }

    public int memUsage(){
        int v = 3 * 4;
        if(gradients != null){
            v += gradients.memUsage();
        }
        return v;
    }

    public abstract T copy();

    public abstract void init();

    public abstract double get(int w, int h, int d);

    public abstract double get(int index);

    public abstract void add(int w, int h, int d, double val);

    public abstract void add(int index, double val);

    public abstract void set(int w, int h, int d, double val);

    public abstract void set(int index, double val);

    public abstract void reset();

    public abstract void normalise();

    public abstract double findMax();

    public abstract double findMin();

    public abstract void randomise(double lower, double upper);

    public abstract int indexOfMax();

    public abstract int indexOfMin();
}
