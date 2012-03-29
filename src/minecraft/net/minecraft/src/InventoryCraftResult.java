package net.minecraft.src;

public class InventoryCraftResult implements IInventory {
	private ItemStack stackResult[];

	public InventoryCraftResult() {
		stackResult = new ItemStack[1];
	}

	public int getSizeInventory() {
		return 1;
	}

	public ItemStack getStackInSlot(int par1) {
		return stackResult[par1];
	}

	public String getInvName() {
		return "Result";
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (stackResult[par1] != null) {
			ItemStack itemstack = stackResult[par1];
			stackResult[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (stackResult[par1] != null) {
			ItemStack itemstack = stackResult[par1];
			stackResult[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		stackResult[par1] = par2ItemStack;
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public void onInventoryChanged() {
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return true;
	}

	public void openChest() {
	}

	public void closeChest() {
	}
}
