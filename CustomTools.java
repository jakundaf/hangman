import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CustomTools {
    // create a JLabel with an image
    public static JLabel loadImage(String resource) {
        BufferedImage image;
        try {
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            return new JLabel(new ImageIcon(image));
        } catch (Exception e) {
            System.out.println("Image error: " + e);
        }
        return null;
    }
    // updates jlabel with image from string path
    public static void updateImage(JLabel imageContainer, String resource){
        BufferedImage image;
        try {
            InputStream inputStream = CustomTools.class.getResourceAsStream(resource);
            image = ImageIO.read(inputStream);
            imageContainer.setIcon(new ImageIcon(image));
            } catch (IOException e) {
            System.out.println("Reset image error: + " + e);
            }
        }

    public static String hideWord(String word) {
        String hiddenWord = "";
        for (int i = 0; i < word.length(); i++) {
            if (!(word.charAt(i) == ' ')) {
                hiddenWord += "*";
            } else {
                hiddenWord += " ";
            }
        }
        return hiddenWord;
    }

}
