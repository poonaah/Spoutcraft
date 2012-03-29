package net.minecraft.src;

import java.util.Random;

public class BlockMushroomCap extends Block {
	private int mushroomType;

	public BlockMushroomCap(int par1, Material par2Material, int par3, int par4) {
		super(par1, par3, par2Material);
		mushroomType = par4;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par2 == 10 && par1 > 1) {
			return blockIndexInTexture - 1;
		}

		if (par2 >= 1 && par2 <= 9 && par1 == 1) {
			return blockIndexInTexture - 16 - mushroomType;
		}

		if (par2 >= 1 && par2 <= 3 && par1 == 2) {
			return blockIndexInTexture - 16 - mushroomType;
		}

		if (par2 >= 7 && par2 <= 9 && par1 == 3) {
			return blockIndexInTexture - 16 - mushroomType;
		}

		if ((par2 == 1 || par2 == 4 || par2 == 7) && par1 == 4) {
			return blockIndexInTexture - 16 - mushroomType;
		}

		if ((par2 == 3 || par2 == 6 || par2 == 9) && par1 == 5) {
			return blockIndexInTexture - 16 - mushroomType;
		}

		if (par2 == 14) {
			return blockIndexInTexture - 16 - mushroomType;
		}

		if (par2 == 15) {
			return blockIndexInTexture - 1;
		} else {
			return blockIndexInTexture;
		}
	}

	public int quantityDropped(Random par1Random) {
		int i = par1Random.nextInt(10) - 7;

		if (i < 0) {
			i = 0;
		}

		return i;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.mushroomBrown.blockID + mushroomType;
	}
}
