package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockStairs extends Block {
	private Block modelBlock;

	protected BlockStairs(int par1, Block par2Block) {
		super(par1, par2Block.blockIndexInTexture, par2Block.blockMaterial);
		modelBlock = par2Block;
		setHardness(par2Block.blockHardness);
		setResistance(par2Block.blockResistance / 3F);
		setStepSound(par2Block.stepSound);
		setLightOpacity(255);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 10;
	}

	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
		return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
	}

	public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		int j = i & 3;
		float f = 0.0F;
		float f1 = 0.5F;
		float f2 = 0.5F;
		float f3 = 1.0F;

		if ((i & 4) != 0) {
			f = 0.5F;
			f1 = 1.0F;
			f2 = 0.0F;
			f3 = 0.5F;
		}

		setBlockBounds(0.0F, f, 0.0F, 1.0F, f1, 1.0F);
		super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);

		if (j == 0) {
			setBlockBounds(0.5F, f2, 0.0F, 1.0F, f3, 1.0F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (j == 1) {
			setBlockBounds(0.0F, f2, 0.0F, 0.5F, f3, 1.0F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (j == 2) {
			setBlockBounds(0.0F, f2, 0.5F, 1.0F, f3, 1.0F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		} else if (j == 3) {
			setBlockBounds(0.0F, f2, 0.0F, 1.0F, f3, 0.5F);
			super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
		}

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		modelBlock.randomDisplayTick(par1World, par2, par3, par4, par5Random);
	}

	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		modelBlock.onBlockClicked(par1World, par2, par3, par4, par5EntityPlayer);
	}

	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		modelBlock.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}

	public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return modelBlock.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
	}

	public float getBlockBrightness(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		return modelBlock.getBlockBrightness(par1IBlockAccess, par2, par3, par4);
	}

	public float getExplosionResistance(Entity par1Entity) {
		return modelBlock.getExplosionResistance(par1Entity);
	}

	public int getRenderBlockPass() {
		return modelBlock.getRenderBlockPass();
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return modelBlock.getBlockTextureFromSideAndMetadata(par1, 0);
	}

	public int getBlockTextureFromSide(int par1) {
		return modelBlock.getBlockTextureFromSideAndMetadata(par1, 0);
	}

	public int tickRate() {
		return modelBlock.tickRate();
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
		return modelBlock.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
	}

	public void velocityToAddToEntity(World par1World, int par2, int par3, int par4, Entity par5Entity, Vec3D par6Vec3D) {
		modelBlock.velocityToAddToEntity(par1World, par2, par3, par4, par5Entity, par6Vec3D);
	}

	public boolean isCollidable() {
		return modelBlock.isCollidable();
	}

	public boolean canCollideCheck(int par1, boolean par2) {
		return modelBlock.canCollideCheck(par1, par2);
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4) {
		return modelBlock.canPlaceBlockAt(par1World, par2, par3, par4);
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		onNeighborBlockChange(par1World, par2, par3, par4, 0);
		modelBlock.onBlockAdded(par1World, par2, par3, par4);
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		modelBlock.onBlockRemoval(par1World, par2, par3, par4);
	}

	public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		modelBlock.onEntityWalking(par1World, par2, par3, par4, par5Entity);
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		modelBlock.updateTick(par1World, par2, par3, par4, par5Random);
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		return modelBlock.blockActivated(par1World, par2, par3, par4, par5EntityPlayer);
	}

	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4) {
		modelBlock.onBlockDestroyedByExplosion(par1World, par2, par3, par4);
	}

	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving) {
		int i = MathHelper.floor_double((double)((par5EntityLiving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
		int j = par1World.getBlockMetadata(par2, par3, par4) & 4;

		if (i == 0) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2 | j);
		}

		if (i == 1) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 1 | j);
		}

		if (i == 2) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3 | j);
		}

		if (i == 3) {
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 0 | j);
		}
	}

	public void onBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {
		if (par5 == 0) {
			int i = par1World.getBlockMetadata(par2, par3, par4);
			par1World.setBlockMetadataWithNotify(par2, par3, par4, i | 4);
		}
	}
}
