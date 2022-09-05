package network.layer;

import network.grid.DenseGrid;
import network.grid.Grid;

import java.util.ArrayList;

public class ConvLayer extends Layer {


    private int filterSize, filterAmount, filterStride, padding;

    public Grid[] getFilters() {
        return filters;
    }

    public void setFilters(Grid[] filters) {
        this.filters = filters;
    }

    public Grid[] getBiases() {
        return biases;
    }

    public void setBiases(Grid[] biases) {
        this.biases = biases;
    }

    private Grid[] filters;
    private Grid[] biases;


    public ConvLayer(int filterSize, int filterAmount, int filterStride, int padding) {
        this.filterSize = filterSize;
        this.filterAmount = filterAmount;
        this.filterStride = filterStride;
        this.padding = padding;
    }

    @Override
    public int[] computeOutputSize() {
        Grid inputGrid = getInput();
        return new int[]{((inputGrid.getWidth() - filterSize + (2 * padding)) / filterStride) + 1,
                         ((inputGrid.getHeight() - filterSize + (2 * padding)) / filterStride) + 1, filterAmount};
    }


    @Override
    public void collectWeights(ArrayList<Grid> grids) {
        for (int i = 0; i < filterAmount; i++) {
            grids.add(filters[i]);
            grids.add(biases[i]);
        }
    }

    @Override
    public void initWeightsArrays() {
        Grid input = getInput();
        filters = new Grid[filterAmount];
        biases = new Grid[filterAmount];
        double factor = 1d / Math.sqrt(getInput().size());
        for (int i = 0; i < filterAmount; i++) {
            filters[i] = new DenseGrid(filterSize, filterSize, input.getDepth());
            filters[i].randomise(-factor, factor);
            biases[i] = new DenseGrid(1, 1, 1);
            biases[i].randomise(-factor, factor);
        }
    }

    @Override
    public boolean inputSizeIsOK() {
        Grid input = getInput();
        double g = (double) input.getWidth() - (double) filterSize + ((double) this.padding * 2) / (double) filterStride + 1;
//        System.out.println("double: "+g);
//        System.out.println("int: "+(int) g);
        double g1 = (double) input.getHeight() - (double) filterSize + ((double) this.padding * 2)  / (double) filterStride + 1;
//        System.out.println("double: "+g1);
//        System.out.println("int: "+(int) g1);
        if (g != (int) g || g1 != (int) g1 || g<0 || g1<0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void compute() {
        for (int i = 0; i < output.getDepth(); i++) {
            for (int j = 0; j < output.getWidth(); j++) {
                for (int n = 0; n < output.getHeight(); n++) {
                    output.set(j, n, i, calcSample(i, j, n));
                }
            }
        }
    }

    public double calcSample(int actIndex, int x, int y) {
        Grid input = getInput();
        double total = biases[actIndex].get(0, 0, 0);
        for (int j = 0; j < input.getDepth(); j++) {
            for (int i = 0; i < filterSize; i++) {
                for (int n = 0; n < filterSize; n++) {
                    int x_i = -padding + (x * filterStride) + i;
                    int y_i = -padding + (y * filterStride) + n;
                    if (x_i >= 0 && y_i >= 0 && x_i < input.getWidth() && y_i < input.getHeight()) {
                        total += (filters[actIndex].get(i, n, j)) * (input.get(x_i, y_i, j));
                    }
                }
            }
        }
        return total;
    }

    @Override
    public void backProp() {

        Grid input = getInput();
        if(input.getGradients() != null){
            input.getGradients().reset();

            for (int i = 0; i < output.getDepth(); i++) {

                filters[i].getGradients().reset();
                biases[i].getGradients().reset();

                for (int j = 0; j < output.getWidth(); j++) {
                    for (int n = 0; n < output.getHeight(); n++) {

                        biases[i].getGradients().add(0,0,0,output.getGradients().get(j,n,i));

                        for (int p = 0; p < input.getDepth(); p++) {
                            for (int q = 0; q < filterSize; q++) {
                                for (int r = 0; r < filterSize; r++) {
                                    int x_i = -padding + (j * filterStride) + q;
                                    int y_i = -padding + (n * filterStride) + n;
                                    if (x_i >= 0 && y_i >= 0 && x_i < input.getWidth() && y_i < input.getHeight()) {
                                        if(input.getGradients() != null) {
                                            input.getGradients().set(x_i, y_i, p,
                                                    input.getGradients().get(x_i, y_i, p) +
                                                            output.getGradients().get(j, n, i) * filters[i].get(q, r, p));
                                        }
                                        filters[i].getGradients().add(q,r,p, input.get(x_i, y_i, p) * output.getGradients().get(j,n,i));
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }else{
            for (int i = 0; i < output.getDepth(); i++) {

                filters[i].getGradients().reset();
                biases[i].getGradients().reset();

                for (int j = 0; j < output.getWidth(); j++) {
                    for (int n = 0; n < output.getHeight(); n++) {

                        biases[i].getGradients().add(0,0,0,output.getGradients().get(j,n,i));

                        for (int p = 0; p < input.getDepth(); p++) {
                            for (int q = 0; q < filterSize; q++) {
                                for (int r = 0; r < filterSize; r++) {
                                    int x_i = -padding + (j * filterStride) + q;
                                    int y_i = -padding + (n * filterStride) + n;
                                    if (x_i >= 0 && y_i >= 0 && x_i < input.getWidth() && y_i < input.getHeight()) {
                                        filters[i].getGradients().add(q,r,p, input.get(x_i, y_i, p) * output.getGradients().get(j,n,i));
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

    }

    @Override
    public int countParams() {
        return ((filterSize*filterSize)*getInput().getDepth()*filterAmount)+biases.length;
    }

    @Override
    public int countConns() {
        return (1+ filterSize * filterSize * getInput().getDepth()) * output.size();
    }

}
