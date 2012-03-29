package net.minecraft.src;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer {
	protected BlockMobSpawner(int par1, int par2) {
		super(par1, par2, Material.rock);
	}

	public TileEntity getBlockEntity() {
		return new TileEntityMobSpawner();
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return 0;
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public boolean isOpaqueCube() {
		return false;
	}
}
