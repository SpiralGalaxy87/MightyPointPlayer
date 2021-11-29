package pkgImageTransitions;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
   
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;
  
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import displayImage.DisplayImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
       

//=============================================================================
/** Class: ImageViewer
 *  Purpose: This class implements the main window for an image viewer utility.
 *    It allows the user to select a directory of images (.jpg and/or .gif)
 *    and display them sequentially.  The user can switch images by clicking
 *    a button to move forward or backward in the list of images or by 
 *    using a timer to produce a slideshow.
 *  Author: Dr. Rick Coleman
 *  Date: April 2008
 */
//=============================================================================
public class ImageTransitionsMain extends JFrame
{
	/** Programmer ID */
	public String m_sID = "Dr. Rick Coleman";
	
	/** Main screen width - based on screen width */
	public int m_iScnWidth;
	
	/** Main screen height - based on screen height */
	public int m_iScnHeight;
		
	/** Panel displaying the images */
	public ImagePanel m_ImagePanel;
	
	/** Panel holding the buttons */
	private JPanel m_ButtonPanel;
	
	/** Display Options button */
	private JButton m_DisplayOptionsBtn;
	
	/** Select image directory button */
	private JButton m_SelectImageDirBtn;
	
	/** Switch to previous image button */
	private JButton m_PrevImageBtn;
	
	/** Switch to next image button */
	private JButton m_NextImageBtn;
	
	/** Exit button */
	private JButton m_ExitBtn;
	
	//------------------------------------------
	// Display option variables
	//------------------------------------------
	/** Scale images flag */
	private boolean m_bScaleImages = true;
	
	/** Show image types flag. Default (3) is show both */
	private int m_iShowTypes = 3;
	
	/** Change images manually flag */
	private boolean m_bChangeManually = true;
	
	/** Time delay is using timer to change */
	private int m_iTimeDelay = 5;
	
	//------------------------------------------
	// Miscellaneous variables
	//------------------------------------------
	/** Image directory to show */
	private String m_sImageDir;
        
        /** Slideshow File **/
        private String m_sSlideshowFile;
	
	/** Vector of image names */
	private Vector<String> m_vImageNames = null;
        private Vector<String> m_vSoundNames = null;
	
	/** Index of the current image */
	private int m_iCurImageIdx;
        private int m_iCurSoundIdx;
	
	/** Image currently displayed */
	private BufferedImage  m_TheImage = null;
        //private SimpleAudioPlayer m_TheSound = null;
	/** Timer for slideshows */
	private Timer m_SSTimer;
        
         Long currentFrame;
         Clip clip;
         String status;
         AudioInputStream audioInputStream;
         static String filePath;
         
	//---------------------------------------------------
	/** Default constructor */
	//---------------------------------------------------
	public ImageTransitionsMain()
	{
		//------------------------------------------
		// Set all parameters for this JFrame object
		//------------------------------------------
		
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        m_iScnWidth = d.width - 100;
        m_iScnHeight = d.height - 100;
        
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocation(5, 5);
        this.setTitle("Slide Show Transitions Demonstration");
		this.setSize(m_iScnWidth, m_iScnHeight);
		this.setResizable(false);
		this.getContentPane().setLayout(null); // We'll do our own layouts, thank you.
		this.getContentPane().setBackground(Color.gray); // Set visible area to gray

		// Create the image panel
		m_ImagePanel = new ImagePanel(this);
		this.getContentPane().add(m_ImagePanel); // Add the panel to the window

		// Create the button panel
		m_ButtonPanel = new JPanel();
		m_ButtonPanel.setSize(this.getSize().width, 100);
		m_ButtonPanel.setLocation(0, this.getSize().height - 100);
		m_ButtonPanel.setBackground(Color.lightGray); // Set the panel color
		m_ButtonPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		// Use the default Flow Layout manager
		this.getContentPane().add(m_ButtonPanel);
		
		// Create the Display Options button
		m_DisplayOptionsBtn = new JButton(new ImageIcon("C:\\Users\\Taylor\\Desktop\\MightyPointPlayer-main\\MightyPointPlayer-main\\src\\main\\java\\pkgImageTransitions\\DisplayOptions.jpg"));
//		m_DisplayOptionsBtn = new JButton(new ImageIcon(getClass().getResource("DisplayOptions.jpg")));

//		m_DisplayOptionsBtn.setPreferredSize(new Dimension(40, 40));
//		m_DisplayOptionsBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
//		m_DisplayOptionsBtn.setToolTipText("Click to set display options.");
//		m_DisplayOptionsBtn.addActionListener(
//				new ActionListener()
//				{
//					public void actionPerformed(ActionEvent e)
//					{
//						//	Handle setting the display options
//						setDisplayOptions();
//					}
//				});
//		m_ButtonPanel.add(m_DisplayOptionsBtn);	
		
		// Create the select image directory button
		m_SelectImageDirBtn = new JButton(new ImageIcon("C:\\Users\\Taylor\\Desktop\\MightyPointPlayer-main\\MightyPointPlayer-main\\src\\main\\java\\pkgImageTransitions\\OpenDirectory.jpg"));
//		m_SelectImageDirBtn = new JButton(new ImageIcon(getClass().getResource("OpenDirectory.jpg")));
		m_SelectImageDirBtn.setPreferredSize(new Dimension(40, 40));
		m_SelectImageDirBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_SelectImageDirBtn.setToolTipText("Click to select directory of images to view.");
		m_SelectImageDirBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						//	Handle getting the image directory to show
						getImageDir();
						if(m_sSlideshowFile != null)
						{
                                                   
                                                       buildImageList();
                                                       showImage(m_iCurImageIdx); // Show first image
                                                        filePath = "C:\\Users\\Taylor\\Desktop\\MightyPointPlayer-main\\MightyPointPlayer-main\\src\\main\\java\\pkgImageTransitions\\ccr.wav";
                                                        try{
                                                        SimpleAudioPlayer audioPlayer = new SimpleAudioPlayer();
                                                        
                                                        audioPlayer.play();
                                                        }
                                                        catch (Exception ex)
                                                        {
                                                             System.out.println("Error with playing sound.");
            ex.printStackTrace();                            ex.printStackTrace();
                                                        }
                                                       /*buildSoundsList();
                                                       System.out.print("sound built");
                                                        try {
                                                    playSound(m_iCurSoundIdx);
                                                    
                                                     System.out.print("sound shoulda played");
                                                } catch (UnsupportedAudioFileException ex) {
                                                    Logger.getLogger(ImageTransitionsMain.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (IOException ex) {
                                                    Logger.getLogger(ImageTransitionsMain.class.getName()).log(Level.SEVERE, null, ex);
                                                } catch (LineUnavailableException ex) {
                                                    Logger.getLogger(ImageTransitionsMain.class.getName()).log(Level.SEVERE, null, ex);
                                                }*/
                                                 
						}
						// Are we doing a slideshow with timer?
						if(!m_bChangeManually)
						{
							doTimerSlideShow();
						}
					}
				});
		m_ButtonPanel.add(m_SelectImageDirBtn);	
		
		// Create the previous image button
		m_PrevImageBtn = new JButton(new ImageIcon("C:\\Users\\Taylor\\Desktop\\MightyPointPlayer-main\\MightyPointPlayer-main\\src\\main\\java\\pkgImageTransitions\\BackArrow.jpg"));
//		m_PrevImageBtn = new JButton(new ImageIcon(getClass().getResource("BackArrow.jpg")));
		m_PrevImageBtn.setPreferredSize(new Dimension(40, 40));
		m_PrevImageBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_PrevImageBtn.setToolTipText("View previous image.");
		m_PrevImageBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						//	Show the previous image
						showPreviousImage();
					}
				});
		m_ButtonPanel.add(m_PrevImageBtn);	
		
		// Create the next image button
		m_NextImageBtn = new JButton(new ImageIcon("C:\\Users\\Taylor\\Desktop\\MightyPointPlayer-main\\MightyPointPlayer-main\\src\\main\\java\\pkgImageTransitions\\NextArrow.jpg"));
//		m_NextImageBtn = new JButton(new ImageIcon(getClass().getResource("NextArrow.jpg")));
		m_NextImageBtn.setPreferredSize(new Dimension(40, 40));
		m_NextImageBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_NextImageBtn.setToolTipText("View next image.");
		m_NextImageBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						//	Show the next image
						showNextImage();
                                             
                                            
					}
				});
		m_ButtonPanel.add(m_NextImageBtn);	

		// Create the exit button
		m_ExitBtn = new JButton(new ImageIcon("C:\\Users\\Taylor\\Desktop\\MightyPointPlayer-main\\MightyPointPlayer-main\\src\\main\\java\\pkgImageTransitions\\Exit.jpg"));
//		m_ExitBtn = new JButton(new ImageIcon(getClass().getResource("Exit.jpg")));
		m_ExitBtn.setPreferredSize(new Dimension(40, 40));
		m_ExitBtn.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		m_ExitBtn.setToolTipText("Click to exit the application.");
		m_ExitBtn.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						// Exit the application with status=0 (normal exit)
						System.exit(0);
					}
				});
		m_ButtonPanel.add(m_ExitBtn);	
		
		// Make the window visible
		this.setVisible(true);
	}

	//----------------------------------------------------------------------
	/** Show a dialog box for the user to set the display options */
	//----------------------------------------------------------------------
	private void setDisplayOptions()
	{
		int retVal;
		
		// Create and show a dialog box
		SetDisplayOptionsDlg dlg = new SetDisplayOptionsDlg(this, true);
		dlg.setVisible(true); // show it
		retVal = dlg.getExitStatus();
		if(retVal == 0) // If the user clicked OK get the values
		{
			m_bScaleImages = dlg.getScaleImage();
			m_iShowTypes = dlg.getShowTypes();
			m_bChangeManually = dlg.getChangeManually();
			m_iTimeDelay = dlg.getTimeDelay();
		}
		dlg.dispose(); // Destroy the dialog box
	}
	
	//----------------------------------------------------------------------
	/** Show an open file dialog box in order to get the directory of
	 *   images to display. */
	//----------------------------------------------------------------------
	private void getImageDir()
	{
		int retValue;	// Return value from the JFileChooser
		
	     JFileChooser chooser = new JFileChooser();	// Create the file chooser dialog box
	     chooser.setDialogTitle("Select Slideshow File"); // Set dialog title
	     chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Only select dirs
	     chooser.setApproveButtonText("Select");
	     retValue = chooser.showOpenDialog(this); // Show the dialog box
	     if(retValue == JFileChooser.APPROVE_OPTION) // User selected a file
	     {
	    	 // Got a directory so get it's full path
	    	 m_sSlideshowFile = chooser.getSelectedFile().getAbsolutePath();
	     }
		System.out.println("File: " + m_sSlideshowFile);
	}
	
	//----------------------------------------------------------------------
	/** Build the list of images to show */
	//----------------------------------------------------------------------
	private void buildImageList()
	{
            System.out.println("this ran");
        // Create the vector of names
        if(m_vImageNames != null) // If we already have one
        	m_vImageNames.removeAllElements(); // Clean it out
        else                      // If we don't have one
        	m_vImageNames = new Vector(); // Create a new one.
               
        
        try(FileReader fileReader = new FileReader(m_sSlideshowFile)){
            
            JSONParser jsonParser = new JSONParser();

            // Read JSON file
            Object obj = jsonParser.parse(fileReader);

            JSONObject jo = (JSONObject) obj;
            
            //set manual change boolean
            System.out.println(jo.get("changeManually"));  
            m_bChangeManually = jo.get("changeManually").equals("true");
            System.out.println(m_bChangeManually);
            
            //set image duration
            m_iTimeDelay = (int) (long) jo.get("imageDuration");
            
            
            JSONArray images = (JSONArray) jo.get("images");
            images.forEach( image -> m_vImageNames.add((String) image));
            
        
            
        } catch(FileNotFoundException e){
            System.err.println("FileNotFoundException: " + e.getMessage());
        } catch(IOException e){
            System.err.println("IOException: " + e.getMessage());
        } catch(ParseException e){
            System.err.println("ParseException: " + e.getMessage());
        }
        
        m_iCurImageIdx = 0; // Initialize the current image index
    }
        
	
	//----------------------------------------------------------------------
	/** Show the image at index. */
	//----------------------------------------------------------------------
	private void showImage(int idx)
	{
        File		imageFile; // the jpg or gif file
		// Make sure we have an image file
        if((m_vImageNames.size() < 0) || (idx >= m_vImageNames.size()))
        {
			JOptionPane.showMessageDialog(this, 
					"Error: Unable to display image " + idx + ". Does not exist.", 
					"Error Loading Image", JOptionPane.ERROR_MESSAGE);
			return;
        }
        imageFile = new File((String)(m_vImageNames.elementAt(idx)));
		if(!imageFile.exists()) // If we failed to opened it
		{
			JOptionPane.showMessageDialog(this, 
					"Error: Unable to load " + (String)(m_vImageNames.elementAt(idx)), 
					"Error Loading Image", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Load the image
        // Use ImageIO and pass a BufferedImage.TYPE_INT_RGB to ImagePanel
        if(m_TheImage != null)
        	m_TheImage = null; // Clear the previous image
        try
        {
//       
                
            DisplayImage newImg = new DisplayImage(this.getSize().width, this.getSize().height);
            BufferedImage dispImg = newImg.getDisplayImage(m_vImageNames.elementAt(idx));

            m_TheImage = dispImg;
       
            //m_TheImage = bi2;
     
        }
        catch (IOException e)
        {
			JOptionPane.showMessageDialog(this, 
					"Error: Unable to load " + (String)(m_vImageNames.elementAt(idx)), 
					"Error Loading Image", JOptionPane.ERROR_MESSAGE);
			return;
        }
        m_ImagePanel.setImage(m_TheImage);
	}
	
	//----------------------------------------------------------------------
	/** Show the previous image. */
	//----------------------------------------------------------------------
	private void showPreviousImage()
	{
		if(m_iCurImageIdx > 0)
		{
			m_iCurImageIdx--; // Decrement to previous image
			showImage(m_iCurImageIdx); // Show it
		}
	}
	
	//----------------------------------------------------------------------
	/** Show the next image. */
	//----------------------------------------------------------------------
	private void showNextImage()
	{
		if(m_iCurImageIdx < (m_vImageNames.size() - 1))
		{
			m_iCurImageIdx++; // Increment to next image
			showImage(m_iCurImageIdx); // Show it
		}
	}

	//----------------------------------------------------------------------
	/** Show the next image. */
	//----------------------------------------------------------------------
	private void doTimerSlideShow()
	{
		// Disable the previous and next buttons while the slideshow runs
		m_PrevImageBtn.setEnabled(false);
		m_NextImageBtn.setEnabled(false);
		
		// Create a javax.swing.timer
		m_SSTimer = new Timer(m_iTimeDelay * 1000,
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Show the next image
					if(m_iCurImageIdx < m_vImageNames.size() - 1)
					{
						showNextImage();
					}
					else
					{
						m_SSTimer.stop();
						// Enable the previous and next buttons again
						m_PrevImageBtn.setEnabled(true);
						m_NextImageBtn.setEnabled(true);
					}
				}
			});
		m_SSTimer.setRepeats(true); // Repeat till we kill it
		m_SSTimer.start();  // Start the timer
	}

        
        private void buildSoundsList()
        {
             // Create the vector of names
        if(m_vSoundNames != null) // If we already have one
        	m_vSoundNames.removeAllElements(); // Clean it out
        else                      // If we don't have one
        	m_vSoundNames = new Vector(); // Create a new one.
        try(FileReader fileReader = new FileReader(m_sSlideshowFile)){
         JSONParser jsonParser = new JSONParser();

        // Read JSON file
        Object obj = jsonParser.parse(fileReader);

        JSONObject jo = (JSONObject) obj;
        JSONArray sounds = (JSONArray) jo.get("sounds");
       
        
        sounds.forEach(sound -> m_vSoundNames.add((String) sound));
        System.out.print(m_vSoundNames);
        }
        
        catch(FileNotFoundException e){
        System.err.println("FileNotFoundException: " + e.getMessage());
        } catch(IOException e){
        System.err.println("IOException: " + e.getMessage());
        } catch(ParseException e){
        System.err.println("ParseException: " + e.getMessage());
        }

        m_iCurSoundIdx = 0; // Initialize the current sound index
        }
        
        private void playSound(int idx) throws UnsupportedAudioFileException, IOException, LineUnavailableException
        {
            File soundFile;
            if(m_vSoundNames.size() < 0 || (idx >= m_vSoundNames.size()))
            {
                System.out.print("error sound");
                JOptionPane.showMessageDialog(this, "Error: Unable to load sound"+idx+", does not exist.","Error Loading Sound",JOptionPane.ERROR_MESSAGE);
                return;     
            }
            soundFile = new File((String)m_vSoundNames.elementAt(idx));
            if(!soundFile.exists())
            {
                System.out.print("found sound file");
                JOptionPane.showMessageDialog(this,
                "Error: Unable to load " + (String)(m_vSoundNames.elementAt(idx)),
                "Error Loading Image", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("Sounds");
            /* audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            //m_vSoundNames.add(filePath);
            */
            System.out.print("sound shoulda played");
           // m_TheSound.play();
            
        
        }
	//----------------------------------------------------------------------
	/** Main function for this demonstration
	 * @param args - Array of strings from the command line
	 */
	//----------------------------------------------------------------------
	public static void main(String[] args) 
	{
		// When you start this application this function gets called by the
		//  operating system.  Main just creates an ImageViewer object.
		//  To follow the execution trail from here go to the ImageViewer
		//  constructor.
            
		ImageTransitionsMain IV = new ImageTransitionsMain();
	}

}
