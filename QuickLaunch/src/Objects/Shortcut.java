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
}
