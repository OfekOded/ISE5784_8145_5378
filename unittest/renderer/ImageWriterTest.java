package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void initialImageTest() {
        ImageWriter image = new ImageWriter("image", 800, 500);
        Color yellow = new Color(255, 255, 0);
        Color red = new Color(255, 0, 0);
        for (int i = 0; i < 800; i++){
            for (int j = 0; j < 500; j++){
                if(i%50==0 || i==799 || j%50==0 || j==499)
                    image.writePixel(i, j, red);
                else
                  image.writePixel(i, j, yellow);
            }
        }
        image.writeToImage();
    }
}