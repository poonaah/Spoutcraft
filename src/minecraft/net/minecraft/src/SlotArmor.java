package net.minecraft.src;

class SlotArmor extends Slot {
	final int armorType;
	final ContainerPlayer parent;

	SlotArmor(ContainerPlayer par1ContainerPlayer, IInventory par2IInventory, int par3, int par4, int par5, int par6) {
		super(par2IInventory, par3, par4, par5);
		parent = par1ContainerPlayer;
		armorType = par6;
	}

	public int getSlotStackLimit() {
		return 1;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		if (par1ItemStack.getItem() instanceof ItemArmor) {
			return ((ItemArmor)par1ItemStack.getItem()).armorType == armorType;
		}

		if (par1ItemStack.getItem().shiftedIndex == Block.pumpkin.blockID) {
			return armorType == 0;
		} else {
			return false;
		}
	}

	public int getBackgroundIconIndex() {
		return 15 + armorType * 16;
	}
}
