package network.layer;

import network.grid.Grid;

import java.util.ArrayList;


public class Pooling extends Layer {
public static final int MAX_POOLING=1;
public static final int MEAN_POOLING=0;

    private int poolWindow, poolStride;
    private int poolingType;


    public Pooling(int poolWindow, int poolStride, int poolingType){
        this.poolWindow = poolWindow;
        this.poolStride = poolStride;
        this.poolingType = poolingType;
        if(this.poolWindow==0){
            this.poolWindow=1;
        }
        if(this.poolStride==0){
            this.poolStride=this.poolWindow;
        }
    }

    @Override
    public int[] computeOutputSize() {
        Grid input = getInput();
        return new int[]{(((input.getWidth()-1)/poolStride)+1),((input.getHeight()-1)/poolStride)+1,input.getDepth()};
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
        if(poolingType==MAX_POOLING){
            for(int i=0;i<output.getDepth();i++){
                for (int j =0;j<output.getWidth();j++){
                    for(int n = 0;n<output.getHeight();n++){
                        double max =0;
                        for(int x=0;x<poolWindow;x++){
                            for(int y=0;y<poolWindow;y++){

                                int x_i = (j * poolStride) + x;
//                            System.out.println(x_i);
                                int y_i  = (n * poolStride) + y;
//                            System.out.println(y_i);

                                if(x_i < this.getInput().getWidth() && y_i < this.getInput().getHeight()){
                                    if(getInput().get(x_i,y_i,i)>max){
//                                    System.out.println(input.get(x_i,y_i,i)+" > "+max);
                                        max = getInput().get(x_i,y_i,i);
                                    }
                                }
                            }
                        }
//                    System.out.println(j+", "+n+", "+i+" max: "+max);
                        output.set(j,n,i,max);
//                    System.out.println(max);

                    }
                }
            }
        }else if(poolingType==MEAN_POOLING){
            for(int i=0;i<output.getDepth();i++){
                for (int j =0;j<output.getWidth();j++){
                    for(int n = 0;n<output.getHeight();n++){
                        double t =0;
                        double m =0;
                        for(int x=0;x<poolWindow;x++){
                            for(int y=0;y<poolWindow;y++){

                                int x_i = (j * poolStride) + x;
//                            System.out.println(x_i);
                                int y_i  = (n * poolStride) + y;
//                            System.out.println(y_i);

                                if(x_i < this.getInput().getWidth() && y_i < this.getInput().getHeight()){
                                    m++;
                                    t+=getInput().get(x_i,y_i,i);
                                }
                            }
                        }
//                    System.out.println(j+", "+n+", "+i+" max: "+max);
                        output.set(j,n,i,t/m);
//                    System.out.println(max);

                    }
                }
            }

        }else{
            System.out.println("ERROR: pooling mode not recognised");
            System.exit(0);
        }
    }

    @Override
    public void backProp() {
        if(getInput().getGradients() == null) return;
//        System.out.println(input);
//        System.out.println(output);
        if(poolingType==MAX_POOLING){
            for(int d=0;d<output.getDepth();d++) {
                for (int w = 0; w < output.getWidth(); w++) {
                    for (int h = 0; h < output.getHeight(); h++) {
                        for (int x = 0; x < poolWindow; x++) {
                            for (int y = 0; y < poolWindow; y++) {
                                int x_i = (w * poolStride) + x;
                                int y_i = (h * poolStride) + y;
                                if (x_i >= 0 && y_i >= 0 && x_i < getInput().getWidth() && y_i < getInput().getHeight()) {
                                    if (getInput().get(x_i, y_i, d) == output.get(w, h, d)) {
//                                        System.out.println("value found");
                                        getInput().getGradients().set(x_i, y_i, d, output.getGradients().get(w, h, d));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else if(poolingType==MEAN_POOLING){
            for(int d=0;d<output.getDepth();d++){
                for (int w =0;w<output.getWidth();w++){
                    for(int h = 0;h<output.getHeight();h++){
                        for(int x=0;x<poolWindow;x++){
                            for(int y=0;y<poolWindow;y++){
                                int x_i = (w * poolStride) + x;
                                int y_i  = (h * poolStride) + y;
                                if (x_i >= 0 && y_i >= 0 && x_i < getInput().getWidth() && y_i < getInput().getHeight()) {
                                    getInput().getGradients().set(x_i,y_i,d,(output.getGradients().get(w,h,d))/(poolWindow*poolWindow));

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
        return 0;
    }

    @Override
    public int countConns() {
        return output.size()*poolWindow*poolWindow;
    }

    public int getPoolingType() {
        return poolingType;
    }

    public void setPoolingType(int poolingType) {
        this.poolingType = poolingType;
    }
}
