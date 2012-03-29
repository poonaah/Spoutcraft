package net.minecraft.src;

public class BlockStoneBrick extends Block {
	public BlockStoneBrick(int par1) {
		super(par1, 54, Material.rock);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		switch (par2) {
			default:
				return 54;

			case 1:
				return 100;

			case 2:
				return 101;

			case 3:
				return 213;
		}
	}

	protected int damageDropped(int par1) {
		return par1;
	}
}
