package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class BlockPistonExtension extends Block {
	private int headTexture;

	public BlockPistonExtension(int par1, int par2) {
		super(par1, par2, Material.piston);
		headTexture = -1;
		setStepSound(soundStoneFootstep);
		setHardness(0.5F);
	}

	public void setHeadTexture(int par1) {
		headTexture = par1;
	}

	public void clearHeadTexture() {
		headTexture = -1;
	}

	public void onBlockRemoval(World par1World, int par2, int par3, int par4) {
		super.onBlockRemoval(par1World, par2, par3, par4);
		int i = par1World.getBlockMetadata(par2, par3, par4);
		int k = Facing.faceToSide[getDirectionMeta(i)];
		par2 += Facing.offsetsXForSide[k];
		par3 += Facing.offsetsYForSide[k];
		par4 += Facing.offsetsZForSide[k];
		int l = par1World.getBlockId(par2, par3, par4);

		if (l == Block.pistonBase.blockID || l == Block.pistonStickyBase.blockID) {
			int j = par1World.getBlockMetadata(par2, par3, par4);

			if (BlockPistonBase.isExtended(j)) {
				Block.blocksList[l].dropBlockAsItem(par1World, par2, par3, par4, j, 0);
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		}
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		int i = getDirectionMeta(par2);

		if (par1 == i) {
			if (headTexture >= 0) {
				return headTexture;
			}

			if ((par2 & 8) != 0) {
				return blockIndexInTexture - 1;
			} else {
				return blockIndexInTexture;
			}
		}

		return par1 != Facing.faceToSide[i] ? 108 : 107;
	}

	public int getRenderType() {
		return 17;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int i) {
		return false;
	}

	public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int i, int j) {
		return false;
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public void getCollidingBoundingBoxes(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, ArrayList par6ArrayList) {
		int i = par1World.getBlockMetadata(par2, par3, par4);

		switch (getDirectionMeta(i)) {
			case 0:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				break;

			case 1:
				setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				break;

			case 2:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				break;

			case 3:
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				break;

			case 4:
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				break;

			case 5:
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
				super.getCollidingBoundingBoxes(par1World, par2, par3, par4, par5AxisAlignedBB, par6ArrayList);
				break;
		}

		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		int i = par1IBlockAccess.getBlockMetadata(par2, par3, par4);

		switch (getDirectionMeta(i)) {
			case 0:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
				break;

			case 1:
				setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;

			case 2:
				setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
				break;

			case 3:
				setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
				break;

			case 4:
				setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
				break;

			case 5:
				setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
				break;
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		int i = getDirectionMeta(par1World.getBlockMetadata(par2, par3, par4));
		int j = par1World.getBlockId(par2 - Facing.offsetsXForSide[i], par3 - Facing.offsetsYForSide[i], par4 - Facing.offsetsZForSide[i]);

		if (j != Block.pistonBase.blockID && j != Block.pistonStickyBase.blockID) {
			par1World.setBlockWithNotify(par2, par3, par4, 0);
		} else {
			Block.blocksList[j].onNeighborBlockChange(par1World, par2 - Facing.offsetsXForSide[i], par3 - Facing.offsetsYForSide[i], par4 - Facing.offsetsZForSide[i], par5);
		}
	}

	public static int getDirectionMeta(int par0) {
		return par0 & 7;
	}
}
