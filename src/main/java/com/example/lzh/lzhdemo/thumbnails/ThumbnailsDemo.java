package com.example.lzh.lzhdemo.thumbnails;


import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;


public class ThumbnailsDemo {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void test1() throws Exception {
        URL url = new URL("https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/baike/pic/item/8326cffc1e178a826cab63adf203738da977e82c.jpg");
        BufferedImage image = ImageIO.read(url);
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("height = " + height);
        System.out.println("width = " + width);
        int pos = Math.min(width, height);
        Thumbnails.of(url)
                .sourceRegion(Positions.CENTER, pos, pos)
                .size(WIDTH, HEIGHT)
                .toFile("src/main/java/com/example/lzh/lzhdemo/image/222_1_200x200.jpg");
    }
}
