package reifnsk.minimap;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class GuiWaypointScreen extends GuiScreen implements GuiScreenInterface
{
    static final int MIN_STRING_WIDTH = 64;
    static final int MAX_STRING_WIDTH = 160;
    static final int LIST_SIZE = 9;
    private ReiMinimap rmm;
    private List wayPts;
    private GuiWaypoint guiWaypoints[];
    private GuiScrollbar scrollbar;
    private GuiSimpleButton backButton;
    private GuiSimpleButton addButton;
    private GuiSimpleButton removeFlagButton;
    private GuiSimpleButton removeApplyButton;
    private GuiSimpleButton removeCancelButton;
    private GuiSimpleButton prevDimension;
    private GuiSimpleButton nextDimension;
    private ConcurrentHashMap deleteObject;
    private int scroll;
    private boolean removeMode;
    private int maxStringWidth;
    private GuiScreen parent;

    public GuiWaypointScreen(GuiScreen guiscreen)
    {
        rmm = ReiMinimap.instance;
        wayPts = rmm.getWaypoints();
        guiWaypoints = new GuiWaypoint[9];
        scrollbar = new GuiScrollbar(0, 0, 0, 12, 90);
        deleteObject = new ConcurrentHashMap();
        parent = guiscreen;

        for (int i = 0; i < 9; i++)
        {
            guiWaypoints[i] = new GuiWaypoint(i, this);
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        controlList.clear();
        Keyboard.enableRepeatEvents(true);
        GuiWaypoint aguiwaypoint[] = guiWaypoints;
        int j = aguiwaypoint.length;

        for (int k = 0; k < j; k++)
        {
            GuiWaypoint guiwaypoint = aguiwaypoint[k];
            controlList.add(guiwaypoint);
        }

        controlList.add(scrollbar);
        updateWaypoints();
        int i = width / 2;
        j = height + 90 >> 1;
        backButton = new GuiSimpleButton(0, i - 65, j + 7, 40, 14, parent != null ? "Back" : "Close");
        controlList.add(backButton);
        addButton = new GuiSimpleButton(0, i - 20, j + 7, 40, 14, "Add");
        controlList.add(addButton);
        removeFlagButton = new GuiSimpleButton(0, i + 25, j + 7, 40, 14, "Remove");
        controlList.add(removeFlagButton);
        removeApplyButton = new GuiSimpleButton(0, i - 65, j + 7, 60, 14, "Remove");
        controlList.add(removeApplyButton);
        removeCancelButton = new GuiSimpleButton(0, i + 5, j + 7, 60, 14, "Cancel");
        controlList.add(removeCancelButton);
        prevDimension = new GuiSimpleButton(0, 0, 0, 14, 14, "<");
        controlList.add(prevDimension);
        nextDimension = new GuiSimpleButton(0, 0, 0, 14, 14, ">");
        controlList.add(nextDimension);
        setRemoveMode(removeMode);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        backButton.enabled = backButton.drawButton = !removeMode;
        addButton.enabled = addButton.drawButton = !removeMode;
        removeFlagButton.enabled = removeFlagButton.drawButton = !removeMode;
        removeApplyButton.enabled = removeApplyButton.drawButton = removeMode;
        removeCancelButton.enabled = removeCancelButton.drawButton = removeMode;
        addButton.enabled = rmm.getCurrentDimension() == rmm.getWaypointDimension();
        int k = Math.min(160, maxStringWidth) + 16;
        int l = height - 90 >> 1;
        int i1 = height + 90 >> 1;
        int j1 = width - k - 45 - 10 >> 1;
        int k1 = width + k + 45 + 10 >> 1;
        drawRect(j1 - 2, l - 2, k1 + 2, i1 + 2, 0xa0000000);
        String s = String.format("Waypoints [%s]", new Object[]
                {
                    ReiMinimap.instance.getDimensionName(ReiMinimap.instance.getWaypointDimension())
                });
        int l1 = fontRenderer.getStringWidth(s);
        int i2 = width - l1 >> 1;
        int j2 = width + l1 >> 1;
        prevDimension.xPosition = i2 - 18;
        prevDimension.yPosition = l - 22;
        nextDimension.xPosition = j2 + 4;
        nextDimension.yPosition = l - 22;
        super.drawScreen(i, j, f);
        drawRect(i2 - 2, l - 22, j2 + 2, l - 8, 0xa0000000);
        drawCenteredString(fontRenderer, s, width / 2, l - 19, -1);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        int i = (int)scrollbar.getValue();

        if (scroll != i)
        {
            scroll = i;
            setWaypoints();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        super.keyTyped(c, i);

        switch (i)
        {
            case 200:
                scrollbar.unitDecrement();
                break;

            case 208:
                scrollbar.unitIncrement();
                break;

            case 201:
                scrollbar.blockDecrement();
                break;

            case 209:
                scrollbar.blockIncrement();
                break;

            case 199:
                scrollbar.setValue(scrollbar.getMinimum());
                break;

            case 207:
                scrollbar.setValue(scrollbar.getMaximum());
                break;
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int i = Mouse.getDWheel();

        if (i != 0)
        {
            i = i >= 0 ? -3 : 3;
            scrollbar.setValue(scrollbar.getValue() + (float)i);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton == backButton)
        {
            mc.displayGuiScreen(parent);
        }

        if (guibutton == removeFlagButton)
        {
            setRemoveMode(true);
        }

        if (guibutton == removeCancelButton)
        {
            setRemoveMode(false);
        }

        if (guibutton == removeApplyButton)
        {
            boolean flag = false;

            for (Iterator iterator = deleteObject.keySet().iterator(); iterator.hasNext();)
            {
                Waypoint waypoint = (Waypoint)iterator.next();
                flag |= wayPts.remove(waypoint);
            }

            if (flag)
            {
                rmm.saveWaypoints();
                updateWaypoints();
            }

            setRemoveMode(false);
        }

        if (guibutton == addButton && rmm.getCurrentDimension() == rmm.getWaypointDimension())
        {
            mc.displayGuiScreen(new GuiWaypointEditorScreen(this, null));
        }

        if (guibutton == prevDimension)
        {
            setRemoveMode(false);
            rmm.prevDimension();
            wayPts = rmm.getWaypoints();
            updateWaypoints();
        }

        if (guibutton == nextDimension)
        {
            setRemoveMode(false);
            rmm.nextDimension();
            wayPts = rmm.getWaypoints();
            updateWaypoints();
        }
    }

    void setRemoveMode(boolean flag)
    {
        removeMode = flag;
        deleteObject.clear();
    }

    boolean getRemoveMode()
    {
        return removeMode;
    }

    boolean isRemove(Waypoint waypoint)
    {
        return deleteObject.containsKey(waypoint);
    }

    void addWaypoint(Waypoint waypoint)
    {
        if (!wayPts.contains(waypoint))
        {
            wayPts.add(waypoint);
            rmm.saveWaypoints();
            updateWaypoints();
            scrollbar.setValue(scrollbar.getMaximum());
        }
    }

    void removeWaypoint(Waypoint waypoint)
    {
        if (removeMode)
        {
            if (deleteObject.remove(waypoint) == null)
            {
                deleteObject.put(waypoint, waypoint);
            }
        }
        else if (wayPts.remove(waypoint))
        {
            rmm.saveWaypoints();
            updateWaypoints();
        }
    }

    void updateWaypoint(Waypoint waypoint)
    {
        if (wayPts.contains(waypoint))
        {
            rmm.saveWaypoints();
            updateWaypoints();
        }
    }

    private void updateWaypoints()
    {
        maxStringWidth = 64;
        int i = 0;

        for (int j = wayPts.size(); i < j; i++)
        {
            Waypoint waypoint = (Waypoint)wayPts.get(i);
            maxStringWidth = Math.max(maxStringWidth, fontRenderer.getStringWidth((new StringBuilder()).append(i + 1).append(") ").append(waypoint.name).toString()));
        }

        scrollbar.setMinimum(0.0F);
        scrollbar.setMaximum(wayPts.size());
        scrollbar.setVisibleAmount(Math.min(9, wayPts.size()));
        scroll = (int)scrollbar.getValue();
        updateGui();
        setWaypoints();
    }

    private void updateGui()
    {
        int i = Math.min(160, maxStringWidth) + 16;
        int j = height - 90 - 4 >> 1;
        int k = width - i - 45 - 12 >> 1;
        int l = width + i + 45 + 12 >> 1;

        for (int i1 = 0; i1 < 9; i1++)
        {
            guiWaypoints[i1].bounds(k + 2, j + 2 + 10 * i1, i + 45, 9);
        }

        scrollbar.xPosition = l - 12;
        scrollbar.yPosition = j + 2;
    }

    private void setWaypoints()
    {
        for (int i = 0; i < 9; i++)
        {
            int j = i + scroll;
            guiWaypoints[i].setWaypoint(j + 1, j >= wayPts.size() ? null : (Waypoint)wayPts.get(j));
        }
    }

    Minecraft getMinecraft()
    {
        return mc;
    }
}
