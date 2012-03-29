package net.minecraft.src;

import java.util.*;

public class BlockBed extends BlockDirectional {
	public static final int headBlockToFootBlockMap[][] = {
		{
			0, 1
		}, {
			-1, 0
		}, {
			0, -1
		}, {
			1, 0
		}
	};

	public BlockBed(int par1) {
		super(par1, 134, Material.cloth);
		setBounds();
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.isRemote) {
			return true;
		}

		int i = par1World.getBlockMetadata(par2, par3, par4);

		if (!isBlockFootOfBed(i)) {
			int j = getDirection(i);
			par2 += headBlockToFootBlockMap[j][0];
			par4 += headBlockToFootBlockMap[j][1];

			if (par1World.getBlockId(par2, par3, par4) != blockID) {
				return true;
			}

			i = par1World.getBlockMetadata(par2, par3, par4);
		}

		if (!par1World.worldProvider.canRespawnHere()) {
			double d = (double)par2 + 0.5D;
			double d1 = (double)par3 + 0.5D;
			double d2 = (double)par4 + 0.5D;
			par1World.setBlockWithNotify(par2, par3, par4, 0);
			int k = getDirection(i);
			par2 += headBlockToFootBlockMap[k][0];
			par4 += headBlockToFootBlockMap[k][1];

			if (par1World.getBlockId(par2, par3, par4) == blockID) {
				par1World.setBlockWithNotify(par2, par3, par4, 0);
				d = (d + (double)par2 + 0.5D) / 2D;
				d1 = (d1 + (double)par3 + 0.5D) / 2D;
				d2 = (d2 + (double)par4 + 0.5D) / 2D;
			}

			par1World.newExplosion(null, (float)par2 + 0.5F, (float)par3 + 0.5F, (float)par4 + 0.5F, 5F, true);
			return true;
		}

		if (isBedOccupied(i)) {
			EntityPlayer entityplayer = null;
			Iterator iterator = par1World.playerEntities.iterator();

			do {
				if (!iterator.hasNext()) {
					break;
				}

				EntityPlayer entityplayer1 = (EntityPlayer)iterator.next();

				if (entityplayer1.isPlayerSleeping()) {
					ChunkCoordinates chunkcoordinates = entityplayer1.playerLocation;

					if (chunkcoordinates.posX == par2 && chunkcoordinates.posY == par3 && chunkcoordinates.posZ == par4) {
						entityplayer = entityplayer1;
					}
				}
			} while (true);

			if (entityplayer == null) {
				setBedOccupied(par1World, par2, par3, par4, false);
			} else {
				par5EntityPlayer.addChatMessage("tile.bed.occupied");
				return true;
			}
		}

		EnumStatus enumstatus = par5EntityPlayer.sleepInBedAt(par2, par3, par4);

		if (enumstatus == EnumStatus.OK) {
			setBedOccupied(par1World, par2, par3, par4, true);
			return true;
		}

		if (enumstatus == EnumStatus.NOT_POSSIBLE_NOW) {
			par5EntityPlayer.addChatMessage("tile.bed.noSleep");
		} else if (enumstatus == EnumStatus.NOT_SAFE) {
			par5EntityPlayer.addChatMessage("tile.bed.notSafe");
		}

		return true;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par1 == 0) {
			return Block.planks.blockIndexInTexture;
		}

		int i = getDirection(par2);
		int j = Direction.bedDirection[i][par1];

		if (isBlockFootOfBed(par2)) {
			if (j == 2) {
				return blockIndexInTexture + 2 + 16;
			}

			if (j == 5 || j == 4) {
				return blockIndexInTexture + 1 + 16;
			} else {
				return blockIndexInTexture + 1;
			}
		}

		if (j == 3) {
			return (blockIndexInTexture - 1) + 16;
		}

		if (j == 5 || j == 4) {
			return blockIndexInTexture + 16;
		} else {
			return blockIndexInTexture;
		}
	}

	public int getRenderType() {
		return 14;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
		setBounds();
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		int i = par1World.getBlockMetadata(par2, par3, par4);
		int j = getDirection(i);

		if (isBlockFootOfBed(i)) {
			if (par1World.getBlockId(par2 - headBlockToFootBlockMap[j][0], par3, par4 - headBlockToFootBlockMap[j][1]) != blockID) {
				par1World.setBlockWithNotify(par2, par3, par4, 0);
			}
		} else if (par1World.getBlockId(par2 + headBlockToFootBlockMap[j][0], par3, par4 + headBlockToFootBlockMap[j][1]) != blockID) {
			par1World.setBlockWithNotify(par2, par3, par4, 0);

			if (!par1World.isRemote) {
				dropBlockAsItem(par1World, par2, par3, par4, i, 0);
			}
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		if (isBlockFootOfBed(par1)) {
			return 0;
		} else {
			return Item.bed.shiftedIndex;
		}
	}

	private void setBounds() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}

	public static boolean isBlockFootOfBed(int par0) {
		return (par0 & 8) != 0;
	}

	public static boolean isBedOccupied(int par0) {
		return (par0 & 4) != 0;
	}

	public static void setBedOccupied(World par0World, int par1, int par2, int par3, boolean par4) {
		int i = par0World.getBlockMetadata(par1, par2, par3);

		if (par4) {
			i |= 4;
		} else {
			i &= -5;
		}

		par0World.setBlockMetadataWithNotify(par1, par2, par3, i);
	}

	public static ChunkCoordinates getNearestEmptyChunkCoordinates(World par0World, int par1, int par2, int par3, int par4) {
		int i = par0World.getBlockMetadata(par1, par2, par3);
		int j = BlockDirectional.getDirection(i);

		for (int k = 0; k <= 1; k++) {
			int l = par1 - headBlockToFootBlockMap[j][0] * k - 1;
			int i1 = par3 - headBlockToFootBlockMap[j][1] * k - 1;
			int j1 = l + 2;
			int k1 = i1 + 2;

			for (int l1 = l; l1 <= j1; l1++) {
				for (int i2 = i1; i2 <= k1; i2++) {
					if (!par0World.isBlockNormalCube(l1, par2 - 1, i2) || !par0World.isAirBlock(l1, par2, i2) || !par0World.isAirBlock(l1, par2 + 1, i2)) {
						continue;
					}

					if (par4 > 0) {
						par4--;
					} else {
						return new ChunkCoordinates(l1, par2, i2);
					}
				}
			}
		}

		return null;
	}

	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (!isBlockFootOfBed(par5)) {
			super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
		}
	}

	public int getMobilityFlag() {
		return 1;
	}
}
