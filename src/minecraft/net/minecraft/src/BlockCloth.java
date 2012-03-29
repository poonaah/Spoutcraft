package net.minecraft.src;

public class BlockCloth extends Block {
	public BlockCloth() {
		super(35, 64, Material.cloth);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par2 == 0) {
			return blockIndexInTexture;
		} else {
			par2 = ~(par2 & 0xf);
			return 113 + ((par2 & 8) >> 3) + (par2 & 7) * 16;
		}
	}

	protected int damageDropped(int par1) {
		return par1;
	}

	public static int getBlockFromDye(int par0) {
		return ~par0 & 0xf;
	}

	public static int getDyeFromBlock(int par0) {
		return ~par0 & 0xf;
	}
}
