package net.minecraft.src;

import java.util.Random;

public class BlockRedstoneLight extends Block {
	private final boolean powered;

	public BlockRedstoneLight(int par1, boolean par2) {
		super(par1, 211, Material.redstoneLight);
		powered = par2;

		if (par2) {
			setLightValue(1.0F);
			blockIndexInTexture++;
		}
	}

	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		if (!par1World.isRemote) {
			if (powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, 4);
			} else if (!powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
				par1World.setBlockWithNotify(par2, par3, par4, Block.redstoneLampActive.blockID);
			}
		}
	}

	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
		if (!par1World.isRemote) {
			if (powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
				par1World.scheduleBlockUpdate(par2, par3, par4, blockID, 4);
			} else if (!powered && par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
				par1World.setBlockWithNotify(par2, par3, par4, Block.redstoneLampActive.blockID);
			}
		}
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		if (!par1World.isRemote && powered && !par1World.isBlockIndirectlyGettingPowered(par2, par3, par4)) {
			par1World.setBlockWithNotify(par2, par3, par4, Block.redstoneLampIdle.blockID);
		}
	}

	public int idDropped(int par1, Random par2Random, int par3) {
		return Block.redstoneLampIdle.blockID;
	}
}
