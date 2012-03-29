package reifnsk.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

public class GuiKeyConfigButton extends GuiButton
{
    private GuiKeyConfigScreen parrent;
    private KeyInput keyInput;
    private String labelText;
    private String buttonText;
    private int labelWidth;
    private int buttonWidth;

    public GuiKeyConfigButton(GuiKeyConfigScreen guikeyconfigscreen, int i, int j, int k, int l, int i1, KeyInput keyinput)
    {
        super(i, j, k, l + 12 + i1, 9, "");
        parrent = guikeyconfigscreen;
        keyInput = keyinput;
        labelWidth = l;
        buttonWidth = i1;
        labelText = keyInput.label();
        buttonText = keyInput.getKeyName();
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (keyInput == null)
        {
            return;
        }

        boolean flag = i >= xPosition && i < xPosition + width && j >= yPosition && j < yPosition + height;
        drawString(minecraft.fontRenderer, labelText, xPosition, yPosition + 1, flag ? -1 : 0xffc0c0c0);
        String s = buttonText;

        if (this == parrent.getEditKeyConfig())
        {
            s = (new StringBuilder()).append(">").append(s).append("<").toString();
        }

        flag = i >= (xPosition + width) - buttonWidth && i < xPosition + width && j >= yPosition && j < yPosition + height;
        int k = flag ? 0x66ffffff : keyInput.getKey() != 0 ? keyInput.isDefault() ? 0xa000ff00 : 0xa0ff0000 : keyInput.isDefault() ? 0xa00000ff : 0xa0ff8000;
        drawRect((xPosition + width) - buttonWidth, yPosition, xPosition + width, yPosition + height, k);
        drawCenteredString(minecraft.fontRenderer, s, (xPosition + width) - buttonWidth / 2, yPosition + 1, -1);
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        return i >= (xPosition + width) - buttonWidth && i < xPosition + width && j >= yPosition && j < yPosition + height;
    }

    void setBounds(int i, int j, int k, int l)
    {
        xPosition = i;
        yPosition = j;
        labelWidth = k;
        buttonWidth = l;
        width = k + l + 2;
    }

    KeyInput getKeyInput()
    {
        return keyInput;
    }
}
