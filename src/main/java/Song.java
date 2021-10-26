/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Annaleise
 * @author Emily
 */

/**
 *The Song class contains private variables for the Path and Length for each member of the Song Class.
 */

public class Song implements Item{

    private String path;
    private int time;
    
    /**
    *The Song Function creates an instance of the Song class for each class member.
    */
    public Song(String path, int time) 
    {
        this.path= path;
        this.time = time;
    }
 
 
    /**
    *The getPath Function allows the path variable to be accessed by other classes.
    */
    public String getPath()
    {
        return path;
    }
 
    /**
    *The getSongLength Function allows the songLength variable to be accessed by other classes.
    */
    public int getSongLength()
    {
        return time;
    }
    
}
