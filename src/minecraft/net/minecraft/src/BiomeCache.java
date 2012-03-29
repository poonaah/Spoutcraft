package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class BiomeCache {
	private final WorldChunkManager chunkManager;
	private long lastCleanupTime;
	private LongHashMap cacheMap;
	private List cache;

	public BiomeCache(WorldChunkManager par1WorldChunkManager) {
		lastCleanupTime = 0L;
		cacheMap = new LongHashMap();
		cache = new ArrayList();
		chunkManager = par1WorldChunkManager;
	}

	public BiomeCacheBlock getBiomeCacheBlock(int par1, int par2) {
		par1 >>= 4;
		par2 >>= 4;
		long l = (long)par1 & 0xffffffffL | ((long)par2 & 0xffffffffL) << 32;
		BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)cacheMap.getValueByKey(l);

		if (biomecacheblock == null) {
			biomecacheblock = new BiomeCacheBlock(this, par1, par2);
			cacheMap.add(l, biomecacheblock);
			cache.add(biomecacheblock);
		}

		biomecacheblock.lastAccessTime = System.currentTimeMillis();
		return biomecacheblock;
	}

	public BiomeGenBase getBiomeGenAt(int par1, int par2) {
		return getBiomeCacheBlock(par1, par2).getBiomeGenAt(par1, par2);
	}

	public void cleanupCache() {
		long l = System.currentTimeMillis();
		long l1 = l - lastCleanupTime;

		if (l1 > 7500L || l1 < 0L) {
			lastCleanupTime = l;

			for (int i = 0; i < cache.size(); i++) {
				BiomeCacheBlock biomecacheblock = (BiomeCacheBlock)cache.get(i);
				long l2 = l - biomecacheblock.lastAccessTime;

				if (l2 > 30000L || l2 < 0L) {
					cache.remove(i--);
					long l3 = (long)biomecacheblock.xPosition & 0xffffffffL | ((long)biomecacheblock.zPosition & 0xffffffffL) << 32;
					cacheMap.remove(l3);
				}
			}
		}
	}

	public BiomeGenBase[] getCachedBiomes(int par1, int par2) {
		return getBiomeCacheBlock(par1, par2).biomes;
	}

	static WorldChunkManager getChunkManager(BiomeCache par0BiomeCache) {
		return par0BiomeCache.chunkManager;
	}
}
