package net.minecraft.src;

import java.util.Random;

public class BlockLog extends Block {
	protected BlockLog(int par1) {
		super(par1, Material.wood);
		blockIndexInTexture = 20;
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.wood.blockID;
	}

	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		byte byte0 = 4;
		int i = byte0 + 1;

		if (par1World.checkChunksExist(par2 - i, par3 - i, par4 - i, par2 + i, par3 + i, par4 + i)) {
			for (int j = -byte0; j <= byte0; j++) {
				for (int k = -byte0; k <= byte0; k++) {
					for (int l = -byte0; l <= byte0; l++) {
						int i1 = par1World.getBlockId(par2 + j, par3 + k, par4 + l);

						if (i1 != Block.leaves.blockID) {
							continue;
						}

						int j1 = par1World.getBlockMetadata(par2 + j, par3 + k, par4 + l);

						if ((j1 & 8) == 0) {
							par1World.setBlockMetadata(par2 + j, par3 + k, par4 + l, j1 | 8);
						}
					}
				}
			}
		}
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1) {
			return 21;
		}

		if (par1 == 0) {
			return 21;
		}

		if (par2 == 1) {
			return 116;
		}

		if (par2 == 2) {
			return 117;
		}

		return par2 != 3 ? 20 : 153;
	}

	protected int damageDropped(int par1) {
		return par1;
	}
}
