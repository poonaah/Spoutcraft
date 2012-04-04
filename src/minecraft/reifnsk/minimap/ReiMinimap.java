package reifnsk.minimap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ChatAllowedCharacters;
import net.minecraft.src.ChatLine;
import net.minecraft.src.Chunk;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.EmptyChunk;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityAnimal;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.EntityLightningBolt;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityMob;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntitySlime;
import net.minecraft.src.EntitySquid;
import net.minecraft.src.EnumSkyBlock;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.MathHelper;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TexturePackBase;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ReiMinimap implements Runnable
{
    public static final boolean DEBUG_BUILD = false;
    public static final int MC_VERSION_INT = 0x2010203;
    public static final String MC_123 = "1.2.3";
    public static final String MC_110 = "1.1";
    public static final String MC_100 = "1.0.0";
    public static final String MC_B19P5 = "Beta 1.9pre5";
    public static final String MC_B19P4 = "Beta 1.9pre4";
    public static final String MC_B181 = "Beta 1.8.1";
    public static final String MC_B180 = "Beta 1.8";
    public static final String MC_B173 = "Beta 1.7.3";
    public static final String MC_B166 = "Beta 1.6.6";
    public static final String MC_B151 = "Beta 1.5_01";
    public static final int MOD_VERSION_INT = 0x30003;
    public static final String MOD_VERSION = "v3.0_03";
    public static final String MC_VERSION = "1.2.3";
    public static final String version = String.format("%s [%s]", new Object[]
            {
                "v3.0_03", "1.2.3"
            });
    public static final boolean SUPPORT_HEIGHT_MOD = true;
    public static final boolean SUPPORT_NEW_LIGHTING = true;
    public static final boolean SUPPORT_SWAMPLAND_BIOME_COLOR = true;
    public static final boolean CHANGE_SUNRISE_DIRECTION = true;
    public boolean useModloader;
    private static final double renderZ = 1D;
    private static final boolean noiseAdded = false;
    private static final float noiseAlpha = 0.1F;
    static final File directory;
    private float lightBrightnessTable[];
    private static final int updateFrequencys[] =
    {
        2, 5, 10, 20, 40
    };
    public static final ReiMinimap instance;
    private static final int TEXTURE_SIZE = 256;
    private static BiomeGenBase bgbList[];
    Minecraft theMinecraft;
    private Tessellator tessellator;
    private World theWorld;
    private EntityPlayer thePlayer;
    private GuiIngame ingameGUI;
    private ScaledResolution scaledResolution;
    private String errorString;
    private boolean multiplayer;
    private SocketAddress currentServer;
    private String currentLevelName;
    private int currentDimension;
    private int scWidth;
    private int scHeight;
    private GLTextureBufferedImage texture;
    private ChunkCache chunkCache;
    final Thread mcThread = Thread.currentThread();
    private Thread workerThread;
    private Lock lock;
    private Condition condition;
    private StripCounter stripCounter;
    private int stripCountMax1;
    private int stripCountMax2;
    private GuiScreen guiScreen;
    private int posX;
    private int posY;
    private double posYd;
    private int posZ;
    private int chunkCoordX;
    private int chunkCoordZ;
    private float sin;
    private float cos;
    private int lastX;
    private int lastY;
    private int lastZ;
    private int skylightSubtracted;
    private boolean isUpdateImage;
    private boolean isCompleteImage;
    private boolean enable;
    private boolean showMenuKey;
    private boolean filtering;
    private int mapPosition;
    private int textureView;
    private float mapOpacity;
    private float largeMapOpacity;
    private boolean largeMapLabel;
    private int lightmap;
    private int lightType;
    private boolean undulate;
    private boolean transparency;
    private boolean environmentColor;
    private boolean omitHeightCalc;
    private int updateFrequencySetting;
    private boolean threading;
    private int threadPriority;
    private boolean hideSnow;
    private boolean showChunkGrid;
    private boolean showSlimeChunk;
    private boolean heightmap;
    private boolean showCoordinate;
    private int fontScale;
    private int mapScale;
    private int largeMapScale;
    private int coordinateType;
    private boolean visibleWaypoints;
    private boolean deathPoint;
    private boolean useStencil;
    private boolean notchDirection;
    private boolean roundmap;
    private boolean fullmap;
    private boolean forceUpdate;
    private boolean marker;
    private boolean markerLabel;
    private boolean markerIcon;
    private boolean markerDistance;
    private long currentTimeMillis;
    private long currentTime;
    private long previousTime;
    private int renderType;
    private TreeMap wayPtsMap;
    private List wayPts;
    private int waypointDimension;
    private static final double ZOOM_LIST[] =
    {
        0.5D, 1.0D, 1.5D, 2D, 4D, 8D
    };
    private int defaultZoom;
    private int flagZoom;
    private int largeZoom;
    private double targetZoom;
    private double currentZoom;
    private float zoomVisible;
    private int grassColor;
    private int foliageColor;
    private int foliageColorPine;
    private int foliageColorBirch;
    private long delay;
    private boolean delayFlag;
    private TexturePackBase texturePack;
    private int worldHeight;
    private int temperatureColor[];
    private int humidityColor[];
    private HashMap dimensionName;
    private HashMap dimensionScale;
    private boolean chatWelcomed;
    private List chatLineList;
    private ChatLine chatLineLast;
    private long chatTime;
    private boolean configEntitiesRadar;
    private boolean configEntityPlayer;
    private boolean configEntityAnimal;
    private boolean configEntityMob;
    private boolean configEntitySquid;
    private boolean configEntitySlime;
    private boolean configEntityLiving;
    private boolean configEntityLightning;
    private boolean configEntityDirection;
    private boolean allowCavemap;
    private boolean allowEntitiesRadar;
    private boolean allowEntityPlayer;
    private boolean allowEntityAnimal;
    private boolean allowEntityMob;
    private boolean allowEntitySquid;
    private boolean allowEntitySlime;
    private boolean allowEntityLiving;
    private boolean visibleEntitiesRadar;
    private boolean visibleEntityPlayer;
    private boolean visibleEntityAnimal;
    private boolean visibleEntityMob;
    private boolean visibleEntitySquid;
    private boolean visibleEntitySlime;
    private boolean visibleEntityLiving;
    private boolean autoUpdateCheck;
    private int updateCheckFlag;
    private URL updateCheckURL;
    long ntime;
    int count;
    static float temp[];
    private float lightmapRed[];
    private float lightmapGreen[];
    private float lightmapBlue[];
    private static final Map obfascatorFieldMap = createObfuscatorFieldMap();

    boolean getAllowCavemap()
    {
        return allowCavemap;
    }

    boolean getAllowEntitiesRadar()
    {
        return allowEntitiesRadar;
    }

    private ReiMinimap()
    {
        lightBrightnessTable = generateLightBrightnessTable(0.125F);
        tessellator = Tessellator.instance;
        texture = GLTextureBufferedImage.create(256, 256);
        chunkCache = new ChunkCache(6);
        lock = new ReentrantLock();
        condition = lock.newCondition();
        stripCounter = new StripCounter(289);
        stripCountMax1 = 0;
        stripCountMax2 = 0;
        enable = true;
        showMenuKey = true;
        filtering = true;
        mapPosition = 2;
        textureView = 0;
        mapOpacity = 1.0F;
        largeMapOpacity = 1.0F;
        largeMapLabel = false;
        lightmap = 0;
        lightType = 0;
        undulate = true;
        transparency = true;
        environmentColor = true;
        omitHeightCalc = true;
        updateFrequencySetting = 2;
        threading = false;
        threadPriority = 1;
        hideSnow = false;
        showChunkGrid = false;
        showSlimeChunk = false;
        heightmap = true;
        showCoordinate = true;
        fontScale = 1;
        mapScale = 1;
        largeMapScale = 1;
        coordinateType = 1;
        visibleWaypoints = true;
        deathPoint = false;
        useStencil = false;
        notchDirection = true;
        roundmap = false;
        fullmap = false;
        marker = true;
        markerLabel = true;
        markerIcon = true;
        markerDistance = true;
        renderType = 0;
        wayPtsMap = new TreeMap();
        wayPts = new ArrayList();
        defaultZoom = 1;
        flagZoom = 1;
        largeZoom = 0;
        targetZoom = 1.0D;
        currentZoom = 1.0D;
        worldHeight = 255;
        dimensionName = new HashMap();
        dimensionScale = new HashMap();
        dimensionName.put(Integer.valueOf(0), "Overworld");
        dimensionScale.put(Integer.valueOf(0), Double.valueOf(1.0D));
        dimensionName.put(Integer.valueOf(-1), "Nether");
        dimensionScale.put(Integer.valueOf(-1), Double.valueOf(8D));
        dimensionName.put(Integer.valueOf(1), "The Ender");
        dimensionScale.put(Integer.valueOf(1), Double.valueOf(1.0D));
        chatTime = 0L;
        configEntitiesRadar = false;
        configEntityPlayer = true;
        configEntityAnimal = true;
        configEntityMob = true;
        configEntitySquid = true;
        configEntitySlime = true;
        configEntityLiving = true;
        configEntityLightning = true;
        configEntityDirection = true;
        autoUpdateCheck = false;
        updateCheckFlag = 0;

        try
        {
            updateCheckURL = new URL("http://dl.dropbox.com/u/34787499/minecraft/version.txt");
        }
        catch (Exception exception) { }

        ntime = 0L;
        count = 0;
        lightmapRed = new float[256];
        lightmapGreen = new float[256];
        lightmapBlue = new float[256];

        if (!directory.exists())
        {
            directory.mkdirs();
        }

        if (!directory.isDirectory())
        {
            errorString = "[Rei's Minimap] ERROR: Failed to create the rei_minimap folder.";
            error(errorString);
        }

        loadOptions();
    }

    public void onTickInGame(Minecraft minecraft)
    {
        currentTimeMillis = System.currentTimeMillis();
        GL11.glPushAttrib(0xfffff);
        GL11.glPushClientAttrib(-1);
        GL11.glPushMatrix();

        try
        {
            if (minecraft == null)
            {
                return;
            }

            if (errorString != null)
            {
                scaledResolution = new ScaledResolution(minecraft.gameSettings, minecraft.displayWidth, minecraft.displayHeight);
                minecraft.fontRenderer.drawStringWithShadow(errorString, scaledResolution.getScaledWidth() - minecraft.fontRenderer.getStringWidth(errorString) - 2, 2, 0xffff0000);
                return;
            }

            if (theMinecraft == null)
            {
                theMinecraft = minecraft;
                ingameGUI = theMinecraft.ingameGUI;
                chatLineList = (List)getField(ingameGUI, "chatMessageList");
                chatLineList = ((List)(chatLineList != null ? chatLineList : ((List)(new ArrayList()))));

                try
                {
                    Field afield[] = (net.minecraft.src.RenderManager.class).getDeclaredFields();
                    int j = afield.length;
                    int l = 0;

                    do
                    {
                        if (l >= j)
                        {
                            break;
                        }

                        Field field = afield[l];

                        if (field.getType() == (java.util.Map.class))
                        {
                            WaypointEntityRender waypointentityrender = new WaypointEntityRender(minecraft);
                            waypointentityrender.setRenderManager(RenderManager.instance);
                            field.setAccessible(true);
                            ((Map)field.get(RenderManager.instance)).put(reifnsk.minimap.WaypointEntity.class, waypointentityrender);
                            break;
                        }

                        l++;
                    }
                    while (true);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }

            if (texturePack != minecraft.texturePackList.selectedTexturePack)
            {
                texturePack = minecraft.texturePackList.selectedTexturePack;
                BlockColor.textureColorUpdate();
                BlockColor.calcBlockColorTD();
                temperatureColor = GLTexture.TEMPERATURE.getData();
                humidityColor = GLTexture.HUMIDITY.getData();
            }

            thePlayer = theMinecraft.thePlayer;

            if (theWorld != theMinecraft.theWorld)
            {
                delay = currentTimeMillis + 500L;
                isUpdateImage = false;
                texture.unregister();
                theWorld = theMinecraft.theWorld;
                theWorld.addWeatherEffect(new WaypointEntity(theMinecraft));
                multiplayer = thePlayer instanceof EntityClientPlayerMP;

                if (theWorld != null)
                {
                    worldHeight = theWorld.getWorldHeight() - 1;
                    Environment.setWorld(theWorld);
                    boolean flag;

                    if (multiplayer)
                    {
                        Object obj = null;
                        SocketAddress socketaddress = (SocketAddress)getFields(thePlayer, new String[]
                                {
                                    "sendQueue", "netManager", "remoteSocketAddress"
                                });

                        if (socketaddress == null)
                        {
                            throw new MinimapException("SMP ADDRESS ACQUISITION FAILURE");
                        }

                        flag = currentServer != socketaddress;

                        if (flag)
                        {
                            String s3 = socketaddress.toString().replaceAll("[\r\n]", "");
                            Matcher matcher1 = Pattern.compile("(.*)/(.*):([0-9]+)").matcher(s3);
                            String s;

                            if (matcher1.matches())
                            {
                                s = matcher1.group(1);

                                if (s.isEmpty())
                                {
                                    s = matcher1.group(2);
                                }

                                if (!matcher1.group(3).equals("25565"))
                                {
                                    s = (new StringBuilder()).append(s).append("[").append(matcher1.group(3)).append("]").toString();
                                }
                            }
                            else
                            {
                                String s4 = socketaddress.toString().replaceAll("[a-z]", "a").replaceAll("[A-Z]", "A").replaceAll("[0-9]", "*");
                                throw new MinimapException((new StringBuilder()).append("SMP ADDRESS FORMAT EXCEPTION: ").append(s4).toString());
                            }

                            char ac2[] = ChatAllowedCharacters.allowedCharactersArray;
                            int l3 = ac2.length;

                            for (int j4 = 0; j4 < l3; j4++)
                            {
                                char c2 = ac2[j4];
                                s = s.replace(c2, '_');
                            }

                            currentLevelName = s;
                            currentServer = socketaddress;
                        }
                    }
                    else
                    {
                        String s1 = (String)getFields(theWorld, new String[]
                                {
                                    "worldInfo", "levelName"
                                });

                        if (s1 == null)
                        {
                            throw new MinimapException("WORLD_NAME ACQUISITION FAILURE");
                        }

                        char ac[] = ChatAllowedCharacters.allowedCharactersArray;
                        int i1 = ac.length;

                        for (int k1 = 0; k1 < i1; k1++)
                        {
                            char c = ac[k1];
                            s1 = s1.replace(c, '_');
                        }

                        flag = !s1.equals(currentLevelName) || currentServer != null;

                        if (flag)
                        {
                            currentLevelName = s1;
                            flag = true;
                        }

                        currentServer = null;
                    }

                    currentDimension = thePlayer.dimension;
                    waypointDimension = currentDimension;

                    if (flag)
                    {
                        chatTime = System.currentTimeMillis();
                        chatWelcomed = multiplayer;
                        allowCavemap = multiplayer;
                        allowEntitiesRadar = multiplayer;
                        allowEntityPlayer = multiplayer;
                        allowEntityAnimal = multiplayer;
                        allowEntityMob = multiplayer;
                        allowEntitySlime = multiplayer;
                        allowEntitySquid = multiplayer;
                        allowEntityLiving = multiplayer;
                        loadWaypoints();
                    }

                    wayPts = (List)wayPtsMap.get(Integer.valueOf(waypointDimension));

                    if (wayPts == null)
                    {
                        wayPts = new ArrayList();
                        wayPtsMap.put(Integer.valueOf(waypointDimension), wayPts);
                    }
                }

                stripCounter.reset();
            }

            delayFlag = currentTimeMillis < delay;
            Environment.calcEnvironment();

            if (!chatWelcomed && System.currentTimeMillis() < chatTime + 10000L)
            {
                Iterator iterator = chatLineList.iterator();

                do
                {
                    if (!iterator.hasNext())
                    {
                        break;
                    }

                    ChatLine chatline = (ChatLine)iterator.next();

                    if (chatline == null || chatLineLast == chatline)
                    {
                        break;
                    }

                    Matcher matcher = Pattern.compile("\2470\2470((?:\247[1-9a-d])+)\247e\247f").matcher(chatline.message);

                    while (matcher.find())
                    {
                        chatWelcomed = true;
                        char ac1[] = matcher.group(1).toCharArray();
                        int l1 = ac1.length;
                        int l2 = 0;

                        while (l2 < l1)
                        {
                            char c1 = ac1[l2];

                            switch (c1)
                            {
                                case 49:
                                    allowCavemap = true;
                                    break;

                                case 50:
                                    allowEntityPlayer = true;
                                    break;

                                case 51:
                                    allowEntityAnimal = true;
                                    break;

                                case 52:
                                    allowEntityMob = true;
                                    break;

                                case 53:
                                    allowEntitySlime = true;
                                    break;

                                case 54:
                                    allowEntitySquid = true;
                                    break;

                                case 55:
                                    allowEntityLiving = true;
                                    break;
                            }

                            l2++;
                        }
                    }
                }
                while (true);

                chatLineLast = chatLineList.isEmpty() ? null : (ChatLine)chatLineList.get(0);

                if (chatWelcomed)
                {
                    allowEntitiesRadar = allowEntityPlayer || allowEntityAnimal || allowEntityMob || allowEntitySlime || allowEntitySquid || allowEntityLiving;

                    if (allowCavemap)
                    {
                        chatInfo("\247E[Rei's Minimap] enabled: cavemapping.");
                    }

                    if (allowEntitiesRadar)
                    {
                        StringBuilder stringbuilder = new StringBuilder("\247E[Rei's Minimap] enabled: entities radar (");

                        if (allowEntityPlayer)
                        {
                            stringbuilder.append("Player, ");
                        }

                        if (allowEntityAnimal)
                        {
                            stringbuilder.append("Animal, ");
                        }

                        if (allowEntityMob)
                        {
                            stringbuilder.append("Mob, ");
                        }

                        if (allowEntitySlime)
                        {
                            stringbuilder.append("Slime, ");
                        }

                        if (allowEntitySquid)
                        {
                            stringbuilder.append("Squid, ");
                        }

                        if (allowEntityLiving)
                        {
                            stringbuilder.append("Living, ");
                        }

                        stringbuilder.setLength(stringbuilder.length() - 2);
                        stringbuilder.append(")");
                        chatInfo(stringbuilder.toString());
                    }
                }
            }
            else
            {
                chatWelcomed = true;
            }

            visibleEntitiesRadar = allowEntitiesRadar && configEntitiesRadar;
            visibleEntityPlayer = allowEntityPlayer && configEntityPlayer;
            visibleEntityAnimal = allowEntityAnimal && configEntityAnimal;
            visibleEntityMob = allowEntityMob && configEntityMob;
            visibleEntitySlime = allowEntitySlime && configEntitySlime;
            visibleEntitySquid = allowEntitySquid && configEntitySquid;
            visibleEntityLiving = allowEntityLiving && configEntityLiving;
            int i = theMinecraft.displayWidth;
            int k = theMinecraft.displayHeight;
            scaledResolution = new ScaledResolution(theMinecraft.gameSettings, i, k);
            GL11.glScaled(1.0D / (double)scaledResolution.scaleFactor, 1.0D / (double)scaledResolution.scaleFactor, 1.0D);
            scWidth = minecraft.displayWidth;
            scHeight = minecraft.displayHeight;
            KeyInput.update();

            if (minecraft.currentScreen == null)
            {
                if (!fullmap)
                {
                    if (KeyInput.TOGGLE_ZOOM.isKeyPush())
                    {
                        if (Keyboard.isKeyDown(theMinecraft.gameSettings.keyBindSneak.keyCode))
                        {
                            flagZoom = (flagZoom != 0 ? flagZoom : ZOOM_LIST.length) - 1;
                        }
                        else
                        {
                            flagZoom = (flagZoom + 1) % ZOOM_LIST.length;
                        }
                    }
                    else if (KeyInput.ZOOM_IN.isKeyPush() && flagZoom < ZOOM_LIST.length - 1)
                    {
                        flagZoom++;
                    }
                    else if (KeyInput.ZOOM_OUT.isKeyPush() && flagZoom > 0)
                    {
                        flagZoom--;
                    }

                    targetZoom = ZOOM_LIST[flagZoom];
                }
                else
                {
                    if (KeyInput.TOGGLE_ZOOM.isKeyPush())
                    {
                        if (Keyboard.isKeyDown(theMinecraft.gameSettings.keyBindSneak.keyCode))
                        {
                            largeZoom = (largeZoom != 0 ? largeZoom : ZOOM_LIST.length) - 1;
                        }
                        else
                        {
                            largeZoom = (largeZoom + 1) % ZOOM_LIST.length;
                        }
                    }
                    else if (KeyInput.ZOOM_IN.isKeyPush() && largeZoom < ZOOM_LIST.length - 1)
                    {
                        largeZoom++;
                    }
                    else if (KeyInput.ZOOM_OUT.isKeyPush() && largeZoom > 0)
                    {
                        largeZoom--;
                    }

                    targetZoom = ZOOM_LIST[largeZoom];
                }

                if (KeyInput.TOGGLE_ENABLE.isKeyPush())
                {
                    enable = !enable;
                    stripCounter.reset();
                    forceUpdate = true;
                }

                if (KeyInput.TOGGLE_RENDER_TYPE.isKeyPush())
                {
                    if (Keyboard.isKeyDown(theMinecraft.gameSettings.keyBindSneak.keyCode))
                    {
                        renderType--;

                        if (renderType < 0)
                        {
                            renderType = EnumOption.RENDER_TYPE.getValueNum() - 1;
                        }

                        if (!allowCavemap && EnumOption.RENDER_TYPE.getValue(renderType) == EnumOptionValue.CAVE)
                        {
                            renderType--;
                        }
                    }
                    else
                    {
                        renderType++;

                        if (!allowCavemap && EnumOption.RENDER_TYPE.getValue(renderType) == EnumOptionValue.CAVE)
                        {
                            renderType++;
                        }

                        if (renderType >= EnumOption.RENDER_TYPE.getValueNum())
                        {
                            renderType = 0;
                        }
                    }

                    stripCounter.reset();
                    forceUpdate = true;
                }

                if (KeyInput.TOGGLE_WAYPOINTS_DIMENSION.isKeyPush())
                {
                    if (Keyboard.isKeyDown(theMinecraft.gameSettings.keyBindSneak.keyCode))
                    {
                        prevDimension();
                    }
                    else
                    {
                        nextDimension();
                    }
                }

                if (KeyInput.TOGGLE_WAYPOINTS_VISIBLE.isKeyPush())
                {
                    visibleWaypoints = !visibleWaypoints;
                }

                if (KeyInput.TOGGLE_WAYPOINTS_MARKER.isKeyPush())
                {
                    marker = !marker;
                }

                if (KeyInput.TOGGLE_LARGE_MAP.isKeyPush())
                {
                    fullmap = !fullmap;
                    currentZoom = targetZoom = ZOOM_LIST[fullmap ? largeZoom : flagZoom];
                    forceUpdate = true;
                    stripCounter.reset();

                    if (threading)
                    {
                        lock.lock();

                        try
                        {
                            stripCounter.reset();
                            mapCalc(false);
                        }
                        finally
                        {
                            lock.unlock();
                        }
                    }
                }

                if (KeyInput.TOGGLE_LARGE_MAP_LABEL.isKeyPush() && fullmap)
                {
                    largeMapLabel = !largeMapLabel;
                }

                if (allowEntitiesRadar && KeyInput.TOGGLE_ENTITIES_RADAR.isKeyPush())
                {
                    configEntitiesRadar = !configEntitiesRadar;
                }

                if (KeyInput.SET_WAYPOINT.isKeyPushUp())
                {
                    waypointDimension = currentDimension;
                    wayPts = (List)wayPtsMap.get(Integer.valueOf(waypointDimension));
                    minecraft.displayGuiScreen(new GuiWaypointEditorScreen(minecraft, null));
                }

                if (KeyInput.WAYPOINT_LIST.isKeyPushUp())
                {
                    minecraft.displayGuiScreen(new GuiWaypointScreen(null));
                }

                if (KeyInput.MENU_KEY.isKeyPush())
                {
                    minecraft.displayGuiScreen(new GuiOptionScreen());
                }
            }
            else if (fullmap)
            {
                currentZoom = targetZoom = ZOOM_LIST[flagZoom];
                fullmap = false;
                forceUpdate = true;
                stripCounter.reset();
            }

            if (!allowCavemap && EnumOption.RENDER_TYPE.getValue(renderType) == EnumOptionValue.CAVE)
            {
                renderType = 0;
            }

            if (deathPoint && (theMinecraft.currentScreen instanceof GuiGameOver) && !(guiScreen instanceof GuiGameOver))
            {
                String s2 = "Death Point";
                int j1 = MathHelper.floor_double(thePlayer.posX);
                int i2 = MathHelper.floor_double(thePlayer.posY);
                int i3 = MathHelper.floor_double(thePlayer.posZ);
                Random random = new Random();
                float f = random.nextFloat();
                float f1 = random.nextFloat();
                float f2 = random.nextFloat();
                boolean flag1 = false;
                Iterator iterator1 = wayPts.iterator();

                do
                {
                    if (!iterator1.hasNext())
                    {
                        break;
                    }

                    Waypoint waypoint = (Waypoint)iterator1.next();

                    if (waypoint.type != 1 || waypoint.x != j1 || waypoint.y != i2 || waypoint.z != i3 || !waypoint.enable)
                    {
                        continue;
                    }

                    flag1 = true;
                    break;
                }
                while (true);

                if (!flag1)
                {
                    wayPts.add(new Waypoint(s2, j1, i2, i3, true, f, f1, f2, 1));
                    saveWaypoints();
                }
            }

            guiScreen = theMinecraft.currentScreen;

            if (!enable || !checkGuiScreen(minecraft.currentScreen))
            {
                return;
            }

            if (threading)
            {
                if (workerThread == null || !workerThread.isAlive())
                {
                    workerThread = new Thread(this);
                    workerThread.setPriority(3 + threadPriority);
                    workerThread.setDaemon(true);
                    workerThread.start();
                }
            }
            else
            {
                mapCalc(true);
            }

            if (lock.tryLock())
            {
                try
                {
                    if (isUpdateImage)
                    {
                        isUpdateImage = false;
                        texture.setMinFilter(filtering);
                        texture.setMagFilter(filtering);
                        texture.setClampTexture(true);
                        texture.register();
                    }

                    condition.signal();
                }
                finally
                {
                    lock.unlock();
                }
            }

            currentTime = System.nanoTime();
            double d = (double)(currentTime - previousTime) * 1.0E-009D;
            zoomVisible -= d;

            if (currentZoom != targetZoom)
            {
                double d1 = Math.max(0.0D, Math.min(1.0D, d * 4D));
                currentZoom += (targetZoom - currentZoom) * d1;

                if (Math.abs(currentZoom - targetZoom) < 0.0005D)
                {
                    currentZoom = targetZoom;
                }

                zoomVisible = 3F;
            }

            previousTime = currentTime;

            if (texture.getId() != 0)
            {
                int i4 = fontScale != 0 ? fontScale : scaledResolution.scaleFactor + 1 >> 1;

                switch (mapPosition)
                {
                    case 0:
                        byte byte0 = 37;
                        byte byte2 = 37;
                        break;

                    case 1:
                        byte byte1 = 37;
                        int j3 = scHeight - 37;
                        j3 -= (i4 * ((showMenuKey | showCoordinate ? 2 : 0) + (showMenuKey ? 9 : 0) + (showCoordinate ? 18 : 0))) / scaledResolution.scaleFactor;
                        break;

                    case 2:
                    default:
                        int j2 = scWidth - 37;
                        byte byte3 = 37;
                        break;

                    case 3:
                        int k2 = scWidth - 37;
                        int k3 = scHeight - 37;
                        k3 -= (i4 * ((showMenuKey | showCoordinate ? 2 : 0) + (showMenuKey ? 9 : 0) + (showCoordinate ? 18 : 0))) / scaledResolution.scaleFactor;
                        break;
                }

                if (fullmap)
                {
                    renderFullMap();
                }
                else if (roundmap)
                {
                    renderRoundMap();
                }
                else
                {
                    renderSquareMap();
                }
            }
        }
        catch (RuntimeException runtimeexception)
        {
            runtimeexception.printStackTrace();
            errorString = (new StringBuilder()).append("[Rei's Minimap] ERROR: ").append(runtimeexception.getMessage()).toString();
            error("mainloop runtime exception", runtimeexception);
        }
        finally
        {
            GL11.glPopMatrix();
            GL11.glPopClientAttrib();
            GL11.glPopAttrib();
        }

        if (count != 0)
        {
            theMinecraft.fontRenderer.drawStringWithShadow(String.format("%12d", new Object[]
                    {
                        Long.valueOf(ntime / (long)count)
                    }), 2, 12, -1);
        }

        Thread.yield();
    }

    public void run()
    {
        if (theMinecraft == null)
        {
            return;
        }

        Thread thread;

        do
        {
            for (thread = Thread.currentThread(); enable && thread == workerThread && threading;)
            {
                try
                {
                    if (renderType == 0)
                    {
                        Thread.sleep(updateFrequencys[updateFrequencys.length - updateFrequencySetting - 1] * 2);
                    }
                    else
                    {
                        Thread.sleep(updateFrequencys[updateFrequencys.length - updateFrequencySetting - 1] * 6);
                    }
                }
                catch (InterruptedException interruptedexception)
                {
                    return;
                }

                lock.lock();

                try
                {
                    try
                    {
                        mapCalc(false);

                        if (isCompleteImage || isUpdateImage)
                        {
                            condition.await();
                        }
                    }
                    catch (InterruptedException interruptedexception1)
                    {
                        return;
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                        errorString = (new StringBuilder()).append("[Rei's Minimap] ERROR: ").append(exception.getMessage()).toString();
                        error("mainloop runtime exception", exception);
                    }
                }
                finally
                {
                    lock.unlock();
                }
            }

            try
            {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedexception2)
            {
                return;
            }

            lock.lock();

            try
            {
                condition.await();
            }
            catch (InterruptedException interruptedexception3)
            {
                return;
            }
            finally
            {
                lock.unlock();
            }
        }
        while (thread == workerThread);
    }

    private void startDrawingQuads()
    {
        tessellator.startDrawingQuads();
    }

    private void draw()
    {
        tessellator.draw();
    }

    private void addVertexWithUV(double d, double d1, double d2, double d3, double d4)
    {
        tessellator.addVertexWithUV(d, d1, d2, d3, d4);
    }

    private void mapCalc(boolean flag)
    {
        if (delayFlag)
        {
            return;
        }

        if (theWorld == null || thePlayer == null)
        {
            return;
        }

        Thread thread = Thread.currentThread();

        if (stripCounter.count() == 0)
        {
            posX = MathHelper.floor_double(thePlayer.posX);
            posY = MathHelper.floor_double(thePlayer.posY);
            posYd = thePlayer.posY;
            posZ = MathHelper.floor_double(thePlayer.posZ);
            chunkCoordX = thePlayer.chunkCoordX;
            chunkCoordZ = thePlayer.chunkCoordZ;
            skylightSubtracted = calculateSkylightSubtracted(theWorld.getWorldTime(), 0.0F);

            if (lightType == 0)
            {
                switch (lightmap)
                {
                    case 0:
                        updateLightmap(theWorld.getWorldTime(), 0.0F);
                        break;

                    case 1:
                        updateLightmap(6000L, 0.0F);
                        break;

                    case 2:
                        updateLightmap(18000L, 0.0F);
                        break;

                    case 3:
                        updateLightmap(6000L, 0.0F);
                        break;
                }
            }

            double d = Math.toRadians(roundmap && !fullmap ? 45F - thePlayer.rotationYaw : notchDirection ? 225 : -45);
            sin = (float)Math.sin(d);
            cos = (float)Math.cos(d);
            grassColor = ColorizerGrass.getGrassColor(0.5D, 1.0D);
            foliageColor = ColorizerFoliage.getFoliageColor(0.5D, 1.0D);
            foliageColorPine = ColorizerFoliage.getFoliageColorPine();
            foliageColorBirch = ColorizerFoliage.getFoliageColorBirch();
        }

        if (fullmap)
        {
            stripCountMax1 = 289;
            stripCountMax2 = 289;
        }
        else
        {
            double d1 = Math.ceil(4D / currentZoom) * 2D + 1.0D;
            stripCountMax1 = (int)(d1 * d1);
            d1 = Math.ceil(4D / targetZoom) * 2D + 1.0D;
            stripCountMax2 = (int)(d1 * d1);
        }

        if (renderType == 1)
        {
            if (forceUpdate || !flag)
            {
                biomeCalc(thread);
            }
            else
            {
                biomeCalcStrip(thread);
            }
        }
        else if (renderType == 2)
        {
            if (forceUpdate || !flag)
            {
                caveCalc();
            }
            else
            {
                caveCalcStrip();
            }
        }
        else if (forceUpdate || !flag)
        {
            surfaceCalc(thread);
        }
        else
        {
            surfaceCalcStrip(thread);
        }

        if (isCompleteImage)
        {
            forceUpdate = false;
            isCompleteImage = false;
            stripCounter.reset();
            lastX = posX;
            lastY = posY;
            lastZ = posZ;
        }
    }

    private void surfaceCalc(Thread thread)
    {
        Chunk chunk;

        for (int i = Math.max(stripCountMax1, stripCountMax2); stripCounter.count() < i; surfaceCalc(chunk, thread))
        {
            java.awt.Point point = stripCounter.next();
            chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void surfaceCalcStrip(Thread thread)
    {
        int i = Math.max(stripCountMax1, stripCountMax2);
        int j = updateFrequencys[updateFrequencySetting];

        for (int k = 0; k < j && stripCounter.count() < i; k++)
        {
            java.awt.Point point = stripCounter.next();
            Chunk chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
            surfaceCalc(chunk, thread);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void surfaceCalc(Chunk chunk, Thread thread)
    {
        if (delayFlag)
        {
            return;
        }

        if (chunk == null || (chunk instanceof EmptyChunk))
        {
            return;
        }

        int i = (128 + chunk.xPosition * 16) - posX;
        int j = (128 + chunk.zPosition * 16) - posZ;
        boolean flag = showSlimeChunk && currentDimension == 0 && chunkCache.isSlimeSpawn(chunk.xPosition, chunk.zPosition);
        PixelColor pixelcolor = new PixelColor(transparency);
        Chunk chunk1 = null;
        Chunk chunk2 = null;
        Chunk chunk3 = null;
        Chunk chunk4 = null;
        Object obj = null;
        Object obj1 = null;
        Chunk chunk7 = null;
        Chunk chunk8 = null;

        if (undulate)
        {
            chunk3 = getChunk(chunk.worldObj, chunk.xPosition, chunk.zPosition - 1);
            chunk4 = getChunk(chunk.worldObj, chunk.xPosition, chunk.zPosition + 1);
            chunk1 = getChunk(chunk.worldObj, chunk.xPosition - 1, chunk.zPosition);
            chunk2 = getChunk(chunk.worldObj, chunk.xPosition + 1, chunk.zPosition);
        }

        for (int k = 0; k < 16; k++)
        {
            int l = j + k;

            if (l < 0)
            {
                continue;
            }

            if (l >= 256)
            {
                break;
            }

            if (undulate)
            {
                chunk7 = k != 0 ? chunk : chunk3;
                chunk8 = k != 15 ? chunk : chunk4;
            }

            for (int i1 = 0; i1 < 16; i1++)
            {
                int j1 = i + i1;

                if (j1 < 0)
                {
                    continue;
                }

                if (j1 >= 256)
                {
                    break;
                }

                pixelcolor.clear();
                int k1 = !omitHeightCalc && !heightmap && !undulate ? worldHeight : Math.min(worldHeight, chunk.getHeightValue(i1, k));
                int l1 = omitHeightCalc ? Math.min(worldHeight, k1 + 1) : worldHeight;

                if (l1 < 0)
                {
                    if (transparency)
                    {
                        texture.setRGB(j1, l, 0xff00ff);
                    }
                    else
                    {
                        texture.setRGB(j1, l, 0xff000000);
                    }

                    continue;
                }

                surfaceCalc(chunk, i1, l1, k, pixelcolor, null, thread);

                if (heightmap)
                {
                    float f = undulate ? 0.15F : 0.6F;
                    double d = (double)k1 - posYd;
                    float f2 = (float)Math.log10(Math.abs(d) * 0.125D + 1.0D) * f;

                    if (d >= 0.0D)
                    {
                        pixelcolor.red += f2 * (1.0F - pixelcolor.red);
                        pixelcolor.green += f2 * (1.0F - pixelcolor.green);
                        pixelcolor.blue += f2 * (1.0F - pixelcolor.blue);
                    }
                    else
                    {
                        f2 = Math.abs(f2);
                        pixelcolor.red -= f2 * pixelcolor.red;
                        pixelcolor.green -= f2 * pixelcolor.green;
                        pixelcolor.blue -= f2 * pixelcolor.blue;
                    }
                }

                float f1 = 1.0F;

                if (undulate)
                {
                    Chunk chunk5 = i1 != 0 ? chunk : chunk1;
                    Chunk chunk6 = i1 != 15 ? chunk : chunk2;
                    int i2 = chunk5.getHeightValue(i1 - 1 & 0xf, k);
                    int j2 = chunk6.getHeightValue(i1 + 1 & 0xf, k);
                    int k2 = chunk7.getHeightValue(i1, k - 1 & 0xf);
                    int l2 = chunk8.getHeightValue(i1, k + 1 & 0xf);
                    f1 += Math.max(-4F, Math.min(3F, (float)(i2 - j2) * sin + (float)(k2 - l2) * cos)) * 0.1414214F * 0.8F;
                }

                if (flag)
                {
                    pixelcolor.red *= 1.2D;
                    pixelcolor.green *= 0.5D;
                    pixelcolor.blue *= 0.5D;
                }

                if (showChunkGrid && (i1 == 0 || k == 0))
                {
                    pixelcolor.red *= 0.7D;
                    pixelcolor.green *= 0.7D;
                    pixelcolor.blue *= 0.7D;
                }

                byte byte0 = ftob(pixelcolor.red * f1);
                byte byte1 = ftob(pixelcolor.green * f1);
                byte byte2 = ftob(pixelcolor.blue * f1);

                if (transparency)
                {
                    texture.setRGBA(j1, l, byte0, byte1, byte2, ftob(pixelcolor.alpha));
                }
                else
                {
                    texture.setRGB(j1, l, byte0, byte1, byte2);
                }
            }
        }
    }

    private void biomeCalc(Thread thread)
    {
        Chunk chunk;

        for (int i = Math.max(stripCountMax1, stripCountMax2); stripCounter.count() < i; biomeCalc(chunk, thread))
        {
            java.awt.Point point = stripCounter.next();
            chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void biomeCalcStrip(Thread thread)
    {
        int i = Math.max(stripCountMax1, stripCountMax2);
        int j = updateFrequencys[updateFrequencySetting];

        for (int k = 0; k < j && stripCounter.count() < i; k++)
        {
            java.awt.Point point = stripCounter.next();
            Chunk chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
            biomeCalc(chunk, thread);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void biomeCalc(Chunk chunk, Thread thread)
    {
        if (delayFlag)
        {
            return;
        }

        if (chunk == null)
        {
            return;
        }

        int i = (128 + chunk.xPosition * 16) - posX;
        int j = (128 + chunk.zPosition * 16) - posZ;

        for (int k = 0; k < 16; k++)
        {
            int l = k + j;

            if (l < 0)
            {
                continue;
            }

            if (l >= 256)
            {
                break;
            }

            for (int i1 = 0; i1 < 16; i1++)
            {
                int j1 = i1 + i;

                if (j1 < 0)
                {
                    continue;
                }

                if (j1 >= 256)
                {
                    break;
                }

                int k1 = Environment.getEnvironment(chunk, i1, k, thread).getBiome().color;
                byte byte0 = (byte)(k1 >> 16);
                byte byte1 = (byte)(k1 >> 8);
                byte byte2 = (byte)(k1 >> 0);
                texture.setRGB(j1, l, byte0, byte1, byte2);
            }
        }
    }

    private void temperatureCalc(Thread thread)
    {
        Chunk chunk;

        for (int i = Math.max(stripCountMax1, stripCountMax2); stripCounter.count() < i; temperatureCalc(chunk, thread))
        {
            java.awt.Point point = stripCounter.next();
            chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void temperatureCalcStrip(Thread thread)
    {
        int i = Math.max(stripCountMax1, stripCountMax2);
        int j = updateFrequencys[updateFrequencySetting];

        for (int k = 0; k < j && stripCounter.count() < i; k++)
        {
            java.awt.Point point = stripCounter.next();
            Chunk chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
            temperatureCalc(chunk, thread);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void temperatureCalc(Chunk chunk, Thread thread)
    {
        if (delayFlag)
        {
            return;
        }

        if (chunk == null || (chunk instanceof EmptyChunk))
        {
            return;
        }

        int i = (128 + chunk.xPosition * 16) - posX;
        int j = (128 + chunk.zPosition * 16) - posZ;

        for (int k = 0; k < 16; k++)
        {
            int l = k + j;

            if (l < 0)
            {
                continue;
            }

            if (l >= 256)
            {
                break;
            }

            for (int i1 = 0; i1 < 16; i1++)
            {
                int j1 = i1 + i;

                if (j1 < 0)
                {
                    continue;
                }

                if (j1 >= 256)
                {
                    break;
                }

                float f = Environment.getEnvironment(chunk, i1, k, thread).getTemperature();
                int k1 = (int)(f * 255F);
                texture.setRGB(j1, l, temperatureColor[k1]);
            }
        }
    }

    private void humidityCalc(Thread thread)
    {
        Chunk chunk;

        for (int i = Math.max(stripCountMax1, stripCountMax2); stripCounter.count() < i; humidityCalc(chunk, thread))
        {
            java.awt.Point point = stripCounter.next();
            chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void humidityCalcStrip(Thread thread)
    {
        int i = Math.max(stripCountMax1, stripCountMax2);
        int j = updateFrequencys[updateFrequencySetting];

        for (int k = 0; k < j && stripCounter.count() < i; k++)
        {
            java.awt.Point point = stripCounter.next();
            Chunk chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
            humidityCalc(chunk, thread);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void humidityCalc(Chunk chunk, Thread thread)
    {
        if (delayFlag)
        {
            return;
        }

        if (chunk == null || (chunk instanceof EmptyChunk))
        {
            return;
        }

        int i = (128 + chunk.xPosition * 16) - posX;
        int j = (128 + chunk.zPosition * 16) - posZ;

        for (int k = 0; k < 16; k++)
        {
            int l = k + j;

            if (l < 0)
            {
                continue;
            }

            if (l >= 256)
            {
                break;
            }

            for (int i1 = 0; i1 < 16; i1++)
            {
                int j1 = i1 + i;

                if (j1 < 0)
                {
                    continue;
                }

                if (j1 >= 256)
                {
                    break;
                }

                float f = Environment.getEnvironment(chunk, i1, k, thread).getHumidity();
                int k1 = (int)(f * 255F);
                texture.setRGB(j1, l, humidityColor[k1]);
            }
        }
    }

    private static final byte ftob(float f)
    {
        return (byte)Math.max(0, Math.min(255, (int)(f * 255F)));
    }

    private void surfaceCalc(Chunk chunk, int i, int j, int k, PixelColor pixelcolor, TintType tinttype, Thread thread)
    {
        int l = chunk.getBlockID(i, j, k);

        if (l == 0 || hideSnow && l == 78)
        {
            if (j > 0)
            {
                surfaceCalc(chunk, i, j - 1, k, pixelcolor, null, thread);
            }

            return;
        }

        int i1 = BlockColor.useMetadata(l) ? chunk.getBlockMetadata(i, j, k) : 0;
        BlockColor blockcolor = BlockColor.getBlockColor(l, i1);

        if (transparency)
        {
            if (blockcolor.alpha < 1.0F && j > 0)
            {
                surfaceCalc(chunk, i, j - 1, k, pixelcolor, blockcolor.tintType, thread);

                if (blockcolor.alpha == 0.0F)
                {
                    return;
                }
            }
        }
        else if (blockcolor.alpha == 0.0F && j > 0)
        {
            surfaceCalc(chunk, i, j - 1, k, pixelcolor, blockcolor.tintType, thread);
            return;
        }


        if (lightType == 0)
        {
            int j1 = 15;

            switch (lightmap)
            {
                default:
                    lightmap = 0;

                case 0:
                case 1:
                case 2:
                    j1 = j >= worldHeight ? 15 : chunk.getSavedLightValue(EnumSkyBlock.Sky, i, j + 1, k);
                    break;

                case 3:
                    j1 = 15;
                    break;
            }

            int l1 = Math.max(Block.lightValue[l], chunk.getSavedLightValue(EnumSkyBlock.Block, i, Math.min(worldHeight, j + 1), k));
            int i2 = j1 << 4 | l1;
            float f1 = lightmapRed[i2];
            float f2 = lightmapGreen[i2];
            float f3 = lightmapBlue[i2];

            if (blockcolor.tintType == TintType.WATER && tinttype == TintType.WATER)
            {
                return;
            }

            if (environmentColor)
            {
                switch (ReiEnumHelper.RTintType[blockcolor.tintType.ordinal()])
                {
                    case 1:
                        Environment environment2 = Environment.getEnvironment(chunk, i, k, thread);
                        int i4 = environment2.getGrassColor();
                        BiomeGenBase biomegenbase3 = environment2.getBiome();
                        pixelcolor.composite(blockcolor.alpha, Environment.calcGrassColor(biomegenbase3, i4), f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                        return;

                    case 2:
                        long l3 = i * 0x2fc20f + k * 0x5d8875 + j;
                        l3 = l3 * l3 * 0x285b825L + l3 * 11L;
                        int k4 = (int)((long)i + ((l3 >> 14 & 31L) - 16L));
                        int l4 = (int)((long)k + ((l3 >> 24 & 31L) - 16L));
                        int i5 = Environment.getEnvironment(chunk, k4, l4, thread).getGrassColor();
                        BiomeGenBase biomegenbase5 = Environment.getEnvironment(chunk, i, k, thread).getBiome();
                        pixelcolor.composite(blockcolor.alpha, Environment.calcGrassColor(biomegenbase5, i5), f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                        return;

                    case 3:
                        Environment environment3 = Environment.getEnvironment(chunk, i, k, thread);
                        int j4 = environment3.getFoliageColor();
                        BiomeGenBase biomegenbase4 = environment3.getBiome();
                        pixelcolor.composite(blockcolor.alpha, Environment.calcFoliageColor(biomegenbase4, j4), f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                        return;

                    case 4:
                        if ((l == 8 || l == 9) && Environment.getEnvironment(chunk, i, k, thread).getBiome() == BiomeGenBase.swampland)
                        {
                            pixelcolor.composite(blockcolor.alpha, blockcolor.red * 0.8784314F * f1, blockcolor.green * f2, blockcolor.blue * 0.4392157F * f3);
                            return;
                        }

                        break;
                }
            }
            else
            {
                switch (ReiEnumHelper.RTintType[blockcolor.tintType.ordinal()])
                {
                    case 1:
                        pixelcolor.composite(blockcolor.alpha, grassColor, f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                        return;

                    case 2:
                        pixelcolor.composite(blockcolor.alpha, grassColor, f1 * blockcolor.red * 0.9F, f2 * blockcolor.green * 0.9F, f3 * blockcolor.blue * 0.9F);
                        return;

                    case 3:
                        pixelcolor.composite(blockcolor.alpha, foliageColor, f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                        return;
                }
            }

            if (blockcolor.tintType == TintType.PINE)
            {
                pixelcolor.composite(blockcolor.alpha, foliageColorPine, f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                return;
            }

            if (blockcolor.tintType == TintType.BIRCH)
            {
                pixelcolor.composite(blockcolor.alpha, foliageColorBirch, f1 * blockcolor.red, f2 * blockcolor.green, f3 * blockcolor.blue);
                return;
            }

            if (blockcolor.tintType == TintType.GLASS && tinttype == TintType.GLASS)
            {
                return;
            }

            pixelcolor.composite(blockcolor.alpha, blockcolor.red * f1, blockcolor.green * f2, blockcolor.blue * f3);
        }
        else
        {
            int k1;

            switch (lightmap)
            {
                default:
                    lightmap = 0;

                case 0:
                    k1 = j >= worldHeight ? 15 - skylightSubtracted : chunk.getBlockLightValue(i, j + 1, k, skylightSubtracted);
                    break;

                case 1:
                    k1 = j >= worldHeight ? 15 : chunk.getBlockLightValue(i, j + 1, k, 0);
                    break;

                case 2:
                    k1 = j >= worldHeight ? 4 : chunk.getBlockLightValue(i, j + 1, k, 11);
                    break;

                case 3:
                    k1 = 15;
                    break;
            }

            float f = lightBrightnessTable[k1];

            if (blockcolor.tintType == TintType.WATER && tinttype == TintType.WATER)
            {
                return;
            }

            if (environmentColor)
            {
                switch (ReiEnumHelper.RTintType[blockcolor.tintType.ordinal()])
                {
                    case 1:
                        Environment environment = Environment.getEnvironment(chunk, i, k, thread);
                        int j2 = environment.getGrassColor();
                        BiomeGenBase biomegenbase = environment.getBiome();
                        pixelcolor.composite(blockcolor.alpha, Environment.calcGrassColor(biomegenbase, j2), f * 0.6F);
                        return;

                    case 2:
                        long l2 = i * 0x2fc20f + k * 0x5d8875 + j;
                        l2 = l2 * l2 * 0x285b825L + l2 * 11L;
                        int i3 = (int)((long)i + ((l2 >> 14 & 31L) - 16L));
                        int j3 = (int)((long)k + ((l2 >> 24 & 31L) - 16L));
                        int k3 = Environment.getEnvironment(chunk, i3, j3, thread).getGrassColor();
                        BiomeGenBase biomegenbase2 = Environment.getEnvironment(chunk, i, k, thread).getBiome();
                        pixelcolor.composite(blockcolor.alpha, Environment.calcGrassColor(biomegenbase2, k3), f * 0.5F);
                        return;

                    case 3:
                        Environment environment1 = Environment.getEnvironment(chunk, i, k, thread);
                        int k2 = environment1.getFoliageColor();
                        BiomeGenBase biomegenbase1 = environment1.getBiome();
                        pixelcolor.composite(blockcolor.alpha, Environment.calcFoliageColor(biomegenbase1, k2), f * 0.5F);
                        return;

                    case 4:
                        if ((l == 8 || l == 9) && Environment.getEnvironment(chunk, i, k, thread).getBiome() == BiomeGenBase.swampland)
                        {
                            pixelcolor.composite(blockcolor.alpha, blockcolor.red * 0.8784314F, blockcolor.green, blockcolor.blue * 0.4392157F, f);
                            return;
                        }

                        break;
                }
            }
            else
            {
                switch (ReiEnumHelper.RTintType[blockcolor.tintType.ordinal()])
                {
                    case 1:
                        pixelcolor.composite(blockcolor.alpha, grassColor, f * blockcolor.red, f * blockcolor.green, f * blockcolor.blue);
                        return;

                    case 2:
                        pixelcolor.composite(blockcolor.alpha, grassColor, f * blockcolor.red * 0.9F, f * blockcolor.green * 0.9F, f * blockcolor.blue * 0.9F);
                        return;

                    case 3:
                        pixelcolor.composite(blockcolor.alpha, foliageColor, f * blockcolor.red, f * blockcolor.green, f * blockcolor.blue);
                        return;

                    case 4:
                        if ((l == 8 || l == 9) && Environment.getEnvironment(chunk, i, k, thread).getBiome() == BiomeGenBase.swampland)
                        {
                            pixelcolor.composite(blockcolor.alpha, blockcolor.red * 0.8784314F, blockcolor.green, blockcolor.blue * 0.4392157F, f);
                            return;
                        }

                        break;
                }
            }

            if (blockcolor.tintType == TintType.PINE)
            {
                pixelcolor.composite(blockcolor.alpha, foliageColorPine, f * blockcolor.red, f * blockcolor.green, f * blockcolor.blue);
                return;
            }

            if (blockcolor.tintType == TintType.BIRCH)
            {
                pixelcolor.composite(blockcolor.alpha, foliageColorBirch, f * blockcolor.red, f * blockcolor.green, f * blockcolor.blue);
                return;
            }

            if (blockcolor.tintType == TintType.GLASS && tinttype == TintType.GLASS)
            {
                return;
            }

            pixelcolor.composite(blockcolor.alpha, blockcolor.red, blockcolor.green, blockcolor.blue, f);
        }
    }

    private void caveCalc()
    {
        Chunk chunk;

        for (int i = Math.max(stripCountMax1, stripCountMax2); stripCounter.count() < i; caveCalc(chunk))
        {
            java.awt.Point point = stripCounter.next();
            chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void caveCalcStrip()
    {
        int i = Math.max(stripCountMax1, stripCountMax2);
        int j = updateFrequencys[updateFrequencySetting];

        for (int k = 0; k < j && stripCounter.count() < i; k++)
        {
            java.awt.Point point = stripCounter.next();
            Chunk chunk = chunkCache.get(theWorld, chunkCoordX + point.x, chunkCoordZ + point.y);
            caveCalc(chunk);
        }

        isUpdateImage = stripCounter.count() >= stripCountMax1;
        isCompleteImage = isUpdateImage && stripCounter.count() >= stripCountMax2;
    }

    private void caveCalc(Chunk chunk)
    {
        if (chunk == null || (chunk instanceof EmptyChunk))
        {
            return;
        }

        int i = (128 + chunk.xPosition * 16) - posX;
        int j = (128 + chunk.zPosition * 16) - posZ;

        for (int k = 0; k < 16; k++)
        {
            int l = j + k;

            if (l < 0)
            {
                continue;
            }

            if (l >= 256)
            {
                break;
            }

            for (int i1 = 0; i1 < 16; i1++)
            {
                int j1 = i + i1;

                if (j1 < 0)
                {
                    continue;
                }

                if (j1 >= 256)
                {
                    break;
                }

                float f = 0.0F;
                label0:

                switch (currentDimension)
                {
                    case 0:
                        for (int k1 = 0; k1 < temp.length; k1++)
                        {
                            int j2 = posY - k1;

                            if (j2 > worldHeight || j2 >= 0 && chunk.getBlockID(i1, j2, k) == 0 && chunk.getBlockLightValue(i1, j2, k, 12) != 0)
                            {
                                f += temp[k1];
                            }

                            j2 = posY + k1 + 1;

                            if (j2 > worldHeight || j2 >= 0 && chunk.getBlockID(i1, j2, k) == 0 && chunk.getBlockLightValue(i1, j2, k, 12) != 0)
                            {
                                f += temp[k1];
                            }
                        }

                        break;

                    case -1:
                        int l1 = 0;

                        do
                        {
                            if (l1 >= temp.length)
                            {
                                break label0;
                            }

                            int k2 = posY - l1;

                            if (k2 >= 0 && k2 <= worldHeight && chunk.getBlockID(i1, k2, k) == 0 && chunk.getBlockLightValue(i1, k2, k, 12) != 0)
                            {
                                f += temp[l1];
                            }

                            k2 = posY + l1 + 1;

                            if (k2 >= 0 && k2 <= worldHeight && chunk.getBlockID(i1, k2, k) == 0 && chunk.getBlockLightValue(i1, k2, k, 12) != 0)
                            {
                                f += temp[l1];
                            }

                            l1++;
                        }
                        while (true);

                    case 1:
                    case 2:
                    case 3:
                    default:
                        for (int i2 = 0; i2 < temp.length; i2++)
                        {
                            int l2 = posY - i2;

                            if (l2 < 0 || l2 > worldHeight || chunk.getBlockID(i1, l2, k) == 0 && chunk.getBlockLightValue(i1, l2, k, 12) != 0)
                            {
                                f += temp[i2];
                            }

                            l2 = posY + i2 + 1;

                            if (l2 < 0 || l2 > worldHeight || chunk.getBlockID(i1, l2, k) == 0 && chunk.getBlockLightValue(i1, l2, k, 12) != 0)
                            {
                                f += temp[i2];
                            }
                        }

                        break;
                }

                f = 0.8F - f;
                texture.setRGB(j1, l, ftob(0.0F), ftob(f), ftob(0.0F));
            }
        }
    }

    private void renderRoundMap()
    {
        int i = 1;

        if (mapScale == 0)
        {
            i = scaledResolution.scaleFactor;
        }
        else if (mapScale == 1)
        {
            for (; scWidth >= (i + 1) * 320 && scHeight >= (i + 1) * 240; i++) { }
        }
        else
        {
            i = mapScale - 1;
        }

        int j = fontScale - 1;

        if (fontScale == 0)
        {
            j = scaledResolution.scaleFactor + 1 >> 1;
        }
        else if (fontScale == 1)
        {
            j = i + 1 >> 1;
        }

        int k = (mapPosition & 2) != 0 ? scWidth - 37 * i : 37 * i;
        int l = (mapPosition & 1) != 0 ? scHeight - 37 * i : 37 * i;

        if ((mapPosition & 1) == 1)
        {
            l -= ((showMenuKey | showCoordinate ? 2 : 0) + (showMenuKey ? 9 : 0) + (showCoordinate ? 18 : 0)) * j;
        }

        GL11.glTranslated(k, l, 0.0D);
        GL11.glScalef(i, i, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColorMask(false, false, false, false);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        if (useStencil)
        {
            GL11.glAlphaFunc(GL11.GL_LEQUAL, 0.1F);
            GL11.glClearStencil(0);
            GL11.glClear(1024);
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            GL11.glStencilFunc(GL11.GL_ALWAYS, 1, -1);
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
            GL11.glDepthMask(false);
        }
        else
        {
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
            GL11.glDepthMask(true);
        }

        GL11.glPushMatrix();
        GL11.glRotatef(90F - thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
        GLTexture.ROUND_MAP_MASK.bind();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        drawCenteringRectangle(0.0D, 0.0D, 1.01D, 64D, 64D);

        if (useStencil)
        {
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
            GL11.glStencilFunc(GL11.GL_EQUAL, 1, -1);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, true);
        double d = 0.25D / currentZoom;
        double d1 = (thePlayer.posX - (double)lastX) * 0.00390625D;
        double d2 = (thePlayer.posZ - (double)lastZ) * 0.00390625D;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, mapOpacity);
        texture.bind();
        startDrawingQuads();
        addVertexWithUV(-32D, 32D, 1.0D, 0.5D + d + d1, 0.5D + d + d2);
        addVertexWithUV(32D, 32D, 1.0D, 0.5D + d + d1, (0.5D - d) + d2);
        addVertexWithUV(32D, -32D, 1.0D, (0.5D - d) + d1, (0.5D - d) + d2);
        addVertexWithUV(-32D, -32D, 1.0D, (0.5D - d) + d1, 0.5D + d + d2);
        draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();

        if (visibleEntitiesRadar)
        {
            double d3 = useStencil ? 34D : 29D;
            ArrayList arraylist = new ArrayList();
            arraylist.addAll(theWorld.loadedEntityList);
            Iterator iterator1 = arraylist.iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    break;
                }

                Entity entity = (Entity)iterator1.next();

                if (entity != null)
                {
                    int i1 = getEntityColor(entity);

                    if (i1 != 0)
                    {
                        double d9 = thePlayer.posX - entity.posX;
                        double d12 = thePlayer.posZ - entity.posZ;
                        float f7 = (float)Math.toDegrees(Math.atan2(d9, d12));
                        double d15 = Math.sqrt(d9 * d9 + d12 * d12) * currentZoom * 0.5D;

                        try
                        {
                            GL11.glPushMatrix();

                            if (d15 < d3)
                            {
                                float f11 = (float)(i1 >> 16 & 0xff) * 0.003921569F;
                                float f12 = (float)(i1 >> 8 & 0xff) * 0.003921569F;
                                float f13 = (float)(i1 & 0xff) * 0.003921569F;
                                float f14 = (float)Math.max(0.2D, 1.0D - Math.abs(thePlayer.posY - entity.posY) * 0.04D);
                                float f15 = (float)Math.min(1.0D, Math.max(0.5D, 1.0D - (thePlayer.boundingBox.minY - entity.boundingBox.minY) * 0.1D));
                                f11 *= f15;
                                f12 *= f15;
                                f13 *= f15;
                                if(entity instanceof EntityPlayer)
                                {
                                	GL11.glColor4f(f11, f12, f13, 1.0F);
                                }
                                else
                                {
                                	GL11.glColor4f(f11, f12, f13, f14);
                                }
                                
                                GL11.glRotatef((-f7 - thePlayer.rotationYaw) + 180F, 0.0F, 0.0F, 1.0F);
                                GL11.glTranslated(0.0D, -d15, 0.0D);
                                GL11.glRotatef(-((-f7 - thePlayer.rotationYaw) + 180F), 0.0F, 0.0F, 1.0F);
                                
                                if (configEntityDirection)
                                {
                                    GL11.glRotatef(entity.rotationYaw - thePlayer.rotationYaw, 0.0F, 0.0F, 1.0F);
                                    GLTexture.ENTITY2.bind();
                                    drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                                }
                                else
                                {
                                    GLTexture.ENTITY.bind();
                                    drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                                }
                            }
                        }
                        finally
                        {
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
            while (true);

            if (configEntityLightning)
            {
                Iterator iterator2 = theWorld.weatherEffects.iterator();

                do
                {
                    if (!iterator2.hasNext())
                    {
                        break;
                    }

                    Entity entity1 = (Entity)iterator2.next();

                    if (entity1 instanceof EntityLightningBolt)
                    {
                        double d8 = thePlayer.posX - entity1.posX;
                        double d11 = thePlayer.posZ - entity1.posZ;
                        float f4 = (float)Math.toDegrees(Math.atan2(d8, d11));
                        double d14 = Math.sqrt(d8 * d8 + d11 * d11) * currentZoom * 0.5D;

                        try
                        {
                            GL11.glPushMatrix();

                            if (d14 < d3)
                            {
                                float f10 = (float)Math.max(0.2D, 1.0D - Math.abs(thePlayer.posY - entity1.posY) * 0.04D);
                                GL11.glColor4f(1.0F, 1.0F, 1.0F, f10);
                                GL11.glRotatef((-f4 - thePlayer.rotationYaw) + 180F, 0.0F, 0.0F, 1.0F);
                                GL11.glTranslated(0.0D, -d14, 0.0D);
                                GL11.glRotatef(-((-f4 - thePlayer.rotationYaw) + 180F), 0.0F, 0.0F, 1.0F);
                                GLTexture.LIGHTNING.bind();
                                drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                            }
                        }
                        finally
                        {
                            GL11.glPopMatrix();
                        }
                    }
                }
                while (true);
            }
        }

        if (useStencil)
        {
            GL11.glDisable(GL11.GL_STENCIL_TEST);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, mapOpacity);
        GLTexture.ROUND_MAP.bind();
        drawCenteringRectangle(0.0D, 0.0D, 1.0D, 64D, 64D);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (visibleWaypoints)
        {
            double d4 = getVisibleDimensionScale();
            Iterator iterator = wayPts.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                Waypoint waypoint = (Waypoint)iterator.next();

                if (waypoint.enable)
                {
                    double d7 = thePlayer.posX - (double)waypoint.x * d4 - 0.5D;
                    double d10 = thePlayer.posZ - (double)waypoint.z * d4 - 0.5D;
                    float f1 = (float)Math.toDegrees(Math.atan2(d7, d10));
                    double d13 = Math.sqrt(d7 * d7 + d10 * d10) * currentZoom * 0.5D;

                    try
                    {
                        GL11.glPushMatrix();

                        if (d13 < 31D)
                        {
                            GL11.glColor4f(waypoint.red, waypoint.green, waypoint.blue, (float)Math.min(1.0D, Math.max(0.4D, (d13 - 1.0D) * 0.5D)));
                            Waypoint.FILE[waypoint.type].bind();
                            GL11.glRotatef((-f1 - thePlayer.rotationYaw) + 180F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslated(0.0D, -d13, 0.0D);
                            GL11.glRotatef(-((-f1 - thePlayer.rotationYaw) + 180F), 0.0F, 0.0F, 1.0F);
                            drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                        }
                        else
                        {
                            GL11.glColor3f(waypoint.red, waypoint.green, waypoint.blue);
                            Waypoint.MARKER[waypoint.type].bind();
                            GL11.glRotatef((-f1 - thePlayer.rotationYaw) + 180F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslated(0.0D, -34D, 0.0D);
                            drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                        }
                    }
                    finally
                    {
                        GL11.glPopMatrix();
                    }
                }
            }
            while (true);
        }

        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        double d5 = Math.sin(Math.toRadians(thePlayer.rotationYaw)) * 28D;
        double d6 = Math.cos(Math.toRadians(thePlayer.rotationYaw)) * 28D;

        if (notchDirection)
        {
            GLTexture.W.bind();
            drawCenteringRectangle(d6, -d5, 1.0D, 8D, 8D);
            GLTexture.S.bind();
            drawCenteringRectangle(-d5, -d6, 1.0D, 8D, 8D);
            GLTexture.E.bind();
            drawCenteringRectangle(-d6, d5, 1.0D, 8D, 8D);
            GLTexture.N.bind();
            drawCenteringRectangle(d5, d6, 1.0D, 8D, 8D);
        }
        else
        {
            GLTexture.N.bind();
            drawCenteringRectangle(d6, -d5, 1.0D, 8D, 8D);
            GLTexture.W.bind();
            drawCenteringRectangle(-d5, -d6, 1.0D, 8D, 8D);
            GLTexture.S.bind();
            drawCenteringRectangle(-d6, d5, 1.0D, 8D, 8D);
            GLTexture.E.bind();
            drawCenteringRectangle(d5, d6, 1.0D, 8D, 8D);
        }

        GL11.glScaled(1.0D / (double)i, 1.0D / (double)i, 1.0D);
        FontRenderer fontrenderer = theMinecraft.fontRenderer;
        int j1 = (int)(zoomVisible * 255F);

        if (j1 > 0)
        {
            String s = String.format("%2.2fx", new Object[]
                    {
                        Double.valueOf(currentZoom)
                    });
            int l1 = fontrenderer.getStringWidth(s);

            if (j1 > 255)
            {
                j1 = 255;
            }

            int i2 = 30 * i - l1 * j;
            int j2 = 30 * i - 8 * j;
            GL11.glTranslatef(i2, j2, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            int l2 = j1 << 24 | 0xffffff;
            fontrenderer.drawStringWithShadow(s, 0, 0, l2);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(-i2, -j2, 0.0F);
        }

        if (visibleWaypoints && currentDimension != waypointDimension)
        {
            GL11.glPushMatrix();
            String s1 = getDimensionName(waypointDimension);
            float f = (float)fontrenderer.getStringWidth(s1) * 0.5F * (float)j;
            float f2 = (float)(37 * i) >= f ? 0.0F : (float)(37 * i) - f;

            if ((mapPosition & 2) == 0)
            {
                f2 = -f2;
            }

            GL11.glTranslated(f2 - f, -30 * i, 0.0D);
            GL11.glScaled(j, j, 1.0D);
            fontrenderer.drawStringWithShadow(s1, 0, 0, 0xffffff);
            GL11.glPopMatrix();
        }

        int k1 = 32 * i;

        if (showCoordinate)
        {
            String s2;
            String s4;

            if (coordinateType == 0)
            {
                int k2 = MathHelper.floor_double(thePlayer.posX);
                int i3 = MathHelper.floor_double(thePlayer.boundingBox.minY);
                int j3 = MathHelper.floor_double(thePlayer.posZ);
                s2 = String.format("%+d, %+d", new Object[]
                        {
                            Integer.valueOf(k2), Integer.valueOf(j3)
                        });
                s4 = Integer.toString(i3);
            }
            else
            {
                s2 = String.format("%+1.2f, %+1.2f", new Object[]
                        {
                            Double.valueOf(thePlayer.posX), Double.valueOf(thePlayer.posZ)
                        });
                s4 = String.format("%1.2f (%d)", new Object[]
                        {
                            Double.valueOf(thePlayer.posY), Integer.valueOf((int)thePlayer.boundingBox.minY)
                        });
            }

            float f5 = (float)fontrenderer.getStringWidth(s2) * 0.5F * (float)j;
            float f8 = (float)fontrenderer.getStringWidth(s4) * 0.5F * (float)j;
            float f9 = (float)(37 * i) >= f5 ? 0.0F : (float)(37 * i) - f5;

            if ((mapPosition & 2) == 0)
            {
                f9 = -f9;
            }

            GL11.glTranslatef(f9 - f5, k1, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            fontrenderer.drawStringWithShadow(s2, 0, 2, 0xffffff);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(f5 - f8, 0.0F, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            fontrenderer.drawStringWithShadow(s4, 0, 11, 0xffffff);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(f8 - f9, -k1, 0.0F);
            k1 += 18 * j;
        }

        if (showMenuKey)
        {
            String s3 = String.format("Menu: %s key", new Object[]
                    {
                        KeyInput.MENU_KEY.getKeyName()
                    });
            float f3 = (float)theMinecraft.fontRenderer.getStringWidth(s3) * 0.5F * (float)j;
            float f6 = (float)(32 * i) - f3;

            if ((mapPosition & 2) == 0 && (float)(32 * i) < f3)
            {
                f6 = (float)(-32 * i) + f3;
            }

            GL11.glTranslatef(f6 - f3, k1, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            fontrenderer.drawStringWithShadow(s3, 0, 2, 0xffffff);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(f3 - f6, -k1, 0.0F);
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void renderSquareMap()
    {
        int i = 1;

        if (mapScale == 0)
        {
            i = scaledResolution.scaleFactor;
        }
        else if (mapScale == 1)
        {
            for (; scWidth >= (i + 1) * 320 && scHeight >= (i + 1) * 240; i++) { }
        }
        else
        {
            i = mapScale - 1;
        }

        int j = fontScale - 1;

        if (fontScale == 0)
        {
            j = scaledResolution.scaleFactor + 1 >> 1;
        }
        else if (fontScale == 1)
        {
            j = i + 1 >> 1;
        }

        int k = (mapPosition & 2) != 0 ? scWidth - 37 * i : 37 * i;
        int l = (mapPosition & 1) != 0 ? scHeight - 37 * i : 37 * i;

        if ((mapPosition & 1) == 1)
        {
            l -= ((showMenuKey | showCoordinate ? 2 : 0) + (showMenuKey ? 9 : 0) + (showCoordinate ? 18 : 0)) * j;
        }

        GL11.glTranslated(k, l, 0.0D);
        GL11.glScalef(i, i, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColorMask(false, false, false, false);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        if (useStencil)
        {
            GL11.glAlphaFunc(GL11.GL_LEQUAL, 0.1F);
            GL11.glClearStencil(0);
            GL11.glClear(1024);
            GL11.glEnable(GL11.GL_STENCIL_TEST);
            GL11.glStencilFunc(GL11.GL_ALWAYS, 1, -1);
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_REPLACE, GL11.GL_REPLACE);
            GL11.glDepthMask(false);
        }
        else
        {
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
            GL11.glDepthMask(true);
        }

        GLTexture.SQUARE_MAP_MASK.bind();
        drawCenteringRectangle(0.0D, 0.0D, 1.001D, 64D, 64D);

        if (useStencil)
        {
            GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
            GL11.glStencilFunc(GL11.GL_EQUAL, 1, -1);
        }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        double d = 0.25D / currentZoom;
        double d1 = (thePlayer.posX - (double)lastX) * 0.00390625D;
        double d2 = (thePlayer.posZ - (double)lastZ) * 0.00390625D;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, mapOpacity);
        texture.bind();
        startDrawingQuads();

        if (notchDirection)
        {
            addVertexWithUV(32D, 32D, 1.0D, 0.5D + d + d1, 0.5D + d + d2);
            addVertexWithUV(32D, -32D, 1.0D, 0.5D + d + d1, (0.5D - d) + d2);
            addVertexWithUV(-32D, -32D, 1.0D, (0.5D - d) + d1, (0.5D - d) + d2);
            addVertexWithUV(-32D, 32D, 1.0D, (0.5D - d) + d1, 0.5D + d + d2);
        }
        else
        {
            addVertexWithUV(-32D, 32D, 1.0D, 0.5D + d + d1, 0.5D + d + d2);
            addVertexWithUV(32D, 32D, 1.0D, 0.5D + d + d1, (0.5D - d) + d2);
            addVertexWithUV(32D, -32D, 1.0D, (0.5D - d) + d1, (0.5D - d) + d2);
            addVertexWithUV(-32D, -32D, 1.0D, (0.5D - d) + d1, 0.5D + d + d2);
        }

        draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (visibleEntitiesRadar)
        {
            float f = useStencil ? 34F : 31F;
            ArrayList arraylist = new ArrayList();
            arraylist.addAll(theWorld.loadedEntityList);
            Iterator iterator = arraylist.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                Entity entity = (Entity)iterator.next();

                if (entity != null)
                {
                    int l1 = getEntityColor(entity);

                    if (l1 != 0)
                    {
                        double d6 = thePlayer.posX - entity.posX;
                        double d9 = thePlayer.posZ - entity.posZ;
                        d6 = d6 * currentZoom * 0.5D;
                        d9 = d9 * currentZoom * 0.5D;
                        double d11 = Math.max(Math.abs(d6), Math.abs(d9));

                        try
                        {
                            GL11.glPushMatrix();

                            if (d11 < (double)f)
                            {
                                float f10 = (float)(l1 >> 16 & 0xff) * 0.003921569F;
                                float f11 = (float)(l1 >> 8 & 0xff) * 0.003921569F;
                                float f12 = (float)(l1 & 0xff) * 0.003921569F;
                                float f13 = (float)Math.max(0.2D, 1.0D - Math.abs(thePlayer.posY - entity.posY) * 0.04D);
                                float f14 = (float)Math.min(1.0D, Math.max(0.5D, 1.0D - (thePlayer.boundingBox.minY - entity.boundingBox.minY) * 0.1D));
                                f10 *= f14;
                                f11 *= f14;
                                f12 *= f14;
                                
                                if(entity instanceof EntityPlayer)
                                {
                                	GL11.glColor4f(f10, f11, f12, 1.0F);
                                }
                                else
                                {
                                	GL11.glColor4f(f10, f11, f12, f13);
                                }
                                
                                
                                
                                double d17;
                                double d18;
                                float f17;

                                if (notchDirection)
                                {
                                    d17 = -d6;
                                    d18 = -d9;
                                    f17 = entity.rotationYaw + 180F;
                                }
                                else
                                {
                                    d17 = d9;
                                    d18 = -d6;
                                    f17 = entity.rotationYaw - 90F;
                                }

                                if (configEntityDirection)
                                {
                                    GL11.glTranslated(d17, d18, 0.0D);
                                    GL11.glRotatef(f17, 0.0F, 0.0F, 1.0F);
                                    GL11.glTranslated(-d17, -d18, 0.0D);
                                    GLTexture.ENTITY2.bind();
                                    drawCenteringRectangle(d17, d18, 1.0D, 8D, 8D);
                                }
                                else
                                {
                                    GLTexture.ENTITY.bind();
                                    drawCenteringRectangle(d17, d18, 1.0D, 8D, 8D);
                                }
                            }
                        }
                        finally
                        {
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
            while (true);

            if (configEntityLightning)
            {
                Iterator iterator1 = theWorld.weatherEffects.iterator();

                do
                {
                    if (!iterator1.hasNext())
                    {
                        break;
                    }

                    Entity entity1 = (Entity)iterator1.next();

                    if (entity1 instanceof EntityLightningBolt)
                    {
                        double d4 = thePlayer.posX - entity1.posX;
                        double d7 = thePlayer.posZ - entity1.posZ;
                        d4 = d4 * currentZoom * 0.5D;
                        d7 = d7 * currentZoom * 0.5D;
                        double d10 = Math.max(Math.abs(d4), Math.abs(d7));

                        try
                        {
                            GL11.glPushMatrix();

                            if (d10 < (double)f)
                            {
                                float f9 = (float)Math.max(0.2D, 1.0D - Math.abs(thePlayer.posY - entity1.posY) * 0.04D);
                                GL11.glColor4f(1.0F, 1.0F, 1.0F, f9);
                                double d13;
                                double d15;

                                if (notchDirection)
                                {
                                    d13 = -d4;
                                    d15 = -d7;
                                    float f15 = entity1.rotationYaw + 180F;
                                }
                                else
                                {
                                    d13 = d7;
                                    d15 = -d4;
                                    float f16 = entity1.rotationYaw - 90F;
                                }

                                GLTexture.LIGHTNING.bind();
                                drawCenteringRectangle(d13, d15, 1.0D, 8D, 8D);
                            }
                        }
                        finally
                        {
                            GL11.glPopMatrix();
                        }
                    }
                }
                while (true);
            }
        }

        if (useStencil)
        {
            GL11.glDisable(GL11.GL_STENCIL_TEST);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, mapOpacity);
        GLTexture.SQUARE_MAP.bind();
        drawCenteringRectangle(0.0D, 0.0D, 1.0D, 64D, 64D);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (visibleWaypoints)
        {
            double d3 = getVisibleDimensionScale();
            Iterator iterator2 = wayPts.iterator();

            do
            {
                if (!iterator2.hasNext())
                {
                    break;
                }

                Waypoint waypoint = (Waypoint)iterator2.next();

                if (waypoint.enable)
                {
                    double d5 = thePlayer.posX - (double)waypoint.x * d3 - 0.5D;
                    double d8 = thePlayer.posZ - (double)waypoint.z * d3 - 0.5D;
                    d5 = d5 * currentZoom * 0.5D;
                    d8 = d8 * currentZoom * 0.5D;
                    float f8 = (float)Math.toDegrees(Math.atan2(d5, d8));
                    double d12 = Math.max(Math.abs(d5), Math.abs(d8));

                    try
                    {
                        GL11.glPushMatrix();

                        if (d12 < 31D)
                        {
                            GL11.glColor4f(waypoint.red, waypoint.green, waypoint.blue, (float)Math.min(1.0D, Math.max(0.4D, (d12 - 1.0D) * 0.5D)));
                            Waypoint.FILE[waypoint.type].bind();

                            if (notchDirection)
                            {
                                drawCenteringRectangle(-d5, -d8, 1.0D, 8D, 8D);
                            }
                            else
                            {
                                drawCenteringRectangle(d8, -d5, 1.0D, 8D, 8D);
                            }
                        }
                        else
                        {
                            double d14 = 34D / d12;
                            d5 *= d14;
                            d8 *= d14;
                            double d16 = Math.sqrt(d5 * d5 + d8 * d8);
                            GL11.glColor3f(waypoint.red, waypoint.green, waypoint.blue);
                            Waypoint.MARKER[waypoint.type].bind();
                            GL11.glRotatef((notchDirection ? 0.0F : 90F) - f8, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslated(0.0D, -d16, 0.0D);
                            drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                        }
                    }
                    finally
                    {
                        GL11.glPopMatrix();
                    }
                }
            }
            while (true);
        }

        try
        {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GLTexture.MMARROW.bind();
            GL11.glRotatef(thePlayer.rotationYaw - (notchDirection ? 180F : 90F), 0.0F, 0.0F, 1.0F);
            drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
        }
        catch (Exception exception) { }
        finally
        {
            GL11.glPopMatrix();
        }

        GL11.glScaled(1.0D / (double)i, 1.0D / (double)i, 1.0D);
        FontRenderer fontrenderer = theMinecraft.fontRenderer;
        int i1 = (int)(zoomVisible * 255F);

        if (i1 > 0)
        {
            String s = String.format("%2.2fx", new Object[]
                    {
                        Double.valueOf(currentZoom)
                    });
            int k1 = fontrenderer.getStringWidth(s);

            if (i1 > 255)
            {
                i1 = 255;
            }

            int i2 = 30 * i - k1 * j;
            int j2 = 30 * i - 8 * j;
            GL11.glTranslatef(i2, j2, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            int l2 = i1 << 24 | 0xffffff;
            fontrenderer.drawStringWithShadow(s, 0, 0, l2);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(-i2, -j2, 0.0F);
        }

        if (visibleWaypoints && currentDimension != waypointDimension)
        {
            GL11.glPushMatrix();
            String s1 = getDimensionName(waypointDimension);
            float f1 = (float)fontrenderer.getStringWidth(s1) * 0.5F * (float)j;
            float f2 = (float)(37 * i) >= f1 ? 0.0F : (float)(37 * i) - f1;

            if ((mapPosition & 2) == 0)
            {
                f2 = -f2;
            }

            GL11.glTranslated(f2 - f1, -30 * i, 0.0D);
            GL11.glScaled(j, j, 1.0D);
            fontrenderer.drawStringWithShadow(s1, 0, 0, 0xffffff);
            GL11.glPopMatrix();
        }

        int j1 = 32 * i;

        if (showCoordinate)
        {
            String s2;
            String s4;

            if (coordinateType == 0)
            {
                int k2 = MathHelper.floor_double(thePlayer.posX);
                int i3 = MathHelper.floor_double(thePlayer.boundingBox.minY);
                int j3 = MathHelper.floor_double(thePlayer.posZ);
                s2 = String.format("%+d, %+d", new Object[]
                        {
                            Integer.valueOf(k2), Integer.valueOf(j3)
                        });
                s4 = Integer.toString(i3);
            }
            else
            {
                s2 = String.format("%+1.2f, %+1.2f", new Object[]
                        {
                            Double.valueOf(thePlayer.posX), Double.valueOf(thePlayer.posZ)
                        });
                s4 = String.format("%1.2f (%d)", new Object[]
                        {
                            Double.valueOf(thePlayer.posY), Integer.valueOf((int)thePlayer.boundingBox.minY)
                        });
            }

            float f4 = (float)fontrenderer.getStringWidth(s2) * 0.5F * (float)j;
            float f6 = (float)fontrenderer.getStringWidth(s4) * 0.5F * (float)j;
            float f7 = (float)(37 * i) >= f4 ? 0.0F : (float)(37 * i) - f4;

            if ((mapPosition & 2) == 0)
            {
                f7 = -f7;
            }

            GL11.glTranslatef(f7 - f4, j1, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            fontrenderer.drawStringWithShadow(s2, 0, 2, 0xffffff);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(f4 - f6, 0.0F, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            fontrenderer.drawStringWithShadow(s4, 0, 11, 0xffffff);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(f6 - f7, -j1, 0.0F);
            j1 += 18 * j;
        }

        if (showMenuKey)
        {
            String s3 = String.format("Menu: %s key", new Object[]
                    {
                        KeyInput.MENU_KEY.getKeyName()
                    });
            float f3 = (float)theMinecraft.fontRenderer.getStringWidth(s3) * 0.5F * (float)j;
            float f5 = (float)(32 * i) - f3;

            if ((mapPosition & 2) == 0 && (float)(32 * i) < f3)
            {
                f5 = (float)(-32 * i) + f3;
            }

            GL11.glTranslatef(f5 - f3, j1, 0.0F);
            GL11.glScalef(j, j, 1.0F);
            fontrenderer.drawStringWithShadow(s3, 0, 2, 0xffffff);
            GL11.glScaled(1.0D / (double)j, 1.0D / (double)j, 1.0D);
            GL11.glTranslatef(f3 - f5, -j1, 0.0F);
        }

        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void renderFullMap()
    {
        int i = 1;

        if (largeMapScale == 0)
        {
            i = scaledResolution.scaleFactor;
        }
        else
        {
            for (int j = largeMapScale != 1 ? largeMapScale - 1 : 1000; i < j && scWidth >= (i + 1) * 240 && scHeight >= (i + 1) * 240; i++) { }
        }

        int k = fontScale - 1;

        if (fontScale == 0)
        {
            k = scaledResolution.scaleFactor + 1 >> 1;
        }
        else if (fontScale == 1)
        {
            k = i + 1 >> 1;
        }

        GL11.glTranslated((double)scWidth * 0.5D, (double)scHeight * 0.5D, 0.0D);
        GL11.glScalef(i, i, 0.0F);
        double d = 0.234375D / currentZoom;
        double d1 = (thePlayer.posX - (double)lastX) * 0.00390625D;
        double d2 = (thePlayer.posZ - (double)lastZ) * 0.00390625D;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        texture.bind();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, largeMapOpacity);
        startDrawingQuads();

        if (notchDirection)
        {
            addVertexWithUV(120D, 120D, 1.0D, 0.5D + d + d1, 0.5D + d + d2);
            addVertexWithUV(120D, -120D, 1.0D, 0.5D + d + d1, (0.5D - d) + d2);
            addVertexWithUV(-120D, -120D, 1.0D, (0.5D - d) + d1, (0.5D - d) + d2);
            addVertexWithUV(-120D, 120D, 1.0D, (0.5D - d) + d1, 0.5D + d + d2);
        }
        else
        {
            addVertexWithUV(-120D, 120D, 1.0D, 0.5D + d + d1, 0.5D + d + d2);
            addVertexWithUV(120D, 120D, 1.0D, 0.5D + d + d1, (0.5D - d) + d2);
            addVertexWithUV(120D, -120D, 1.0D, (0.5D - d) + d1, (0.5D - d) + d2);
            addVertexWithUV(-120D, -120D, 1.0D, (0.5D - d) + d1, 0.5D + d + d2);
        }

        draw();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (visibleEntitiesRadar)
        {
            ArrayList arraylist = new ArrayList();
            arraylist.addAll(theWorld.loadedEntityList);
            Iterator iterator1 = arraylist.iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    break;
                }

                Entity entity = (Entity)iterator1.next();

                if (entity != null)
                {
                    int k1 = getEntityColor(entity);

                    if (k1 != 0)
                    {
                        double d5 = thePlayer.posX - entity.posX;
                        double d8 = thePlayer.posZ - entity.posZ;
                        d5 = d5 * currentZoom * 2D;
                        d8 = d8 * currentZoom * 2D;
                        double d11 = Math.max(Math.abs(d5), Math.abs(d8));

                        try
                        {
                            GL11.glPushMatrix();

                            if (d11 < 114D)
                            {
                                float f5 = (float)(k1 >> 16 & 0xff) * 0.003921569F;
                                float f7 = (float)(k1 >> 8 & 0xff) * 0.003921569F;
                                float f8 = (float)(k1 & 0xff) * 0.003921569F;
                                float f9 = (float)Math.max(0.2D, 1.0D - Math.abs(thePlayer.posY - entity.posY) * 0.04D);
                                float f10 = (float)Math.min(1.0D, Math.max(0.5D, 1.0D - (thePlayer.boundingBox.minY - entity.boundingBox.minY) * 0.1D));
                                f5 *= f10;
                                f7 *= f10;
                                f8 *= f10;
                                
                                if(entity instanceof EntityPlayer)
                                {
                                	GL11.glColor4f(f5, f7, f8, 1.0F);
                                }
                                else
                                {
                                	GL11.glColor4f(f5, f7, f8, f9);
                                }
                                
                                double d19;
                                double d20;
                                float f13;

                                if (notchDirection)
                                {
                                    d19 = -d5;
                                    d20 = -d8;
                                    f13 = entity.rotationYaw + 180F;
                                }
                                else
                                {
                                    d19 = d8;
                                    d20 = -d5;
                                    f13 = entity.rotationYaw - 90F;
                                }

                                if (configEntityDirection)
                                {
                                    GL11.glTranslated(d19, d20, 0.0D);
                                    GL11.glRotatef(f13, 0.0F, 0.0F, 1.0F);
                                    GL11.glTranslated(-d19, -d20, 0.0D);
                                    GLTexture.ENTITY2.bind();
                                    drawCenteringRectangle(d19, d20, 1.0D, 8D, 8D);
                                }
                                else
                                {
                                    GLTexture.ENTITY.bind();
                                    drawCenteringRectangle(d19, d20, 1.0D, 8D, 8D);
                                }
                            }
                        }
                        finally
                        {
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
            while (true);

            if (configEntityLightning)
            {
                Iterator iterator2 = theWorld.weatherEffects.iterator();

                do
                {
                    if (!iterator2.hasNext())
                    {
                        break;
                    }

                    Entity entity1 = (Entity)iterator2.next();

                    if (entity1 instanceof EntityLightningBolt)
                    {
                        double d4 = thePlayer.posX - entity1.posX;
                        double d7 = thePlayer.posZ - entity1.posZ;
                        d4 = d4 * currentZoom * 2D;
                        d7 = d7 * currentZoom * 2D;
                        double d10 = Math.max(Math.abs(d4), Math.abs(d7));

                        try
                        {
                            GL11.glPushMatrix();

                            if (d10 < 114D)
                            {
                                float f3 = (float)Math.max(0.2D, 1.0D - Math.abs(thePlayer.posY - entity1.posY) * 0.04D);
                                GL11.glColor4f(1.0F, 1.0F, 1.0F, f3);
                                double d13;
                                double d16;

                                if (notchDirection)
                                {
                                    d13 = -d4;
                                    d16 = -d7;
                                    float f11 = entity1.rotationYaw + 180F;
                                }
                                else
                                {
                                    d13 = d7;
                                    d16 = -d4;
                                    float f12 = entity1.rotationYaw - 90F;
                                }

                                GLTexture.LIGHTNING.bind();
                                drawCenteringRectangle(d13, d16, 1.0D, 8D, 8D);
                            }
                        }
                        finally
                        {
                            GL11.glPopMatrix();
                        }
                    }
                }
                while (true);
            }
        }

        try
        {
            GL11.glColor3f(1.0F, 1.0F, 1.0F);
            GL11.glPushMatrix();
            GLTexture.MMARROW.bind();
            GL11.glRotatef(thePlayer.rotationYaw - (notchDirection ? 180F : 90F), 0.0F, 0.0F, 1.0F);
            drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
        }
        catch (Exception exception) { }
        finally
        {
            GL11.glPopMatrix();
        }

        if (visibleWaypoints)
        {
            Iterator iterator = wayPts.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                Waypoint waypoint = (Waypoint)iterator.next();
                double d3 = getVisibleDimensionScale();

                if (waypoint.enable)
                {
                    double d6 = thePlayer.posX - (double)waypoint.x * d3 - 0.5D;
                    double d9 = thePlayer.posZ - (double)waypoint.z * d3 - 0.5D;
                    d6 = d6 * currentZoom * 2D;
                    d9 = d9 * currentZoom * 2D;
                    float f1 = (float)Math.toDegrees(Math.atan2(d6, d9));
                    double d12 = Math.max(Math.abs(d6), Math.abs(d9));

                    try
                    {
                        GL11.glPushMatrix();

                        if (d12 < 114D)
                        {
                            GL11.glColor4f(waypoint.red, waypoint.green, waypoint.blue, (float)Math.min(1.0D, Math.max(0.4D, (d12 - 1.0D) * 0.5D)));
                            Waypoint.FILE[waypoint.type].bind();
                            double d14;
                            double d17;

                            if (notchDirection)
                            {
                                d14 = -d6;
                                d17 = -d9;
                            }
                            else
                            {
                                d14 = d9;
                                d17 = -d6;
                            }

                            drawCenteringRectangle(d14, d17, 1.0D, 8D, 8D);

                            if (largeMapLabel && waypoint.name != null && !waypoint.name.isEmpty())
                            {
                                GL11.glDisable(GL11.GL_TEXTURE_2D);
                                GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.627451F);
                                int k3 = theMinecraft.fontRenderer.getStringWidth(waypoint.name);
                                int l3 = (int)d14;
                                int i4 = (int)d17;
                                int j4 = l3 - (k3 >> 1);
                                int k4 = j4 + k3;
                                int l4 = i4 - 15;
                                int i5 = i4 - 5;
                                tessellator.startDrawingQuads();
                                tessellator.addVertex(j4 - 1, i5, 1.0D);
                                tessellator.addVertex(k4 + 1, i5, 1.0D);
                                tessellator.addVertex(k4 + 1, l4, 1.0D);
                                tessellator.addVertex(j4 - 1, l4, 1.0D);
                                tessellator.draw();
                                GL11.glEnable(GL11.GL_TEXTURE_2D);
                                theMinecraft.fontRenderer.drawStringWithShadow(waypoint.name, j4, l4 + 1, waypoint.type != 0 ? 0xffff0000 : -1);
                            }
                        }
                        else
                        {
                            double d15 = 117D / d12;
                            d6 *= d15;
                            d9 *= d15;
                            double d18 = Math.sqrt(d6 * d6 + d9 * d9);
                            GL11.glColor3f(waypoint.red, waypoint.green, waypoint.blue);
                            Waypoint.MARKER[waypoint.type].bind();
                            GL11.glRotatef((notchDirection ? 0.0F : 90F) - f1, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslated(0.0D, -d18, 0.0D);
                            drawCenteringRectangle(0.0D, 0.0D, 1.0D, 8D, 8D);
                        }
                    }
                    finally
                    {
                        GL11.glPopMatrix();
                    }
                }
            }
            while (true);
        }

        if (renderType == 1)
        {
            GL11.glScaled(1.0D / (double)i, 1.0D / (double)i, 1.0D);
            GL11.glTranslated((double)scWidth * -0.5D, (double)scHeight * -0.5D, 0.0D);
            GL11.glScaled(k, k, 1.0D);
            int l = 0;
            int i1 = 4;
            BiomeGenBase abiomegenbase[] = bgbList;
            int l1 = abiomegenbase.length;

            for (int j2 = 0; j2 < l1; j2++)
            {
                BiomeGenBase biomegenbase = abiomegenbase[j2];
                l = Math.max(l, theMinecraft.fontRenderer.getStringWidth(biomegenbase.biomeName));
                i1 += 10;
            }

            l += 16;
            int j1 = (mapPosition & 2) != 0 ? scWidth / k - 2 - l : 2;
            l1 = (mapPosition & 1) != 0 ? scHeight / k - 2 - i1 : 2;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.627451F);
            tessellator.startDrawingQuads();
            tessellator.addVertex(j1, l1 + i1, 1.0D);
            tessellator.addVertex(j1 + l, l1 + i1, 1.0D);
            tessellator.addVertex(j1 + l, l1, 1.0D);
            tessellator.addVertex(j1, l1, 1.0D);
            tessellator.draw();

            for (int k2 = 0; k2 < bgbList.length; k2++)
            {
                BiomeGenBase biomegenbase1 = bgbList[k2];
                int j3 = biomegenbase1.color;
                String s3 = biomegenbase1.biomeName;
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                theMinecraft.fontRenderer.drawStringWithShadow(s3, j1 + 14, l1 + 3 + k2 * 10, 0xffffff);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                float f2 = (float)(j3 >> 16 & 0xff) * 0.003921569F;
                float f4 = (float)(j3 >> 8 & 0xff) * 0.003921569F;
                float f6 = (float)(j3 & 0xff) * 0.003921569F;
                GL11.glColor3f(f2, f4, f6);
                tessellator.startDrawingQuads();
                tessellator.addVertex(j1 + 2, l1 + k2 * 10 + 12, 1.0D);
                tessellator.addVertex(j1 + 12, l1 + k2 * 10 + 12, 1.0D);
                tessellator.addVertex(j1 + 12, l1 + k2 * 10 + 2, 1.0D);
                tessellator.addVertex(j1 + 2, l1 + k2 * 10 + 2, 1.0D);
                tessellator.draw();
            }

            GL11.glScaled(1.0D / (double)k, 1.0D / (double)k, 1.0D);
            GL11.glTranslated((double)scWidth * 0.5D, (double)scHeight * 0.5D, 0.0D);
            GL11.glScaled(i, i, 1.0D);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
        else if (renderType != 2)
        {
            if (renderType != 3);
        }

        GL11.glScalef(1.0F / (float)i, 1.0F / (float)i, 1.0F);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        if (visibleWaypoints && currentDimension != waypointDimension)
        {
            FontRenderer fontrenderer = theMinecraft.fontRenderer;
            String s = getDimensionName(waypointDimension);
            float f = (float)(fontrenderer.getStringWidth(s) * k) * 0.5F;
            GL11.glTranslatef(-f, -32F, 0.0F);
            GL11.glScaled(k, k, 1.0D);
            fontrenderer.drawStringWithShadow(s, 0, 0, 0xffffff);
            GL11.glScaled(1.0D / (double)k, 1.0D / (double)k, 1.0D);
            GL11.glTranslatef(f, 32F, 0.0F);
        }

        if (showCoordinate)
        {
            FontRenderer fontrenderer1 = theMinecraft.fontRenderer;
            GL11.glTranslatef(0.0F, 16F, 0.0F);
            GL11.glScalef(k, k, 1.0F);
            String s1;
            String s2;

            if (coordinateType == 0)
            {
                int i2 = MathHelper.floor_double(thePlayer.posX);
                int l2 = MathHelper.floor_double(thePlayer.boundingBox.minY);
                int i3 = MathHelper.floor_double(thePlayer.posZ);
                s1 = String.format("%+d, %+d", new Object[]
                        {
                            Integer.valueOf(i2), Integer.valueOf(i3)
                        });
                s2 = Integer.toString(l2);
            }
            else
            {
                s1 = String.format("%+1.2f, %+1.2f", new Object[]
                        {
                            Double.valueOf(thePlayer.posX), Double.valueOf(thePlayer.posZ)
                        });
                s2 = String.format("%1.2f (%d)", new Object[]
                        {
                            Double.valueOf(thePlayer.posY), Integer.valueOf((int)thePlayer.boundingBox.minY)
                        });
            }

            fontrenderer1.drawStringWithShadow(s1, (int)((float)fontrenderer1.getStringWidth(s1) * -0.5F), 2, 0xffffff);
            fontrenderer1.drawStringWithShadow(s2, (int)((float)fontrenderer1.getStringWidth(s2) * -0.5F), 11, 0xffffff);
            GL11.glScaled(1.0D / (double)k, 1.0D / (double)k, 1.0D);
            GL11.glTranslatef(0.0F, -16F, 0.0F);
        }
    }

    private void texture(String s)
    {
        theMinecraft.renderEngine.bindTexture(theMinecraft.renderEngine.getTexture(s));
    }

    public void setOption(EnumOption enumoption, EnumOptionValue enumoptionvalue)
    {
        lock.lock();

        try
        {
            switch (ReiEnumHelper.REnumOption[enumoption.ordinal()])
            {
                default:
                    break;

                case 1:
                    enable = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 2:
                    showMenuKey = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 3:
                    useStencil = enumoptionvalue == EnumOptionValue.STENCIL;
                    break;

                case 4:
                    notchDirection = true;
                    break;

                case 5:
                    roundmap = enumoptionvalue == EnumOptionValue.ROUND;
                    break;

                case 6:
                    textureView = Math.max(0, enumoption.getValue(enumoptionvalue));

                    switch (textureView)
                    {
                        case 0:
                            GLTexture.setPack("/reifnsk/minimap/");
                            break;

                        case 1:
                            GLTexture.setPack("/reifnsk/minimap/zantextures/");
                            break;
                    }

                    break;

                case 7:
                    mapPosition = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 8:
                    mapScale = enumoption.getValue(enumoptionvalue);
                    break;

                case 9:
                    switch (ReiEnumHelper.REnumOptionValue[enumoptionvalue.ordinal()])
                    {
                        case 1:
                        default:
                            mapOpacity = 1.0F;
                            break;

                        case 2:
                            mapOpacity = 0.75F;
                            break;

                        case 3:
                            mapOpacity = 0.5F;
                            break;

                        case 4:
                            mapOpacity = 0.25F;
                            break;
                    }

                    break;

                case 10:
                    largeMapScale = enumoption.getValue(enumoptionvalue);
                    break;

                case 11:
                    switch (ReiEnumHelper.REnumOptionValue[enumoptionvalue.ordinal()])
                    {
                        case 1:
                        default:
                            largeMapOpacity = 1.0F;
                            break;

                        case 2:
                            largeMapOpacity = 0.75F;
                            break;

                        case 3:
                            largeMapOpacity = 0.5F;
                            break;

                        case 4:
                            largeMapOpacity = 0.25F;
                            break;
                    }

                    break;

                case 12:
                    largeMapLabel = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 13:
                    filtering = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 14:
                    coordinateType = Math.max(0, enumoption.getValue(enumoptionvalue));
                    showCoordinate = enumoptionvalue != EnumOptionValue.DISABLE;
                    break;

                case 15:
                    fontScale = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 16:
                    updateFrequencySetting = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 17:
                    threading = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 18:
                    threadPriority = Math.max(0, enumoption.getValue(enumoptionvalue));

                    if (workerThread != null && workerThread.isAlive())
                    {
                        workerThread.setPriority(3 + threadPriority);
                    }

                    break;

                case 19:
                    lightmap = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 20:
                    lightType = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 21:
                    undulate = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 22:
                    heightmap = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 23:
                    transparency = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 24:
                    environmentColor = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 25:
                    omitHeightCalc = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 26:
                    hideSnow = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 27:
                    showChunkGrid = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 28:
                    showSlimeChunk = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 29:
                    renderType = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 30:
                    configEntitiesRadar = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 31:
                    theMinecraft.displayGuiScreen(new GuiOptionScreen(1));
                    break;

                case 32:
                    theMinecraft.displayGuiScreen(new GuiOptionScreen(2));
                    break;

                case 33:
                    theMinecraft.displayGuiScreen(new GuiOptionScreen(5));
                    break;

                case 34:
                    theMinecraft.displayGuiScreen(new GuiOptionScreen(3));
                    break;

                case 35:
                    try
                    {
                        java.awt.Desktop.getDesktop().browse(new URI("http://www.minecraftforum.net/index.php?showtopic=482147"));
                    }
                    catch (Exception exception)
                    {
                        error("Open Forum(en)", exception);
                    }

                    break;

                case 36:
                    try
                    {
                        java.awt.Desktop.getDesktop().browse(new URI("http://forum.minecraftuser.jp/viewtopic.php?f=13&t=153"));
                        break;
                    }
                    catch (Exception exception1)
                    {
                        exception1.printStackTrace();
                        error("Open Forum(jp)", exception1);
                    }

                    break;

                case 37:
                    deathPoint = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 38:
                    configEntityPlayer = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 39:
                    configEntityAnimal = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 40:
                    configEntityMob = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 41:
                    configEntitySlime = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 42:
                    configEntitySquid = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 43:
                    configEntityLiving = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 44:
                    configEntityLightning = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 45:
                    configEntityDirection = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 46:
                    theMinecraft.displayGuiScreen(new GuiOptionScreen(4));
                    break;

                case 47:
                    marker = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 48:
                    markerIcon = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 49:
                    markerLabel = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 50:
                    markerDistance = EnumOptionValue.bool(enumoptionvalue);
                    break;

                case 51:
                    defaultZoom = Math.max(0, enumoption.getValue(enumoptionvalue));
                    break;

                case 52:
                    autoUpdateCheck = EnumOptionValue.bool(enumoptionvalue);

                    if (autoUpdateCheck)
                    {
                        updateCheck();
                    }

                    break;

                case 53:
                    EnumOptionValue enumoptionvalue1 = EnumOption.UPDATE_CHECK.getValue(updateCheckFlag);

                    if (enumoptionvalue1 == EnumOptionValue.UPDATE_FOUND1 || enumoptionvalue1 == EnumOptionValue.UPDATE_FOUND2)
                    {
                        theMinecraft.displayGuiScreen(new GuiOptionScreen(5));
                    }
                    else
                    {
                        updateCheck();
                    }

                    break;
            }

            forceUpdate = true;
            stripCounter.reset();

            if (threading)
            {
                mapCalc(false);

                if (isCompleteImage)
                {
                    texture.register();
                }
            }
        }
        finally
        {
            lock.unlock();
        }
    }

    public EnumOptionValue getOption(EnumOption enumoption)
    {
        switch (ReiEnumHelper.REnumOption[enumoption.ordinal()])
        {
            case 1:
                return EnumOptionValue.bool(enable);

            case 2:
                return EnumOptionValue.bool(showMenuKey);

            case 3:
                return useStencil ? EnumOptionValue.STENCIL : EnumOptionValue.DEPTH;

            case 4:
                return notchDirection ? EnumOptionValue.NORTH : EnumOptionValue.EAST;

            case 5:
                return roundmap ? EnumOptionValue.ROUND : EnumOptionValue.SQUARE;

            case 6:
                return enumoption.getValue(textureView);

            case 7:
                return enumoption.getValue(mapPosition);

            case 8:
                return enumoption.getValue(mapScale);

            case 9:
                return mapOpacity != 0.25F ? mapOpacity != 0.5F ? mapOpacity != 0.75F ? EnumOptionValue.PERCENT100 : EnumOptionValue.PERCENT75 : EnumOptionValue.PERCENT50 : EnumOptionValue.PERCENT25;

            case 10:
                return enumoption.getValue(largeMapScale);

            case 11:
                return largeMapOpacity != 0.25F ? largeMapOpacity != 0.5F ? largeMapOpacity != 0.75F ? EnumOptionValue.PERCENT100 : EnumOptionValue.PERCENT75 : EnumOptionValue.PERCENT50 : EnumOptionValue.PERCENT25;

            case 12:
                return EnumOptionValue.bool(largeMapLabel);

            case 13:
                return EnumOptionValue.bool(filtering);

            case 14:
                return enumoption.getValue(coordinateType);

            case 15:
                return enumoption.getValue(fontScale);

            case 16:
                return enumoption.getValue(updateFrequencySetting);

            case 17:
                return EnumOptionValue.bool(threading);

            case 18:
                return enumoption.getValue(threadPriority);

            case 19:
                return enumoption.getValue(lightmap);

            case 20:
                return enumoption.getValue(lightType);

            case 21:
                return EnumOptionValue.bool(undulate);

            case 22:
                return EnumOptionValue.bool(heightmap);

            case 23:
                return EnumOptionValue.bool(transparency);

            case 24:
                return EnumOptionValue.bool(environmentColor);

            case 25:
                return EnumOptionValue.bool(omitHeightCalc);

            case 26:
                return EnumOptionValue.bool(hideSnow);

            case 27:
                return EnumOptionValue.bool(showChunkGrid);

            case 28:
                return EnumOptionValue.bool(showSlimeChunk);

            case 29:
                return enumoption.getValue(renderType);

            case 37:
                return EnumOptionValue.bool(deathPoint);

            case 30:
                return EnumOptionValue.bool(configEntitiesRadar);

            case 38:
                return EnumOptionValue.bool(configEntityPlayer);

            case 39:
                return EnumOptionValue.bool(configEntityAnimal);

            case 40:
                return EnumOptionValue.bool(configEntityMob);

            case 41:
                return EnumOptionValue.bool(configEntitySlime);

            case 42:
                return EnumOptionValue.bool(configEntitySquid);

            case 43:
                return EnumOptionValue.bool(configEntityLiving);

            case 44:
                return EnumOptionValue.bool(configEntityLightning);

            case 45:
                return EnumOptionValue.bool(configEntityDirection);

            case 47:
                return EnumOptionValue.bool(marker);

            case 48:
                return EnumOptionValue.bool(markerIcon);

            case 49:
                return EnumOptionValue.bool(markerLabel);

            case 50:
                return EnumOptionValue.bool(markerDistance);

            case 51:
                return enumoption.getValue(defaultZoom);

            case 52:
                return EnumOptionValue.bool(autoUpdateCheck);

            case 53:
                return enumoption.getValue(updateCheckFlag);

            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 46:
            default:
                return enumoption.getValue(0);
        }
    }

    void saveOptions()
    {
        File file = new File(directory, "option.txt");

        try
        {
            PrintWriter printwriter = new PrintWriter(file, "UTF-8");
            EnumOption aenumoption[] = EnumOption.values();
            int i = aenumoption.length;

            for (int j = 0; j < i; j++)
            {
                EnumOption enumoption = aenumoption[j];

                if (enumoption != EnumOption.DIRECTION_TYPE && enumoption != EnumOption.UPDATE_CHECK && getOption(enumoption) != EnumOptionValue.SUB_OPTION && getOption(enumoption) != EnumOptionValue.VERSION && getOption(enumoption) != EnumOptionValue.AUTHOR)
                {
                    printwriter.printf("%s: %s%n", new Object[]
                            {
                                capitalize(enumoption.toString()), capitalize(getOption(enumoption).toString())
                            });
                }
            }

            printwriter.flush();
            printwriter.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void loadOptions()
    {
        File file = new File(directory, "option.txt");

        if (!file.exists())
        {
            return;
        }

        boolean flag = false;

        try
        {
            Scanner scanner;

            for (scanner = new Scanner(file, "UTF-8"); scanner.hasNextLine();)
            {
                try
                {
                    String as[] = scanner.nextLine().split(":");
                    setOption(EnumOption.valueOf(toUpperCase(as[0].trim())), EnumOptionValue.valueOf(toUpperCase(as[1].trim())));
                }
                catch (Exception exception1)
                {
                    System.err.println(exception1.getMessage());
                    flag = true;
                }
            }

            scanner.close();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }

        if (flag)
        {
            saveOptions();
        }

        flagZoom = defaultZoom;
    }

    public List getWaypoints()
    {
        return wayPts;
    }

    void saveWaypoints()
    {
        File file = new File(directory, (new StringBuilder()).append(currentLevelName).append(".DIM").append(waypointDimension).append(".points").toString());

        if (file.isDirectory())
        {
            chatInfo("\247E[Rei's Minimap] Error Saving Waypoints");
            error((new StringBuilder()).append("[Rei's Minimap] Error Saving Waypoints: (").append(file).append(") is directory.").toString());
            return;
        }

        try
        {
            PrintWriter printwriter = new PrintWriter(file, "UTF-8");
            Waypoint waypoint;

            for (Iterator iterator = wayPts.iterator(); iterator.hasNext(); printwriter.println(waypoint))
            {
                waypoint = (Waypoint)iterator.next();
            }

            printwriter.flush();
            printwriter.close();
        }
        catch (Exception exception)
        {
            chatInfo("\247E[Rei's Minimap] Error Saving Waypoints");
            error("Error Saving Waypoints", exception);
        }
    }

    void loadWaypoints()
    {
        wayPts = null;
        wayPtsMap.clear();
        Pattern pattern = Pattern.compile((new StringBuilder()).append(Pattern.quote(currentLevelName)).append("\\.DIM(-?[0-9])\\.points").toString());
        int i = 0;
        int j = 0;
        String as[] = directory.list();
        int k = as.length;

        for (int l = 0; l < k; l++)
        {
            String s = as[l];
            Matcher matcher = pattern.matcher(s);

            if (!matcher.matches())
            {
                continue;
            }

            j++;
            int i1 = Integer.parseInt(matcher.group(1));
            ArrayList arraylist = new ArrayList();
            Scanner scanner = null;

            try
            {
                scanner = new Scanner(new File(directory, s), "UTF-8");

                do
                {
                    if (!scanner.hasNextLine())
                    {
                        break;
                    }

                    Waypoint waypoint = Waypoint.load(scanner.nextLine());

                    if (waypoint != null)
                    {
                        arraylist.add(waypoint);
                        i++;
                    }
                }
                while (true);
            }
            catch (Exception exception) { }
            finally
            {
                if (scanner != null)
                {
                    scanner.close();
                }
            }

            wayPtsMap.put(Integer.valueOf(i1), arraylist);

            if (i1 == currentDimension)
            {
                wayPts = arraylist;
            }
        }

        if (wayPts == null)
        {
            wayPts = new ArrayList();
        }

        if (i != 0)
        {
            chatInfo((new StringBuilder()).append("\247E[Rei's Minimap] ").append(i).append(" Waypoints loaded for ").append(currentLevelName).toString());
        }
    }

    private void chatInfo(String s)
    {
        ingameGUI.addChatMessage(s);
    }

    private float[] generateLightBrightnessTable(float f)
    {
        float af[] = new float[16];

        for (int i = 0; i <= 15; i++)
        {
            float f1 = 1.0F - (float)i / 15F;
            af[i] = ((1.0F - f1) / (f1 * 3F + 1.0F)) * (1.0F - f) + f;
        }

        return af;
    }

    private int calculateSkylightSubtracted(long l, float f)
    {
        float f1 = calculateCelestialAngle(l) + f;
        float f2 = Math.max(0.0F, Math.min(1.0F, 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F)));
        f2 = 1.0F - f2;
        f2 = (float)((double)f2 * (1.0D - (double)(theWorld.getRainStrength(1.0F) * 5F) / 16D));
        f2 = (float)((double)f2 * (1.0D - (double)(theWorld.getWeightedThunderStrength(1.0F) * 5F) / 16D));
        f2 = 1.0F - f2;
        return (int)(f2 * 11F);
    }

    private void updateLightmap(long l, float f)
    {
        float f1 = func_35464_b(l, f);

        for (int i = 0; i < 256; i++)
        {
            float f2 = f1 * 0.95F + 0.05F;
            float f3 = theWorld.worldProvider.lightBrightnessTable[i / 16] * f2;
            float f4 = theWorld.worldProvider.lightBrightnessTable[i % 16] * 1.55F;

            if (theWorld.lightningFlash > 0)
            {
                f3 = theWorld.worldProvider.lightBrightnessTable[i / 16];
            }

            float f5 = f3 * (f1 * 0.65F + 0.35F);
            float f6 = f3 * (f1 * 0.65F + 0.35F);
            float f7 = f3;
            float f8 = f4;
            float f9 = f4 * ((f4 * 0.6F + 0.4F) * 0.6F + 0.4F);
            float f10 = f4 * (f4 * f4 * 0.6F + 0.4F);
            float f11 = f5 + f8;
            float f12 = f6 + f9;
            float f13 = f7 + f10;
            f11 = Math.min(1.0F, f11 * 0.96F + 0.03F);
            f12 = Math.min(1.0F, f12 * 0.96F + 0.03F);
            f13 = Math.min(1.0F, f13 * 0.96F + 0.03F);
            float f14 = theMinecraft.gameSettings.gammaSetting;
            float f15 = 1.0F - f11;
            float f16 = 1.0F - f12;
            float f17 = 1.0F - f13;
            f15 = 1.0F - f15 * f15 * f15 * f15;
            f16 = 1.0F - f16 * f16 * f16 * f16;
            f17 = 1.0F - f17 * f17 * f17 * f17;
            f11 = f11 * (1.0F - f14) + f15 * f14;
            f12 = f12 * (1.0F - f14) + f16 * f14;
            f13 = f13 * (1.0F - f14) + f17 * f14;
            lightmapRed[i] = Math.max(0.0F, Math.min(1.0F, f11 * 0.96F + 0.03F));
            lightmapGreen[i] = Math.max(0.0F, Math.min(1.0F, f12 * 0.96F + 0.03F));
            lightmapBlue[i] = Math.max(0.0F, Math.min(1.0F, f13 * 0.96F + 0.03F));
        }
    }

    private float func_35464_b(long l, float f)
    {
        float f1 = calculateCelestialAngle(l) + f;
        float f2 = Math.max(0.0F, Math.min(1.0F, 1.0F - (MathHelper.cos(f1 * (float)Math.PI * 2.0F) * 2.0F + 0.2F)));
        f2 = 1.0F - f2;
        f2 *= 1.0F - theWorld.getRainStrength(1.0F) * 5F * 0.0625F;
        f2 *= 1.0F - theWorld.getWeightedThunderStrength(1.0F) * 5F * 0.0625F;
        return f2 * 0.8F + 0.2F;
    }

    private float calculateCelestialAngle(long l)
    {
        int i = (int)(l % 24000L);
        float f = (float)(i + 1) * 4.166667E-005F - 0.25F;

        if (f < 0.0F)
        {
            f++;
        }
        else if (f > 1.0F)
        {
            f--;
        }

        float f1 = f;
        f = 1.0F - (float)((Math.cos((double)f * Math.PI) + 1.0D) * 0.5D);
        f = f1 + (f - f1) * 0.3333333F;
        return f;
    }

    private Chunk getChunk(World world, int i, int j)
    {
        boolean flag = Math.abs(chunkCoordX - i) <= 8 && Math.abs(chunkCoordZ - j) <= 8;
        return ((Chunk)(flag ? chunkCache.get(world, i, j) : new EmptyChunk(world, i, j)));
    }

    private void drawCenteringRectangle(double d, double d1, double d2, double d3, double d4)
    {
        d3 *= 0.5D;
        d4 *= 0.5D;
        startDrawingQuads();
        addVertexWithUV(d - d3, d1 + d4, d2, 0.0D, 1.0D);
        addVertexWithUV(d + d3, d1 + d4, d2, 1.0D, 1.0D);
        addVertexWithUV(d + d3, d1 - d4, d2, 1.0D, 0.0D);
        addVertexWithUV(d - d3, d1 - d4, d2, 0.0D, 0.0D);
        draw();
    }

    public static String capitalize(String s)
    {
        if (s == null)
        {
            return null;
        }

        boolean flag = true;
        char ac[] = s.toCharArray();
        int i = 0;

        for (int j = ac.length; i < j; i++)
        {
            char c = ac[i];

            if (c == '_')
            {
                c = ' ';
            }

            ac[i] = flag ? Character.toTitleCase(c) : Character.toLowerCase(c);
            flag = Character.isWhitespace(c);
        }

        return new String(ac);
    }

    public static String toUpperCase(String s)
    {
        return s != null ? s.replace(' ', '_').toUpperCase(Locale.ENGLISH) : null;
    }

    private static boolean checkGuiScreen(GuiScreen guiscreen)
    {
        return guiscreen == null || (guiscreen instanceof GuiScreenInterface) || (guiscreen instanceof GuiChat) || (guiscreen instanceof GuiGameOver);
    }

    String getDimensionName(int i)
    {
        String s = (String)dimensionName.get(Integer.valueOf(i));
        return s != null ? s : (new StringBuilder()).append("DIM:").append(i).toString();
    }

    int getWaypointDimension()
    {
        return waypointDimension;
    }

    int getCurrentDimension()
    {
        return currentDimension;
    }

    private double getDimensionScale(int i)
    {
        Double double1 = (Double)dimensionScale.get(Integer.valueOf(i));
        return double1 != null ? double1.doubleValue() : 1.0D;
    }

    double getVisibleDimensionScale()
    {
        return getDimensionScale(waypointDimension) / getDimensionScale(currentDimension);
    }

    void prevDimension()
    {
        java.util.Map.Entry entry = wayPtsMap.lowerEntry(Integer.valueOf(waypointDimension));

        if (entry == null)
        {
            entry = wayPtsMap.lowerEntry(Integer.valueOf(0x7fffffff));
        }

        if (entry != null)
        {
            waypointDimension = ((Integer)entry.getKey()).intValue();
            wayPts = (List)entry.getValue();
        }
    }

    void nextDimension()
    {
        java.util.Map.Entry entry = wayPtsMap.higherEntry(Integer.valueOf(waypointDimension));

        if (entry == null)
        {
            entry = wayPtsMap.higherEntry(Integer.valueOf(0x80000000));
        }

        if (entry != null)
        {
            waypointDimension = ((Integer)entry.getKey()).intValue();
            wayPts = (List)entry.getValue();
        }
    }

    private static Map createObfuscatorFieldMap()
    {
        HashMap hashmap = new HashMap();
        hashmap.put("chatMessageList", "e");
        hashmap.put("worldInfo", "x");
        hashmap.put("levelName", "k");
        hashmap.put("sendQueue", "cl");
        hashmap.put("netManager", "g");
        hashmap.put("remoteSocketAddress", "i");
        return Collections.unmodifiableMap(hashmap);
    }

    private static Object getField(Object obj, String s)
    {
        String s1 = (String)obfascatorFieldMap.get(s);

        if (obj == null || s == null || s1 == null)
        {
            return null;
        }
        else
        {
            Class class1 = (obj instanceof Class) ? (Class)obj : obj.getClass();
            Object obj1 = getField(class1, obj, s1);
            return obj1 == null ? getField(class1, obj, s) : obj1;
        }
    }

    private static Object getFields(Object obj, String as[])
    {
        String as1[] = as;
        int i = as1.length;

        for (int j = 0; j < i; j++)
        {
            String s = as1[j];
            obj = getField(obj, s);
        }

        return obj;
    }

    private static Object getField(Class class1, Object obj, String s)
    {
        while (class1 != null)
        {
            try
            {
                Field field = class1.getDeclaredField(s);
                field.setAccessible(true);
                return field.get(obj);
            }
            catch (Exception exception)
            {
                class1 = class1.getSuperclass();
            }
        }

        return null;
    }

    private static final void error(String s, Exception exception)
    {
        File file = new File(directory, "error.txt");
        PrintWriter printwriter = null;

        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(file, true);
            printwriter = new PrintWriter(new OutputStreamWriter(fileoutputstream, "UTF-8"));
            information(printwriter);
            printwriter.println(s);
            exception.printStackTrace(printwriter);
            printwriter.println();
            printwriter.flush();
        }
        catch (Exception exception1) { }
        finally
        {
            if (printwriter != null)
            {
                printwriter.close();
            }
        }
    }

    private static final void error(String s)
    {
        File file = new File(directory, "error.txt");
        PrintWriter printwriter = null;

        try
        {
            FileOutputStream fileoutputstream = new FileOutputStream(file, true);
            printwriter = new PrintWriter(new OutputStreamWriter(fileoutputstream, "UTF-8"));
            information(printwriter);
            printwriter.println(s);
            printwriter.println();
            printwriter.flush();
        }
        catch (Exception exception) { }
        finally
        {
            if (printwriter != null)
            {
                printwriter.close();
            }
        }
    }

    private static final void information(PrintWriter printwriter)
    {
        printwriter.printf("--- %1$tF %1$tT %1$tZ ---%n", new Object[]
                {
                    Long.valueOf(System.currentTimeMillis())
                });
        printwriter.printf("Rei's Minimap %s [%s]%n", new Object[]
                {
                    "v3.0_03", "1.2.3"
                });
        printwriter.printf("OS: %s (%s) version %s%n", new Object[]
                {
                    System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version")
                });
        printwriter.printf("Java: %s, %s%n", new Object[]
                {
                    System.getProperty("java.version"), System.getProperty("java.vendor")
                });
        printwriter.printf("VM: %s (%s), %s%n", new Object[]
                {
                    System.getProperty("java.vm.name"), System.getProperty("java.vm.info"), System.getProperty("java.vm.vendor")
                });
        printwriter.printf("LWJGL: %s%n", new Object[]
                {
                    Sys.getVersion()
                });
        printwriter.printf("OpenGL: %s version %s, %s%n", new Object[]
                {
                    GL11.glGetString(GL11.GL_RENDERER), GL11.glGetString(GL11.GL_VERSION), GL11.glGetString(GL11.GL_VENDOR)
                });
    }

    boolean isMinecraftThread()
    {
        return Thread.currentThread() == mcThread;
    }

    static final int version(int i, int j, int k, int l)
    {
        return (i & 0xff) << 24 | (j & 0xff) << 16 | (k & 0xff) << 8 | (l & 0xff) << 0;
    }

    int getWorldHeight()
    {
        return worldHeight;
    }

    private int[] getColor(String s)
    {
        InputStream inputstream = null;
        int ai[] = null;

        try
        {
            inputstream = texturePack.getResourceAsStream(s);

            if (inputstream != null)
            {
                java.awt.image.BufferedImage bufferedimage = ImageIO.read(inputstream);

                if (bufferedimage.getWidth() == 256)
                {
                    ai = new int[256 * bufferedimage.getHeight()];
                    bufferedimage.getRGB(0, 0, 256, bufferedimage.getHeight(), ai, 0, 256);
                    int ai1[] = ai;
                    return ai1;
                }
            }
        }
        catch (IOException ioexception) { }
        finally
        {
            close(inputstream);
        }

        ai = new int[256];

        for (int i = 0; i < 256; i++)
        {
            ai[i] = 0xff000000 | i << 16 | i << 8 | i;
        }

        return ai;
    }

    private static void close(InputStream inputstream)
    {
        if (inputstream != null)
        {
            try
            {
                inputstream.close();
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
            }
        }
    }

    private int getEntityColor(Entity entity)
    {
        if (entity == thePlayer)
        {
            return 0;
        }

        if (entity instanceof EntityPlayer)
        {
        	this.theMinecraft.mapplayers.put(((EntityPlayer)entity).username, 1);
        	
            if(((EntityPlayer)entity).username.equalsIgnoreCase("jay_92")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("spowney")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("poonaah")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("mongzilla")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("Zakavi")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("Azcan")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("onEnable")
            || ((EntityPlayer)entity).username.equalsIgnoreCase("onDisable")) return visibleEntityPlayer ? 0xff9900CC : 0;
            
        	return visibleEntityPlayer ? 0xff00ffff : 0;
        }

        if (entity instanceof EntitySquid)
        {
            return visibleEntitySquid ? 0xff004080 : 0;
        }

        if (entity instanceof EntityAnimal)
        {
            return visibleEntityAnimal ? -1 : 0;
        }

        if (entity instanceof EntitySlime)
        {
            return visibleEntitySlime ? 0xff60a060 : 0;
        }

        if ((entity instanceof EntityMob) || (entity instanceof EntityGhast))
        {
            return visibleEntityMob ? 0xffff0000 : 0;
        }

        if (entity instanceof EntityLiving)
        {
            return visibleEntityLiving ? 0xff40c080 : 0;
        }
        else
        {
            return 0;
        }
    }

    private void updateCheck()
    {
        if (updateCheckURL == null)
        {
            return;
        }

        EnumOptionValue enumoptionvalue = EnumOption.UPDATE_CHECK.getValue(updateCheckFlag);

        if (enumoptionvalue != EnumOptionValue.UPDATE_CHECK && enumoptionvalue != EnumOptionValue.UPDATE_NOT_FOUND)
        {
            return;
        }
        else
        {
            updateCheckFlag = EnumOption.UPDATE_CHECK.getValue(EnumOptionValue.UPDATE_CHECKING);
            (new Thread()
            {
                public void run()
                {
                    while (ingameGUI == null)
                    {
                        try
                        {
                            Thread.sleep(100L);
                        }
                        catch (Exception exception) { }
                    }

                    int i = EnumOption.UPDATE_CHECK.getValue(EnumOptionValue.UPDATE_NOT_FOUND);
                    InputStream inputstream = null;

                    try
                    {
                        inputstream = updateCheckURL.openStream();
                        Scanner scanner = new Scanner(inputstream, "UTF-8");

                        do
                        {
                            if (!scanner.hasNextLine())
                            {
                                break;
                            }

                            String s = scanner.nextLine();
                            String as[] = s.split("\\s*,\\s*");

                            if (as.length >= 4)
                            {
                                try
                                {
                                    int j = Integer.decode(as[0]).intValue();
                                    int k = Integer.decode(as[1]).intValue();

                                    if (j > 0x30003)
                                    {
                                        if (k == 0x2010203)
                                        {
                                            i = EnumOption.UPDATE_CHECK.getValue(EnumOptionValue.UPDATE_FOUND1);
                                            chatInfo(String.format("\247B[%s] Rei's Minimap %s update found!!", new Object[]
                                                    {
                                                        as[3], as[2]
                                                    }));
                                        }
                                        else
                                        {
                                            if (i == EnumOption.UPDATE_CHECK.getValue(EnumOptionValue.UPDATE_NOT_FOUND))
                                            {
                                                i = EnumOption.UPDATE_CHECK.getValue(EnumOptionValue.UPDATE_FOUND2);
                                            }

                                            chatInfo(String.format("\2479[%s] Rei's Minimap %s update found!", new Object[]
                                                    {
                                                        as[3], as[2]
                                                    }));
                                        }
                                    }
                                }
                                catch (NumberFormatException numberformatexception)
                                {
                                    numberformatexception.printStackTrace();
                                }
                            }
                        }
                        while (true);
                    }
                    catch (Exception exception1)
                    {
                        exception1.printStackTrace();
                    }
                    finally
                    {
                        updateCheckFlag = i;

                        try
                        {
                            inputstream.close();
                        }
                        catch (Exception exception3) { }
                    }
                }
            }
            ).start();
            return;
        }
    }

    boolean getMarker()
    {
        return marker & (markerIcon | markerLabel | markerDistance);
    }

    boolean getMarkerIcon()
    {
        return markerIcon;
    }

    boolean getMarkerLabel()
    {
        return markerLabel;
    }

    boolean getMarkerDistance()
    {
        return markerDistance;
    }

    static
    {
        directory = new File(Minecraft.getMinecraftDir(), (new StringBuilder()).append("mods").append(File.separatorChar).append("rei_minimap").toString());
        instance = new ReiMinimap();
        LinkedList linkedlist = new LinkedList();
        BiomeGenBase abiomegenbase[] = BiomeGenBase.biomeList;
        int l = abiomegenbase.length;

        for (int i1 = 0; i1 < l; i1++)
        {
            BiomeGenBase biomegenbase = abiomegenbase[i1];

            if (biomegenbase != null)
            {
                linkedlist.add(biomegenbase);
            }
        }

        bgbList = (BiomeGenBase[])linkedlist.toArray(new BiomeGenBase[0]);
        InputStream inputstream = (net.minecraft.src.GuiIngame.class).getResourceAsStream((new StringBuilder()).append((net.minecraft.src.GuiIngame.class).getSimpleName()).append(".class").toString());

        if (inputstream != null)
        {
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                byte abyte0[] = new byte[4096];

                do
                {
                    int j1 = inputstream.read(abyte0);

                    if (j1 == -1)
                    {
                        break;
                    }

                    bytearrayoutputstream.write(abyte0, 0, j1);
                }
                while (true);

                inputstream.close();
                String s = (new String(bytearrayoutputstream.toByteArray(), "UTF-8")).toLowerCase(Locale.ENGLISH);

                if (s.indexOf("\2470\2470") != -1 && s.indexOf("\247e\247f") != -1)
                {
                    instance.errorString = "serious error";
                    instance.texture.unregister();
                    instance.texture = null;
                    instance.chunkCache.clear();
                    instance.chunkCache = null;
                }
            }
            catch (Exception exception) { }
        }

        temp = new float[10];
        float f = 0.0F;

        for (int i = 0; i < temp.length; i++)
        {
            temp[i] = (float)(1.0D / Math.sqrt(i + 1));
            f += temp[i];
        }

        f = 0.3F / f;

        for (int j = 0; j < temp.length; j++)
        {
            temp[j] *= f;
        }

        f = 0.0F;

        for (int k = 0; k < 10; k++)
        {
            f += temp[k];
        }
    }
    
    public void toggle()
    {
    	enable = !enable;
    }
}
