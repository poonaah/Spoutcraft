package net.minecraft.src;

public class Slot {
	private final int slotIndex;
	public final IInventory inventory;
	public int slotNumber;
	public int xDisplayPosition;
	public int yDisplayPosition;

	public Slot(IInventory par1IInventory, int par2, int par3, int par4) {
		inventory = par1IInventory;
		slotIndex = par2;
		xDisplayPosition = par3;
		yDisplayPosition = par4;
	}

	public void func_48433_a(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		if (par1ItemStack == null || par2ItemStack == null) {
			return;
		}

		if (par1ItemStack.itemID != par2ItemStack.itemID) {
			return;
		}

		int i = par2ItemStack.stackSize - par1ItemStack.stackSize;

		if (i > 0) {
			func_48435_a(par1ItemStack, i);
		}
	}

	protected void func_48435_a(ItemStack itemstack, int i) {
	}

	protected void func_48434_c(ItemStack itemstack) {
	}

	public void onPickupFromSlot(ItemStack par1ItemStack) {
		onSlotChanged();
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return true;
	}

	public ItemStack getStack() {
		return inventory.getStackInSlot(slotIndex);
	}

	public boolean getHasStack() {
		return getStack() != null;
	}

	public void putStack(ItemStack par1ItemStack) {
		inventory.setInventorySlotContents(slotIndex, par1ItemStack);
		onSlotChanged();
	}

	public void onSlotChanged() {
		inventory.onInventoryChanged();
	}

	public int getSlotStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	public int getBackgroundIconIndex() {
		return -1;
	}

	public ItemStack decrStackSize(int par1) {
		return inventory.decrStackSize(slotIndex, par1);
	}
}
