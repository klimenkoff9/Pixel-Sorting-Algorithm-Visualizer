package sorting.visualizer.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Picture {
    private int[] pixels;
    private BufferedImage image;
    private String path;

    public Picture(String path) {
        this.path = path;
        System.out.println(this.path);
        load();
    }

    private void load() {
        try {
            image = ImageIO.read(new FileInputStream(this.path));
            int w = image.getWidth();
            int h = image.getHeight();
            pixels = new int[w * h];
            image.getRGB(0,0, w, h, pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getPixels(){
        return pixels;
    }
    // Override
    public int getWidth(){
        return image.getWidth();
    }
    // Override
    public int getHeight() {
        return image.getHeight();
    }
}
