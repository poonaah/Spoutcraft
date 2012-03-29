package net.minecraft.src;

class SlotBrewingStandPotion extends Slot {
	private EntityPlayer player;
	final ContainerBrewingStand container;

	public SlotBrewingStandPotion(ContainerBrewingStand par1ContainerBrewingStand, EntityPlayer par2EntityPlayer, IInventory par3IInventory, int par4, int par5, int par6) {
		super(par3IInventory, par4, par5, par6);
		container = par1ContainerBrewingStand;
		player = par2EntityPlayer;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return par1ItemStack != null && (par1ItemStack.itemID == Item.potion.shiftedIndex || par1ItemStack.itemID == Item.glassBottle.shiftedIndex);
	}

	public int getSlotStackLimit() {
		return 1;
	}

	public void onPickupFromSlot(ItemStack par1ItemStack) {
		if (par1ItemStack.itemID == Item.potion.shiftedIndex && par1ItemStack.getItemDamage() > 0) {
			player.addStat(AchievementList.potion, 1);
		}

		super.onPickupFromSlot(par1ItemStack);
	}
}
