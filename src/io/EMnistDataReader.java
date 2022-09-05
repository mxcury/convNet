package io;

import network.Network;
import network.grid.Grid;
import network.grid.SparseGrid;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class EMnistDataReader {
    public ArrayList<Grid> getInputs() {
        return inputs;
    }

    public ArrayList<Grid> getOutputs() {
        return outputs;
    }

    private ArrayList<Grid> inputs = new ArrayList<Grid>();
    private ArrayList<Grid> outputs = new ArrayList<>();

    public EMnistDataReader(String file, int num, JProgressBar progressBar,JLabel jLabel) throws IOException {
        int e=0;
        progressBar.setMinimum(0);
        progressBar.setMaximum(Classes.values().length);
        for(Classes c : Classes.values()){

            String path = file+"/"+c.folder;
            String[] pathnames;
            File f = new File(path);
            pathnames = f.list();
            Grid o = new SparseGrid(Classes.values().length);
            o.set(c.index,1);
            int index = 0;
            progressBar.setValue(e);
            e++;
            System.out.println("t");
            if(pathnames == null) continue;

            for(String s : pathnames){
                System.out.println("s "+s);
                jLabel.setText(path + "/" + s);
                BufferedImage image = ImageLoader.loadImage(path + "/" + s,ImageLoader.BLACK_WHITE, ImageLoader.INVERT);
                Grid i = ImageLoader.imageToSparseTensor(image,ImageLoader.BLACK_WHITE, ImageLoader.NORMALISE);
                inputs.add(i);
                outputs.add(o);
                index++;
                if(index>=num){
                    break;
                }

            }


        }

    }


    public  void shuffle (){
        for(int i=0;i<inputs.size();i++){
            int index1 = (int) (Math.random()*inputs.size());
            int index2 = (int) (Math.random()*inputs.size());

            Collections.swap(inputs, index1, index2);
            Collections.swap(outputs, index1, index2);

        }
    }



    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

  public String getFileExtensiom(String fileName){
      String extension = "";
      int i = fileName.lastIndexOf('.');
      if (i >= 0) { extension = fileName.substring(i+1); }
      return extension;
  }


}

