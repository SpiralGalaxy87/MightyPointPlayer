/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Annaleise
 */
public class SlideshowThread implements Runnable {
    
    private PlayerGUI gui;
    //private int curImage;
    private int imageTime;
    private int timeRemaining;
    
    public SlideshowThread(PlayerGUI gui, int imageTime) {
        this.gui = gui;
        // this.curImage = 0;
        this.imageTime = imageTime;
        this.timeRemaining = imageTime;
    }
    
    public void run(){
        try { 
            Thread.sleep((long) this.timeRemaining);
            this.timeRemaining = 0;
            gui.nextImage();
            }
        catch (InterruptedException ex) {
            
        }
        
        while(true){
            try {
                Thread.sleep((long)(this.imageTime));
                gui.nextImage();
            } 
            catch (InterruptedException ex) {
                this.timeRemaining = this.imageTime;
                return;
            }
        }
    }
}
