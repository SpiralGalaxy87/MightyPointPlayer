/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package displayImage;

/*
 * @Author Taylor
 * @Co-Author Roberto
 */

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;

/**
 * 
 * DisplayImage is a class that creates the objects that will hold information
 * for the thumbnails to be used
 */
public class DisplayImage {
    private String imagePath;
    private ImageIcon image;
    private boolean isValid = true;
    private int height;
    private int width;
    /**
     * Constructor for DisplayImage
     */
    public DisplayImage(int w, int h){
        width = w;
        height = h;
    }
    /**
     * getDisplayImage acquires the image at the given path, and creates an icon
     * for it that can then be accessed by the DisplayImage object
     * @param path is the path to the image
     * @return is used to return the icon for the thumbnail for the image
     * @throws IOException 
     */
    public BufferedImage getDisplayImage(String path) throws IOException {
        
        BufferedImage img = ImageIO.read(new File(path));
        BufferedImage thumbnail = resize(img,height,width);                          // 100x150 is preferred size for thumbnails
        if(thumbnail == null)
        {
            return null;
        }
        //ImageIcon icon = new ImageIcon(thumbnail);                                //display thumnail on screen
        return thumbnail;
    }
    
    /**
     * resize takes a JPEG image via its file path, along with the new height and width to change the image to
     * @param img is the image that needs to be resized
     * @param height is the requested height for the image
     * @param width is the requested width for the image
     * @return the resized BufferedImage
     */
    private static BufferedImage resize(BufferedImage img, int height, int width) {
        try{
            Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();
            return resized;
        }catch(NullPointerException exception){
            return null;
        }
    }
    /**
     * setImagePath sets the path for the image for the given DisplayImage object
     * @param path is the path of the image
     */
    public void setImagePath(String path){
        imagePath = path;
    }
    /**
     * Acquires the path of the image in the given DisplayImage object
     * @return the image path
     */
    public String getImagePath(){
        return imagePath;
    }
    /**
     * Acquire the image from the given DisplayImage object
     * @return the image of the given DisplayImage object
     */
  /*  public ImageIcon getImage(){
        try{
            image = getDisplayImage(imagePath);
        }
        catch(IOException exception){    
        }
        return image;
    }  */
}