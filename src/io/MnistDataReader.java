package io;

import network.grid.DenseGrid;
import network.grid.Grid;
import network.Network;

import java.io.*;
import java.util.ArrayList;

public class MnistDataReader  {

    public static void readData(String dataFilePath, String labelFilePath, ArrayList<Grid> inputs, ArrayList<Grid> outputs,int dataAmount) throws IOException {

        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        int magicNumber = dataInputStream.readInt();
        int numberOfItems = dataInputStream.readInt();
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

        for(int i = 0; i < Math.min(numberOfItems,dataAmount); i++) {
            Grid input = new DenseGrid(nRows, nCols,1);
            Grid output = new DenseGrid(10,1,1);
            output.set(labelInputStream.readUnsignedByte(),0,0,1);
            for (int r = 0; r < nRows; r++) {
                for (int c = 0; c < nCols; c++) {
                    input.set(c,r,0,dataInputStream.readUnsignedByte()/255.0);
                }
            }
            inputs.add(input);
            outputs.add(output);
        }
        dataInputStream.close();
        labelInputStream.close();
    }
    public static double validate(ArrayList<Grid> inputs, ArrayList<Grid> outputs, Network net){
        double numCorrect = 0;
        for(int i=0;i<inputs.size();i++){
            Grid g = net.compute(inputs.get(i));
            Grid n = outputs.get(i);
            if(g.indexOfMax() == n.indexOfMax()){
                numCorrect++;
            }
        }
        return numCorrect/inputs.size();
    }
}