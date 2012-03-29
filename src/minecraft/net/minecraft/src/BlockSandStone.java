package net.minecraft.src;

public class BlockSandStone extends Block {
	public BlockSandStone(int par1) {
		super(par1, 192, Material.rock);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 1 || par1 == 0 && (par2 == 1 || par2 == 2)) {
			return 176;
		}

		if (par1 == 0) {
			return 208;
		}

		if (par2 == 1) {
			return 229;
		}

		return par2 != 2 ? 192 : 230;
	}

	public int getBlockTextureFromSide(int par1) {
		if (par1 == 1) {
			return blockIndexInTexture - 16;
		}

		if (par1 == 0) {
			return blockIndexInTexture + 16;
		} else {
			return blockIndexInTexture;
		}
	}

	protected int damageDropped(int par1) {
		return par1;
	}
}
