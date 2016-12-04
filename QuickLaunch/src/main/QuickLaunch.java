package main;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import userinterfaces.Launcher;

public class QuickLaunch
{
	public static void main(String[] args)
	{
		final Launcher shortcutLauncher = new Launcher();
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL, (int)'J');
		System.out.println(JIntellitype.isJIntellitypeSupported());
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener()
		{
            @Override
            public void onHotKey(int arg0)
            {
                // TODO Auto-generated method stub
                if (arg0 == 1)
                {
                    shortcutLauncher.toFront();
                }

            }
        });
	}
}
