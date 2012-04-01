package net.minecraft.src;

import java.util.Random;

public class BlockEnchantmentTable extends BlockContainer {
	protected BlockEnchantmentTable(int par1) {
		super(par1, 166, Material.rock);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
		setLightOpacity(0);
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		for (int i = par2 - 2; i <= par2 + 2; i++) {
			for (int j = par4 - 2; j <= par4 + 2; j++) {
				if (i > par2 - 2 && i < par2 + 2 && j == par4 - 1) {
					j = par4 + 2;
				}

				if (par5Random.nextInt(16) != 0) {
					continue;
				}

				for (int k = par3; k <= par3 + 1; k++) {
					if (par1World.getBlockId(i, k, j) != Block.bookShelf.blockID) {
						continue;
					}

					if (!par1World.isAirBlock((i - par2) / 2 + par2, k, (j - par4) / 2 + par4)) {
						break;
					}

					par1World.spawnParticle("enchantmenttable", (double)par2 + 0.5D, (double)par3 + 2D, (double)par4 + 0.5D, (double)((float)(i - par2) + par5Random.nextFloat()) - 0.5D, (float)(k - par3) - par5Random.nextFloat() - 1.0F, (double)((float)(j - par4) + par5Random.nextFloat()) - 0.5D);
				}
			}
		}
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		return getBlockTextureFromSide(par1);
	}

	public int getBlockTextureFromSide(int par1) {
		if (par1 == 0) {
			return blockIndexInTexture + 17;
		}

		if (par1 == 1) {
			return blockIndexInTexture;
		} else {
			return blockIndexInTexture + 16;
		}
	}

	public TileEntity getBlockEntity() {
		return new TileEntityEnchantmentTable();
	}

	public boolean blockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		if (par1World.isRemote) {
			return true;
		} else {
			par5EntityPlayer.displayGUIEnchantment(par2, par3, par4);
			return true;
		}
	}
}