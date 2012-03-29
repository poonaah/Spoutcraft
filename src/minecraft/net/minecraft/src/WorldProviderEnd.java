package net.minecraft.src;

public class WorldProviderEnd extends WorldProvider {
	public WorldProviderEnd() {
	}

	public void registerWorldChunkManager() {
		worldChunkMgr = new WorldChunkManagerHell(BiomeGenBase.sky, 0.5F, 0.0F);
		worldType = 1;
		hasNoSky = true;
	}

	public IChunkProvider getChunkProvider() {
		return new ChunkProviderEnd(worldObj, worldObj.getSeed());
	}

	public float calculateCelestialAngle(long par1, float par3) {
		return 0.0F;
	}

	public float[] calcSunriseSunsetColors(float par1, float par2) {
		return null;
	}

	public Vec3D getFogColor(float par1, float par2) {
		int i = 0x8080a0;
		float f = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

		if (f < 0.0F) {
			f = 0.0F;
		}

		if (f > 1.0F) {
			f = 1.0F;
		}

		float f1 = (float)(i >> 16 & 0xff) / 255F;
		float f2 = (float)(i >> 8 & 0xff) / 255F;
		float f3 = (float)(i & 0xff) / 255F;
		f1 *= f * 0.0F + 0.15F;
		f2 *= f * 0.0F + 0.15F;
		f3 *= f * 0.0F + 0.15F;
		return Vec3D.createVector(f1, f2, f3);
	}

	public boolean isSkyColored() {
		return false;
	}

	public boolean canRespawnHere() {
		return false;
	}

	public boolean func_48217_e() {
		return false;
	}

	public float getCloudHeight() {
		return 8F;
	}

	public boolean canCoordinateBeSpawn(int par1, int par2) {
		int i = worldObj.getFirstUncoveredBlock(par1, par2);

		if (i == 0) {
			return false;
		} else {
			return Block.blocksList[i].blockMaterial.blocksMovement();
		}
	}

	public ChunkCoordinates getEntrancePortalLocation() {
		return new ChunkCoordinates(100, 50, 0);
	}

	public int getAverageGroundLevel() {
		return 50;
	}

	public boolean func_48218_b(int par1, int par2) {
		return true;
	}
}
