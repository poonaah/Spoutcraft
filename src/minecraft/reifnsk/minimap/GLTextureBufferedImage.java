package reifnsk.minimap;

import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.src.GLAllocation;
import org.lwjgl.opengl.GL11;

public class GLTextureBufferedImage extends BufferedImage
{
    private static final ByteBuffer buffer = GLAllocation.createDirectByteBuffer(0x40000);
    private static final HashMap registerImage = new HashMap();
    private static final Lock lock = new ReentrantLock();
    public byte data[];
    private int register;
    private boolean magFiltering;
    private boolean minFiltering;
    private boolean clampTexture;

    private GLTextureBufferedImage(ColorModel colormodel, WritableRaster writableraster, boolean flag, Hashtable hashtable)
    {
        super(colormodel, writableraster, flag, hashtable);
        data = ((DataBufferByte)writableraster.getDataBuffer()).getData();
    }

    public static GLTextureBufferedImage create(int i, int j)
    {
        ColorSpace colorspace = ColorSpace.getInstance(1000);
        int ai[] =
        {
            8, 8, 8, 8
        };
        int ai1[] =
        {
            0, 1, 2, 3
        };
        ComponentColorModel componentcolormodel = new ComponentColorModel(colorspace, ai, true, false, 3, 0);
        WritableRaster writableraster = Raster.createInterleavedRaster(0, i, j, i * 4, 4, ai1, null);
        return new GLTextureBufferedImage(componentcolormodel, writableraster, false, null);
    }

    public static GLTextureBufferedImage create(BufferedImage bufferedimage)
    {
        GLTextureBufferedImage gltexturebufferedimage = create(bufferedimage.getWidth(), bufferedimage.getHeight());
        Graphics g = gltexturebufferedimage.getGraphics();
        g.drawImage(bufferedimage, 0, 0, null);
        g.dispose();
        return gltexturebufferedimage;
    }

    public int register()
    {
        lock.lock();

        try
        {
            if (register != 0)
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, register);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
                char c = (char) (clampTexture ? 10496 : 10497);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, c);
                GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, c);
                buffer.clear();
                buffer.put(data);
                buffer.flip();
                GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, getWidth(), getHeight(), GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
                int i = register;
                return i;
            }

            register = GL11.glGenTextures();
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, register);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, minFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, magFiltering ? GL11.GL_LINEAR : GL11.GL_NEAREST);
            char c1 = (char) (clampTexture ? 10496 : 10497);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, c1);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, c1);
            buffer.clear();
            buffer.put(data);
            buffer.flip();
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, getWidth(), getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
            registerImage.put(Integer.valueOf(register), this);
            int j = register;
            return j;
        }
        finally
        {
            lock.unlock();
        }
    }

    public boolean bind()
    {
        lock.lock();

        try
        {
            if (register != 0)
            {
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, register);
                boolean flag = true;
                return flag;
            }

            boolean flag1 = false;
            return flag1;
        }
        finally
        {
            lock.unlock();
        }
    }

    public void unregister()
    {
        lock.lock();

        try
        {
            if (register == 0)
            {
                return;
            }

            GL11.glDeleteTextures(register);
            register = 0;
            registerImage.remove(Integer.valueOf(register));
        }
        finally
        {
            lock.unlock();
        }
    }

    public static void unregister(int i)
    {
        lock.lock();

        try
        {
            GLTextureBufferedImage gltexturebufferedimage = (GLTextureBufferedImage)registerImage.get(Integer.valueOf(i));

            if (gltexturebufferedimage != null)
            {
                gltexturebufferedimage.unregister();
            }
        }
        finally
        {
            lock.unlock();
        }
    }

    public void setMagFilter(boolean flag)
    {
        magFiltering = flag;
    }

    public void setMinFilter(boolean flag)
    {
        minFiltering = flag;
    }

    public int getId()
    {
        return register;
    }

    public boolean getMagFilter()
    {
        return magFiltering;
    }

    public boolean getMinFilter()
    {
        return minFiltering;
    }

    public void setClampTexture(boolean flag)
    {
        clampTexture = flag;
    }

    public boolean isClampTexture()
    {
        return clampTexture;
    }

    public void setRGBA(int i, int j, byte byte0, byte byte1, byte byte2, byte byte3)
    {
        int k = (j * getWidth() + i) * 4;
        data[k++] = byte0;
        data[k++] = byte1;
        data[k++] = byte2;
        data[k] = byte3;
    }

    public void setRGB(int i, int j, byte byte0, byte byte1, byte byte2)
    {
        int k = (j * getWidth() + i) * 4;
        data[k++] = byte0;
        data[k++] = byte1;
        data[k++] = byte2;
        data[k] = -1;
    }

    public void setRGB(int i, int j, int k)
    {
        int l = (j * getWidth() + i) * 4;
        data[l++] = (byte)(k >> 16);
        data[l++] = (byte)(k >> 8);
        data[l++] = (byte)(k >> 0);
        data[l] = (byte)(k >> 24);
    }

    public static void createTexture(int ai[], int i, int j, int k, boolean flag, boolean flag1)
    {
        byte abyte0[] = new byte[i * j * 4];
        int l = 0;
        int i1 = ai.length;
        int j1 = 0;

        for (; l < i1; l++)
        {
            int k1 = ai[l];
            abyte0[j1++] = (byte)(k1 >> 16);
            abyte0[j1++] = (byte)(k1 >> 8);
            abyte0[j1++] = (byte)(k1 >> 0);
            abyte0[j1++] = (byte)(k1 >> 24);
        }

        createTexture(abyte0, i, j, k, flag, flag1);
    }

    public static void createTexture(byte abyte0[], int i, int j, int k, boolean flag, boolean flag1)
    {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, k);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, flag ? GL11.GL_LINEAR : GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, flag ? GL11.GL_LINEAR : GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, flag1 ? GL11.GL_CLAMP : GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, flag1 ? GL11.GL_CLAMP : GL11.GL_REPEAT);
        buffer.clear();
        buffer.put(abyte0);
        buffer.flip();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, i, j, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }
}
