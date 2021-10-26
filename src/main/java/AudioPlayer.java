/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException; 
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * author: Emily
 * 
 */

//public static ArrayList<Song> catalog = new ArrayList<Song>(); // Create an ArrayList object

/**
 *The Jukebox Class contains functions to build the song catalog, play and stop a song, and build and play a 3 song playlist.
 */
public class AudioPlayer
{
    public static ArrayList<Song> catalog = new ArrayList<Song>(); // Create an ArrayList object
    private static ArrayList<Integer> Songlist = new ArrayList<Integer>();//Create an ArrayList of playlist songs
    public int stopFlag;
    private Clip clip;
    
    public AudioPlayer()
    {    
    }
   
    /**
    *The buildCatalog Function reads song data from an external text file (SongInfo.txt) to create a catalog of all songs held by the jukebox.
    */
    public void buildCatalog (String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        FileReader fr=new FileReader(fileName); //create FileReader object
        BufferedReader br=new BufferedReader(fr); //create BufferedReader object to read lines into an array list
        String line="";
        String[] arrs=null; //initialize array
        int num=0;  //initialize integer to hold number of lines in SongInfo.txt file
        while ((line=br.readLine())!=null) //read lines in while not end of file
        {
            arrs=line.split(",");   //set comma as delimiter
            catalog.add(new Song(Integer.valueOf(arrs[0]), arrs[1], arrs[2], arrs[3], Integer.valueOf(arrs[4]))); //add song information from SongInfo.txt to catalog
            num++;
        }
        br.close();
        fr.close();

        for(int i=0 ; i< catalog.size(); i++) 
        {
            //Print out song catalog
            System.out.println("ID: " + catalog.get(i).getID() + " Title: " + catalog.get(i).getTitle() + " Artist: " + catalog.get(i).getArtist() + " Genre: " + catalog.get(i).getGenre());
        }      
    }
  
    /**
    *The playSong Function creates an audio stream variable to play songs from the song catalog.
    */
    public void playSong(int songID)throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        //Clip clip;
        stopFlag = 1;  
        AudioInputStream audioInputStream;
        audioInputStream = AudioSystem.getAudioInputStream(new File(catalog.get(songID).getTitle()+".wav").getAbsoluteFile()); //pass song information into audio stream variable
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start(); //start song
    
        try 
        {
            Thread.sleep(catalog.get(songID).getSongLength()*1000);
        } 
        catch(InterruptedException e)
        {}
    }
    /*Clip clip;
    AudioInputStream audioInputStream;
          
    audioInputStream = AudioSystem.getAudioInputStream(new File(catalog.get(songID).getTitle()+".wav").getAbsoluteFile()); 
    clip = AudioSystem.getClip();
    clip.open(audioInputStream); 
    clip.start();
   
    //searchCatalog

    //will change buildplaylist to take in 3 strings that look through an array of one song and find its properties so its easier to search
   
    //just in case i mess up*/
    
    
     /**
    *The stopSong Function stops, and then closes the audio clip.
    */
    public void stopSong()
    {    
        clip.stop();
        clip.close();
    }
    
    /**
    *The buildPlaylist Function stores 3 songs that are saved in order.
    */
    public void buildPlaylist(int songID1,int songID2, int songID3) // Builds a playlist. Needs to be linked with search
    { 
        int max=25;//number of total songs
        int playlistLength=3; //number of songs in a playlist
        if(songID1>max||songID2>max||songID3>max) //make sure the song exists by making sure the song number is less than 25
        {
            Songlist.set(0,null );
        }
        
        if(playlistLength > 0)
        {
            Songlist.add(songID1);// adds song to a song list for the playlist to reference
            Songlist.add(songID2);// adds song to a song list for the playlist to reference
            Songlist.add(songID3);// adds song to a song list for the playlist to reference
            playlistLength--;
        }
    }
    /**
    *The playPlaylist Function plays the 3 songs that make up the playlist, and then clears the playlist so that another can be created.
    */
    public void playPlaylist() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        for(int i=0;i<3;i++)  //playSong executes 3 times
        {
            playSong(Songlist.get(i));
        }
        Songlist.clear();
    }
}
    

