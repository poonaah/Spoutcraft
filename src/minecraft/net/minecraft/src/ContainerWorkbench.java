package net.minecraft.src;

import java.util.List;

public class ContainerWorkbench extends Container {
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;

	public ContainerWorkbench(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5) {
		craftMatrix = new InventoryCrafting(this, 3, 3);
		craftResult = new InventoryCraftResult();
		worldObj = par2World;
		posX = par3;
		posY = par4;
		posZ = par5;
		addSlot(new SlotCrafting(par1InventoryPlayer.player, craftMatrix, craftResult, 0, 124, 35));

		for (int i = 0; i < 3; i++) {
			for (int l = 0; l < 3; l++) {
				addSlot(new Slot(craftMatrix, l + i * 3, 30 + l * 18, 17 + i * 18));
			}
		}

		for (int j = 0; j < 3; j++) {
			for (int i1 = 0; i1 < 9; i1++) {
				addSlot(new Slot(par1InventoryPlayer, i1 + j * 9 + 9, 8 + i1 * 18, 84 + j * 18));
			}
		}

		for (int k = 0; k < 9; k++) {
			addSlot(new Slot(par1InventoryPlayer, k, 8 + k * 18, 142));
		}

		onCraftMatrixChanged(craftMatrix);
	}

	public void onCraftMatrixChanged(IInventory par1IInventory) {
		craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftMatrix));
	}

	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer) {
		super.onCraftGuiClosed(par1EntityPlayer);

		if (worldObj.isRemote) {
			return;
		}

		for (int i = 0; i < 9; i++) {
			ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

			if (itemstack != null) {
				par1EntityPlayer.dropPlayerItem(itemstack);
			}
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		if (worldObj.getBlockId(posX, posY, posZ) != Block.workbench.blockID) {
			return false;
		}

		return par1EntityPlayer.getDistanceSq((double)posX + 0.5D, (double)posY + 0.5D, (double)posZ + 0.5D) <= 64D;
	}

	public ItemStack transferStackInSlot(int par1) {
		ItemStack itemstack = null;
		Slot slot = (Slot)inventorySlots.get(par1);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par1 == 0) {
				if (!mergeItemStack(itemstack1, 10, 46, true)) {
					return null;
				}

				slot.func_48433_a(itemstack1, itemstack);
			} else if (par1 >= 10 && par1 < 37) {
				if (!mergeItemStack(itemstack1, 37, 46, false)) {
					return null;
				}
			} else if (par1 >= 37 && par1 < 46) {
				if (!mergeItemStack(itemstack1, 10, 37, false)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 10, 46, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack(null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize != itemstack.stackSize) {
				slot.onPickupFromSlot(itemstack1);
			} else {
				return null;
			}
		}

		return itemstack;
	}
}