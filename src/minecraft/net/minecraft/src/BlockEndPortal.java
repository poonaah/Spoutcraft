package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockEndPortal extends BlockContainer {
	public static boolean bossDefeated = false;

	protected BlockEndPortal(int par1, Material par2Material) {
		super(par1, 0, par2Material);
		setLightValue(1.0F);
	}

	public TileEntity getBlockEntity() {
		return new TileEntityEndPortal();
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		float f = 0.0625F;
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		if (par5 != 0) {
			return false;
		} else {
			return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
		}
	}

	public void getCollidingBoundingBoxes(World world, int i, int j, int k, AxisAlignedBB axisalignedbb, ArrayList arraylist) {
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && (par5Entity instanceof EntityPlayer) && !par1World.isRemote) {
			((EntityPlayer)par5Entity).travelToTheEnd(1);
		}
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		double d = (float)par2 + par5Random.nextFloat();
		double d1 = (float)par3 + 0.8F;
		double d2 = (float)par4 + par5Random.nextFloat();
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d5 = 0.0D;
		par1World.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
	}

	public int getRenderType() {
		return -1;
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (bossDefeated) {
			return;
		}

		if (par1World.worldProvider.worldType != 0) {
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			return;
		} else {
			return;
		}
	}
}
