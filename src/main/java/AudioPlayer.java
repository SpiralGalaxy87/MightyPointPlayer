
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Annaleise
 */
public class AudioPlayer {
    /**
    *The playSong Function creates an audio stream variable to play songs from the song catalog.
    * @param songID an integer to identify each song
    * @throws UnsupportedAudioFileException
    * @throws IOException
    * @throws LineUnavailableException
    */
    private Clip clip;
    
    public AudioPlayer(){
        
    }
    
    
    public void playSong(String path)throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        
        AudioInputStream audioInputStream;
        audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile()); //pass song information into audio stream variable
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start(); //start song
        
        //skip song
        
        long clipTimePos=clip.getMicrosecondPosition();//keeps sart position of song
        
        
        while(clipTimePos<clip.getMicrosecondLength()) //while current frame position is less than song length
        {
            
            if(clipTimePos<clip.getMicrosecondLength())
            {
                //sets up what the dialogue window will display
                JOptionPane jop = new JOptionPane();
                jop.setMessageType(JOptionPane.PLAIN_MESSAGE);
                jop.setMessage("Skip");
                JDialog dialog = jop.createDialog(null, "Click ok to skip");

	// Set a 2 second timer
                    new Thread(new Runnable() {
                            @Override
                    public void run() {
                        try {
                            Thread.sleep(15000);
                            } catch (Exception e) {}
                                dialog.dispose();
                                      }

            }).start();
            
            dialog.setVisible(true);//sets visibility of skip dialogue window
        
            clip.close();//skip song and break out of while loop
            break;
            }
            if(clip.getFramePosition()==clip.getMicrosecondLength())
            { 
            clip.close();//skip song and break out of while loop
            break;
            }
          break;
        }
  
    }
}
