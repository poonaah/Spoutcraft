package reifnsk.minimap;

public class Waypoint
{
    private static final int MAX_TYPE_VALUE = 1;
    public static final int NORMAL = 0;
    public static final int DEATH_POINT = 1;
    static final GLTexture FILE[];
    static final GLTexture MARKER[];
    public String name;
    public int x;
    public int y;
    public int z;
    public boolean enable;
    public float red;
    public float green;
    public float blue;
    public int type;

    Waypoint(String s, int i, int j, int k, boolean flag, float f, float f1, float f2)
    {
        name = s != null ? s : "";
        x = i;
        y = j;
        z = k;
        enable = flag;
        red = f;
        green = f1;
        blue = f2;
    }

    Waypoint(String s, int i, int j, int k, boolean flag, float f, float f1, float f2, int l)
    {
        name = s != null ? s : "";
        x = i;
        y = j;
        z = k;
        enable = flag;
        red = f;
        green = f1;
        blue = f2;
        type = Math.max(0, l > 1 ? 0 : l);
    }

    Waypoint(Waypoint waypoint)
    {
        set(waypoint);
    }

    void set(Waypoint waypoint)
    {
        name = waypoint.name;
        x = waypoint.x;
        y = waypoint.y;
        z = waypoint.z;
        enable = waypoint.enable;
        red = waypoint.red;
        green = waypoint.green;
        blue = waypoint.blue;
        type = Math.max(0, waypoint.type > 1 ? 0 : waypoint.type);
    }

    static Waypoint load(String s)
    {
        try
        {
            String as[] = s.split(":");
            String s1 = as[0];
            int i = Integer.parseInt(as[1]);
            int j = Integer.parseInt(as[2]);
            int k = Integer.parseInt(as[3]);
            boolean flag = Boolean.parseBoolean(as[4]);
            int l = Integer.parseInt(as[5], 16);
            float f = (float)(l >> 16 & 0xff) / 255F;
            float f1 = (float)(l >> 8 & 0xff) / 255F;
            float f2 = (float)(l >> 0 & 0xff) / 255F;
            int i1 = as.length < 7 ? 0 : Integer.parseInt(as[6]);
            return new Waypoint(s1, i, j, k, flag, f, f1, f2, i1);
        }
        catch (RuntimeException runtimeexception)
        {
            runtimeexception.printStackTrace();
        }

        return null;
    }

    public String toString()
    {
        int i = (int)(red * 255F) & 0xff;
        int j = (int)(green * 255F) & 0xff;
        int k = (int)(blue * 255F) & 0xff;
        int l = i << 16 | j << 8 | k;
        return String.format(type != 0 ? "%s:%d:%d:%d:%s:%06X:%d" : "%s:%d:%d:%d:%s:%06X", new Object[]
                {
                    name, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Boolean.valueOf(enable), Integer.valueOf(l), Integer.valueOf(type)
                });
    }

    static
    {
        FILE = (new GLTexture[]
                {
                    GLTexture.WAYPOINT1, GLTexture.WAYPOINT2
                });
        MARKER = (new GLTexture[]
                {
                    GLTexture.MARKER1, GLTexture.MARKER2
                });
    }
}
