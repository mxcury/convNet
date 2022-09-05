package network;

import gui.Draw;
import io.EMnistDataReader;
import io.ImageLoader;
import network.grid.DenseGrid;
import network.grid.Grid;
import network.layer.Layer;
import network.layer.Pooling;
import network.loss.Loss;
import network.optimiser.ADAM;
import network.optimiser.GD;
import network.optimiser.Optimiser;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Network implements Serializable {

    private ArrayList<Layer> layers = new ArrayList<Layer>();
    private Loss loss;
    private Optimiser optimiser;

    public Grid compute(Grid input) {
        layers.get(0).setOutput(input);

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).compute();
        }

        Layer lastLayer = layers.get(layers.size() - 1);

        return lastLayer.getOutput().copy();
    }

    public void prepareTraining() {
        ArrayList<Grid> weights = new ArrayList<>();
        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).collectWeights(weights);
        }
        optimiser.init(weights);
    }

    public double computeLoss(Grid target) {
        return loss.computeLoss(layers.get(layers.size() - 1).getOutput(), target);
    }

    public void backprop() {
        for (int i = layers.size() - 1; i >= 0; i--) {
            layers.get(i).backProp();
        }
    }

    public void updateWeights() {
        optimiser.optimise();
    }

    public void addLayer(Layer layer) {

        if (layers.size() != 0) {
            layer.connect(layers.get(layers.size() - 1));
        } else {
            layer.connect(null);
        }

        layers.add(layer);
    }

    public String overview() {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < layers.size(); i++) {
            String info = "-";
            if(layers.get(i) instanceof Pooling){
                Pooling p = (Pooling) layers.get(i);
                info = p.getPoolingType()==Pooling.MAX_POOLING ? "MAX_POOLING" : "MEAN_POOLING";
            }
            output.append(String.format("%-10s %-26s %-25s %-26s %-20s %-25s %-10s\n",
                    "LayerIndex: " + i,
                    "LayerName:" + layers.get(i).getClass().getSimpleName(),
                    "InputSize:" + (layers.get(i).getInput() == null ? "-" : layers.get(i).getInput().getDimensions()),
                    "OutputSize:" + layers.get(i).getOutput().getDimensions(),
                    "Parameters:" + layers.get(i).countParams(),
                    "Connections:" + layers.get(i).countConns(),
                    "Info:" + info));
            output.append("-".repeat(160)+"\n");
//            System.out.println("------------------------------------------------------------------------------------------");

        }
//        System.out.println("##########################################################################################");
        output.append("#".repeat(160));
        return output.toString();
    }


    public void saveNetwork(String network){
        try
        {
            FileOutputStream fileOut = new FileOutputStream(network);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            System.out.println(network+": SAVED");
            out.close();
            fileOut.close();
        }
        catch (IOException i)
        {
            System.out.println("Network could not be saved");;
        }
    }

    public static Network loadNetwork(String network){
        try
        {
            FileInputStream fileIn = new FileInputStream(network);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Network deserializedNetwork = (Network) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Network successfully loaded");
            return deserializedNetwork;
        }
        catch (IOException ioe)
        {
            //display in GUI
            System.out.println("Network could not be loaded");;
        }
        catch (ClassNotFoundException cnfe)
        {
            System.out.println("Network could not be loaded as Class Was Not Found");;
        }
        return null;
    }

    public void findOptimalInput(DenseGrid outputTarget){

        DenseGrid inputTarget = (DenseGrid) this.getInputLayer().getOutput();
        for(int i = 0; i < inputTarget.size(); i++){
            inputTarget.set(i,Math.random());
        }

        ADAM optimiser = new ADAM(0.001);
        ArrayList<Grid> optimiserGrids = new ArrayList<>();
        optimiserGrids.add(inputTarget);
        optimiser.init(optimiserGrids);

        for(int i =0; i < 100000; i++){
            this.compute(inputTarget);
            double loss = this.computeLoss(outputTarget);
            if(i % 10000 == 0)
                System.out.println(loss);
            this.backprop();

            optimiser.optimise();
        }

        System.out.println(inputTarget);
        System.out.println(this.compute(inputTarget));
        inputTarget.normalise();
        ImageLoader.displayImage(ImageLoader.tensorToImage(inputTarget,ImageLoader.NORMALISE));

    }

    public Loss getLoss() {
        return loss;
    }

    public void setLoss(Loss loss) {
        this.loss = loss;
    }

    public Optimiser getOptimiser() {
        return optimiser;
    }

    public void setOptimiser(Optimiser optimiser) {
        this.optimiser = optimiser;
    }

    public Layer getInputLayer() {
        return layers.get(0);
    }

    public Layer getLayer(int i) {
        return layers.get(i);
    }

    public Layer getFinalLayer() {
        return layers.get(layers.size() - 1);
    }

    public int getSize(){return layers.size();}

    public int maxLayerSize(){
        int max = 0;
        for(int i=0;i<layers.size();i++){
            if(layers.get(i).getOutput().size()>max){
                max = layers.get(i).getOutput().size();
            }
        }
        return max;
    }

    public int maxLayerWidth(){
        int max = 1;
        for(int i=0;i<layers.size();i++){
            if(layers.get(i).getOutput().size()>max && layers.get(i).getOutput().size() == layers.get(i).getOutput().getWidth()){
                max = layers.get(i).getOutput().size();
            }
        }
        return max;
    }

}
