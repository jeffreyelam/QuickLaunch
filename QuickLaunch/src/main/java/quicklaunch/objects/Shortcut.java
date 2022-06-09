package quicklaunch.objects;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class Shortcut implements Comparable, Serializable {
    private static final long serialVersionUID = 1L;

    File file;

    URL urlPath;

    String shortcut;

    public Shortcut(String sCut, File fName, URL url) {
        this.file = fName;
        this.shortcut = sCut;
        this.urlPath = url;
    }

    public File getFile() {
        return this.file;
    }

    public URL getURL() {
        return this.urlPath;
    }

    public String getShortcut() {
        return this.shortcut;
    }

    public Icon getIcon() {
        Icon ico = FileSystemView.getFileSystemView().getSystemIcon(this.file);
        return ico;
    }

    public String toString() {
        return "<html><i>" + this.shortcut + "</i><br><br>" + ((this.file != null) ? this.file.getAbsolutePath() : this.urlPath.toString()) + "<br></html>";
    }

    public int compareTo(Object o) {
        Shortcut s = (Shortcut)o;
        return this.shortcut.compareTo(s.shortcut);
    }
}
