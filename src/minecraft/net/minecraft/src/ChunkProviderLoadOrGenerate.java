package net.minecraft.src;

import java.io.IOException;
import java.util.List;

public class ChunkProviderLoadOrGenerate implements IChunkProvider {
	private Chunk blankChunk;
	private IChunkProvider chunkProvider;
	private IChunkLoader chunkLoader;
	private Chunk chunks[];
	private World worldObj;
	int lastQueriedChunkXPos;
	int lastQueriedChunkZPos;
	private Chunk lastQueriedChunk;
	private int curChunkX;
	private int curChunkY;

	public void setCurrentChunkOver(int par1, int par2) {
		curChunkX = par1;
		curChunkY = par2;
	}

	public boolean canChunkExist(int par1, int par2) {
		byte byte0 = 15;
		return par1 >= curChunkX - byte0 && par2 >= curChunkY - byte0 && par1 <= curChunkX + byte0 && par2 <= curChunkY + byte0;
	}

	public boolean chunkExists(int par1, int par2) {
		if (!canChunkExist(par1, par2)) {
			return false;
		}

		if (par1 == lastQueriedChunkXPos && par2 == lastQueriedChunkZPos && lastQueriedChunk != null) {
			return true;
		} else {
			int i = par1 & 0x1f;
			int j = par2 & 0x1f;
			int k = i + j * 32;
			return chunks[k] != null && (chunks[k] == blankChunk || chunks[k].isAtLocation(par1, par2));
		}
	}

	public Chunk loadChunk(int par1, int par2) {
		return provideChunk(par1, par2);
	}

	public Chunk provideChunk(int par1, int par2) {
		if (par1 == lastQueriedChunkXPos && par2 == lastQueriedChunkZPos && lastQueriedChunk != null) {
			return lastQueriedChunk;
		}

		if (!worldObj.findingSpawnPoint && !canChunkExist(par1, par2)) {
			return blankChunk;
		}

		int i = par1 & 0x1f;
		int j = par2 & 0x1f;
		int k = i + j * 32;

		if (!chunkExists(par1, par2)) {
			if (chunks[k] != null) {
				chunks[k].onChunkUnload();
				saveChunk(chunks[k]);
				saveExtraChunkData(chunks[k]);
			}

			Chunk chunk = func_542_c(par1, par2);

			if (chunk == null) {
				if (chunkProvider == null) {
					chunk = blankChunk;
				} else {
					chunk = chunkProvider.provideChunk(par1, par2);
					chunk.removeUnknownBlocks();
				}
			}

			chunks[k] = chunk;
			chunk.func_4143_d();

			if (chunks[k] != null) {
				chunks[k].onChunkLoad();
			}

			chunks[k].populateChunk(this, this, par1, par2);
		}

		lastQueriedChunkXPos = par1;
		lastQueriedChunkZPos = par2;
		lastQueriedChunk = chunks[k];
		return chunks[k];
	}

	private Chunk func_542_c(int par1, int par2) {
		if (chunkLoader == null) {
			return blankChunk;
		}

		try {
			Chunk chunk = chunkLoader.loadChunk(worldObj, par1, par2);

			if (chunk != null) {
				chunk.lastSaveTime = worldObj.getWorldTime();
			}

			return chunk;
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return blankChunk;
	}

	private void saveExtraChunkData(Chunk par1Chunk) {
		if (chunkLoader == null) {
			return;
		}

		try {
			chunkLoader.saveExtraChunkData(worldObj, par1Chunk);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void saveChunk(Chunk par1Chunk) {
		if (chunkLoader == null) {
			return;
		}

		try {
			par1Chunk.lastSaveTime = worldObj.getWorldTime();
			chunkLoader.saveChunk(worldObj, par1Chunk);
		} catch (IOException ioexception) {
			ioexception.printStackTrace();
		}
	}

	public void populate(IChunkProvider par1IChunkProvider, int par2, int par3) {
		Chunk chunk = provideChunk(par2, par3);

		if (!chunk.isTerrainPopulated) {
			chunk.isTerrainPopulated = true;

			if (chunkProvider != null) {
				chunkProvider.populate(par1IChunkProvider, par2, par3);
				chunk.setChunkModified();
			}
		}
	}

	public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate) {
		int i = 0;
		int j = 0;

		if (par2IProgressUpdate != null) {
			for (int k = 0; k < chunks.length; k++) {
				if (chunks[k] != null && chunks[k].needsSaving(par1)) {
					j++;
				}
			}
		}

		int l = 0;

		for (int i1 = 0; i1 < chunks.length; i1++) {
			if (chunks[i1] == null) {
				continue;
			}

			if (par1) {
				saveExtraChunkData(chunks[i1]);
			}

			if (!chunks[i1].needsSaving(par1)) {
				continue;
			}

			saveChunk(chunks[i1]);
			chunks[i1].isModified = false;

			if (++i == 2 && !par1) {
				return false;
			}

			if (par2IProgressUpdate != null && ++l % 10 == 0) {
				par2IProgressUpdate.setLoadingProgress((l * 100) / j);
			}
		}

		if (par1) {
			if (chunkLoader == null) {
				return true;
			}

			chunkLoader.saveExtraData();
		}

		return true;
	}

	public boolean unload100OldestChunks() {
		if (chunkLoader != null) {
			chunkLoader.chunkTick();
		}

		return chunkProvider.unload100OldestChunks();
	}

	public boolean canSave() {
		return true;
	}

	public String makeString() {
		return (new StringBuilder()).append("ChunkCache: ").append(chunks.length).toString();
	}

	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4) {
		return chunkProvider.getPossibleCreatures(par1EnumCreatureType, par2, par3, par4);
	}

	public ChunkPosition findClosestStructure(World par1World, String par2Str, int par3, int par4, int par5) {
		return chunkProvider.findClosestStructure(par1World, par2Str, par3, par4, par5);
	}
}
