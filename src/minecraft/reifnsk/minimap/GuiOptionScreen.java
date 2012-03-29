package reifnsk.minimap;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class GuiOptionScreen extends GuiScreen implements GuiScreenInterface
{
    private static final int LIGHTING_VERSION = 0x1010800;
    private static final int SUNRISE_DIRECTION = 0x1010883;
    public static final int minimapMenu = 0;
    public static final int optionMinimap = 1;
    public static final int optionSurfaceMap = 2;
    public static final int optionEntitiesRadar = 3;
    public static final int optionMarker = 4;
    public static final int aboutMinimap = 5;
    private static final String TITLE_STRING[];
    private int page;
    private ArrayList buttonList;
    private GuiSimpleButton exitMenu;
    private GuiSimpleButton waypoint;
    private GuiSimpleButton keyconfig;
    private int top;
    private int left;
    private int right;
    private int bottom;
    private int centerX;
    private int centerY;

    public GuiOptionScreen()
    {
        buttonList = new ArrayList();
    }

    GuiOptionScreen(int i)
    {
        buttonList = new ArrayList();
        page = i;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        centerX = width / 2;
        centerY = height / 2;
        controlList.clear();
        buttonList.clear();
        EnumOption aenumoption[] = EnumOption.values();
        int j = aenumoption.length;

        for (int k = 0; k < j; k++)
        {
            EnumOption enumoption = aenumoption[k];

            if (enumoption.getPage() == page && (!mc.theWorld.isRemote || enumoption != EnumOption.ENTITIES_RADAR_OPTION || ReiMinimap.instance.getAllowEntitiesRadar()) && enumoption != EnumOption.DIRECTION_TYPE)
            {
                GuiOptionButton guioptionbutton1 = new GuiOptionButton(mc.fontRenderer, enumoption);
                guioptionbutton1.setValue(ReiMinimap.instance.getOption(enumoption));
                controlList.add(guioptionbutton1);
                buttonList.add(guioptionbutton1);
            }
        }

        left = width - GuiOptionButton.getWidth() >> 1;
        top = height - buttonList.size() * 10 >> 1;
        right = width + GuiOptionButton.getWidth() >> 1;
        bottom = height + buttonList.size() * 10 >> 1;

        for (int i = 0; i < buttonList.size(); i++)
        {
            GuiOptionButton guioptionbutton = (GuiOptionButton)buttonList.get(i);
            guioptionbutton.xPosition = left;
            guioptionbutton.yPosition = top + i * 10;
        }

        if (page == 0)
        {
            exitMenu = new GuiSimpleButton(0, centerX - 95, bottom + 7, 60, 14, "Exit Menu");
            controlList.add(exitMenu);
            waypoint = new GuiSimpleButton(1, centerX - 30, bottom + 7, 60, 14, "Waypoints");
            controlList.add(waypoint);
            keyconfig = new GuiSimpleButton(2, centerX + 35, bottom + 7, 60, 14, "Keyconfig");
            controlList.add(keyconfig);
        }
        else
        {
            exitMenu = new GuiSimpleButton(0, centerX - 30, bottom + 7, 60, 14, "Back");
            controlList.add(exitMenu);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        String s = TITLE_STRING[page];
        int k = fontRenderer.getStringWidth(s);
        int l = width - k >> 1;
        int i1 = width + k >> 1;
        drawRect(l - 2, top - 22, i1 + 2, top - 8, 0xa0000000);
        drawCenteredString(fontRenderer, s, centerX, top - 19, -1);
        drawRect(left - 2, top - 2, right + 2, bottom + 1, 0xa0000000);
        super.drawScreen(i, j, f);
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton instanceof GuiOptionButton)
        {
            GuiOptionButton guioptionbutton = (GuiOptionButton)guibutton;
            ReiMinimap.instance.setOption(guioptionbutton.getOption(), guioptionbutton.getValue());
            ReiMinimap.instance.saveOptions();
        }

        if (guibutton instanceof GuiSimpleButton)
        {
            if (guibutton == exitMenu)
            {
                mc.displayGuiScreen(page != 0 ? ((GuiScreen)(new GuiOptionScreen(0))) : null);
            }

            if (guibutton == waypoint)
            {
                mc.displayGuiScreen(new GuiWaypointScreen(this));
            }

            if (guibutton == keyconfig)
            {
                mc.displayGuiScreen(new GuiKeyConfigScreen());
            }
        }
    }

    static
    {
        TITLE_STRING = (new String[]
                {
                    (new StringBuilder()).append("Rei's Minimap ").append(ReiMinimap.version).toString(), "Minimap Options", "SurfaceMap Options", "Entities Radar Options", "Marker Options", "About Rei's Minimap"
                });
    }
}
