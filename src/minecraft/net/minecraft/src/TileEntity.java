package net.minecraft.src;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

public class TileEntity {
	private static Map nameToClassMap = new HashMap();
	private static Map classToNameMap = new HashMap();
	public World worldObj;
	public int xCoord;
	public int yCoord;
	public int zCoord;
	protected boolean tileEntityInvalid;
	public int blockMetadata;
	public Block blockType;

	public TileEntity() {
		blockMetadata = -1;
	}

	private static void addMapping(Class par0Class, String par1Str) {
		if (classToNameMap.containsKey(par1Str)) {
			throw new IllegalArgumentException((new StringBuilder()).append("Duplicate id: ").append(par1Str).toString());
		} else {
			nameToClassMap.put(par1Str, par0Class);
			classToNameMap.put(par0Class, par1Str);
			return;
		}
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		xCoord = par1NBTTagCompound.getInteger("x");
		yCoord = par1NBTTagCompound.getInteger("y");
		zCoord = par1NBTTagCompound.getInteger("z");
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		String s = (String)classToNameMap.get(getClass());

		if (s == null) {
			throw new RuntimeException((new StringBuilder()).append(getClass()).append(" is missing a mapping! This is a bug!").toString());
		} else {
			par1NBTTagCompound.setString("id", s);
			par1NBTTagCompound.setInteger("x", xCoord);
			par1NBTTagCompound.setInteger("y", yCoord);
			par1NBTTagCompound.setInteger("z", zCoord);
			return;
		}
	}

	public void updateEntity() {
	}

	public static TileEntity createAndLoadEntity(NBTTagCompound par0NBTTagCompound) {
		TileEntity tileentity = null;

		try {
			Class class1 = (Class)nameToClassMap.get(par0NBTTagCompound.getString("id"));

			if (class1 != null) {
				tileentity = (TileEntity)class1.newInstance();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		if (tileentity != null) {
			tileentity.readFromNBT(par0NBTTagCompound);
		} else {
			System.out.println((new StringBuilder()).append("Skipping TileEntity with id ").append(par0NBTTagCompound.getString("id")).toString());
		}

		return tileentity;
	}

	public int getBlockMetadata() {
		if (blockMetadata == -1) {
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		}

		return blockMetadata;
	}

	public void onInventoryChanged() {
		if (worldObj != null) {
			blockMetadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			worldObj.updateTileEntityChunkAndDoNothing(xCoord, yCoord, zCoord, this);
		}
	}

	public double getDistanceFrom(double par1, double par3, double par5) {
		double d = ((double)xCoord + 0.5D) - par1;
		double d1 = ((double)yCoord + 0.5D) - par3;
		double d2 = ((double)zCoord + 0.5D) - par5;
		return d * d + d1 * d1 + d2 * d2;
	}

	public Block getBlockType() {
		if (blockType == null) {
			blockType = Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
		}

		return blockType;
	}

	public boolean isInvalid() {
		return tileEntityInvalid;
	}

	public void invalidate() {
		tileEntityInvalid = true;
	}

	public void validate() {
		tileEntityInvalid = false;
	}

	public void onTileEntityPowered(int i, int j) {
	}

	public void updateContainingBlockInfo() {
		blockType = null;
		blockMetadata = -1;
	}

	static {
		addMapping(net.minecraft.src.TileEntityFurnace.class, "Furnace");
		addMapping(net.minecraft.src.TileEntityChest.class, "Chest");
		addMapping(net.minecraft.src.TileEntityRecordPlayer.class, "RecordPlayer");
		addMapping(net.minecraft.src.TileEntityDispenser.class, "Trap");
		addMapping(net.minecraft.src.TileEntitySign.class, "Sign");
		addMapping(net.minecraft.src.TileEntityMobSpawner.class, "MobSpawner");
		addMapping(net.minecraft.src.TileEntityNote.class, "Music");
		addMapping(net.minecraft.src.TileEntityPiston.class, "Piston");
		addMapping(net.minecraft.src.TileEntityBrewingStand.class, "Cauldron");
		addMapping(net.minecraft.src.TileEntityEnchantmentTable.class, "EnchantTable");
		addMapping(net.minecraft.src.TileEntityEndPortal.class, "Airportal");
	}
}
