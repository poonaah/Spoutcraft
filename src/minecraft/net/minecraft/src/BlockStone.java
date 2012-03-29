package net.minecraft.src;

import java.util.Random;

public class BlockStone extends Block {
	public BlockStone(int par1, int par2) {
		super(par1, par2, Material.rock);
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.cobblestone.blockID;
	}
}
