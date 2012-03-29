package reifnsk.minimap;

import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public final class BlockColor
{
    private static class TempBlockAccess
        implements IBlockAccess
    {
        private int blockId;
        private TileEntity blockTileEntity;
        private int lightBrightnessForSkyBlocks;
        private float brightness;
        private float getLightBrightness;
        private int blockMetadata;
        private Material blockMaterial;
        private boolean blockOpaqueCube;
        private boolean blockNormalCube;
        private boolean airBlock;
        private WorldChunkManager worldChunkManager;

        public int getBlockId(int i, int j, int k)
        {
            return blockId;
        }

        public TileEntity getBlockTileEntity(int i, int j, int k)
        {
            return blockTileEntity;
        }

        public int getLightBrightnessForSkyBlocks(int i, int j, int k, int l)
        {
            return lightBrightnessForSkyBlocks;
        }

        public float getBrightness(int i, int j, int k, int l)
        {
            return brightness;
        }

        public float getLightBrightness(int i, int j, int k)
        {
            return getLightBrightness;
        }

        public int getBlockMetadata(int i, int j, int k)
        {
            return blockMetadata;
        }

        public Material getBlockMaterial(int i, int j, int k)
        {
            return blockMaterial;
        }

        public boolean isBlockOpaqueCube(int i, int j, int k)
        {
            return blockOpaqueCube;
        }

        public boolean isBlockNormalCube(int i, int j, int k)
        {
            return blockNormalCube;
        }

        public boolean isAirBlock(int i, int j, int k)
        {
            return airBlock;
        }

        public BiomeGenBase func_48454_a(int i, int j)
        {
            return BiomeGenBase.plains;
        }

        public int func_48453_b()
        {
            return 0;
        }

        public boolean func_48452_a()
        {
            return true;
        }

        private TempBlockAccess()
        {
        }

		@Override
		public WorldChunkManager getWorldChunkManager() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGrassColorCache(int x, int y, int z) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setGrassColorCache(int x, int y, int z, int color) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getWaterColorCache(int x, int y, int z) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setWaterColorCache(int x, int y, int z, int color) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getWorldHeight() {
			// TODO Auto-generated method stub
			return 0;
		}
    }

    private static final ArrayList list = new ArrayList();
    private static final float d = 0.003921569F;
    private static final int AIR_COLOR = 0xff00ff;
    private static final BlockColor AIR_BLOCK;
    private static final int BLOCK_NUM;
    private static final BlockColor defaultColor[];
    private static final BlockColor textureColor[];
    private static final BlockColor userColor[];
    private static final BlockColor blockColor[];
    private static final boolean opaqueList[];
    private static final boolean useMetadata[];
    private static final boolean mcpatcher;
    private static final boolean custom_lava;
    private static final boolean custom_water;
    private final int argb;
    public final TintType tintType;
    public final float alpha;
    public final float red;
    public final float green;
    public final float blue;

    private static void calcBlockColor(BlockColor ablockcolor[][])
    {
        Arrays.fill(blockColor, AIR_BLOCK);
        Arrays.fill(useMetadata, false);

        for (int i = 0; i < BLOCK_NUM; i++)
        {
            BlockColor ablockcolor1[] = null;
            BlockColor blockcolor = null;
            BlockColor ablockcolor2[][] = ablockcolor;
            int k = ablockcolor2.length;
            int i1 = 0;

            do
            {
                if (i1 >= k)
                {
                    break;
                }

                BlockColor ablockcolor3[] = ablockcolor2[i1];

                if (ablockcolor3[i << 4] != null)
                {
                    ablockcolor1 = ablockcolor3;
                    blockcolor = ablockcolor1[i << 4];
                    blockColor[i << 4] = blockcolor;
                    break;
                }

                i1++;
            }
            while (true);

            if (ablockcolor1 == null)
            {
                continue;
            }

            for (int j = 1; j < 16; j++)
            {
                int l = pointer(i, j);

                if (ablockcolor1[l] == AIR_BLOCK || ablockcolor1[l] == blockcolor)
                {
                    blockColor[l] = blockcolor;
                }
                else
                {
                    blockColor[l] = ablockcolor1[l];
                    useMetadata[i] = true;
                }
            }
        }
    }

    public static void calcBlockColorTD()
    {
        calcBlockColor(new BlockColor[][]
                {
                    textureColor, defaultColor
                });
    }

    public static void calcBlockColorD()
    {
        calcBlockColor(new BlockColor[][]
                {
                    defaultColor
                });
    }

    public static void calcBlockColorT()
    {
        calcBlockColor(new BlockColor[][]
                {
                    textureColor
                });
    }

    public static boolean useMetadata(int i)
    {
        return useMetadata[i];
    }

    public static BlockColor getBlockColor(int i, int j)
    {
        return blockColor[pointer(i, j)];
    }

    private static BlockColor instance(int i)
    {
        return instance(i, TintType.NONE);
    }

    private static BlockColor instance(int i, TintType tinttype)
    {
        BlockColor blockcolor = new BlockColor(i, tinttype);
        int j = list.indexOf(blockcolor);

        if (j == -1)
        {
            list.add(blockcolor);
            return blockcolor;
        }
        else
        {
            return (BlockColor)list.get(j);
        }
    }

    private static void setDefaultColor(int i, int j, int k)
    {
        TintType tinttype = TintType.NONE;

        switch (i)
        {
            default:
                break;

            case 2:
            case 106:
                tinttype = TintType.GRASS;
                break;

            case 8:
            case 9:
            case 79:
                tinttype = TintType.WATER;
                break;

            case 18:
                int l = j & 3;

                if (l == 0)
                {
                    tinttype = TintType.FOLIAGE;
                }

                if (l == 1)
                {
                    tinttype = TintType.PINE;
                }

                if (l == 2)
                {
                    tinttype = TintType.BIRCH;
                }

                if (l == 3)
                {
                    tinttype = TintType.FOLIAGE;
                }

                break;

            case 20:
                tinttype = TintType.GLASS;
                break;

            case 31:
                if (j == 1 || j == 2)
                {
                    tinttype = TintType.TALL_GRASS;
                }

                break;
        }

        defaultColor[pointer(i, j)] = instance(k, tinttype);
    }

    public static void textureColorUpdate()
    {
        Minecraft minecraft = ReiMinimap.instance.theMinecraft;
        TexturePackList texturepacklist = minecraft.texturePackList;
        TexturePackBase texturepackbase = texturepacklist.selectedTexturePack;
        HashMap hashmap = new HashMap();
        BufferedImage abufferedimage[] = splitImage(readImage(texturepackbase, "/terrain.png"));
        hashmap.put(null, abufferedimage);
        boolean flag = false;
        boolean flag1 = false;

        try
        {
            Class class1 = Class.forName("ModLoader");
            Field field = class1.getDeclaredField("overrides");
            field.setAccessible(true);
            Map map = (Map)field.get(null);

            if (map != null)
            {
                Map map1 = (Map)map.get(Integer.valueOf(0));

                if (map1 != null)
                {
                    for (Iterator iterator = map1.entrySet().iterator(); iterator.hasNext();)
                    {
                        java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
                        abufferedimage[((Integer)entry.getValue()).intValue()] = readImage(texturepackbase, (String)entry.getKey());
                    }
                }
            }
        }
        catch (Exception exception) { }

        Arrays.fill(textureColor, AIR_BLOCK);
        TempBlockAccess tempblockaccess = new TempBlockAccess();
        int i = 0;

        for (int k = BLOCK_NUM; i < k; i++)
        {
            Block block = Block.blocksList[i];

            if (block == null)
            {
                continue;
            }

            tempblockaccess.blockId = i;
            String s = getBlockTexture(block);
            BufferedImage abufferedimage1[] = (BufferedImage[])hashmap.get(s);

            if (abufferedimage1 == null)
            {
                abufferedimage1 = splitImage(readImage(texturepackbase, s));
                hashmap.put(s, abufferedimage1);
            }

            int i1 = block.getRenderType();

            for (int j1 = 0; j1 < 16; j1++)
            {
                try
                {
                    boolean flag3 = block instanceof BlockRedstoneTorch;
                    int k1 = block.getBlockTextureFromSideAndMetadata(flag3 ? 0 : 1, j1);

                    if (i == 18)
                    {
                        k1 &= -2;
                    }

                    tempblockaccess.blockMetadata = j1;
                    block.setBlockBoundsBasedOnState(tempblockaccess, 0, 0, 0);
                    double d1 = block.minX;
                    double d2 = block.minZ;
                    double d3 = block.maxX;
                    double d4 = block.maxZ;

                    switch (i1)
                    {
                        case 0:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 1:
                            int l1 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((l1 & 0xff000000) != 0)
                            {
                                int j4 = Math.max(l1 >>> 24, 48) << 24;
                                setTextureColor(i, j1, l1 & 0xffffff | j4);
                            }

                            break;

                        case 2:
                            int i12 = calcColorInt(abufferedimage1[k1], 0.4375D, 0.4375D, 0.5625D, 0.5625D);
                            int k12 = calcColorInt(abufferedimage1[k1], 0.375D, 0.375D, 0.625D, 0.625D);
                            int i2 = i12 >> 24 & 0xff;
                            int k4 = k12 >> 24 & 0xff;
                            int i7 = i2 + k4;

                            if (i7 != 0)
                            {
                                int j8 = ((i12 >> 16 & 0xff) * i2 + (k12 >> 16 & 0xff) * k4) / i7;
                                int l9 = ((i12 >> 8 & 0xff) * i2 + (k12 >> 8 & 0xff) * k4) / i7;
                                int j11 = ((i12 >> 0 & 0xff) * i2 + (k12 >> 0 & 0xff) * k4) / i7;
                                setTextureColor(i, j1, 0x80000000 | j8 << 16 | l9 << 8 | j11);
                                break;
                            }

                            i12 = calcColorInt(abufferedimage1[k1], 0.25D, 0.25D, 0.75D, 0.75D);
                            k12 = calcColorInt(abufferedimage1[k1], 0.0D, 0.0D, 1.0D, 1.0D);
                            i2 = i12 >> 24 & 0xff;
                            k4 = k12 >> 24 & 0xff;
                            i7 = i2 + k4;

                            if (i7 != 0)
                            {
                                int k8 = ((i12 >> 16 & 0xff) * i2 + (k12 >> 16 & 0xff) * k4) / i7;
                                int i10 = ((i12 >> 8 & 0xff) * i2 + (k12 >> 8 & 0xff) * k4) / i7;
                                int k11 = ((i12 >> 0 & 0xff) * i2 + (k12 >> 0 & 0xff) * k4) / i7;
                                setTextureColor(i, j1, 0x80000000 | k8 << 16 | i10 << 8 | k11);
                                break;
                            }

                        case 3:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 4:
                            String s1 = null;
                            BufferedImage bufferedimage = abufferedimage1[k1];
                            int j7 = 0;

                            if (mcpatcher)
                            {
                                if (s == null)
                                {
                                    if (!flag && custom_water && i == 8 || i == 9)
                                    {
                                        s1 = "/custom_water_still.png";
                                    }
                                    else if (!flag1 && custom_lava && i == 10 || i == 11)
                                    {
                                        s1 = "/custom_lava_still.png";
                                    }
                                }

                                if (s1 != null)
                                {
                                    InputStream inputstream = texturepackbase.getResourceAsStream(s1);

                                    if (inputstream != null)
                                    {
                                        try
                                        {
                                            bufferedimage = ImageIO.read(inputstream);
                                        }
                                        catch (IOException ioexception) { }
                                        finally
                                        {
                                            try
                                            {
                                                inputstream.close();
                                            }
                                            catch (IOException ioexception1) { }
                                        }
                                    }
                                }

                                if (bufferedimage != abufferedimage1[k1])
                                {
                                    abufferedimage1[k1] = bufferedimage;

                                    if (i == 8 || i == 9)
                                    {
                                        flag = true;
                                    }

                                    if (i == 10 || i == 11)
                                    {
                                        flag1 = true;
                                    }
                                }
                            }
                            else
                            {
                                if (i == 8 || i == 9)
                                {
                                    abufferedimage1[k1] = new BufferedImage(1, 1, 2);
                                    abufferedimage1[k1].setRGB(0, 0, 0x8b2a5eff);
                                    flag = true;
                                }

                                if (i == 10 || i == 11)
                                {
                                    abufferedimage1[k1] = new BufferedImage(1, 1, 2);
                                    abufferedimage1[k1].setRGB(0, 0, 0xffd96514);
                                    flag = true;
                                }
                            }

                            j7 = calcColorInt(bufferedimage, 0.0D, 0.0D, 1.0D, 1.0D);

                            if (i == 8 || i == 10)
                            {
                                int l8 = j7 >> 30 & 0xff;
                                int j10 = j7 >> 16 & 0xff;
                                int l11 = j7 >> 8 & 0xff;
                                int j12 = j7 >> 0 & 0xff;
                                j10 = (int)((double)j10 * 0.9D);
                                l11 = (int)((double)l11 * 0.9D);
                                j12 = (int)((double)j12 * 0.9D);
                                j7 = l8 << 30 | j10 << 16 | l11 << 8 | j12 << 0;
                            }

                            setTextureColor(i, j1, j7);
                            break;

                        case 5:
                            float f = (float)j1 / 15F;
                            int l4 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((l4 & 0xff000000) != 0)
                            {
                                int k7 = Math.max(l4 >> 24 & 0xff, 108);
                                int i9 = (int)((float)(l4 >> 16 & 0xff) * Math.max(0.3F, f * 0.6F + 0.4F));
                                int k10 = (int)((float)(l4 >> 8 & 0xff) * Math.max(0.0F, f * f * 0.7F - 0.5F));
                                setTextureColor(i, j1, k7 << 24 | i9 << 16 | k10 << 8);
                            }

                            break;

                        case 6:
                            int j2 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((j2 & 0xff000000) != 0)
                            {
                                int i5 = Math.max(j2 >>> 24, 32) << 24;
                                setTextureColor(i, j1, j2 & 0xffffff | i5);
                            }

                            break;

                        case 7:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 8:
                            int k2 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((k2 & 0xff000000) != 0)
                            {
                                int j5 = Math.min(k2 >>> 24, 40) << 24;
                                setTextureColor(i, j1, k2 & 0xffffff | j5);
                            }

                            break;

                        case 9:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 10:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 11:
                            int l2 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((l2 & 0xff000000) != 0)
                            {
                                int k5 = Math.min(l2 >>> 24, 96) << 24;
                                setTextureColor(i, j1, l2 & 0xffffff | k5);
                            }

                            break;

                        case 12:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 13:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 14:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 15:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 16:
                            if (j1 >= 10 && j1 <= 13)
                            {
                                setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], 0.0D, 0.25D, 1.0D, 1.0D));
                            }
                            else
                            {
                                setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            }

                            break;

                        case 17:
                            if ((j1 & 7) == 0 || (j1 & 7) == 1)
                            {
                                setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            }
                            else
                            {
                                setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], 0.0D, 0.0D, 1.0D, 0.25D));
                            }

                            break;

                        case 18:
                            int i3 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((i3 & 0xff000000) != 0)
                            {
                                int l5 = Math.min(i3 >>> 24, 40) << 24;
                                setTextureColor(i, j1, i3 & 0xffffff | l5);
                            }

                            break;

                        case 19:
                            int j3 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((j3 & 0xff000000) != 0)
                            {
                                int i6 = Math.max(48, j3 >> 24 & 0xff);
                                int l7 = Math.min(255, Math.max(0, (j1 * 32 * (j3 >> 16 & 0xff)) / 255));
                                int j9 = Math.min(255, Math.max(0, ((255 - j1 * 8) * (j3 >> 8 & 0xff)) / 255));
                                int l10 = Math.min(255, Math.max(0, (j1 * 4 * (j3 >> 0 & 0xff)) / 255));
                                setTextureColor(i, j1, i6 << 24 | l7 << 16 | j9 << 8 | l10 << 0);
                            }

                            break;

                        case 20:
                            int k3 = calcColorInt(abufferedimage1[k1], 0.0D, 0.0D, 1.0D, 1.0D);

                            if ((k3 & 0xff000000) != 0)
                            {
                                int j6 = Math.min(k3 >>> 24, 32) << 24;
                                setTextureColor(i, j1, k3 & 0xffffff | j6);
                            }

                            break;

                        case 21:
                            int l3 = calcColorInt(abufferedimage1[k1], 0.0D, 0.0D, 1.0D, 1.0D);

                            if ((l3 & 0xff000000) != 0)
                            {
                                int k6 = Math.min(l3 >>> 24, 128) << 24;
                                setTextureColor(i, j1, l3 & 0xffffff | k6);
                            }

                            break;

                        case 22:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        case 23:
                            int i4 = calcColorInt(abufferedimage1[k1], d1, d2, d3, d4);

                            if ((i4 & 0xff000000) != 0)
                            {
                                int l6 = i4 >> 24 & 0xff;
                                int i8 = (int)((float)((i4 >> 16 & 0xff) * 32) * 0.003921569F);
                                int k9 = (int)((float)((i4 >> 8 & 0xff) * 128) * 0.003921569F);
                                int i11 = (int)((float)((i4 >> 0 & 0xff) * 48) * 0.003921569F);
                                setTextureColor(i, j1, l6 << 24 | i8 << 16 | k9 << 8 | i11 << 0);
                            }

                            break;

                        case 24:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;

                        default:
                            setTextureColor(i, j1, calcColorInt(abufferedimage1[k1], d1, d2, d3, d4));
                            break;
                    }
                }
                catch (Exception exception1) { }
            }
        }

        Arrays.fill(useMetadata, false);

        for (int j = 0; j < BLOCK_NUM; j++)
        {
            BlockColor blockcolor = textureColor[pointer(j, 0)];

            if (blockcolor == null)
            {
                continue;
            }

            boolean flag2 = false;

            for (int l = 1; !flag2 && l < 16; l++)
            {
                flag2 = !blockcolor.equals(textureColor[pointer(j, l)]);
            }

            useMetadata[j] = flag2;
        }
    }

    private static void setTextureColor(int i, int j, int k)
    {
        if (opaqueList[i])
        {
            k |= 0xff000000;
        }

        if ((k & 0xff000000) == 0)
        {
            textureColor[pointer(i, j)] = AIR_BLOCK;
            return;
        }

        TintType tinttype = TintType.NONE;

        switch (i)
        {
            case 2:
            case 106:
                tinttype = TintType.GRASS;
                break;

            case 8:
            case 9:
            case 79:
                tinttype = TintType.WATER;
                break;

            case 18:
                int l = j & 3;

                if (l == 0)
                {
                    tinttype = TintType.FOLIAGE;
                }

                if (l == 1)
                {
                    tinttype = TintType.PINE;
                }

                if (l == 2)
                {
                    tinttype = TintType.BIRCH;
                }

                if (l == 3)
                {
                    tinttype = TintType.FOLIAGE;
                }

                break;

            case 20:
                tinttype = TintType.GLASS;
                break;

            case 31:
                if (j == 1 || j == 2)
                {
                    tinttype = TintType.TALL_GRASS;
                }

                break;
        }

        if ("ic2.common.BlockRubLeaves".equals(Block.blocksList[i].getClass().getCanonicalName()))
        {
            tinttype = TintType.BIRCH;
        }

        if ("eloraam.world.BlockCustomLeaves".equals(Block.blocksList[i].getClass().getCanonicalName()))
        {
            int i1 = j & 3;

            if (i1 == 0)
            {
                tinttype = TintType.FOLIAGE;
            }

            if (i1 == 1)
            {
                tinttype = TintType.PINE;
            }

            if (i1 == 2)
            {
                tinttype = TintType.BIRCH;
            }

            if (i1 == 3)
            {
                tinttype = TintType.FOLIAGE;
            }
        }

        textureColor[pointer(i, j)] = instance(k, tinttype);
    }

    private static int pointer(int i, int j)
    {
        return i << 4 | j;
    }

    private static BufferedImage readImage(TexturePackBase texturepackbase, String s)
    {
        InputStream inputstream = null;

        try
        {
            inputstream = texturepackbase.getResourceAsStream(s);

            if (inputstream != null)
            {
                BufferedImage bufferedimage = ImageIO.read(inputstream);
                return bufferedimage;
            }
        }
        catch (IOException ioexception) { }
        finally
        {
            if (inputstream != null)
            {
                try
                {
                    inputstream.close();
                }
                catch (IOException ioexception1) { }
            }
        }

        BufferedImage bufferedimage1 = new BufferedImage(1, 1, 2);
        bufferedimage1.setRGB(0, 0, 0xff00ff);
        return bufferedimage1;
    }

    private static BufferedImage[] splitImage(BufferedImage bufferedimage)
    {
        if (bufferedimage == null)
        {
            bufferedimage = new BufferedImage(1, 1, 2);
            bufferedimage.setRGB(0, 0, 0xff00ff);
            BufferedImage abufferedimage[] = new BufferedImage[256];
            Arrays.fill(abufferedimage, bufferedimage);
            return abufferedimage;
        }

        int i = Math.max(1, bufferedimage.getWidth() >> 4);
        int j = Math.max(1, bufferedimage.getHeight() >> 4);
        BufferedImage abufferedimage1[] = new BufferedImage[256];

        for (int k = 0; k < 256; k++)
        {
            abufferedimage1[k] = GLTextureBufferedImage.create(i, j);
            int l = (k & 0xf) * bufferedimage.getWidth() >> 4;
            int i1 = (k >> 4) * bufferedimage.getHeight() >> 4;

            for (int j1 = 0; j1 < j; j1++)
            {
                for (int k1 = 0; k1 < i; k1++)
                {
                    abufferedimage1[k].setRGB(k1, j1, bufferedimage.getRGB(k1 + l, j1 + i1));
                }
            }
        }

        return abufferedimage1;
    }

    private static int calcColorInt(BufferedImage bufferedimage, double d1, double d2, double d3, double d4)
    {
        int i = (int)Math.floor((double)bufferedimage.getWidth() * d1);
        int j = (int)Math.floor((double)bufferedimage.getHeight() * d2);
        int k = (int)Math.floor((double)bufferedimage.getWidth() * d3);
        int l = (int)Math.floor((double)bufferedimage.getHeight() * d4);
        long l1 = 0L;
        long l2 = 0L;
        long l3 = 0L;
        long l4 = 0L;

        for (int i1 = j; i1 < l; i1++)
        {
            for (int k1 = i; k1 < k; k1++)
            {
                int i2 = bufferedimage.getRGB(k1, i1);
                int j2 = i2 >> 24 & 0xff;
                l1 += j2;
                l2 += (i2 >> 16 & 0xff) * j2;
                l3 += (i2 >> 8 & 0xff) * j2;
                l4 += (i2 >> 0 & 0xff) * j2;
            }
        }

        if (l1 == 0L)
        {
            return 0xff00ff;
        }
        else
        {
            int j1 = bufferedimage.getWidth() * bufferedimage.getHeight();
            double d5 = 1.0D / (double)l1;
            l1 /= j1;
            l2 = Math.min(255, Math.max(0, (int)((double)l2 * d5)));
            l3 = Math.min(255, Math.max(0, (int)((double)l3 * d5)));
            l4 = Math.min(255, Math.max(0, (int)((double)l4 * d5)));
            return (int)(l1 << 24 | l2 << 16 | l3 << 8 | l4);
        }
    }

    private BlockColor(int i, TintType tinttype)
    {
        if (tinttype == null)
        {
            tinttype = TintType.NONE;
        }

        float f = (float)(i >> 24 & 0xff) * 0.003921569F;
        float f1 = (float)(i >> 16 & 0xff) * 0.003921569F;
        float f2 = (float)(i >> 8 & 0xff) * 0.003921569F;
        float f3 = (float)(i >> 0 & 0xff) * 0.003921569F;
        alpha = f;
        red = f1;
        green = f2;
        blue = f3;
        argb = i;
        tintType = tinttype;
    }

    public String toString()
    {
        return String.format("%08X:%s", new Object[]
                {
                    Integer.valueOf(argb), tintType
                });
    }

    public int hashCode()
    {
        return argb;
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof BlockColor) && equals((BlockColor)obj);
    }

    boolean equals(BlockColor blockcolor)
    {
        return argb == blockcolor.argb && tintType == blockcolor.tintType;
    }

    private static String getBlockTexture(Block block)
    {
        Method amethod[] = block.getClass().getMethods();
        int i = amethod.length;
        int j = 0;

        do
        {
            if (j >= i)
            {
                break;
            }

            Method method = amethod[j];

            if (method.getReturnType() == (java.lang.String.class) && (method.getParameterTypes().length == 0 && method.getName().equals("getTextureFile")))
            {
                try
                {
                    return (String)method.invoke(block, new Object[0]);
                }
                catch (Exception exception) { }

                break;
            }

            j++;
        }
        while (true);

        return null;
    }

    static
    {
        AIR_BLOCK = instance(0xff00ff);
        BLOCK_NUM = Block.blocksList.length;
        defaultColor = new BlockColor[BLOCK_NUM * 16 + 1];
        textureColor = new BlockColor[BLOCK_NUM * 16 + 1];
        userColor = new BlockColor[BLOCK_NUM * 16 + 1];
        blockColor = new BlockColor[BLOCK_NUM * 16 + 1];
        opaqueList = new boolean[BLOCK_NUM];
        useMetadata = new boolean[BLOCK_NUM];
        boolean flag = false;
        boolean flag1 = false;
        boolean flag3 = false;
        boolean flag4 = false;
        boolean flag5 = false;

        try
        {
            Class class1 = Class.forName("com.pclewis.mcpatcher.MCPatcherUtils");
            flag = true;
            Method method = class1.getMethod("getBoolean", new Class[]
                    {
                        java.lang.String.class, java.lang.String.class, Boolean.TYPE
                    });
            flag1 = ((Boolean)method.invoke(null, new Object[]
                    {
                        "HD Textures", "customLava", Boolean.TRUE
                    })).booleanValue();
            flag3 = ((Boolean)method.invoke(null, new Object[]
                    {
                        "HD Textures", "customWater", Boolean.TRUE
                    })).booleanValue();
        }
        catch (Exception exception) { }

        mcpatcher = flag;
        custom_lava = flag1;
        custom_water = flag3;
        System.out.println((new StringBuilder()).append("[Rei's Minimap] MCPatcher HD Fix: ").append(mcpatcher ? "Found" : "Not found").toString());
        Arrays.fill(defaultColor, AIR_BLOCK);
        setDefaultColor(1, 0, 0xff686868);
        setDefaultColor(2, 0, 0xff476c3d);
        setDefaultColor(3, 0, 0xff79553a);
        setDefaultColor(4, 0, 0xff959595);
        setDefaultColor(5, 0, 0xffbc9862);
        setDefaultColor(6, 0, 0x6c436d12);
        setDefaultColor(6, 1, 0x54323921);
        setDefaultColor(6, 2, 0x6c759553);
        setDefaultColor(7, 0, 0xff333333);
        setDefaultColor(8, 0, 0x8b2a5eff);
        setDefaultColor(9, 0, 0x8b2a5eff);
        setDefaultColor(10, 0, 0xffd96514);
        setDefaultColor(11, 0, 0xffd96514);
        setDefaultColor(12, 0, 0xffddd7a0);
        setDefaultColor(13, 0, 0xff897f7e);
        setDefaultColor(14, 0, 0xff908b7c);
        setDefaultColor(15, 0, 0xff88827f);
        setDefaultColor(16, 0, 0xff747373);
        setDefaultColor(17, 0, 0xff675132);
        setDefaultColor(17, 1, 0xff342919);
        setDefaultColor(17, 2, 0xffc8c29f);
        setDefaultColor(18, 0, 0x9a305a25);
        setDefaultColor(18, 1, 0xa53a4c26);
        setDefaultColor(18, 2, 0x9a335133);
        setDefaultColor(18, 3, 0x9a335133);
        setDefaultColor(18, 4, 0x9a305a25);
        setDefaultColor(18, 5, 0xa53a4c26);
        setDefaultColor(18, 6, 0x9a335133);
        setDefaultColor(18, 7, 0x9a335133);
        setDefaultColor(19, 0, 0xffe5e54e);
        setDefaultColor(20, 0, 0x40ffffff);
        setDefaultColor(21, 0, 0xff677087);
        setDefaultColor(22, 0, 0xff1d47a6);
        setDefaultColor(23, 0, 0xff585858);
        setDefaultColor(24, 0, 0xffdbd29f);
        setDefaultColor(25, 0, 0xff644432);
        setDefaultColor(26, 0, 0xff9f4545);
        setDefaultColor(26, 1, 0xff9f4545);
        setDefaultColor(26, 2, 0xff9f4545);
        setDefaultColor(26, 3, 0xff9f4545);
        setDefaultColor(26, 4, 0xff9f4545);
        setDefaultColor(26, 5, 0xff9f4545);
        setDefaultColor(26, 6, 0xff9f4545);
        setDefaultColor(26, 7, 0xff9f4545);
        setDefaultColor(26, 8, 0xff9e6161);
        setDefaultColor(26, 9, 0xff9e6161);
        setDefaultColor(26, 10, 0xff9e6161);
        setDefaultColor(26, 11, 0xff9e6161);
        setDefaultColor(26, 12, 0xff9e6161);
        setDefaultColor(26, 13, 0xff9e6161);
        setDefaultColor(26, 14, 0xff9e6161);
        setDefaultColor(26, 15, 0xff9e6161);
        setDefaultColor(27, 0, 0xe0806060);
        setDefaultColor(27, 1, 0xe0806060);
        setDefaultColor(27, 2, 0xe0806060);
        setDefaultColor(27, 3, 0xe0806060);
        setDefaultColor(27, 4, 0xe0806060);
        setDefaultColor(27, 5, 0xe0806060);
        setDefaultColor(27, 6, 0xe0806060);
        setDefaultColor(27, 7, 0xe0806060);
        setDefaultColor(27, 8, 0xe0d06060);
        setDefaultColor(27, 9, 0xe0d06060);
        setDefaultColor(27, 10, 0xe0d06060);
        setDefaultColor(27, 11, 0xe0d06060);
        setDefaultColor(27, 12, 0xe0d06060);
        setDefaultColor(27, 13, 0xe0d06060);
        setDefaultColor(27, 14, 0xe0d06060);
        setDefaultColor(27, 15, 0xe0d06060);
        setDefaultColor(28, 0, 0xff776458);
        setDefaultColor(29, 0, 0xff6d6d6d);
        setDefaultColor(29, 1, 0xff8d9163);
        setDefaultColor(29, 2, 0xff6a665e);
        setDefaultColor(29, 3, 0xff6a665e);
        setDefaultColor(29, 4, 0xff6a665e);
        setDefaultColor(29, 5, 0xff6a665e);
        setDefaultColor(29, 8, 0xff6d6d6d);
        setDefaultColor(29, 9, 0xff8d9163);
        setDefaultColor(29, 10, 0xff6a665e);
        setDefaultColor(29, 11, 0xff6a665e);
        setDefaultColor(29, 12, 0xff6a665e);
        setDefaultColor(29, 13, 0xff6a665e);
        setDefaultColor(30, 0, 0x69d9d9d9);
        setDefaultColor(31, 0, 0x527a4e19);
        setDefaultColor(31, 1, 0xa2508032);
        setDefaultColor(31, 2, 0x4f508032);
        setDefaultColor(32, 0, 0x527a4e19);
        setDefaultColor(33, 0, 0xff6d6d6d);
        setDefaultColor(33, 1, 0xff99815a);
        setDefaultColor(33, 2, 0xff6a665e);
        setDefaultColor(33, 3, 0xff6a665e);
        setDefaultColor(33, 4, 0xff6a665e);
        setDefaultColor(33, 5, 0xff6a665e);
        setDefaultColor(33, 8, 0xff6d6d6d);
        setDefaultColor(33, 9, 0xff99815a);
        setDefaultColor(33, 10, 0xff6a665e);
        setDefaultColor(33, 11, 0xff6a665e);
        setDefaultColor(33, 12, 0xff6a665e);
        setDefaultColor(33, 13, 0xff6a665e);
        setDefaultColor(34, 0, 0xff99815a);
        setDefaultColor(34, 1, 0xff99815a);
        setDefaultColor(34, 2, 0x8099815a);
        setDefaultColor(34, 3, 0x8099815a);
        setDefaultColor(34, 4, 0x8099815a);
        setDefaultColor(34, 5, 0x8099815a);
        setDefaultColor(34, 8, 0xff99815a);
        setDefaultColor(34, 9, 0xff8d9163);
        setDefaultColor(34, 10, 0x8099815a);
        setDefaultColor(34, 11, 0x8099815a);
        setDefaultColor(34, 12, 0x8099815a);
        setDefaultColor(34, 13, 0x8099815a);
        setDefaultColor(35, 0, 0xffdddddd);
        setDefaultColor(35, 1, 0xffe97e36);
        setDefaultColor(35, 2, 0xffbd4ec8);
        setDefaultColor(35, 3, 0xff678ad3);
        setDefaultColor(35, 4, 0xffc1b41c);
        setDefaultColor(35, 5, 0xff3abb2f);
        setDefaultColor(35, 6, 0xffd8829a);
        setDefaultColor(35, 7, 0xff424242);
        setDefaultColor(35, 8, 0xff9da4a4);
        setDefaultColor(35, 9, 0xff277494);
        setDefaultColor(35, 10, 0xff8035c2);
        setDefaultColor(35, 11, 0xff263299);
        setDefaultColor(35, 12, 0xff55331b);
        setDefaultColor(35, 13, 0xff374c18);
        setDefaultColor(35, 14, 0xffa32c28);
        setDefaultColor(35, 15, 0xff1a1717);
        setDefaultColor(37, 0, 0xc0f1f902);
        setDefaultColor(38, 0, 0xc0f7070f);
        setDefaultColor(39, 0, 0xc0916d55);
        setDefaultColor(40, 0, 0xc09a171c);
        setDefaultColor(41, 0, 0xfffefb5d);
        setDefaultColor(42, 0, 0xffe9e9e9);
        setDefaultColor(43, 0, 0xffa8a8a8);
        setDefaultColor(43, 1, 0xffe5ddaf);
        setDefaultColor(43, 2, 0xff94794a);
        setDefaultColor(43, 3, 0xff828282);
        setDefaultColor(43, 4, 0xff9b6d61);
        setDefaultColor(43, 5, 0xff7a7a7a);
        setDefaultColor(44, 0, 0xffa8a8a8);
        setDefaultColor(44, 1, 0xffe5ddaf);
        setDefaultColor(44, 2, 0xff94794a);
        setDefaultColor(44, 3, 0xff828282);
        setDefaultColor(44, 4, 0xff9b6d61);
        setDefaultColor(44, 5, 0xff7a7a7a);
        setDefaultColor(45, 0, 0xff9b6d61);
        setDefaultColor(46, 0, 0xffdb441a);
        setDefaultColor(47, 0, 0xffb4905a);
        setDefaultColor(48, 0, 0xff1f471f);
        setDefaultColor(49, 0, 0xff13121d);
        setDefaultColor(50, 0, 0x60ffd800);
        setDefaultColor(51, 0, 0xffc05a01);
        setDefaultColor(52, 0, 0xff265f87);
        setDefaultColor(53, 0, 0xffbc9862);
        setDefaultColor(54, 0, 0xff8f691d);
        setDefaultColor(55, 0, 0x6cececec);
        setDefaultColor(56, 0, 0xff818c8f);
        setDefaultColor(57, 0, 0xff60e0e0);
        setDefaultColor(58, 0, 0xff855935);
        setDefaultColor(59, 0, 0x1200990f);
        setDefaultColor(59, 1, 0x3912ab0f);
        setDefaultColor(59, 2, 0x561c890b);
        setDefaultColor(59, 3, 0x9a258b08);
        setDefaultColor(59, 4, 0xce2e8007);
        setDefaultColor(59, 5, 0xe0417a07);
        setDefaultColor(59, 6, 0xe04f7607);
        setDefaultColor(59, 7, 0xe0566507);
        setDefaultColor(60, 0, 0xff734b2d);
        setDefaultColor(60, 1, 0xff6e4629);
        setDefaultColor(60, 2, 0xff694225);
        setDefaultColor(60, 3, 0xff643d21);
        setDefaultColor(60, 4, 0xff5f391d);
        setDefaultColor(60, 5, 0xff5a3519);
        setDefaultColor(60, 6, 0xff553015);
        setDefaultColor(60, 7, 0xff502c11);
        setDefaultColor(60, 8, 0xff4c280e);
        setDefaultColor(61, 0, 0xff747474);
        setDefaultColor(62, 0, 0xff808080);
        setDefaultColor(63, 0, 0xa0b49055);
        setDefaultColor(64, 0, 0xc0866532);
        setDefaultColor(65, 0, 0x80785e34);
        setDefaultColor(66, 0, 0xff776a55);
        setDefaultColor(67, 0, 0xff9e9e9e);
        setDefaultColor(68, 0, 0xa0b49055);
        setDefaultColor(69, 0, 0xa0695433);
        setDefaultColor(70, 0, 0xff8f8f8f);
        setDefaultColor(71, 0, 0xc0c1c1c1);
        setDefaultColor(72, 0, 0xffbc9862);
        setDefaultColor(73, 0, 0xff957861);
        setDefaultColor(74, 0, 0xff957861);
        setDefaultColor(75, 0, 0x80581d12);
        setDefaultColor(76, 0, 0x80a12413);
        setDefaultColor(77, 0, 0x80747474);
        setDefaultColor(78, 0, 0xffebefef);
        setDefaultColor(79, 0, 0x9f7cacfd);
        setDefaultColor(80, 0, 0xfff0f0f0);
        setDefaultColor(81, 0, 0xff108020);
        setDefaultColor(82, 0, 0xff9ea3b0);
        setDefaultColor(83, 0, 0xff93bf64);
        setDefaultColor(84, 0, 0xff6a4936);
        setDefaultColor(85, 0, 0xffbc9862);
        setDefaultColor(86, 0, 0xffbf7515);
        setDefaultColor(87, 0, 0xff6b3433);
        setDefaultColor(88, 0, 0xff544033);
        setDefaultColor(89, 0, 0xffc08f46);
        setDefaultColor(90, 0, 0xff732486);
        setDefaultColor(91, 0, 0xffc4971e);
        setDefaultColor(92, 0, 0xffe3cccd);
        setDefaultColor(93, 0, 0xff979393);
        setDefaultColor(94, 0, 0xffc09393);
        setDefaultColor(95, 0, 0xff8f691d);
        setDefaultColor(96, 0, 0xff7e5d2d);
        setDefaultColor(96, 1, 0xff7e5d2d);
        setDefaultColor(96, 2, 0xff7e5d2d);
        setDefaultColor(96, 3, 0xff7e5d2d);
        setDefaultColor(96, 4, 0x207e5d2d);
        setDefaultColor(96, 5, 0x207e5d2d);
        setDefaultColor(96, 6, 0x207e5d2d);
        setDefaultColor(96, 7, 0x207e5d2d);
        setDefaultColor(97, 0, 0xff686868);
        setDefaultColor(97, 1, 0xff959595);
        setDefaultColor(97, 2, 0xff7a7a7a);
        setDefaultColor(98, 0, 0xff7a7a7a);
        setDefaultColor(98, 1, 0xff72776a);
        setDefaultColor(98, 2, 0xff767676);
        setDefaultColor(99, 0, 0xffcaab78);
        setDefaultColor(99, 1, 0xff8d6a53);
        setDefaultColor(99, 2, 0xff8d6a53);
        setDefaultColor(99, 3, 0xff8d6a53);
        setDefaultColor(99, 4, 0xff8d6a53);
        setDefaultColor(99, 5, 0xff8d6a53);
        setDefaultColor(99, 6, 0xff8d6a53);
        setDefaultColor(99, 7, 0xff8d6a53);
        setDefaultColor(99, 8, 0xff8d6a53);
        setDefaultColor(99, 9, 0xff8d6a53);
        setDefaultColor(99, 10, 0xffcaab78);
        setDefaultColor(100, 0, 0xffcaab78);
        setDefaultColor(100, 1, 0xffb62524);
        setDefaultColor(100, 2, 0xffb62524);
        setDefaultColor(100, 3, 0xffb62524);
        setDefaultColor(100, 4, 0xffb62524);
        setDefaultColor(100, 5, 0xffb62524);
        setDefaultColor(100, 6, 0xffb62524);
        setDefaultColor(100, 7, 0xffb62524);
        setDefaultColor(100, 8, 0xffb62524);
        setDefaultColor(100, 9, 0xffb62524);
        setDefaultColor(100, 10, 0xffcaab78);
        setDefaultColor(101, 0, 0x806d6c6a);
        setDefaultColor(102, 0, 0x60ffffff);
        setDefaultColor(103, 0, 0xff979924);
        setDefaultColor(104, 0, 0x40009900);
        setDefaultColor(104, 1, 0x48139402);
        setDefaultColor(104, 2, 0x50269004);
        setDefaultColor(104, 3, 0x58398b07);
        setDefaultColor(104, 4, 0x604d8609);
        setDefaultColor(104, 5, 0x6860810c);
        setDefaultColor(104, 6, 0x70737c0e);
        setDefaultColor(104, 7, 0x78877810);
        setDefaultColor(105, 0, 0x40009900);
        setDefaultColor(105, 1, 0x48139402);
        setDefaultColor(105, 2, 0x50269004);
        setDefaultColor(105, 3, 0x58398b07);
        setDefaultColor(105, 4, 0x604d8609);
        setDefaultColor(105, 5, 0x6860810c);
        setDefaultColor(105, 6, 0x70737c0e);
        setDefaultColor(105, 7, 0x78877810);
        setDefaultColor(106, 0, 0x801f4e0a);
        setDefaultColor(107, 0, 0xc0bc9862);
        setDefaultColor(108, 0, 0xff9b6d61);
        setDefaultColor(109, 0, 0xff7a7a7a);

        for (int i = 0; i < BLOCK_NUM; i++)
        {
            boolean flag2 = true;

            for (int j = 0; flag2 && j < 16; j++)
            {
                BlockColor blockcolor = defaultColor[pointer(i, j)];
                flag2 = blockcolor == null || blockcolor.alpha != 0.0F && blockcolor.alpha != 1.0F;
            }

            opaqueList[i] = flag2;
        }
    }
}
