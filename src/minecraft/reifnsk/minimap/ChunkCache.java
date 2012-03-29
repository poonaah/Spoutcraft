package reifnsk.minimap;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.src.*;

public class ChunkCache
{
    private static final int threshold = 128;
    private final int shift;
    private final int size;
    private final int mask;
    private Chunk cache[];
    private int count[];
    private boolean slime[];

    public ChunkCache(int i)
    {
        shift = i;
        size = 1 << shift;
        mask = size - 1;
        cache = new Chunk[size * size];
        count = new int[size * size];
        slime = new boolean[size * size];
    }

    public Chunk get(World world, int i, int j)
    {
        int k = i & mask | (j & mask) << shift;
        Object obj = cache[k];

        if (obj == null || ((Chunk)(obj)).worldObj != world || !((Chunk)(obj)).isAtLocation(i, j) || --count[k] < 0)
        {
            if (world.blockExists(i << 4, 0, j << 4))
            {
                cache[k] = ((Chunk)(obj = world.getChunkFromChunkCoords(i, j)));
                count[k] = 128;
            }
            else if ((obj instanceof EmptyChunk) && ((Chunk)(obj)).isAtLocation(i, j))
            {
                count[k] = 8;
            }
            else
            {
                cache[k] = ((Chunk)(obj = new EmptyChunk(world, i, j)));
                count[k] = 8;
            }

            slime[k] = world.getSeed() != 0L && ((Chunk)(obj)).getRandomWithSeed(0x3ad8025fL).nextInt(10) == 0;
        }

        return ((Chunk)(obj));
    }

    public boolean isSlimeSpawn(int i, int j)
    {
        return slime[i & mask | (j & mask) << shift];
    }

    public void clear()
    {
        Arrays.fill(cache, null);
        Arrays.fill(count, 0);
    }
}
