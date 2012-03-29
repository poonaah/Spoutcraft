package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EmptyChunk extends Chunk {
	public EmptyChunk(World par1World, int par2, int par3) {
		super(par1World, par2, par3);
	}

	public boolean isAtLocation(int par1, int par2) {
		return par1 == xPosition && par2 == zPosition;
	}

	public int getHeightValue(int par1, int par2) {
		return 0;
	}

	public void generateHeightMap() {
	}

	public void generateSkylightMap() {
	}

	public void func_4143_d() {
	}

	public int getBlockID(int par1, int par2, int par3) {
		return 0;
	}

	public int getBlockLightOpacity(int par1, int par2, int par3) {
		return 255;
	}

	public boolean setBlockIDWithMetadata(int par1, int par2, int par3, int i, int j) {
		return true;
	}

	public boolean setBlockID(int par1, int par2, int par3, int i) {
		return true;
	}

	public int getBlockMetadata(int par1, int par2, int par3) {
		return 0;
	}

	public boolean setBlockMetadata(int par1, int par2, int par3, int i) {
		return false;
	}

	public int getSavedLightValue(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int i) {
		return 0;
	}

	public void setLightValue(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
	}

	public int getBlockLightValue(int par1, int par2, int par3, int i) {
		return 0;
	}

	public void addEntity(Entity entity) {
	}

	public void removeEntity(Entity entity) {
	}

	public void removeEntityAtIndex(Entity entity, int i) {
	}

	public boolean canBlockSeeTheSky(int par1, int par2, int par3) {
		return false;
	}

	public TileEntity getChunkBlockTileEntity(int par1, int par2, int par3) {
		return null;
	}

	public void addTileEntity(TileEntity tileentity) {
	}

	public void setChunkBlockTileEntity(int i, int j, int k, TileEntity tileentity) {
	}

	public void removeChunkBlockTileEntity(int i, int j, int k) {
	}

	public void onChunkLoad() {
	}

	public void onChunkUnload() {
	}

	public void setChunkModified() {
	}

	public void getEntitiesWithinAABBForEntity(Entity entity, AxisAlignedBB axisalignedbb, List list) {
	}

	public void getEntitiesOfTypeWithinAAAB(Class class1, AxisAlignedBB axisalignedbb, List list) {
	}

	public boolean needsSaving(boolean par1) {
		return false;
	}

	public Random getRandomWithSeed(long par1) {
		return new Random(worldObj.getSeed() + (long)(xPosition * xPosition * 0x4c1906) + (long)(xPosition * 0x5ac0db) + (long)(zPosition * zPosition) * 0x4307a7L + (long)(zPosition * 0x5f24f) ^ par1);
	}

	public boolean isEmpty() {
		return true;
	}

	public boolean getAreLevelsEmpty(int par1, int par2) {
		return true;
	}
}
