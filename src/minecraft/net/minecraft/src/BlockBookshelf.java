package net.minecraft.src;

import java.util.Random;

public class BlockBookshelf extends Block {
	public BlockBookshelf(int par1, int par2) {
		super(par1, par2, Material.wood);
	}

	public int getBlockTextureFromSide(int par1) {
		if (par1 <= 1) {
			return 4;
		} else {
			return blockIndexInTexture;
		}
	}

	public int quantityDropped(Random par1Random) {
		return 3;
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.book.shiftedIndex;
	}
}
