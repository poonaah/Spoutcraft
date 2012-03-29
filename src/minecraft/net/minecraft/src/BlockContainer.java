package net.minecraft.src;

public abstract class BlockContainer extends Block {
	protected BlockContainer(int par1, Material par2Material) {
		super(par1, par2Material);
		isBlockContainer = true;
	}

	protected BlockContainer(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		isBlockContainer = true;
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
		par1World.setBlockTileEntity(par2, par3, par4, getBlockEntity());
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		super.onBlockRemoval(par1World, par2, par3, par4);
		par1World.removeBlockTileEntity(par2, par3, par4);
	}

	public abstract TileEntity getBlockEntity();

	public void powerBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
		super.powerBlock(par1World, par2, par3, par4, par5, par6);
		TileEntity tileentity = par1World.getBlockTileEntity(par2, par3, par4);

		if (tileentity != null) {
			tileentity.onTileEntityPowered(par5, par6);
		}
	}
}
