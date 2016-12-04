package userinterfaces;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddShortcut extends JFrame
{
	
	private JTextField shortcutText;
	private JTextField pathText;
	private JCheckBox environmentBasedCheck;
	public AddShortcut()
	{
		//Create panel to hold the input boxes and labels
        JPanel contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(200, 175));
        
		//Text label to define the add shortcut input box
		JLabel addLabel = new JLabel("SHORTCUT");
		contentPane.add(addLabel);
		
		//Create text box to add shortcut text
		shortcutText = new JTextField();
		Font shortcutFont = new Font("SansSerif", Font.BOLD, 20);
		shortcutText.setFont(shortcutFont);
        shortcutText.setPreferredSize( new Dimension( 175, 30 ) );
        contentPane.add(shortcutText);
        
        //Text label to define the add path input box
        JLabel pathLabel = new JLabel("PATH");
        contentPane.add(pathLabel);
        
        //Create text box to add shortcut path
        pathText = new JTextField();
        Font pathFont = new Font("SansSerif", Font.BOLD, 20);
        pathText.setFont(pathFont);
        pathText.setPreferredSize( new Dimension( 175, 30 ) );
        contentPane.add(pathText);
        
        JLabel environmentCheckLabel = new JLabel("ENVIRONMENT BASED?");
        contentPane.add(environmentCheckLabel);
        
        environmentBasedCheck = new JCheckBox();
        contentPane.add(environmentBasedCheck);
        
        JButton addBtn = new JButton("Add Shortcut");
        contentPane.add(addBtn);
       
        this.setContentPane(contentPane);

        //Show this JFrame        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        addBtn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
            	closeAddShortcutFrame();
            }
        });
	}
	
	private void closeAddShortcutFrame()
	{
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public String getShortcutText()
	{
		return this.shortcutText.getText();
	}
	
	public String getPathText()
	{
		return this.pathText.getText();
	}
	
	public boolean getEnvironmentCheck()
	{
		return this.environmentBasedCheck.isSelected();
	}
}
