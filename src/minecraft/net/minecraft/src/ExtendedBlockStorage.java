package net.minecraft.src;

public class ExtendedBlockStorage {
	private int yBase;
	private int blockRefCount;
	private int tickRefCount;
	private byte blockLSBArray[];
	private NibbleArray blockMSBArray;
	private NibbleArray blockMetadataArray;
	private NibbleArray blocklightArray;
	private NibbleArray skylightArray;

	public ExtendedBlockStorage(int par1) {
		yBase = par1;
		blockLSBArray = new byte[4096];
		blockMetadataArray = new NibbleArray(blockLSBArray.length, 4);
		skylightArray = new NibbleArray(blockLSBArray.length, 4);
		blocklightArray = new NibbleArray(blockLSBArray.length, 4);
	}

	public int getExtBlockID(int par1, int par2, int par3) {
		int i = blockLSBArray[par2 << 8 | par3 << 4 | par1] & 0xff;

		if (blockMSBArray != null) {
			return blockMSBArray.get(par1, par2, par3) << 8 | i;
		} else {
			return i;
		}
	}

	public void setExtBlockID(int par1, int par2, int par3, int par4) {
		int i = blockLSBArray[par2 << 8 | par3 << 4 | par1] & 0xff;

		if (blockMSBArray != null) {
			i = blockMSBArray.get(par1, par2, par3) << 8 | i;
		}

		if (i == 0 && par4 != 0) {
			blockRefCount++;

			if (Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly()) {
				tickRefCount++;
			}
		} else if (i != 0 && par4 == 0) {
			blockRefCount--;

			if (Block.blocksList[i] != null && Block.blocksList[i].getTickRandomly()) {
				tickRefCount--;
			}
		} else if (Block.blocksList[i] != null && Block.blocksList[i].getTickRandomly() && (Block.blocksList[par4] == null || !Block.blocksList[par4].getTickRandomly())) {
			tickRefCount--;
		} else if ((Block.blocksList[i] == null || !Block.blocksList[i].getTickRandomly()) && Block.blocksList[par4] != null && Block.blocksList[par4].getTickRandomly()) {
			tickRefCount++;
		}

		blockLSBArray[par2 << 8 | par3 << 4 | par1] = (byte)(par4 & 0xff);

		if (par4 > 255) {
			if (blockMSBArray == null) {
				blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
			}

			blockMSBArray.set(par1, par2, par3, (par4 & 0xf00) >> 8);
		} else if (blockMSBArray != null) {
			blockMSBArray.set(par1, par2, par3, 0);
		}
	}

	public int getExtBlockMetadata(int par1, int par2, int par3) {
		return blockMetadataArray.get(par1, par2, par3);
	}

	public void setExtBlockMetadata(int par1, int par2, int par3, int par4) {
		blockMetadataArray.set(par1, par2, par3, par4);
	}

	public boolean getIsEmpty() {
		return blockRefCount == 0;
	}

	public boolean getNeedsRandomTick() {
		return tickRefCount > 0;
	}

	public int getYLocation() {
		return yBase;
	}

	public void setExtSkylightValue(int par1, int par2, int par3, int par4) {
		skylightArray.set(par1, par2, par3, par4);
	}

	public int getExtSkylightValue(int par1, int par2, int par3) {
		return skylightArray.get(par1, par2, par3);
	}

	public void setExtBlocklightValue(int par1, int par2, int par3, int par4) {
		blocklightArray.set(par1, par2, par3, par4);
	}

	public int getExtBlocklightValue(int par1, int par2, int par3) {
		return blocklightArray.get(par1, par2, par3);
	}

	public void func_48708_d() {
		blockRefCount = 0;
		tickRefCount = 0;

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				for (int k = 0; k < 16; k++) {
					int l = getExtBlockID(i, j, k);

					if (l <= 0) {
						continue;
					}

					if (Block.blocksList[l] == null) {
						blockLSBArray[j << 8 | k << 4 | i] = 0;

						if (blockMSBArray != null) {
							blockMSBArray.set(i, j, k, 0);
						}

						continue;
					}

					blockRefCount++;

					if (Block.blocksList[l].getTickRandomly()) {
						tickRefCount++;
					}
				}
			}
		}
	}

	public void func_48711_e() {
	}

	public int func_48700_f() {
		return blockRefCount;
	}

	public byte[] func_48692_g() {
		return blockLSBArray;
	}

	public void func_48715_h() {
		blockMSBArray = null;
	}

	public NibbleArray getBlockMSBArray() {
		return blockMSBArray;
	}

	public NibbleArray func_48697_j() {
		return blockMetadataArray;
	}

	public NibbleArray getBlocklightArray() {
		return blocklightArray;
	}

	public NibbleArray getSkylightArray() {
		return skylightArray;
	}

	public void setBlockLSBArray(byte par1ArrayOfByte[]) {
		blockLSBArray = par1ArrayOfByte;
	}

	public void setBlockMSBArray(NibbleArray par1NibbleArray) {
		blockMSBArray = par1NibbleArray;
	}

	public void setBlockMetadataArray(NibbleArray par1NibbleArray) {
		blockMetadataArray = par1NibbleArray;
	}

	public void setBlocklightArray(NibbleArray par1NibbleArray) {
		blocklightArray = par1NibbleArray;
	}

	public void setSkylightArray(NibbleArray par1NibbleArray) {
		skylightArray = par1NibbleArray;
	}

	public NibbleArray createBlockMSBArray() {
		blockMSBArray = new NibbleArray(blockLSBArray.length, 4);
		return blockMSBArray;
	}
}
