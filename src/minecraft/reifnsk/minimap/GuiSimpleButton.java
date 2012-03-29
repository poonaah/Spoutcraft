package reifnsk.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;

public class GuiSimpleButton extends GuiButton
{
    public GuiSimpleButton(int i, int j, int k, int l, int i1, String s)
    {
        super(i, j, k, l, i1, s);
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
            FontRenderer fontrenderer = minecraft.fontRenderer;
            boolean flag = i >= xPosition && j >= yPosition && i < xPosition + width && j < yPosition + height;
            int k = !flag || !enabled ? 0xa0000000 : 0xc8666666;
            drawRect(xPosition, yPosition, xPosition + width, yPosition + height, k);
            drawCenteredString(fontrenderer, displayString, xPosition + width / 2, yPosition + (height - 8) / 2, enabled ? -1 : 0xff808080);
            return;
        }
    }
}
