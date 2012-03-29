package net.minecraft.src;

public class TileEntityFurnace extends TileEntity implements IInventory {
	private ItemStack furnaceItemStacks[];
	public int furnaceBurnTime;
	public int currentItemBurnTime;
	public int furnaceCookTime;

	public TileEntityFurnace() {
		furnaceItemStacks = new ItemStack[3];
		furnaceBurnTime = 0;
		currentItemBurnTime = 0;
		furnaceCookTime = 0;
	}

	public int getSizeInventory() {
		return furnaceItemStacks.length;
	}

	public ItemStack getStackInSlot(int par1) {
		return furnaceItemStacks[par1];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if (furnaceItemStacks[par1] != null) {
			if (furnaceItemStacks[par1].stackSize <= par2) {
				ItemStack itemstack = furnaceItemStacks[par1];
				furnaceItemStacks[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = furnaceItemStacks[par1].splitStack(par2);

			if (furnaceItemStacks[par1].stackSize == 0) {
				furnaceItemStacks[par1] = null;
			}

			return itemstack1;
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		if (furnaceItemStacks[par1] != null) {
			ItemStack itemstack = furnaceItemStacks[par1];
			furnaceItemStacks[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		furnaceItemStacks[par1] = par2ItemStack;

		if (par2ItemStack != null && par2ItemStack.stackSize > getInventoryStackLimit()) {
			par2ItemStack.stackSize = getInventoryStackLimit();
		}
	}

	public String getInvName() {
		return "container.furnace";
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
		furnaceItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound.getByte("Slot");

			if (byte0 >= 0 && byte0 < furnaceItemStacks.length) {
				furnaceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		furnaceBurnTime = par1NBTTagCompound.getShort("BurnTime");
		furnaceCookTime = par1NBTTagCompound.getShort("CookTime");
		currentItemBurnTime = getItemBurnTime(furnaceItemStacks[1]);
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("BurnTime", (short)furnaceBurnTime);
		par1NBTTagCompound.setShort("CookTime", (short)furnaceCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < furnaceItemStacks.length; i++) {
			if (furnaceItemStacks[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				furnaceItemStacks[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		par1NBTTagCompound.setTag("Items", nbttaglist);
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getCookProgressScaled(int par1) {
		return (furnaceCookTime * par1) / 200;
	}

	public int getBurnTimeRemainingScaled(int par1) {
		if (currentItemBurnTime == 0) {
			currentItemBurnTime = 200;
		}

		return (furnaceBurnTime * par1) / currentItemBurnTime;
	}

	public boolean isBurning() {
		return furnaceBurnTime > 0;
	}

	public void updateEntity() {
		boolean flag = furnaceBurnTime > 0;
		boolean flag1 = false;

		if (furnaceBurnTime > 0) {
			furnaceBurnTime--;
		}

		if (!worldObj.isRemote) {
			if (furnaceBurnTime == 0 && canSmelt()) {
				currentItemBurnTime = furnaceBurnTime = getItemBurnTime(furnaceItemStacks[1]);

				if (furnaceBurnTime > 0) {
					flag1 = true;

					if (furnaceItemStacks[1] != null) {
						furnaceItemStacks[1].stackSize--;

						if (furnaceItemStacks[1].stackSize == 0) {
							furnaceItemStacks[1] = null;
						}
					}
				}
			}

			if (isBurning() && canSmelt()) {
				furnaceCookTime++;

				if (furnaceCookTime == 200) {
					furnaceCookTime = 0;
					smeltItem();
					flag1 = true;
				}
			} else {
				furnaceCookTime = 0;
			}

			if (flag != (furnaceBurnTime > 0)) {
				flag1 = true;
				BlockFurnace.updateFurnaceBlockState(furnaceBurnTime > 0, worldObj, xCoord, yCoord, zCoord);
			}
		}

		if (flag1) {
			onInventoryChanged();
		}
	}

	private boolean canSmelt() {
		if (furnaceItemStacks[0] == null) {
			return false;
		}

		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);

		if (itemstack == null) {
			return false;
		}

		if (furnaceItemStacks[2] == null) {
			return true;
		}

		if (!furnaceItemStacks[2].isItemEqual(itemstack)) {
			return false;
		}

		if (furnaceItemStacks[2].stackSize < getInventoryStackLimit() && furnaceItemStacks[2].stackSize < furnaceItemStacks[2].getMaxStackSize()) {
			return true;
		}

		return furnaceItemStacks[2].stackSize < itemstack.getMaxStackSize();
	}

	public void smeltItem() {
		if (!canSmelt()) {
			return;
		}

		ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(furnaceItemStacks[0].getItem().shiftedIndex);

		if (furnaceItemStacks[2] == null) {
			furnaceItemStacks[2] = itemstack.copy();
		} else if (furnaceItemStacks[2].itemID == itemstack.itemID) {
			furnaceItemStacks[2].stackSize++;
		}

		furnaceItemStacks[0].stackSize--;

		if (furnaceItemStacks[0].stackSize <= 0) {
			furnaceItemStacks[0] = null;
		}
	}

	private int getItemBurnTime(ItemStack par1ItemStack) {
		if (par1ItemStack == null) {
			return 0;
		}

		int i = par1ItemStack.getItem().shiftedIndex;

		if (i < 256 && Block.blocksList[i].blockMaterial == Material.wood) {
			return 300;
		}

		if (i == Item.stick.shiftedIndex) {
			return 100;
		}

		if (i == Item.coal.shiftedIndex) {
			return 1600;
		}

		if (i == Item.bucketLava.shiftedIndex) {
			return 20000;
		}

		if (i == Block.sapling.blockID) {
			return 100;
		}

		return i != Item.blazeRod.shiftedIndex ? 0 : 2400;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		if (worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) != this) {
			return false;
		}

		return par1EntityPlayer.getDistanceSq((double)xCoord + 0.5D, (double)yCoord + 0.5D, (double)zCoord + 0.5D) <= 64D;
	}

	public void openChest() {
	}

	public void closeChest() {
	}
}
