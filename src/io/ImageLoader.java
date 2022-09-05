package io;


import network.grid.DenseGrid;
import network.grid.Grid;
import network.grid.SparseGrid;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public static final int BLACK_WHITE = 1;
    public static final int NORMALISE = 2;
    public static final int INVERT = 3;

    public static BufferedImage loadImage(String source, int... args) throws IOException {
        BufferedImage img = ImageIO.read(new File(source));

        boolean invert = containsArgument(INVERT, args);

        if (containsArgument(BLACK_WHITE, args)) {
            for (int w = 0; w < img.getWidth(); w++) {
                for (int h = 0; h < img.getHeight(); h++) {
                    Color ac = new Color(img.getRGB(w, h));
                    double v = (ac.getRed() + ac.getBlue() + ac.getGreen()) / 3d * ac.getAlpha() / 255;
                    if (invert) {
                        v = (255 - v);
                    }
                    img.setRGB(w, h, new Color((int) v, (int) v, (int) v).getRGB());
                }
            }
        }

        return img;
    }

    /**
     * Valid arguments:
     * - NORMALISE: tells the function that the values of the tensor are between 0 and 1 and need to be multiplied by 255.
     *
     * @param tensor3D
     * @param args
     * @return
     */
    public static BufferedImage tensorToImage(Grid tensor3D, int... args) {
        BufferedImage img = new BufferedImage(tensor3D.getWidth(), tensor3D.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int scalar = containsArgument(NORMALISE, args) ? 255 : 1;
        for (int w = 0; w < img.getWidth(); w++) {
            for (int h = 0; h < img.getHeight(); h++) {
                if (tensor3D.getDepth() == 1) {
                    double v = Math.max(0, Math.min(1, (tensor3D.get(w, h, 0)))) * scalar;
                    img.setRGB(w, h, new Color((int) v, (int) v, (int) v).getRGB());
                } else {
                    img.setRGB(w, h, new Color(
                            Math.max(0, Math.min(1, (int) (tensor3D.get(w, h, 0)))) * scalar,
                            Math.max(0, Math.min(1, (int) (tensor3D.get(w, h, 1)))) * scalar,
                            Math.max(0, Math.min(1, (int) (tensor3D.get(w, h, 2)))) * scalar
                    ).getRGB());
                }
            }
        }
        return img;
    }


    public static DenseGrid imageToDense(BufferedImage image, int... args) {
        DenseGrid out = new DenseGrid(image.getWidth(), image.getHeight(), containsArgument(BLACK_WHITE, args) ? 1 : 3, false);
        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                Color ac = new Color(image.getRGB(w, h));
                if (containsArgument(BLACK_WHITE, args)) {

                    double v = (ac.getRed() + ac.getBlue() + ac.getGreen()) / 3d * ac.getAlpha() / 255;
                    out.set(w, h, 0, v);
                } else {
                    out.set(w, h, 0, ac.getRed());
                    out.set(w, h, 1, ac.getGreen());
                    out.set(w, h, 2, ac.getBlue());
                }

            }
        }
        if (containsArgument(NORMALISE, args)) {
            for (int i = 0; i < out.size(); i++) {
                out.set(i, out.get(i) / 255d);
            }

        }
        return out;
    }

    public static SparseGrid imageToSparseTensor(BufferedImage image, int... args) {
        SparseGrid out = new SparseGrid(image.getWidth(), image.getHeight(), containsArgument(BLACK_WHITE, args) ? 1 : 3, false);
        for (int w = 0; w < image.getWidth(); w++) {
            for (int h = 0; h < image.getHeight(); h++) {
                Color ac = new Color(image.getRGB(w, h));
                if (containsArgument(BLACK_WHITE, args)) {

                    double v = (ac.getRed() + ac.getBlue() + ac.getGreen()) / 3d * ac.getAlpha() / 255;
                    out.set(w, h, 0, v);
                } else {
                    out.set(w, h, 0, ac.getRed());
                    out.set(w, h, 1, ac.getGreen());
                    out.set(w, h, 2, ac.getBlue());
                }

            }
        }
        if (containsArgument(NORMALISE, args)) {
            for (int i = 0; i < out.size(); i++) {
                out.set(i, out.get(i) / 255d);
            }

        }
        return out;
    }

    public static BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame("FrameDemo");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), Color.WHITE, this);

            }
        };
        label.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        frame.add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    private static boolean containsArgument(int arg, int... args) {
        for (int i : args) {
            if (i == arg) return true;
        }
        return false;
    }

}
