package reifnsk.minimap;

import java.util.*;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class WaypointEntityRender extends Render
{
    class ViewWaypoint {}

    static final ReiMinimap rm;
    final Minecraft mc;
    double far;
    double _d;

    public WaypointEntityRender(Minecraft minecraft)
    {
        far = 1.0D;
        _d = 1.0D;
        mc = minecraft;
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity entity, double d, double d1, double d2, float f, float f1)
    {
        far = (double)(512 >> mc.gameSettings.renderDistance) * 0.8D;
        _d = 1.0D / (double)(256 >> mc.gameSettings.renderDistance);
        double d3 = rm.getVisibleDimensionScale();
        ArrayList arraylist = new ArrayList();

        if (!rm.getMarker())
        {
            return;
        }

        Iterator iterator = rm.getWaypoints().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            Waypoint waypoint = (Waypoint)iterator.next();

            if (waypoint.enable)
            {
                arraylist.add(new ViewWaypoint());
            }
        }
        while (true);

        if (arraylist.isEmpty())
        {
            return;
        }

        Collections.sort(arraylist);
        mc.entityRenderer.disableLightmap(0.0D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        ViewWaypoint viewwaypoint;

        for (Iterator iterator1 = arraylist.iterator(); iterator1.hasNext(); draw(viewwaypoint, f, f1))
        {
            viewwaypoint = (ViewWaypoint)iterator1.next();
        }

        GL11.glEnable(GL11.GL_FOG);
        GL11.glEnable(GL11.GL_LIGHTING);
        mc.entityRenderer.enableLightmap(0.0D);
        shadowSize = 0.0F;
    }

    void draw(ViewWaypoint viewwaypoint, float f, float f1)
    {
       
    }

    static
    {
        rm = ReiMinimap.instance;
    }
}
