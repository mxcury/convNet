package network;

import gui.*;
// import io.EMnistDataReader;
// import io.ImageLoader;
// import io.MnistDataReader;
// import network.grid.DenseGrid;
// import network.layer.*;
// import network.loss.MSE;
// import network.optimiser.*;

// import java.util.ArrayList;
//TODO createTab to be completed





public class Main {
    public static void main(String[] args) throws Exception{
//        EMnistDataReader reader = new EMnistDataReader("res/data",10);
//        reader.shuffle();
//
        new Draw().setVisible(true);

//  this is to show the UI for the network
//  Network net = Network.loadNetwork("res/networks/Trained Networks/ADAM_saved.bin");
//  new EMNISTFrame(net).setVisible(true);
//        new Draw().setVisible(true);
//
//
//        Network net = new Network();
//        net.addLayer(new InputLayer(6,6,2));
//        net.addLayer(new ConvLayer(2,3,1,0));
//        net.addLayer(new Pooling(2,2,1));
//        net.addLayer(new ConvLayer(2,2,1,0));
//        net.addLayer(new Pooling(2,2,1));
//        net.addLayer(new Flatten());
//        net.addLayer(new DenseLayer(8));
//        net.addLayer(new DenseLayer(8));
//        net.addLayer(new DenseLayer(8));
//        net.addLayer(new SigmoidLayer());
//
//        System.out.println(net.overview());
//
//        net.saveNetwork("res/networks/test5.bin");



//        Network net  = new Network();
//        net.addLayer(new InputLayer(128,128,1));
//        net.addLayer(new Flatten());
//        net.addLayer(new DenseLayer(100));
//        net.addLayer(new SigmoidLayer());
//        net.addLayer(new DenseLayer(62));
//        net.addLayer(new Softmax());
//
//        net.setOptimiser(new ADAM(1));
//        net.setLoss(new MSE());
//
//        net.saveNetwork("res/networks/net4.bin");


//
//        net.overview();
//
//        net.prepareTraining();
//
//        long time = System.nanoTime();
//        int m = 0;
//          for(int i=0;i<10000;i++){
//              double loss=0;
//              for(int n=0;n<reader.getInputs().size();n++){
//                  net.compute(reader.getInputs().get(n));
//                  loss+= net.computeLoss(reader.getOutputs().get(n));
//                  net.backprop();
//                  values.add(loss/reader.getOutputs().size());
//                  if(n % 50 == 49){
//                      net.updateWeights();
//                  }
//              }
//              System.out.println(loss/reader.getInputs().size());
//              if(i % 10==9){
//                  net.saveNetwork("res/networks/values.bin");
//                  System.out.println("validation = "+ MnistDataReader.validate(reader.getInputs(), reader.getOutputs(),net)*100+"%");
//                  System.out.println(values.size());
//              }
//          }
//        System.out.println(-(time-System.nanoTime())/1000000);
//        net.saveNetwork("res/networks/test4.bin");




//  this is to test the find optimal input function
 
//          net.setLoss(new MSE());
//
//
//          DenseGrid expectedOutput = new DenseGrid(62,true);
//          expectedOutput.set(4,1);
//
//          net. findOptimalInput(expectedOutput);















    }

}