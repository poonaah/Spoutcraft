package net.minecraft.src;

import java.util.Random;

public class BlockGlass extends BlockBreakable {
	public BlockGlass(int par1, int par2, Material par3Material, boolean par4) {
		super(par1, par2, par3Material, par4);
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	protected boolean func_50074_q() {
		return true;
	}
}
