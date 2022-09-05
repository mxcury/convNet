package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LossGraph extends JPanel {
    int PADDING = 10;
    int ARROW_LENGTH = 3;


    public ArrayList<Double> getValues() {
        return values;
    }

    public void setValues(ArrayList<Double> values) {
        this.values = values;
    }

    public ArrayList<Double> values = new ArrayList<Double>();



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.clearRect(0,0,this.getWidth(), this.getHeight());
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
//        genVals();
        setupAxis(g);
        plotPoints(g);

    }

    public void plotPoints(Graphics graphics) {
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.setColor(Color.RED);
        for(int n=0;n<values.size()-1;n++){
            g2d.drawLine(findXCoord(n),findYCoord(values.get(n)),findXCoord(n+1),findYCoord(values.get(n+1)));
        }
//        for(int i=0;i<10;i++){
//            g2d.drawOval(PADDING+i,PADDING+(2*i),2,2);
//        }
//        g2d.drawOval(PADDING,PADDING,2,2); //(0,max)
//        g2d.drawOval(PADDING,this.getHeight()-(PADDING+3),2,2); //(0,0)
//        g2d.drawOval(this.getWidth()-(PADDING+3),this.getHeight()-(PADDING+3),2,2);//(max,0)
//        g2d.drawLine(PADDING,PADDING,this.getWidth()-PADDING-3,this.getHeight()-PADDING-3)); //from top-left to bottom right



        
    }
    public void setupAxis(Graphics graphics){
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.drawLine(PADDING,PADDING,PADDING,this.getHeight()-PADDING);
        g2d.drawLine(PADDING,this.getHeight()-PADDING,this.getWidth()-PADDING,this.getHeight()-PADDING);
        g2d.drawLine(PADDING,PADDING,PADDING+ARROW_LENGTH,PADDING+ARROW_LENGTH);
        g2d.drawLine(PADDING,PADDING,PADDING-ARROW_LENGTH,PADDING+ARROW_LENGTH);
        g2d.drawLine(this.getWidth()-PADDING,this.getHeight()-PADDING,this.getWidth()-PADDING-3,this.getHeight()-PADDING-3);
        g2d.drawLine(this.getWidth()-PADDING,this.getHeight()-PADDING,this.getWidth()-PADDING-3,this.getHeight()-PADDING+3);
    }

    public Double getMaxValue(ArrayList<Double> values) {
        Double max = 0d;
        for(int i=0;i<values.size();i++){
            if(values.get(i)>max){
                max = values.get(i);
            }
        }
        return max;
    }
    public int findYCoord(Double y){
        int y_range = this.getHeight()-(2*PADDING)-3;
        int m = (int) ( y_range/getMaxValue(values));
        return this.getHeight() - (int) (y*m)-PADDING;
    }
    public int findXCoord(int x){
        int x_range = this.getWidth()-(2*PADDING)-3;
        double m =  ((double)x_range/values.size());
        return  (int) (x*m)+PADDING;
    }

//    public void genVals(){
//        values.clear();
//        for(int i=0;i<2000;i++){
//            values.add(1d/(1+Math.exp(-i/400d+2.5)));
//        }
//    }





}
