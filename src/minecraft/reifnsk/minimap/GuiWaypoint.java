package reifnsk.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

class GuiWaypoint extends GuiButton
{
    private static final int COLOR1[] =
    {
        -1, 0xffff0000
    };
    private static final int COLOR2[] =
    {
        0xffc0c0c0, 0xffc00000
    };
    private static final int COLOR_SIZE = 9;
    private static final int BUTTON_SIZE = 30;
    private static final int ADD_SPACE = 2;
    static final int SIZE = 45;
    private GuiWaypointScreen gws;
    private Waypoint waypoint;
    private int number;
    private String name;
    private int top;
    private int bottom;
    private int left;
    private int right;
    private int ctop;
    private int cbottom;
    private int cleft;
    private int cright;
    private int btop;
    private int bbottom;
    private int bleft;
    private int bright;
    private long clickTime;

    GuiWaypoint(int i, GuiWaypointScreen guiwaypointscreen)
    {
        super(i, 0, 0, 0, 0, null);
        clickTime = System.nanoTime();
        gws = guiwaypointscreen;
    }

    void setWaypoint(int i, Waypoint waypoint1)
    {
        number = i;
        waypoint = waypoint1;
        name = null;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (waypoint == null)
        {
            return;
        }

        FontRenderer fontrenderer = minecraft.fontRenderer;

        if (name == null)
        {
            for (name = (new StringBuilder()).append(number).append(") ").append(waypoint.name).toString(); fontrenderer.getStringWidth(name) > 160; name = name.substring(0, name.length() - 1)) { }
        }

        boolean flag = mouseIn(i, j);
        drawString(fontrenderer, name, xPosition + 1, yPosition + 1, (flag ? COLOR1 : COLOR2)[waypoint.type]);
        boolean flag1 = flag && i < cleft;
        int k = (int)(waypoint.red * 255F) & 0xff;
        int l = (int)(waypoint.green * 255F) & 0xff;
        int i1 = (int)(waypoint.blue * 255F) & 0xff;
        int j1 = 0xff000000 | k << 16 | l << 8 | i1;
        drawRect(cleft, ctop, cright, cbottom, j1);
        flag = buttonIn(i, j);
        String s = gws.getRemoveMode() ? gws.isRemove(waypoint) ? "X" : "KEEP" : waypoint.enable ? "ON" : "OFF";
        j1 = flag ? 0x80ffffff : s != "X" ? s != "KEEP" ? waypoint.enable ? 0xa000ff00 : 0xa0ff0000 : 0xa000ff00 : 0xa0ff0000;
        drawRect(bleft, btop, bright, bbottom, j1);
        drawCenteredString(minecraft.fontRenderer, s, bleft + bright >> 1, btop + 1, -1);

        if (flag1)
        {
            String s1 = String.format("X:%d, Y:%d, Z:%d", new Object[]
                    {
                        Integer.valueOf(waypoint.x), Integer.valueOf(waypoint.y), Integer.valueOf(waypoint.z)
                    });
            int k1 = fontrenderer.getStringWidth(s1);
            int l1 = i - k1 / 2 - 1;
            int i2 = l1 + k1 + 2;
            drawRect(l1, j - 11, i2, j - 1, 0xa0000000);
            drawCenteredString(fontrenderer, s1, i, j - 10, -1);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if (waypoint == null)
        {
            return false;
        }

        if (mouseIn(i, j))
        {
            if (colorIn(i, j))
            {
                waypoint.red = (float)Math.random();
                waypoint.green = (float)Math.random();
                waypoint.blue = (float)Math.random();
                gws.updateWaypoint(waypoint);
                return true;
            }

            if (buttonIn(i, j))
            {
                if (gws.getRemoveMode())
                {
                    gws.removeWaypoint(waypoint);
                }
                else
                {
                    waypoint.enable = !waypoint.enable;
                    gws.updateWaypoint(waypoint);
                }

                return true;
            }

            long l = System.nanoTime();

            if (!gws.getRemoveMode() && l < clickTime + 0x11e1a300L)
            {
                minecraft.displayGuiScreen(new GuiWaypointEditorScreen(gws, waypoint));
                return true;
            }

            clickTime = l;
        }

        return false;
    }

    void bounds(int i, int j, int k, int l)
    {
        xPosition = i;
        yPosition = j;
        width = k;
        height = l;
        top = j;
        bottom = j + l;
        left = i;
        right = i + k;
        ctop = top;
        cbottom = bottom;
        cright = right - 2 - 30 - 2;
        cleft = cright - 9;
        btop = top;
        bbottom = bottom;
        bright = right - 2;
        bleft = bright - 30;
    }

    private boolean mouseIn(int i, int j)
    {
        return j >= top && j < bottom && i >= left && i < right;
    }

    private boolean colorIn(int i, int j)
    {
        return j >= ctop && j < cbottom && i >= cleft && i < cright;
    }

    private boolean buttonIn(int i, int j)
    {
        return j >= btop && j < bbottom && i >= bleft && i < bright;
    }
}
