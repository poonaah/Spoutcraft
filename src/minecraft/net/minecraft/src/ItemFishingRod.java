package net.minecraft.src;

import java.util.Random;

public class ItemFishingRod extends Item {
	public ItemFishingRod(int par1) {
		super(par1);
		setMaxDamage(64);
		setMaxStackSize(1);
	}

	public boolean isFull3D() {
		return true;
	}

	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if (par3EntityPlayer.fishEntity != null) {
			int i = par3EntityPlayer.fishEntity.catchFish();
			par1ItemStack.damageItem(i, par3EntityPlayer);
			par3EntityPlayer.swingItem();
		} else {
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!par2World.isRemote) {
				par2World.spawnEntityInWorld(new EntityFishHook(par2World, par3EntityPlayer));
			}

			par3EntityPlayer.swingItem();
		}

		return par1ItemStack;
	}
}
