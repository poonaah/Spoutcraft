package net.minecraft.src;

import java.util.Random;

public class BlockMelon extends Block {
	protected BlockMelon(int par1) {
		super(par1, Material.pumpkin);
		blockIndexInTexture = 136;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return par1 != 1 && par1 != 0 ? 136 : 137;
	}

	public int getBlockTextureFromSide(int par1) {
		return par1 != 1 && par1 != 0 ? 136 : 137;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.melon.shiftedIndex;
	}

	public int quantityDropped(Random par1Random) {
		return 3 + par1Random.nextInt(5);
	}

	public int quantityDroppedWithBonus(int par1, Random par2Random) {
		int i = quantityDropped(par2Random) + par2Random.nextInt(1 + par1);

		if (i > 9) {
			i = 9;
		}

		return i;
	}
}
