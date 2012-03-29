package reifnsk.minimap;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public class GLTexture
{
    private static String DEFAULT_PACK;
    private static String pack;
    private static ArrayList list = new ArrayList();
    private static GLTexture missing = new GLTexture("missing.png", true, false);
    static final GLTexture TEMPERATURE = new GLTexture("temperature.png", true, true);
    static final GLTexture HUMIDITY = new GLTexture("humidity.png", true, true);
    static final GLTexture ROUND_MAP = new GLTexture("roundmap.png", true, true);
    static final GLTexture ROUND_MAP_MASK = new GLTexture("roundmap_mask.png", false, true);
    static final GLTexture SQUARE_MAP = new GLTexture("squaremap.png", true, true);
    static final GLTexture SQUARE_MAP_MASK = new GLTexture("squaremap_mask.png", false, true);
    static final GLTexture ENTITY = new GLTexture("entity.png", true, true);
    static final GLTexture ENTITY2 = new GLTexture("entity2.png", true, true);
    static final GLTexture LIGHTNING = new GLTexture("lightning.png", true, true);
    static final GLTexture N = new GLTexture("n.png", true, true);
    static final GLTexture E = new GLTexture("e.png", true, true);
    static final GLTexture W = new GLTexture("w.png", true, true);
    static final GLTexture S = new GLTexture("s.png", true, true);
    static final GLTexture MMARROW = new GLTexture("mmarrow.png", true, true);
    static final GLTexture WAYPOINT1 = new GLTexture("waypoint.png", true, true);
    static final GLTexture WAYPOINT2 = new GLTexture("waypoint2.png", true, true);
    static final GLTexture MARKER1 = new GLTexture("marker.png", true, true);
    static final GLTexture MARKER2 = new GLTexture("marker2.png", true, true);
    private final String fileName;
    private final boolean blur;
    private final boolean clamp;
    private int textureId;

    static void setPack(String s)
    {
        if (s.equals(pack))
        {
            return;
        }

        GLTexture gltexture;

        for (Iterator iterator = list.iterator(); iterator.hasNext(); gltexture.release())
        {
            gltexture = (GLTexture)iterator.next();
        }

        pack = s;
    }

    private GLTexture(String s, boolean flag, boolean flag1)
    {
        fileName = s;
        blur = flag;
        clamp = flag1;
        list.add(this);
    }

    int[] getData()
    {
        BufferedImage bufferedimage = read(fileName);
        int i = bufferedimage.getWidth();
        int j = bufferedimage.getHeight();
        int ai[] = new int[i * j];
        bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
        return ai;
    }

    void bind()
    {
        if (textureId == 0)
        {
            BufferedImage bufferedimage = read(fileName);

            if (bufferedimage == null)
            {
                textureId = this != missing ? -1 : -2;
            }
            else
            {
                textureId = GL11.glGenTextures();
                int i = bufferedimage.getWidth();
                int j = bufferedimage.getHeight();
                int ai[] = new int[i * j];
                bufferedimage.getRGB(0, 0, i, j, ai, 0, i);
                GLTextureBufferedImage.createTexture(ai, i, j, textureId, blur, clamp);
            }
        }

        if (textureId == -2)
        {
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
            return;
        }

        if (textureId == -1)
        {
            missing.bind();
        }

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
    }

    void release()
    {
        if (textureId > 0)
        {
            GL11.glDeleteTextures(textureId);
        }

        textureId = 0;
    }

    private static BufferedImage read(String s)
    {
        BufferedImage bufferedimage = readImage((new StringBuilder()).append(pack).append(s).toString());
        return bufferedimage != null ? bufferedimage : readImage((new StringBuilder()).append(DEFAULT_PACK).append(s).toString());
    }

    private static BufferedImage readImage(String s)
    {
        InputStream inputstream = (reifnsk.minimap.GLTexture.class).getResourceAsStream(s);

        if (inputstream == null)
        {
            return null;
        }

        try
        {
            BufferedImage bufferedimage = ImageIO.read(inputstream);
            return bufferedimage;
        }
        catch (Exception exception)
        {
            BufferedImage bufferedimage1 = null;
            return bufferedimage1;
        }
        finally
        {
            try
            {
                inputstream.close();
            }
            catch (Exception exception2) { }
        }
    }

    static
    {
        DEFAULT_PACK = "/reifnsk/minimap/";
        pack = DEFAULT_PACK;
    }
}
