package net.minecraft.src;

public class BlockWood extends Block {
	public BlockWood(int par1) {
		super(par1, 4, Material.wood);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		switch (par2) {
			default:
				return 4;

			case 1:
				return 198;

			case 2:
				return 214;

			case 3:
				return 199;
		}
	}

	protected int damageDropped(int par1) {
		return par1;
	}
}
