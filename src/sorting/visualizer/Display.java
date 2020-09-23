package sorting.visualizer;

import sorting.visualizer.graphics.Picture;
import sorting.visualizer.sorting.MasterSorter;
import sorting.visualizer.sorting.Pixel;
import sorting.visualizer.sorting.SortingMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Display extends Canvas implements Runnable {
    private static final long serialVersionUID = 1L;
    private JFrame frame;
    private Thread thread;
    private static String title = "Sorting Visualizer";
    private int width = 1000;
    private int height = 600;
    private boolean running = false, sorted = false;
    private Picture picture;

    private BufferedImage image;
    private int[] pixels;

    private Pixel[] myPixels;

    public Display() {
        frame = new JFrame();

        picture = new Picture("res/images/pikachu.png");

        width = picture.getWidth();
        height = picture.getHeight();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels =  ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        myPixels = new Pixel[width * height];

        Dimension size = new Dimension(400, (int) (400.0 / width * height));
        this.setPreferredSize(size);

        initPixels();
    }

    private void initPixels() {
        for (int i = 0; i < pixels.length ; i++) {
            myPixels[i] = new Pixel(picture.getPixels()[i], i);
        }

        randomizePixels();
    }

    private void randomizePixels() {
        ArrayList<Pixel> pixelList = new ArrayList<>();

        for (int i = 0; i < myPixels.length ; i++) {
           pixelList.add(myPixels[i]);
        }

        Collections.shuffle(pixelList);

        for (int i = 0; i < myPixels.length ; i++) {
            myPixels[i]= pixelList.get(i);
        }
    }

    public static void main(String[] args) {
        Display display = new Display();
        display.frame.setTitle(title);
        display.frame.add(display);
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null);
        display.frame.setVisible(true);

        display.start();
    }
    public synchronized void start() {
        running = true;
        thread = new Thread(this, "Display");
        thread.start();
    }

    public synchronized void stop(){
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 400;
        double alpha = 0;
        int frames = 0;
        int updates = 0;
        while (running) {
            long now = System.nanoTime();
            alpha += (now - lastTime) / ns;
            lastTime = now;
            while (alpha >= 1) {
                update();
                updates++;
                alpha--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frame.setTitle(title + " | " + frames + " fps");
                frames = 0;
                updates = 0;
            }
        }
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = myPixels[i].color;
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        bs.show();

    }
    private void update(){
        if (!sorted) {
            sorted = MasterSorter.sort(myPixels, SortingMethods.BubbleSort, 50000);
        }
    }
}
