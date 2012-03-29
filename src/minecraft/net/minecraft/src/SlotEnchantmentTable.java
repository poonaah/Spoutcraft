package net.minecraft.src;

class SlotEnchantmentTable extends InventoryBasic {
	final ContainerEnchantment container;

	SlotEnchantmentTable(ContainerEnchantment par1ContainerEnchantment, String par2Str, int par3) {
		super(par2Str, par3);
		container = par1ContainerEnchantment;
	}

	public int getInventoryStackLimit() {
		return 1;
	}

	public void onInventoryChanged() {
		super.onInventoryChanged();
		container.onCraftMatrixChanged(this);
	}
}
