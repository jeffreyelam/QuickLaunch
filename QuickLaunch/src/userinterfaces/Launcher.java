package userinterfaces;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.ShortcutDirectory;

@SuppressWarnings("serial")
public class Launcher extends JFrame
{
	
	private ShortcutDirectory directory;
	public Launcher()
	{
		this.setUndecorated(true);

		//Create text box
		final JTextField input = new JTextField();
		Font font = new Font("SansSerif", Font.BOLD, 20);
		input.setFont(font);
        input.setPreferredSize( new Dimension( 150, 30 ) );
        
        JLabel envLabel = new JLabel("STAGING  ");
        
        //Create panel to hold the input box and label
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setPreferredSize(new Dimension(225, 40));
        contentPane.add(envLabel);
        contentPane.add(input);
        this.setContentPane(contentPane);

        //Create drag listener that moves the JFrame
        FrameDragListener frameDragListener = new FrameDragListener(this);
        this.addMouseListener(frameDragListener);
        this.addMouseMotionListener(frameDragListener);

        //Show this JFrame        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        input.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
            	launchShortcut(input.getText().toLowerCase());
            	//Clear out the input box text
            	input.setText("");
            }
        });
        
        loadShortcutDirectory();
	}
	
	private void closeLauncher()
	{
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	@SuppressWarnings("unchecked")
	private void loadShortcutDirectory()
	{
		File shortcutDirectoryFile = new File("shortcut.directory");
		if(shortcutDirectoryFile.exists() && !shortcutDirectoryFile.isDirectory())
		{ 
		    //load shortcutdirectory into object
			try
			{
				FileInputStream fis = new FileInputStream(shortcutDirectoryFile);
				ObjectInputStream ois = new ObjectInputStream(fis);
				this.directory = (ShortcutDirectory) ois.readObject();
	            ois.close();
	            fis.close();
			} 
			catch (ClassNotFoundException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			this.directory = new ShortcutDirectory();
		}
	}
	
	private void launchShortcut(String shortcut)
	{
		if(shortcut.equals("exit"))
    	{
			//Close application
    		closeLauncher();
    	}
		else if(shortcut.equals("staging"))
		{
			//Switch to staging environment
		}
		else if(shortcut.equals("test"))
		{
			//Switch to test environment
		}
		else if(shortcut.equals("dev"))
		{
			//Switch to dev environment
		}
		else if(shortcut.equals("local"))
		{
			//Switch to local environment
		}
		else if(shortcut.equals("merge"))
		{
			//Launch merge sub-application
		}
		else if(shortcut.equals("add"))
		{
			//Launch add-shortcut functionality
		}
		else if(shortcut.equals("delete"))
		{
			//Launch delete-shortcut functionality
		}
	}
}