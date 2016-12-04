package userinterfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddShortcut extends JFrame
{
	public AddShortcut()
	{
		//Create panel to hold the input boxes and labels
        JPanel contentPane = new JPanel();
        contentPane.setBackground(Color.BLACK);
        contentPane.setPreferredSize(new Dimension(200, 150));
        
		//Text label to define the add shortcut input box
		JLabel addLabel = new JLabel("SHORTCUT");
		contentPane.add(addLabel);
		
		//Create text box to add shortcut text
		final JTextField shortcutText = new JTextField();
		Font shortcutFont = new Font("SansSerif", Font.BOLD, 20);
		shortcutText.setFont(shortcutFont);
        shortcutText.setPreferredSize( new Dimension( 175, 30 ) );
        contentPane.add(shortcutText);
        
        //Text label to define the add path input box
        JLabel pathLabel = new JLabel("PATH");
        contentPane.add(pathLabel);
        
        //Create text box to add shortcut path
        final JTextField pathText = new JTextField();
        Font pathFont = new Font("SansSerif", Font.BOLD, 20);
        pathText.setFont(pathFont);
        pathText.setPreferredSize( new Dimension( 175, 30 ) );
        contentPane.add(pathText);
        
        JButton addBtn = new JButton("Add Shortcut");
        contentPane.add(addBtn);
       
        this.setContentPane(contentPane);

        //Show this JFrame        
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        
        shortcutText.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
            	shortcutText.setText("");
            }
        });
	}
}
