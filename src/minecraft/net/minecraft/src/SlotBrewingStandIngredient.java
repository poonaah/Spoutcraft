package net.minecraft.src;

class SlotBrewingStandIngredient extends Slot {
	final ContainerBrewingStand container;

	public SlotBrewingStandIngredient(ContainerBrewingStand par1ContainerBrewingStand, IInventory par2IInventory, int par3, int par4, int par5) {
		super(par2IInventory, par3, par4, par5);
		container = par1ContainerBrewingStand;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack != null) {
			return Item.itemsList[par1ItemStack.itemID].isPotionIngredient();
		} else {
			return false;
		}
	}

	public int getSlotStackLimit() {
		return 64;
	}
}
