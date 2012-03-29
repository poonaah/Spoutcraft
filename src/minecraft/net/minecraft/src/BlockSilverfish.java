package net.minecraft.src;

import java.util.Random;

public class BlockSilverfish extends Block {
	public BlockSilverfish(int par1) {
		super(par1, 1, Material.clay);
		setHardness(0.0F);
	}

	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
	}

	public int getBlockTextureFromSideAndMetadata(int par1, int par2) {
		if (par2 == 1) {
			return Block.cobblestone.blockIndexInTexture;
		}

		if (par2 == 2) {
			return Block.stoneBrick.blockIndexInTexture;
		} else {
			return Block.stone.blockIndexInTexture;
		}
	}

	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {
		if (!par1World.isRemote) {
			EntitySilverfish entitysilverfish = new EntitySilverfish(par1World);
			entitysilverfish.setLocationAndAngles((double)par2 + 0.5D, par3, (double)par4 + 0.5D, 0.0F, 0.0F);
			par1World.spawnEntityInWorld(entitysilverfish);
			entitysilverfish.spawnExplosionParticle();
		}

		super.onBlockDestroyedByPlayer(par1World, par2, par3, par4, par5);
	}

	public int quantityDropped(Random par1Random) {
		return 0;
	}

	public static boolean getPosingIdByMetadata(int par0) {
		return par0 == Block.stone.blockID || par0 == Block.cobblestone.blockID || par0 == Block.stoneBrick.blockID;
	}

	public static int getMetadataForBlockType(int par0) {
		if (par0 == Block.cobblestone.blockID) {
			return 1;
		}

		return par0 != Block.stoneBrick.blockID ? 0 : 2;
	}

	protected ItemStack createStackedBlock(int par1) {
		Block block = Block.stone;

		if (par1 == 1) {
			block = Block.cobblestone;
		}

		if (par1 == 2) {
			block = Block.stoneBrick;
		}

		return new ItemStack(block);
	}
}
