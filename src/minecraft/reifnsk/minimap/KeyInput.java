package reifnsk.minimap;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import org.lwjgl.input.Keyboard;

public enum KeyInput
{
    MENU_KEY(50),
    TOGGLE_ENABLE(0),
    TOGGLE_RENDER_TYPE(0),
    TOGGLE_ZOOM(44),
    TOGGLE_LARGE_MAP(45),
    TOGGLE_LARGE_MAP_LABEL(0),
    TOGGLE_WAYPOINTS_VISIBLE(0),
    TOGGLE_WAYPOINTS_MARKER(0),
    TOGGLE_WAYPOINTS_DIMENSION(0),
    TOGGLE_ENTITIES_RADAR(0),
    SET_WAYPOINT(46),
    WAYPOINT_LIST(0),
    ZOOM_IN(0),
    ZOOM_OUT(0);

    private static File configFile;
    private final int defaultKeyIndex;
    private String label;
    private int keyIndex;
    private boolean keyDown;
    private boolean oldKeyDown;

    private KeyInput(int j)
    {
        defaultKeyIndex = j;
        keyIndex = j;
        label = ReiMinimap.capitalize(name());
    }

    private KeyInput(String s1, int j)
    {
        label = s1;
        defaultKeyIndex = j;
        keyIndex = j;
    }

    public void setKey(int i)
    {
        if (i == 1)
        {
            i = 0;
        }

        if (i == 0 && this == MENU_KEY)
        {
            return;
        }

        if (i != 0)
        {
            KeyInput akeyinput[] = values();
            int j = akeyinput.length;
            int k = 0;

            do
            {
                if (k >= j)
                {
                    break;
                }

                KeyInput keyinput = akeyinput[k];

                if (keyinput.keyIndex == i)
                {
                    if (keyinput == MENU_KEY && keyIndex == 0)
                    {
                        return;
                    }

                    keyinput.keyIndex = keyIndex;
                    keyinput.keyDown = false;
                    keyinput.oldKeyDown = false;
                    break;
                }

                k++;
            }
            while (true);
        }

        keyIndex = i;
        keyDown = false;
        oldKeyDown = false;
    }

    public int getKey()
    {
        return keyIndex;
    }

    public String label()
    {
        return label;
    }

    public String getKeyName()
    {
        String s = Keyboard.getKeyName(keyIndex);
        return s != null ? ReiMinimap.capitalize(s) : String.format("#%02X", new Object[]
                {
                    Integer.valueOf(keyIndex)
                });
    }

    public void setKey(String s)
    {
        int i = Keyboard.getKeyIndex(s);

        if (s.startsWith("#"))
        {
            try
            {
                i = Integer.parseInt(s.substring(1), 16);
            }
            catch (Exception exception) { }
        }

        setKey(i);
    }

    public boolean isKeyDown()
    {
        return keyDown;
    }

    public boolean isKeyPush()
    {
        return keyDown && !oldKeyDown;
    }

    public boolean isKeyPushUp()
    {
        return !keyDown && oldKeyDown;
    }

    public static void update()
    {
        KeyInput akeyinput[] = values();
        int i = akeyinput.length;

        for (int j = 0; j < i; j++)
        {
            KeyInput keyinput = akeyinput[j];
            keyinput.oldKeyDown = keyinput.keyDown;
            keyinput.keyDown = keyinput.keyIndex != 0 && Keyboard.isKeyDown(keyinput.keyIndex);
        }
    }

    public static boolean saveKeyConfig()
    {
        PrintWriter printwriter = null;

        try
        {
            printwriter = new PrintWriter(configFile);
            KeyInput akeyinput[] = values();
            int i = akeyinput.length;

            for (int j = 0; j < i; j++)
            {
                KeyInput keyinput = akeyinput[j];
                printwriter.println(keyinput.toString());
            }
        }
        catch (Exception exception)
        {
            boolean flag = false;
            return flag;
        }
        finally
        {
            if (printwriter != null)
            {
                printwriter.flush();
                printwriter.close();
            }
        }

        return true;
    }

    public static void loadKeyConfig()
    {
        Scanner scanner = null;

        try
        {
            for (scanner = new Scanner(configFile); scanner.hasNextLine();)
            {
                try
                {
                    String as[] = scanner.nextLine().split(":");
                    valueOf(ReiMinimap.toUpperCase(as[0].trim())).setKey(ReiMinimap.toUpperCase(as[1].trim()));
                }
                catch (Exception exception) { }
            }
        }
        catch (Exception exception1) { }
        finally
        {
            if (scanner != null)
            {
                scanner.close();
            }
        }
    }

    public void setDefault()
    {
        keyIndex = defaultKeyIndex;
    }

    public boolean isDefault()
    {
        return keyIndex == defaultKeyIndex;
    }

    public String toString()
    {
        return (new StringBuilder()).append(ReiMinimap.capitalize(name())).append(": ").append(getKeyName()).toString();
    }

    static
    {
        configFile = new File(ReiMinimap.directory, "keyconfig.txt");
        loadKeyConfig();
        saveKeyConfig();
    }
}
