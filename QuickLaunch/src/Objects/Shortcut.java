package Objects;

public class Shortcut
{
	private String fileShortcut;
	private String filePath;
	private boolean isEnvironmentBased;
	
	public Shortcut(String shortcut, String path, boolean environmentBased)
	{
		this.fileShortcut = shortcut;
		this.filePath = path;
		this.isEnvironmentBased = environmentBased;
	}
	
	public String getFilePath()
	{
		return this.filePath;
	}
	
	public String getFileShortcut()
	{
		return this.fileShortcut;
	}
	
	public boolean getEnviromentBased()
	{
		return this.isEnvironmentBased;
	}
	
	public void updateFilePath(String path)
	{
		this.filePath = path;
	}
	
	public void updateFileShortcut(String shortcut)
	{
		this.fileShortcut = shortcut;
	}
	
	public void updateEnvironmentBased(boolean environmentBased)
	{
		this.isEnvironmentBased = environmentBased;
	}
}
