package net.minecraft.src;

import java.util.Random;

public class BlockLockedChest extends Block {
	protected BlockLockedChest(int par1) {
		super(par1, Material.wood);
		blockIndexInTexture = 26;
	}

	public int getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par5 == 1) {
			return blockIndexInTexture - 1;
		}

		if (par5 == 0) {
			return blockIndexInTexture - 1;
		}

		int i = par1IBlockAccess.getBlockId(par2, par3, par4 - 1);
		int j = par1IBlockAccess.getBlockId(par2, par3, par4 + 1);
		int k = par1IBlockAccess.getBlockId(par2 - 1, par3, par4);
		int l = par1IBlockAccess.getBlockId(par2 + 1, par3, par4);
		byte byte0 = 3;

		if (Block.opaqueCubeLookup[i] && !Block.opaqueCubeLookup[j]) {
			byte0 = 3;
		}

		if (Block.opaqueCubeLookup[j] && !Block.opaqueCubeLookup[i]) {
			byte0 = 2;
		}

		if (Block.opaqueCubeLookup[k] && !Block.opaqueCubeLookup[l]) {
			byte0 = 5;
		}

		if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[k]) {
			byte0 = 4;
		}

		return par5 != byte0 ? blockIndexInTexture : blockIndexInTexture + 1;
	}

	public int getBlockTextureFromSide(int par1) {
		if (par1 == 1) {
			return blockIndexInTexture - 1;
		}

		if (par1 == 0) {
			return blockIndexInTexture - 1;
		}

		if (par1 == 3) {
			return blockIndexInTexture + 1;
		} else {
			return blockIndexInTexture;
		}
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int i) {
		return true;
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		par1World.setBlockWithNotify(par2, par3, par4, 0);
	}
}
