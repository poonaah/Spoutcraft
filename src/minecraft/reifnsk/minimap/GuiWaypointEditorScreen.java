package reifnsk.minimap;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

public class GuiWaypointEditorScreen extends GuiScreen implements GuiScreenInterface
{
    private GuiWaypointScreen parrent;
    private Waypoint waypoint;
    private Waypoint waypointBackup;
    private GuiTextField nameTextField;
    private GuiTextField xCoordTextField;
    private GuiTextField yCoordTextField;
    private GuiTextField zCoordTextField;
    private GuiScrollbar rgb[];
    private GuiSimpleButton okButton;
    private GuiSimpleButton cancelButton;

    public GuiWaypointEditorScreen(Minecraft minecraft, Waypoint waypoint1)
    {
        waypoint = waypoint1;
        waypointBackup = waypoint1 != null ? new Waypoint(waypoint1) : null;
        String s;
        int i;
        int j;
        int k;

        if (waypoint1 == null)
        {
            s = "";
            EntityPlayerSP entityplayersp = minecraft.thePlayer;
            i = MathHelper.floor_double(((EntityPlayer)(entityplayersp)).posX);
            j = MathHelper.floor_double(((EntityPlayer)(entityplayersp)).posY);
            k = MathHelper.floor_double(((EntityPlayer)(entityplayersp)).posZ);
        }
        else
        {
            s = waypoint1.name;
            i = waypoint1.x;
            j = waypoint1.y;
            k = waypoint1.z;
        }

        nameTextField = new GuiTextField(s);
        nameTextField.setInputType(0);
        nameTextField.active();
        xCoordTextField = new GuiTextField(Integer.toString(i));
        xCoordTextField.setInputType(1);
        yCoordTextField = new GuiTextField(Integer.toString(j));
        yCoordTextField.setInputType(2);
        zCoordTextField = new GuiTextField(Integer.toString(k));
        zCoordTextField.setInputType(1);
        nameTextField.setNext(xCoordTextField);
        nameTextField.setPrev(zCoordTextField);
        xCoordTextField.setNext(yCoordTextField);
        xCoordTextField.setPrev(nameTextField);
        yCoordTextField.setNext(zCoordTextField);
        yCoordTextField.setPrev(xCoordTextField);
        zCoordTextField.setNext(nameTextField);
        zCoordTextField.setPrev(yCoordTextField);
        rgb = new GuiScrollbar[3];

        for (int l = 0; l < 3; l++)
        {
            GuiScrollbar guiscrollbar = new GuiScrollbar(0, 0, 0, 118, 10);
            guiscrollbar.setMinimum(0.0F);
            guiscrollbar.setMaximum(255F);
            guiscrollbar.setVisibleAmount(0.0F);
            guiscrollbar.setBlockIncrement(10F);
            guiscrollbar.orientation = 1;
            rgb[l] = guiscrollbar;
        }

        rgb[0].setValue((float)(waypoint1 != null ? waypoint1.red : Math.random()) * 255F);
        rgb[1].setValue((float)(waypoint1 != null ? waypoint1.green : Math.random()) * 255F);
        rgb[2].setValue((float)(waypoint1 != null ? waypoint1.blue : Math.random()) * 255F);
    }

    public GuiWaypointEditorScreen(GuiWaypointScreen guiwaypointscreen, Waypoint waypoint1)
    {
        this(guiwaypointscreen.getMinecraft(), waypoint1);
        parrent = guiwaypointscreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);

        for (int i = 0; i < 3; i++)
        {
            rgb[i].xPosition = width - 150 >> 1;
            rgb[i].yPosition = height / 2 + 20 + i * 10;
            controlList.add(rgb[i]);
        }

        nameTextField.setBounds(width - 150 >> 1, height / 2 - 40, 150, 9);
        xCoordTextField.setBounds(width - 150 >> 1, height / 2 - 20, 150, 9);
        yCoordTextField.setBounds(width - 150 >> 1, height / 2 - 10, 150, 9);
        zCoordTextField.setBounds(width - 150 >> 1, height / 2, 150, 9);
        controlList.add(nameTextField);
        controlList.add(xCoordTextField);
        controlList.add(yCoordTextField);
        controlList.add(zCoordTextField);
        okButton = new GuiSimpleButton(0, width / 2 - 65, height / 2 + 58, 60, 14, "OK");
        cancelButton = new GuiSimpleButton(1, width / 2 + 5, height / 2 + 58, 60, 14, "Cancel");
        controlList.add(okButton);
        controlList.add(cancelButton);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        super.onGuiClosed();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        int k = MathHelper.floor_double(mc.thePlayer.posX);
        int l = MathHelper.floor_double(mc.thePlayer.posY);
        int i1 = MathHelper.floor_double(mc.thePlayer.posZ);
        xCoordTextField.setNorm(k);
        yCoordTextField.setNorm(l);
        zCoordTextField.setNorm(i1);
        String s = "Waypoint Edit";
        int j1 = fontRenderer.getStringWidth(s);
        int k1 = width - j1 >> 1;
        int l1 = width + j1 >> 1;
        drawRect(k1 - 2, height / 2 - 71, l1 + 2, height / 2 - 57, 0xa0000000);
        drawCenteredString(fontRenderer, s, width / 2, height / 2 - 68, -1);
        String s1 = Integer.toString(k).equals(xCoordTextField.displayString) ? "xCoord: (Current)" : "xCoord:";
        drawString(fontRenderer, s1, (width - 150) / 2 + 1, height / 2 - 19, -1);
        s1 = Integer.toString(l).equals(yCoordTextField.displayString) ? "yCoord: (Current)" : "yCoord:";
        drawString(fontRenderer, s1, (width - 150) / 2 + 1, height / 2 - 9, -1);
        s1 = Integer.toString(i1).equals(zCoordTextField.displayString) ? "zCoord: (Current)" : "zCoord:";
        drawString(fontRenderer, s1, (width - 150) / 2 + 1, height / 2 + 1, -1);
        drawRect((width - 150) / 2 - 2, height / 2 - 50, (width + 150) / 2 + 2, height / 2 + 52, 0xa0000000);
        drawCenteredString(fontRenderer, "Waypoint Name", width >> 1, height / 2 - 49, -1);
        drawCenteredString(fontRenderer, "Coordinate", width >> 1, height / 2 - 29, -1);
        drawCenteredString(fontRenderer, "Color", width >> 1, height / 2 + 11, -1);

        if (waypoint != null)
        {
            waypoint.red = rgb[0].getValue() / 255F;
            waypoint.green = rgb[1].getValue() / 255F;
            waypoint.blue = rgb[2].getValue() / 255F;
        }

        int i2 = (int)rgb[0].getValue() & 0xff;
        int j2 = (int)rgb[1].getValue() & 0xff;
        int k2 = (int)rgb[2].getValue() & 0xff;
        int l2 = 0xff000000 | i2 << 16 | j2 << 8 | k2;
        drawCenteredString(fontRenderer, String.format("R:%03d", new Object[]
                {
                    Integer.valueOf(i2)
                }), width / 2 - 15, height / 2 + 21, 0x80808080);
        drawCenteredString(fontRenderer, String.format("G:%03d", new Object[]
                {
                    Integer.valueOf(j2)
                }), width / 2 - 15, height / 2 + 31, 0x80808080);
        drawCenteredString(fontRenderer, String.format("B:%03d", new Object[]
                {
                    Integer.valueOf(k2)
                }), width / 2 - 15, height / 2 + 41, 0x80808080);
        drawRect(width + 90 >> 1, height / 2 + 20, width + 150 >> 1, height / 2 + 50, l2);
        super.drawScreen(i, j, f);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        if (i == 1)
        {
            cancel();
            return;
        }

        if (i == 28 && GuiTextField.getActive() == zCoordTextField)
        {
            zCoordTextField.norm();
            accept();
            return;
        }
        else
        {
            GuiTextField.keyType(mc, c, i);
            return;
        }
    }

    private void cancel()
    {
        if (waypoint != null)
        {
            waypoint.set(waypointBackup);
        }

        mc.displayGuiScreen(parrent);
    }

    private void accept()
    {
        if (waypoint != null)
        {
            waypoint.name = nameTextField.displayString;
            waypoint.x = parseInt(xCoordTextField.displayString);
            waypoint.y = parseInt(yCoordTextField.displayString);
            waypoint.z = parseInt(zCoordTextField.displayString);
            waypoint.red = rgb[0].getValue() / 255F;
            waypoint.green = rgb[1].getValue() / 255F;
            waypoint.blue = rgb[2].getValue() / 255F;
            parrent.updateWaypoint(waypoint);
        }
        else
        {
            String s = nameTextField.displayString;
            int i = parseInt(xCoordTextField.displayString);
            int j = parseInt(yCoordTextField.displayString);
            int k = parseInt(zCoordTextField.displayString);
            float f = rgb[0].getValue() / 255F;
            float f1 = rgb[1].getValue() / 255F;
            float f2 = rgb[2].getValue() / 255F;
            waypoint = new Waypoint(s, i, j, k, true, f, f1, f2);

            if (parrent == null)
            {
                ReiMinimap reiminimap = ReiMinimap.instance;
                List list = reiminimap.getWaypoints();
                list.add(waypoint);
                reiminimap.saveWaypoints();
            }
            else
            {
                parrent.addWaypoint(waypoint);
            }
        }

        mc.displayGuiScreen(parrent);
    }

    private static int parseInt(String s)
    {
        try
        {
            return Integer.parseInt(s);
        }
        catch (Exception exception)
        {
            return 0;
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton == okButton)
        {
            accept();
            return;
        }

        if (guibutton == cancelButton)
        {
            cancel();
            return;
        }
        else
        {
            return;
        }
    }
}
