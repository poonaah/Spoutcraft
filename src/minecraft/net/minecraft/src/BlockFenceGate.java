package net.minecraft.src;

public class BlockFenceGate extends BlockDirectional {
	public BlockFenceGate(int par1, int par2) {
		super(par1, par2, Material.wood);
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		if (!par1World.getBlockMaterial(par2, par3 - 1, par4).isSolid()) {
			return false;
		} else {
			return super.canPlaceBlockAt(par1World, par2, par3, par4);
		}
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (isFenceGateOpen(i)) {
			return null;
		}

		if (i == 2 || i == 0) {
			return AxisAlignedBB.getBoundingBoxFromPool(par2, par3, (float)par4 + 0.375F, par2 + 1, (float)par3 + 1.5F, (float)par4 + 0.625F);
		} else {
			return AxisAlignedBB.getBoundingBoxFromPool((float)par2 + 0.375F, par3, par4, (float)par2 + 0.625F, (float)par3 + 1.5F, par4 + 1);
		}
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = getDirection(par1IBlockAccess.getBlockMetadata(par2, par3, par4));

		if (i == 2 || i == 0) {
			setBlockBounds(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
		} else {
			setBlockBounds(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return isFenceGateOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
	}

	public int getRenderType() {
		return 21;
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		int i = (MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3) % 4;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, i);
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (isFenceGateOpen(i)) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, i & -5);
		} else {
			int j = (MathHelper.floor_double((double)((par5EntityPlayer.rotationYaw * 4F) / 360F) + 0.5D) & 3) % 4;
			int k = getDirection(i);

			if (k == (j + 2) % 4) {
				i = j;
			}

			par1World.setBlockMetadataWithNotify(par2, par3, par4, i | 4);
		}

		par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
		return true;
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (par1World.isRemote) {
			return;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);
		boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);

		if (flag || par5 > 0 && Block.blocksList[par5].canProvidePower() || par5 == 0) {
			if (flag && !isFenceGateOpen(i)) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, i | 4);
				par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
			} else if (!flag && isFenceGateOpen(i)) {
				par1World.setBlockMetadataWithNotify(par2, par3, par4, i & -5);
				par1World.playAuxSFXAtEntity(null, 1003, par2, par3, par4, 0);
			}
		}
	}

	public static boolean isFenceGateOpen(int par0) {
		return (par0 & 4) != 0;
	}
}
