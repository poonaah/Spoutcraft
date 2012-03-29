package net.minecraft.src;

public class InventoryLargeChest implements IInventory {
	private String name;
	private IInventory upperChest;
	private IInventory lowerChest;

	public InventoryLargeChest(String par1Str, IInventory par2IInventory, IInventory par3IInventory) {
		name = par1Str;

		if (par2IInventory == null) {
			par2IInventory = par3IInventory;
		}

		if (par3IInventory == null) {
			par3IInventory = par2IInventory;
		}

		upperChest = par2IInventory;
		lowerChest = par3IInventory;
	}

	public int getSizeInventory() {
		return upperChest.getSizeInventory() + lowerChest.getSizeInventory();
	}

	public String getInvName() {
		return name;
	}

	public ItemStack getStackInSlot(int par1) {
		if (par1 >= upperChest.getSizeInventory()) {
			return lowerChest.getStackInSlot(par1 - upperChest.getSizeInventory());
		} else {
			return upperChest.getStackInSlot(par1);
		}
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (par1 >= upperChest.getSizeInventory()) {
			return lowerChest.decrStackSize(par1 - upperChest.getSizeInventory(), par2);
		} else {
			return upperChest.decrStackSize(par1, par2);
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (par1 >= upperChest.getSizeInventory()) {
			return lowerChest.getStackInSlotOnClosing(par1 - upperChest.getSizeInventory());
		} else {
			return upperChest.getStackInSlotOnClosing(par1);
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		if (par1 >= upperChest.getSizeInventory()) {
			lowerChest.setInventorySlotContents(par1 - upperChest.getSizeInventory(), par2ItemStack);
		} else {
			upperChest.setInventorySlotContents(par1, par2ItemStack);
		}
	}

	public int getInventoryStackLimit() {
		return upperChest.getInventoryStackLimit();
	}

	public void onInventoryChanged() {
		upperChest.onInventoryChanged();
		lowerChest.onInventoryChanged();
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		return upperChest.isUseableByPlayer(par1EntityPlayer) && lowerChest.isUseableByPlayer(par1EntityPlayer);
	}

	public void openChest() {
		upperChest.openChest();
		lowerChest.openChest();
	}

	public void closeChest() {
		upperChest.closeChest();
		lowerChest.closeChest();
	}
}
