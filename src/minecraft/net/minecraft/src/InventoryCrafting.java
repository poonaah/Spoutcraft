package net.minecraft.src;

public class InventoryCrafting implements IInventory {
	private ItemStack stackList[];
	private int inventoryWidth;
	private Container eventHandler;

	public InventoryCrafting(Container par1Container, int par2, int par3) {
		int i = par2 * par3;
		stackList = new ItemStack[i];
		eventHandler = par1Container;
		inventoryWidth = par2;
	}

	public int getSizeInventory() {
		return stackList.length;
	}

	public ItemStack getStackInSlot(int par1) {
		if (par1 >= getSizeInventory()) {
			return null;
		} else {
			return stackList[par1];
		}
	}

	public ItemStack getStackInRowAndColumn(int par1, int par2) {
		if (par1 < 0 || par1 >= inventoryWidth) {
			return null;
		} else {
			int i = par1 + par2 * inventoryWidth;
			return getStackInSlot(i);
		}
	}

	public String getInvName() {
		return "container.crafting";
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (stackList[par1] != null) {
			ItemStack itemstack = stackList[par1];
			stackList[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (stackList[par1] != null) {
			if (stackList[par1].stackSize <= par2) {
				ItemStack itemstack = stackList[par1];
				stackList[par1] = null;
				eventHandler.onCraftMatrixChanged(this);
				return itemstack;
			}

			ItemStack itemstack1 = stackList[par1].splitStack(par2);

			if (stackList[par1].stackSize == 0) {
				stackList[par1] = null;
			}

			eventHandler.onCraftMatrixChanged(this);
			return itemstack1;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		stackList[par1] = par2ItemStack;
		eventHandler.onCraftMatrixChanged(this);
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
