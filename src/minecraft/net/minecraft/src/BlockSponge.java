package net.minecraft.src;

public class BlockSponge extends Block {
	protected BlockSponge(int par1) {
		super(par1, Material.sponge);
		blockIndexInTexture = 48;
	}

	public void onBlockAdded(World world, int i, int j, int k) {
	}

	public void onBlockRemoval(World world, int i, int j, int k) {
	}
}
