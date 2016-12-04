package objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

@SuppressWarnings({ "serial" })
public class ShortcutDirectory extends HashMap<String, Shortcut>
{
	public ShortcutDirectory()
	{
		super();
	}
	
	public void addShortcut(String shortcut, String filePath, boolean environmentBased)
	{
		this.put(shortcut, new Shortcut(filePath, environmentBased));
	}
	
	public void saveShortcutDirectory()
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(new File("shortcut.directory"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
	        oos.writeObject(this);
	        fos.close();
	        oos.close();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
