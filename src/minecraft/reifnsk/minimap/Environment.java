package reifnsk.minimap;

import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.src.*;

class Environment
{
    private static ConcurrentLinkedQueue queue = new ConcurrentLinkedQueue();
    private static final int foliageColorPine = ColorizerFoliage.getFoliageColorPine();
    private static final int foliageColorBirch = ColorizerFoliage.getFoliageColorBirch();
    private static long randomSeed;
    private static Environment envCache[];
    private static World world;
    private int x;
    private int z;
    private int grassColor;
    private int foliageColor;
    private BiomeGenBase biome;
    private float temperature;
    private float humidity;
    private boolean valid;

    Environment()
    {
    }

    private boolean isLocation(int i, int j)
    {
        return x == i && z == j;
    }

    private void set(int i, int j, float f, float f1, BiomeGenBase biomegenbase)
    {
        x = i;
        z = j;
        grassColor = ColorizerGrass.getGrassColor(f, f1);
        foliageColor = ColorizerFoliage.getFoliageColor(f, f1);
        temperature = f;
        humidity = f1;
        biome = biomegenbase;
    }

    public int getGrassColor()
    {
        return grassColor;
    }

    public int getFoliageColor()
    {
        return foliageColor;
    }

    public static int getFoliageColorPine()
    {
        return foliageColorPine;
    }

    public static int getFoliageColorBirch()
    {
        return foliageColorBirch;
    }

    public BiomeGenBase getBiome()
    {
        return biome;
    }

    public float getTemperature()
    {
        return temperature;
    }

    public float getHumidity()
    {
        return humidity;
    }

    public static int calcGrassColor(BiomeGenBase biomegenbase, int i)
    {
        if (biomegenbase == BiomeGenBase.swampland)
        {
            return (i & 0xfefefe) + 0x4e0e4e >> 1;
        }
        else
        {
            return i;
        }
    }

    public static int calcFoliageColor(BiomeGenBase biomegenbase, int i)
    {
        if (biomegenbase == BiomeGenBase.swampland)
        {
            return (i & 0xfefefe) + 0x4e0e4e >> 1;
        }
        else
        {
            return i;
        }
    }

    static void calcEnvironment()
    {
        if (Thread.currentThread() != ReiMinimap.instance.mcThread)
        {
            return;
        }

        do
        {
            Environment environment = (Environment)queue.poll();

            if (environment != null)
            {
                calcEnvironment(environment);
            }
            else
            {
                return;
            }
        }
        while (true);
    }

    public static Environment getEnvironment(int i, int j, Thread thread)
    {
        int k = (i & 0x1ff) << 9 | j & 0x1ff;
        Environment environment = envCache[k];

        if (!environment.isLocation(i, j))
        {
            environment.valid = false;

            if (thread == ReiMinimap.instance.mcThread)
            {
                calcEnvironment(i, j, environment);
            }
            else
            {
                environment.set(i, j, 0.5F, 1.0F, BiomeGenBase.plains);
                queue.offer(environment);
            }
        }

        return environment;
    }

    public static Environment getEnvironment(Chunk chunk, int i, int j, Thread thread)
    {
        return getEnvironment(chunk.xPosition * 16 + i, chunk.zPosition * 16 + j, thread);
    }

    private static void calcEnvironment(Environment environment)
    {
        calcEnvironment(environment.x, environment.z, environment);
    }

    private static void calcEnvironment(int i, int j, Environment environment)
    {
        if (environment.valid)
        {
            return;
        }
        else
        {
            BiomeGenBase biomegenbase = world.func_48454_a(i, j);
            float f = biomegenbase.temperature >= 0.0F ? biomegenbase.temperature <= 1.0F ? biomegenbase.temperature : 1.0F : 0.0F;
            float f1 = biomegenbase.rainfall >= 0.0F ? biomegenbase.rainfall <= 1.0F ? biomegenbase.rainfall : 1.0F : 0.0F;
            environment.set(i, j, f, f1, biomegenbase);
            environment.valid = true;
            return;
        }
    }

    public static void setWorld(World world1)
    {
        world = world1;
        queue.clear();

        for (int i = 0; i < envCache.length; i++)
        {
            envCache[i].x = 0x80000000;
            envCache[i].z = 0x80000000;
        }
    }

    static
    {
        envCache = new Environment[0x40000];

        for (int i = 0; i < envCache.length; i++)
        {
            envCache[i] = new Environment();
        }
    }
}
