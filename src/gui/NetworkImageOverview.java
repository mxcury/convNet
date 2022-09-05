package gui;

import network.Network;
import network.grid.Grid;
import network.layer.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class NetworkImageOverview extends JPanel {

    private Network network;

    private int PADDING_X = 200;
    private int PADDING_Y = 50;
    private int NEURON_DIAMETER = 20;
    private int NEURON_THICKNESS = 1;
    private int CONV_OFFSET = 5;

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
        this.repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        if(network!=null){
            g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(NEURON_THICKNESS));
            g2d.setFont(new Font("Arial", Font.BOLD, NEURON_DIAMETER / 2));

            int maxNeuronCount = network.maxLayerWidth();


            for (int i = 0; i < network.getSize(); i++) {
                drawLayer(network.getLayer(i), maxNeuronCount, i, network.getSize() - 1, g);
            }
        }else{
            g2d.setColor(Color.WHITE);
            g2d.drawRect(0,0,this.getWidth(),this.getHeight());
        }

    }

    public void drawLayer(Layer layer, int maxNeuronCount, int layerID, int maxLayers, Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;

        int neurons = layer.getOutput().size();
        int x = (this.getWidth() - 2 * PADDING_X) * layerID / maxLayers + PADDING_X;
        int y_spacing = (this.getHeight() - 2 * PADDING_Y) / Math.max(1,maxNeuronCount - 1);
        int y_padd = PADDING_Y + ((this.getHeight() - 2 * PADDING_Y) - (y_spacing * (neurons - 1))) / 2;

        if (layer instanceof InputLayer && layer.getOutput().getHeight()==1 && layer.getOutput().getDepth()==1) {

            for (int n = 0; n < layer.getOutput().size(); n++) {

                int y = y_padd + y_spacing * n;
                g2d.drawOval(x - NEURON_DIAMETER / 2, y - NEURON_DIAMETER / 2, NEURON_DIAMETER, NEURON_DIAMETER);
            }
        } else if(layer instanceof InputLayer && (layer.getOutput().getHeight() != 1 || layer.getOutput().getDepth() != 1)){
            g2d.setStroke(new BasicStroke(2));
            int w = Math.min(100,layer.getOutput().getWidth() * 5);
            int h = Math.min(100,layer.getOutput().getHeight() * 5);
            int y_0 = this.getHeight()/2 - layer.getOutput().getDepth() * CONV_OFFSET/2 - h/2;
            int x_0 = x - layer.getOutput().getDepth() * CONV_OFFSET/2 - w/2;

            for(int i=0;i<layer.getOutput().getDepth();i++) {
                int y = y_0 + (CONV_OFFSET * i);
                g2d.drawRect(x_0 + (i * CONV_OFFSET), y, w, h);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x_0 + (i * CONV_OFFSET), y, w-1, h-1);
                g2d.setColor(Color.BLACK);

            }
            g2d.setStroke(new BasicStroke(1));


        } else if (layer instanceof DenseLayer) {

            int neurons_prev = layer.getInput().size();
            int x_prev = (this.getWidth() - 2 * PADDING_X) * (layerID - 1) / maxLayers + PADDING_X;
            int y_prev_padd = PADDING_Y + ((this.getHeight() - 2 * PADDING_Y) - (y_spacing * (neurons_prev - 1))) / 2;

            for (int n = 0; n < layer.getOutput().size(); n++) {

                int y = y_padd + y_spacing * n;
                g2d.drawOval(x - NEURON_DIAMETER / 2, y - NEURON_DIAMETER / 2, NEURON_DIAMETER, NEURON_DIAMETER);

                for (int in = 0; in < layer.getInput().size(); in++) {
                    int y_prev = y_prev_padd + in * y_spacing;
                    g2d.drawLine(x_prev + NEURON_DIAMETER / 2, y_prev, x - NEURON_DIAMETER / 2, y);
                }

            }
        } else if (layer instanceof SigmoidLayer || layer instanceof ReLULayer || layer instanceof Softmax || layer instanceof TanHLayer) {
            int x_prev = (this.getWidth() - 2 * PADDING_X) * (layerID - 1) / maxLayers + PADDING_X;
            for (int n = 0; n < layer.getOutput().size(); n++) {

                int y = y_padd + y_spacing * n;
                g2d.drawOval(x - NEURON_DIAMETER / 2, y - NEURON_DIAMETER / 2, NEURON_DIAMETER, NEURON_DIAMETER);
//                g2d.drawString("A", x - 8, y + 7);
                g2d.drawLine(x_prev + NEURON_DIAMETER / 2, y, x - NEURON_DIAMETER / 2, y);
            }
        }else if (layer instanceof Flatten){
            int y_1 = y_padd - NEURON_DIAMETER/2;
            int y_2 = y_padd + y_spacing * (layer.getOutput().size()-1) + NEURON_DIAMETER/2;
            g2d.drawRect(x ,y_1,9,(y_2)-(y_1));

        }else if (layer instanceof ConvLayer){
            g2d.setStroke(new BasicStroke(2));
            int w = Math.min(100,layer.getOutput().getWidth() * 5);
            int h = Math.min(100,layer.getOutput().getHeight() * 5);
            int y_0 = this.getHeight()/2 - layer.getOutput().getDepth() * CONV_OFFSET/2 - h/2;
            int x_0 = x - layer.getOutput().getDepth() * CONV_OFFSET/2 - w/2;

            for(int i=0;i<layer.getOutput().getDepth();i++) {
                int y = y_0 + (CONV_OFFSET * i);
                g2d.drawRect(x_0 + (i * CONV_OFFSET), y, w, h);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x_0 + (i * CONV_OFFSET), y, w-1, h-1);
                g2d.setColor(Color.BLACK);
            }
            g2d.setStroke(new BasicStroke(1));





        }else if (layer instanceof Pooling){
            g2d.setStroke(new BasicStroke(2));

            int w = Math.min(100,layer.getOutput().getWidth() * 5);
            int h = Math.min(100,layer.getOutput().getHeight() * 5);
            int y_0 = this.getHeight()/2 - layer.getOutput().getDepth() * CONV_OFFSET/2 - h/2;
            int x_0 = x - layer.getOutput().getDepth() * CONV_OFFSET/2 - w/2;

            for(int i=0;i<layer.getOutput().getDepth();i++) {
                int y = y_0 + (CONV_OFFSET * i);
                g2d.drawRect(x_0 + (i * CONV_OFFSET), y, w, h);
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x_0 + (i * CONV_OFFSET), y, w-1, h-1);
                g2d.setColor(Color.BLACK);
            }
            g2d.setStroke(new BasicStroke(1));


        }
    }
//    public void error(Graphics graphics){
//        Graphics2D g2d = (Graphics2D) graphics;
//        g2d.setStroke(new BasicStroke(10));
//        g2d.setColor(Color.RED);
//        g2d.drawRect(0,0,this.getWidth(),this.getHeight());
//
//    }
}

