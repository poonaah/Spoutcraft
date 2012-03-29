package reifnsk.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiScrollbar extends GuiButton
{
    public static final int VERTICAL = 0;
    public static final int HORIZONTAL = 1;
    private long repeatStart;
    private long repeatInterval;
    int orientation;
    private float value;
    private float extent;
    private float min;
    private float max;
    private float unitIncrement;
    private float blockIncrement;
    private int draggingPos;
    private float draggingValue;
    private int dragging;
    private long draggingTimer;
    private int minBarSize;

    public GuiScrollbar(int i, int j, int k, int l, int i1)
    {
        super(i, j, k, l, i1, "");
        repeatStart = 0x1dcd6500L;
        repeatInterval = 0x2625a00L;
        value = 0.0F;
        extent = 0.0F;
        min = 0.0F;
        max = 0.0F;
        unitIncrement = 1.0F;
        blockIncrement = 9F;
        minBarSize = 6;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        if (value > max - extent)
        {
            value = max - extent;
        }

        if (value < min)
        {
            value = min;
        }

        if (orientation == 0)
        {
            drawVertical(minecraft, i, j);
        }
        else if (orientation == 1)
        {
            drawHorizontal(minecraft, i, j);
        }
    }

    private void drawVertical(Minecraft minecraft, int i, int j)
    {
        if (dragging != 0)
        {
            mouseDragged(minecraft, i, j);
        }

        double d = (double)xPosition + (double)width * 0.5D;
        int k = yPosition;
        int l = yPosition + height;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        boolean flag = (double)i >= d - 4D && (double)i <= d + 4D;

        if (flag && j >= k && j <= k + 8 && (dragging == 0 || dragging == 1))
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
        }
        else
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
        }

        tessellator.startDrawingQuads();
        tessellator.addVertex(d, k, 0.0D);
        tessellator.addVertex(d, k, 0.0D);
        tessellator.addVertex(d - 4D, k + 8, 0.0D);
        tessellator.addVertex(d + 4D, k + 8, 0.0D);
        tessellator.draw();

        if (min < max - extent)
        {
            double d1 = height - 20;
            double d3 = extent / (max - min);

            if (d3 * d1 < (double)minBarSize)
            {
                d3 = (double)minBarSize / d1;
            }

            double d5 = (double)(value / (max - min - extent)) * (1.0D - d3);
            double d6 = d5 + d3;
            d5 = (double)k + d5 * d1 + 10D;
            d6 = (double)k + d6 * d1 + 10D;

            if (dragging == 5 || flag && (double)j >= d5 && (double)j <= d6 && dragging == 0)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
            }
            else
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            }

            tessellator.startDrawingQuads();
            tessellator.addVertex(d + 4D, d5, 0.0D);
            tessellator.addVertex(d - 4D, d5, 0.0D);
            tessellator.addVertex(d - 4D, d6, 0.0D);
            tessellator.addVertex(d + 4D, d6, 0.0D);
            tessellator.draw();
        }
        else
        {
            double d2 = k + 10;
            double d4 = l - 10;

            if (dragging == 5 || flag && (double)j >= d2 && (double)j <= d4 && dragging == 0)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
            }
            else
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            }

            tessellator.startDrawingQuads();
            tessellator.addVertex(d + 4D, d2, 0.0D);
            tessellator.addVertex(d - 4D, d2, 0.0D);
            tessellator.addVertex(d - 4D, d4, 0.0D);
            tessellator.addVertex(d + 4D, d4, 0.0D);
            tessellator.draw();
        }

        if (flag && j >= l - 8 && j <= l && (dragging == 0 || dragging == 2))
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
        }
        else
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
        }

        tessellator.startDrawingQuads();
        tessellator.addVertex(d, l, 0.0D);
        tessellator.addVertex(d, l, 0.0D);
        tessellator.addVertex(d + 4D, l - 8, 0.0D);
        tessellator.addVertex(d - 4D, l - 8, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    private void drawHorizontal(Minecraft minecraft, int i, int j)
    {
        if (dragging != 0)
        {
            mouseDragged(minecraft, i, j);
        }

        double d = (double)yPosition + (double)height * 0.5D;
        int k = xPosition;
        int l = xPosition + width;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        boolean flag = (double)j >= d - 4D && (double)j <= d + 4D;

        if (flag && i >= k && i <= k + 8 && (dragging == 0 || dragging == 1))
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
        }
        else
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
        }

        tessellator.startDrawingQuads();
        tessellator.addVertex(k, d, 0.0D);
        tessellator.addVertex(k, d, 0.0D);
        tessellator.addVertex(k + 8, d + 4D, 0.0D);
        tessellator.addVertex(k + 8, d - 4D, 0.0D);
        tessellator.draw();

        if (min < max - extent)
        {
            double d1 = width - 20;
            double d3 = extent / (max - min);

            if (d3 * d1 < (double)minBarSize)
            {
                d3 = (double)minBarSize / d1;
            }

            double d5 = (double)(value / (max - min - extent)) * (1.0D - d3);
            double d6 = d5 + d3;
            d5 = (double)k + d5 * d1 + 10D;
            d6 = (double)k + d6 * d1 + 10D;

            if (dragging == 6 || flag && (double)i >= d5 && (double)i <= d6 && dragging == 0)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
            }
            else
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            }

            tessellator.startDrawingQuads();
            tessellator.addVertex(d5, d - 4D, 0.0D);
            tessellator.addVertex(d5, d + 4D, 0.0D);
            tessellator.addVertex(d6, d + 4D, 0.0D);
            tessellator.addVertex(d6, d - 4D, 0.0D);
            tessellator.draw();
        }
        else
        {
            double d2 = k + 10;
            double d4 = l - 10;

            if (dragging == 6 || flag && (double)i >= d2 && (double)i <= d4 && dragging == 0)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
            }
            else
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
            }

            tessellator.startDrawingQuads();
            tessellator.addVertex(d2, d - 4D, 0.0D);
            tessellator.addVertex(d2, d + 4D, 0.0D);
            tessellator.addVertex(d4, d + 4D, 0.0D);
            tessellator.addVertex(d4, d - 4D, 0.0D);
            tessellator.draw();
        }

        if (flag && i >= l - 8 && i <= l && (dragging == 0 || dragging == 2))
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.6F);
        }
        else
        {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.3F);
        }

        tessellator.startDrawingQuads();
        tessellator.addVertex(l, d, 0.0D);
        tessellator.addVertex(l, d, 0.0D);
        tessellator.addVertex(l - 8, d - 4D, 0.0D);
        tessellator.addVertex(l - 8, d + 4D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if (super.mousePressed(minecraft, i, j))
        {
            if (orientation == 0)
            {
                return mousePressedVertical(minecraft, i, j);
            }

            if (orientation == 1)
            {
                return mousePressedHorizontal(minecraft, i, j);
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean mousePressedVertical(Minecraft minecraft, int i, int j)
    {
        double d = (double)xPosition + (double)width * 0.5D;
        int k = yPosition;
        int l = yPosition + height;

        if ((double)i < d - 4D || (double)i > d + 4D)
        {
            return false;
        }

        if (max == min)
        {
            return true;
        }

        if (dragging == 0)
        {
            draggingTimer = System.nanoTime() + repeatStart;
        }

        if (j >= k && j <= k + 8 && (dragging == 0 || dragging == 1))
        {
            dragging = 1;
            unitDecrement();
            return true;
        }

        if (j >= l - 8 && j <= l && (dragging == 0 || dragging == 2))
        {
            dragging = 2;
            unitIncrement();
            return true;
        }

        double d1 = height - 20;
        double d2 = extent / (max - min);

        if (d2 * d1 < (double)minBarSize)
        {
            d2 = (double)minBarSize / d1;
        }

        double d3 = (double)(value / (max - min - extent)) * (1.0D - d2);
        double d4 = d3 + d2;
        d3 = (double)k + d3 * d1 + 10D;
        d4 = (double)k + d4 * d1 + 10D;

        if ((double)j < d3 && (dragging == 0 || dragging == 3))
        {
            dragging = 3;
            blockDecrement();
            return true;
        }

        if ((double)j > d4 && (dragging == 0 || dragging == 4))
        {
            dragging = 4;
            blockIncrement();
            return true;
        }

        if (dragging == 0)
        {
            dragging = 5;
            draggingPos = j;
            draggingValue = value;
        }

        return true;
    }

    private boolean mousePressedHorizontal(Minecraft minecraft, int i, int j)
    {
        double d = (double)yPosition + (double)height * 0.5D;
        int k = xPosition;
        int l = xPosition + width;

        if ((double)j < d - 4D || (double)j > d + 4D)
        {
            return false;
        }

        if (max == min)
        {
            return true;
        }

        if (dragging == 0)
        {
            draggingTimer = System.nanoTime() + repeatStart;
        }

        if (i >= k && i <= k + 8 && (dragging == 0 || dragging == 1))
        {
            dragging = 1;
            unitDecrement();
            return true;
        }

        if (i >= l - 8 && i <= l && (dragging == 0 || dragging == 2))
        {
            dragging = 2;
            unitIncrement();
            return true;
        }

        double d1 = width - 20;
        double d2 = extent / (max - min);

        if (d2 * d1 < (double)minBarSize)
        {
            d2 = (double)minBarSize / d1;
        }

        double d3 = (double)(value / (max - min - extent)) * (1.0D - d2);
        double d4 = d3 + d2;
        d3 = (double)k + d3 * d1 + 10D;
        d4 = (double)k + d4 * d1 + 10D;

        if ((double)i < d3 && (dragging == 0 || dragging == 3))
        {
            dragging = 3;
            blockDecrement();
            return true;
        }

        if ((double)i > d4 && (dragging == 0 || dragging == 4))
        {
            dragging = 4;
            blockIncrement();
            return true;
        }

        if (dragging == 0)
        {
            dragging = 6;
            draggingPos = i;
            draggingValue = value;
        }

        return true;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft minecraft, int i, int j)
    {
        if (dragging == 5)
        {
            float f = height - 20;
            float f2 = extent / (max - min);

            if (f2 * f < (float)minBarSize)
            {
                f2 = (float)minBarSize / f;
            }

            float f4 = draggingValue + (((max - min - extent) / (1.0F - f2)) * (float)(j - draggingPos)) / f;
            value = Math.max(min, Math.min(max - extent, f4));
        }

        if (dragging == 6)
        {
            float f1 = width - 20;
            float f3 = extent / (max - min);

            if (f3 * f1 < (float)minBarSize)
            {
                f3 = (float)minBarSize / f1;
            }

            float f5 = draggingValue + (((max - min - extent) / (1.0F - f3)) * (float)(i - draggingPos)) / f1;
            value = Math.max(min, Math.min(max - extent, f5));
        }

        long l = System.nanoTime();

        if (draggingTimer < l)
        {
            mousePressed(minecraft, i, j);
            draggingTimer = l + repeatInterval;
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int i, int j)
    {
        dragging = 0;
    }

    public void setValue(float f)
    {
        if (f < min)
        {
            f = min;
        }

        if (f > max - extent)
        {
            f = max - extent;
        }

        value = f;
    }

    public float getValue()
    {
        return value;
    }

    public void setMaximum(float f)
    {
        if (min > f)
        {
            throw new IllegalArgumentException("min > max");
        }
        else
        {
            max = f;
            value = Math.min(value, max);
            return;
        }
    }

    public float getMaximum()
    {
        return max;
    }

    public void setMinimum(float f)
    {
        if (f > max)
        {
            throw new IllegalArgumentException("min > max");
        }
        else
        {
            min = f;
            value = Math.max(value, min);
            return;
        }
    }

    public float getMinimum()
    {
        return min;
    }

    public void setVisibleAmount(float f)
    {
        if (max - min < f)
        {
            throw new IllegalArgumentException("max - min < extent");
        }
        else
        {
            extent = Math.min(max - min, f);
            return;
        }
    }

    public float getVisibleAmount()
    {
        return extent;
    }

    public void unitIncrement()
    {
        value = Math.min(max - extent, value + unitIncrement);
    }

    public void unitDecrement()
    {
        value = Math.max(min, value - unitIncrement);
    }

    public void blockIncrement()
    {
        value = Math.min(max - extent, value + blockIncrement);
    }

    public void blockDecrement()
    {
        value = Math.max(min, value - blockIncrement);
    }

    public void setMinimumBarSize(int i)
    {
        minBarSize = i;
    }

    public int getMinimumBarSize()
    {
        return minBarSize;
    }

    public void setUnitIncrement(float f)
    {
        unitIncrement = f;
    }

    public void setBlockIncrement(float f)
    {
        blockIncrement = f;
    }

    public float getUnitIncrement()
    {
        return unitIncrement;
    }

    public float getBlockIncrement()
    {
        return blockIncrement;
    }
}
