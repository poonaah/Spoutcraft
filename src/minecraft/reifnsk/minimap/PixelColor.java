package reifnsk.minimap;

public class PixelColor
{
    static final float d = 0.003921569F;
    public final boolean alphaComposite;
    public float red;
    public float green;
    public float blue;
    public float alpha;

    public PixelColor()
    {
        this(true);
    }

    public PixelColor(boolean flag)
    {
        alphaComposite = flag;
    }

    public void clear()
    {
        red = green = blue = alpha = 0.0F;
    }

    public void composite(int i)
    {
        composite(i, 1.0F);
    }

    public void composite(int i, float f)
    {
        if (alphaComposite)
        {
            float f1 = (float)(i >> 24 & 0xff) * 0.003921569F;
            float f2 = (float)(i >> 16 & 0xff) * 0.003921569F * f;
            float f3 = (float)(i >> 8 & 0xff) * 0.003921569F * f;
            float f4 = (float)(i >> 0 & 0xff) * 0.003921569F * f;
            red += (f2 - red) * f1;
            green += (f3 - green) * f1;
            blue += (f4 - blue) * f1;
            alpha += (1.0F - alpha) * f1;
        }
        else
        {
            alpha = (float)(i >> 24 & 0xff) * 0.003921569F;
            red = (float)(i >> 16 & 0xff) * 0.003921569F * f;
            green = (float)(i >> 8 & 0xff) * 0.003921569F * f;
            blue = (float)(i >> 0 & 0xff) * 0.003921569F * f;
        }
    }

    public void composite(float f, int i, float f1)
    {
        if (alphaComposite)
        {
            float f2 = f;
            float f3 = (float)(i >> 16 & 0xff) * 0.003921569F * f1;
            float f4 = (float)(i >> 8 & 0xff) * 0.003921569F * f1;
            float f5 = (float)(i >> 0 & 0xff) * 0.003921569F * f1;
            red += (f3 - red) * f2;
            green += (f4 - green) * f2;
            blue += (f5 - blue) * f2;
            alpha += (1.0F - alpha) * f2;
        }
        else
        {
            alpha = (float)(i >> 24 & 0xff) * 0.003921569F;
            red = (float)(i >> 16 & 0xff) * 0.003921569F * f1;
            green = (float)(i >> 8 & 0xff) * 0.003921569F * f1;
            blue = (float)(i >> 0 & 0xff) * 0.003921569F * f1;
        }
    }

    public void composite(float f, int i, float f1, float f2, float f3)
    {
        if (alphaComposite)
        {
            float f4 = f;
            float f5 = (float)(i >> 16 & 0xff) * 0.003921569F * f1;
            float f6 = (float)(i >> 8 & 0xff) * 0.003921569F * f2;
            float f7 = (float)(i >> 0 & 0xff) * 0.003921569F * f3;
            red += (f5 - red) * f4;
            green += (f6 - green) * f4;
            blue += (f7 - blue) * f4;
            alpha += (1.0F - alpha) * f4;
        }
        else
        {
            alpha = (float)(i >> 24 & 0xff) * 0.003921569F;
            red = (float)(i >> 16 & 0xff) * 0.003921569F * f1;
            green = (float)(i >> 8 & 0xff) * 0.003921569F * f2;
            blue = (float)(i >> 0 & 0xff) * 0.003921569F * f3;
        }
    }

    public void composite(float f, float f1, float f2, float f3)
    {
        if (alphaComposite)
        {
            red += (f1 - red) * f;
            green += (f2 - green) * f;
            blue += (f3 - blue) * f;
            alpha += (1.0F - alpha) * f;
        }
        else
        {
            alpha = f;
            red = f1;
            green = f2;
            blue = f3;
        }
    }

    public void composite(float f, float f1, float f2, float f3, float f4)
    {
        if (alphaComposite)
        {
            red += (f1 * f4 - red) * f;
            green += (f2 * f4 - green) * f;
            blue += (f3 * f4 - blue) * f;
            alpha += (1.0F - alpha) * f;
        }
        else
        {
            alpha = f;
            red = f1 * f4;
            green = f2 * f4;
            blue = f3 * f4;
        }
    }
}
