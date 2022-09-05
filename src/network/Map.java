package network;

import network.grid.DenseGrid;
import network.grid.Grid;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {
    private ArrayList<Point> points = new ArrayList<>();
    private Network network;

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    public void clearPoints() {
        points.clear();
    }

    public double train() {
        double lossSum = 0;
        for (int i = 0; i < points.size(); i++) {
            network.getInputLayer().getOutput().set(0, points.get(i).getX());
            network.getInputLayer().getOutput().set(1, points.get(i).getY());

            Grid target = new DenseGrid(1, 1, 1);
            target.set(0, points.get(i).getValue());

            network.compute(network.getInputLayer().getOutput());

            lossSum += network.computeLoss(target);

            network.backprop();

            network.updateWeights();
        }
        return lossSum / points.size();
    }

    public void writeToImage(BufferedImage img) {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                double x = i / (img.getWidth() - 1d);
                double y = j / (img.getHeight() - 1d);

                double minD = 100;
                Point closest = null;

                for (Point p : points) {
                    double d = Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
                    if (d < minD) {
                        minD = d;
                        closest = p;
                    }
                }

                if (minD < 0.02) {
                    if (minD > 0.016) {
                        Color c = new Color(0, 0, 0);

                        img.setRGB(i, j, c.getRGB());
                    } else {
                        Color c = new Color((float) closest.getValue(), 0, 1 - (float) closest.getValue());

                        img.setRGB(i, j, c.getRGB());
                    }

                } else {

                    network.getInputLayer().getOutput().set(0, x);
                    network.getInputLayer().getOutput().set(1, y);

                    float out = (float) (network.compute(network.getInputLayer().getOutput()).get(0));
                    if (out > 1) {
                        out = 1;
                    }
                    if (out < 0) {
                        out = 0;
                    }
                    //System.out.println(out);

                    Color c = new Color(out, 0, 1 - out);

                    img.setRGB(i, j, c.getRGB());
                }


            }
        }
    }


}
