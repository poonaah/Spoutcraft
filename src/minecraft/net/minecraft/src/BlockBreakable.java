package net.minecraft.src;

public class BlockBreakable extends Block {
	private boolean localFlag;

	protected BlockBreakable(int par1, int par2, Material par3Material, boolean par4) {
		super(par1, par2, par3Material);
		localFlag = par4;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		int i = par1IBlockAccess.getBlockId(par2, par3, par4);

		if (!localFlag && i == blockID) {
			return false;
		} else {
			return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		}
	}
}
