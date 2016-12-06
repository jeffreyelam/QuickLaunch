package userinterfaces;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.ShortcutDirectory;

@SuppressWarnings("serial")
public class Launcher extends JFrame
{
	
	private ShortcutDirectory directory;
	private JLabel envLabel;
	private String currEnvironment;
	
	public Launcher()
	{
		this.setUndecorated(true);

		//Create text box
		final JTextField input = new JTextField();
		Font font = new Font("SansSerif", Font.BOLD, 20);
		this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.BLACK));
		input.setFont(font);
        input.setPreferredSize( new Dimension( 150, 30 ) );
        
        this.envLabel = new JLabel("STAGING  ");
        
        //Create panel to hold the input box and label
        JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(225, 40));
        contentPane.add(this.envLabel);
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
        
        this.currEnvironment = "Y:\\";
        loadShortcutDirectory();
	}
	
	private void closeLauncher()
	{
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
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
			envLabel.setText("STAGING  ");
			this.currEnvironment = "Y:\\";
		}
		else if(shortcut.equals("test"))
		{
			//Switch to test environment
			envLabel.setText("TEST     ");
			this.currEnvironment = "X:\\Test\\";
		}
		else if(shortcut.equals("dev"))
		{
			//Switch to dev environment
			envLabel.setText("DEV      ");
			this.currEnvironment = "X:\\Dev\\";
		}
		else if(shortcut.equals("local"))
		{
			//Switch to local environment
			envLabel.setText("LOCAL    ");
			this.currEnvironment = "C:\\inetpub\\wwwroot\\";
		}
		else if(shortcut.equals("merge"))
		{
			//Launch merge sub-application
		}
		else if(shortcut.equals("add"))
		{
			//Launch add-shortcut functionality
			final AddShortcut shortcutAdder = new AddShortcut();
			shortcutAdder.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                addShortcutToDirectory(shortcutAdder.getShortcutText(),shortcutAdder.getPathText(),shortcutAdder.getEnvironmentCheck());
	            }
	        });
		}
		else if(shortcut.equals("delete"))
		{
			//Launch delete-shortcut functionality
		}
		else if(this.directory.get(shortcut) != null && this.directory.get(shortcut).getEnviromentBased())
		{
			String envShortcut = this.directory.get(shortcut).getFilePath().replace("Y:\\", "");
			envShortcut = envShortcut.replace("X:\\Test\\", "");
			envShortcut = envShortcut.replace("X:\\Dev\\", "");
			envShortcut = envShortcut.replace("C:\\inetpub\\wwwroot\\","");
			try
			{
				File file = new File(this.currEnvironment + envShortcut);
				Desktop.getDesktop().open(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else if(this.directory.get(shortcut) != null)
		{
			try
			{
				File file = new File(this.directory.get(shortcut).getFilePath());
				Desktop.getDesktop().open(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				File file = new File(this.currEnvironment + "mys_shared\\" + shortcut);
				Desktop.getDesktop().open(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private void addShortcutToDirectory(String shortcut, String path, boolean environment)
	{
		this.directory.addShortcut(shortcut, path, environment);
	}
}