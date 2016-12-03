package userinterfaces;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Launcher extends JFrame
{
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
            	if(input.getText().toLowerCase().equals("exit"))
            	{
            		closeLauncher();
            	}
            	//Clear out the input box text
            	input.setText("");
            }
        });
	}
	
	private void closeLauncher()
	{
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
        
    public static class FrameDragListener extends MouseAdapter
    {

        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame)
        {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e)
        {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e)
        {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e)
        {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }
}