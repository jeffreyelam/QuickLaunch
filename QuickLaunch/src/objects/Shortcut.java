package Objects;

public class Shortcut
{
	private String filePath;
	private boolean isEnvironmentBased;
	
	public Shortcut(String path, boolean environmentBased)
	{
		this.filePath = path;
		this.isEnvironmentBased = environmentBased;
	}
	
	public String getFilePath()
	{
		return this.filePath;
	}
	
	public boolean getEnviromentBased()
	{
		return this.isEnvironmentBased;
	}
	
	public void updateFilePath(String path)
	{
		this.filePath = path;
	}
	
	public void updateEnvironmentBased(boolean environmentBased)
	{
		this.isEnvironmentBased = environmentBased;
	}
}
