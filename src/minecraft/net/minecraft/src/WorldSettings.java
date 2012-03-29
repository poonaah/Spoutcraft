package net.minecraft.src;

public final class WorldSettings {
	private final long seed;
	private final int gameType;
	private final boolean mapFeaturesEnabled;
	private final boolean hardcoreEnabled;
	private final WorldType terrainType;

	public WorldSettings(long par1, int par3, boolean par4, boolean par5, WorldType par6WorldType) {
		seed = par1;
		gameType = par3;
		mapFeaturesEnabled = par4;
		hardcoreEnabled = par5;
		terrainType = par6WorldType;
	}

	public long getSeed() {
		return seed;
	}

	public int getGameType() {
		return gameType;
	}

	public boolean getHardcoreEnabled() {
		return hardcoreEnabled;
	}

	public boolean isMapFeaturesEnabled() {
		return mapFeaturesEnabled;
	}

	public WorldType getTerrainType() {
		return terrainType;
	}
}
