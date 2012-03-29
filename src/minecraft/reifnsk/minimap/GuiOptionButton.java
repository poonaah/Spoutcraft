package reifnsk.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

public class GuiOptionButton extends GuiButton
{
    private static int NAME_WIDTH;
    private static int VALUE_WIDTH;
    private static int WIDTH;
    private EnumOption option;
    private EnumOptionValue value;

    public GuiOptionButton(FontRenderer fontrenderer, EnumOption enumoption)
    {
        super(0, 0, 0, 0, 10, "");
        option = enumoption;
        value = option.getValue(0);

        for (int i = 0; i < enumoption.getValueNum(); i++)
        {
            String s = enumoption.getValue(i).text();
            int j = fontrenderer.getStringWidth(s) + 4;
            VALUE_WIDTH = Math.max(VALUE_WIDTH, j);
        }

        NAME_WIDTH = Math.max(NAME_WIDTH, fontrenderer.getStringWidth((new StringBuilder()).append(enumoption.getText()).append(": ").toString()));
        WIDTH = VALUE_WIDTH + 8 + NAME_WIDTH;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (!drawButton)
        {
            return;
        }
        else
        {
            value = ReiMinimap.instance.getOption(option);
            FontRenderer fontrenderer = minecraft.fontRenderer;
            boolean flag = i >= xPosition && j >= yPosition && i < xPosition + getWidth() && j < yPosition + getHeight();
            int k = flag ? -1 : 0xffc0c0c0;
            int l = flag ? 0x66ffffff : value.color;
            drawString(fontrenderer, option.getText(), xPosition, yPosition + 1, k);
            int i1 = xPosition + NAME_WIDTH + 8;
            int j1 = i1 + VALUE_WIDTH;
            drawRect(i1, yPosition, j1, (yPosition + getHeight()) - 1, l);
            drawCenteredString(fontrenderer, value.text(), i1 + VALUE_WIDTH / 2, yPosition + 1, -1);
            return;
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if (enabled && i >= xPosition && j >= yPosition && i < xPosition + getWidth() && j < yPosition + getHeight())
        {
            nextValue();
            return true;
        }
        else
        {
            return false;
        }
    }

    public EnumOption getOption()
    {
        return option;
    }

    public EnumOptionValue getValue()
    {
        return value;
    }

    public void setValue(EnumOptionValue enumoptionvalue)
    {
        if (option.getValue(enumoptionvalue) != -1)
        {
            value = enumoptionvalue;
        }
    }

    public void nextValue()
    {
        value = option.getValue((option.getValue(value) + 1) % option.getValueNum());

        if (!ReiMinimap.instance.getAllowCavemap() && option == EnumOption.RENDER_TYPE && value == EnumOptionValue.CAVE)
        {
            nextValue();
        }
    }

    public static int getWidth()
    {
        return WIDTH;
    }

    public static int getHeight()
    {
        return 10;
    }
}
