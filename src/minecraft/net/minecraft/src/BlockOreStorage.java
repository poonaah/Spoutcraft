package net.minecraft.src;

public class BlockOreStorage extends Block {
	public BlockOreStorage(int par1, int par2) {
		super(par1, Material.iron);
		blockIndexInTexture = par2;
	}

	public int getBlockTextureFromSide(int par1) {
		return blockIndexInTexture;
	}
}
