package quicklaunch.main;

import com.melloware.jintellitype.JIntellitype;
import quicklaunch.guis.InputBox;

public class Main {
    static InputBox inBox;

    public static void main(String[] args) {
        inBox = new InputBox();
        addIntellitypeListener();
    }

    public static void addIntellitypeListener() {
        JIntellitype.getInstance();
        JIntellitype.getInstance().addIntellitypeListener(inBox);
        JIntellitype.getInstance().addHotKeyListener(inBox);
        JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL, 'J');
    }
}
