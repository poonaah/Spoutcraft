package reifnsk.minimap;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class WaypointEntity extends Entity
{
    private final Minecraft mc;
    private ArrayList unloadedEntity;

    public WaypointEntity(Minecraft minecraft)
    {
        super(minecraft.theWorld);
        mc = minecraft;
        setSize(0.0F, 0.0F);
        //ignoreFrustrumCheck = true;
        onUpdate();
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    }

    protected void entityInit()
    {
    }

    /**
     * Checks using a Vec3d to determine if this entity is within range of that vector to be rendered. Args: vec3D
     */
    public boolean isInRangeToRenderVec3D(Vec3D vec3d)
    {
        return true;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
    }
}
