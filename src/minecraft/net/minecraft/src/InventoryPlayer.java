package net.minecraft.src;

public class InventoryPlayer implements IInventory {
	public ItemStack mainInventory[];
	public ItemStack armorInventory[];
	public int currentItem;
	public EntityPlayer player;
	private ItemStack itemStack;
	public boolean inventoryChanged;

	public InventoryPlayer(EntityPlayer par1EntityPlayer) {
		mainInventory = new ItemStack[36];
		armorInventory = new ItemStack[4];
		currentItem = 0;
		inventoryChanged = false;
		player = par1EntityPlayer;
	}

	public ItemStack getCurrentItem() {
		if (currentItem < 9 && currentItem >= 0) {
			return mainInventory[currentItem];
		} else {
			return null;
		}
	}

	private int getInventorySlotContainItem(int par1) {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] != null && mainInventory[i].itemID == par1) {
				return i;
			}
		}

		return -1;
	}

	private int getInventorySlotContainItemAndDamage(int par1, int par2) {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] != null && mainInventory[i].itemID == par1 && mainInventory[i].getItemDamage() == par2) {
				return i;
			}
		}

		return -1;
	}

	private int storeItemStack(ItemStack par1ItemStack) {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] != null && mainInventory[i].itemID == par1ItemStack.itemID && mainInventory[i].isStackable() && mainInventory[i].stackSize < mainInventory[i].getMaxStackSize() && mainInventory[i].stackSize < getInventoryStackLimit() && (!mainInventory[i].getHasSubtypes() || mainInventory[i].getItemDamage() == par1ItemStack.getItemDamage()) && ItemStack.func_46154_a(mainInventory[i], par1ItemStack)) {
				return i;
			}
		}

		return -1;
	}

	private int getFirstEmptyStack() {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] == null) {
				return i;
			}
		}

		return -1;
	}

	public void setCurrentItem(int par1, int par2, boolean par3, boolean par4) {
		int i = -1;

		if (par3) {
			i = getInventorySlotContainItemAndDamage(par1, par2);
		} else {
			i = getInventorySlotContainItem(par1);
		}

		if (i >= 0 && i < 9) {
			currentItem = i;
			return;
		} else {
			return;
		}
	}

	public void changeCurrentItem(int par1) {
		if (par1 > 0) {
			par1 = 1;
		}

		if (par1 < 0) {
			par1 = -1;
		}

		for (currentItem -= par1; currentItem < 0; currentItem += 9) { }

		for (; currentItem >= 9; currentItem -= 9) { }
	}

	private int storePartialItemStack(ItemStack par1ItemStack) {
		int i = par1ItemStack.itemID;
		int j = par1ItemStack.stackSize;

		if (par1ItemStack.getMaxStackSize() == 1) {
			int k = getFirstEmptyStack();

			if (k < 0) {
				return j;
			}

			if (mainInventory[k] == null) {
				mainInventory[k] = ItemStack.copyItemStack(par1ItemStack);
			}

			return 0;
		}

		int l = storeItemStack(par1ItemStack);

		if (l < 0) {
			l = getFirstEmptyStack();
		}

		if (l < 0) {
			return j;
		}

		if (mainInventory[l] == null) {
			mainInventory[l] = new ItemStack(i, 0, par1ItemStack.getItemDamage());

			if (par1ItemStack.hasTagCompound()) {
				mainInventory[l].setTagCompound((NBTTagCompound)par1ItemStack.getTagCompound().copy());
			}
		}

		int i1 = j;

		if (i1 > mainInventory[l].getMaxStackSize() - mainInventory[l].stackSize) {
			i1 = mainInventory[l].getMaxStackSize() - mainInventory[l].stackSize;
		}

		if (i1 > getInventoryStackLimit() - mainInventory[l].stackSize) {
			i1 = getInventoryStackLimit() - mainInventory[l].stackSize;
		}

		if (i1 == 0) {
			return j;
		} else {
			j -= i1;
			mainInventory[l].stackSize += i1;
			mainInventory[l].animationsToGo = 5;
			return j;
		}
	}

	public void decrementAnimations() {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] != null) {
				mainInventory[i].updateAnimation(player.worldObj, player, i, currentItem == i);
			}
		}
	}

	public boolean consumeInventoryItem(int par1) {
		int i = getInventorySlotContainItem(par1);

		if (i < 0) {
			return false;
		}

		if (--mainInventory[i].stackSize <= 0) {
			mainInventory[i] = null;
		}

		return true;
	}

	public boolean hasItem(int par1) {
		int i = getInventorySlotContainItem(par1);
		return i >= 0;
	}

	public boolean addItemStackToInventory(ItemStack par1ItemStack) {
		if (!par1ItemStack.isItemDamaged()) {
			int i;

			do {
				i = par1ItemStack.stackSize;
				par1ItemStack.stackSize = storePartialItemStack(par1ItemStack);
			} while (par1ItemStack.stackSize > 0 && par1ItemStack.stackSize < i);

			if (par1ItemStack.stackSize == i && player.capabilities.isCreativeMode) {
				par1ItemStack.stackSize = 0;
				return true;
			} else {
				return par1ItemStack.stackSize < i;
			}
		}

		int j = getFirstEmptyStack();

		if (j >= 0) {
			mainInventory[j] = ItemStack.copyItemStack(par1ItemStack);
			mainInventory[j].animationsToGo = 5;
			par1ItemStack.stackSize = 0;
			return true;
		}

		if (player.capabilities.isCreativeMode) {
			par1ItemStack.stackSize = 0;
			return true;
		} else {
			return false;
		}
	}

	public ItemStack decrStackSize(int par1, int par2) {
		ItemStack aitemstack[] = mainInventory;

		if (par1 >= mainInventory.length) {
			aitemstack = armorInventory;
			par1 -= mainInventory.length;
		}

		if (aitemstack[par1] != null) {
			if (aitemstack[par1].stackSize <= par2) {
				ItemStack itemstack = aitemstack[par1];
				aitemstack[par1] = null;
				return itemstack;
			}

			ItemStack itemstack1 = aitemstack[par1].splitStack(par2);

			if (aitemstack[par1].stackSize == 0) {
				aitemstack[par1] = null;
			}

			return itemstack1;
		} else {
			return null;
		}
	}

	public ItemStack getStackInSlotOnClosing(int par1) {
		ItemStack aitemstack[] = mainInventory;

		if (par1 >= mainInventory.length) {
			aitemstack = armorInventory;
			par1 -= mainInventory.length;
		}

		if (aitemstack[par1] != null) {
			ItemStack itemstack = aitemstack[par1];
			aitemstack[par1] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
		ItemStack aitemstack[] = mainInventory;

		if (par1 >= aitemstack.length) {
			par1 -= aitemstack.length;
			aitemstack = armorInventory;
		}

		aitemstack[par1] = par2ItemStack;
	}

	public float getStrVsBlock(Block par1Block) {
		float f = 1.0F;

		if (mainInventory[currentItem] != null) {
			f *= mainInventory[currentItem].getStrVsBlock(par1Block);
		}

		return f;
	}

	public NBTTagList writeToNBT(NBTTagList par1NBTTagList) {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte)i);
				mainInventory[i].writeToNBT(nbttagcompound);
				par1NBTTagList.appendTag(nbttagcompound);
			}
		}

		for (int j = 0; j < armorInventory.length; j++) {
			if (armorInventory[j] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)(j + 100));
				armorInventory[j].writeToNBT(nbttagcompound1);
				par1NBTTagList.appendTag(nbttagcompound1);
			}
		}

		return par1NBTTagList;
	}

	public void readFromNBT(NBTTagList par1NBTTagList) {
		mainInventory = new ItemStack[36];
		armorInventory = new ItemStack[4];

		for (int i = 0; i < par1NBTTagList.tagCount(); i++) {
			NBTTagCompound nbttagcompound = (NBTTagCompound)par1NBTTagList.tagAt(i);
			int j = nbttagcompound.getByte("Slot") & 0xff;
			ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);

			if (itemstack == null) {
				continue;
			}

			if (j >= 0 && j < mainInventory.length) {
				mainInventory[j] = itemstack;
			}

			if (j >= 100 && j < armorInventory.length + 100) {
				armorInventory[j - 100] = itemstack;
			}
		}
	}

	public int getSizeInventory() {
		return mainInventory.length + 4;
	}

	public ItemStack getStackInSlot(int par1) {
		ItemStack aitemstack[] = mainInventory;

		if (par1 >= aitemstack.length) {
			par1 -= aitemstack.length;
			aitemstack = armorInventory;
		}

		return aitemstack[par1];
	}

	public String getInvName() {
		return "container.inventory";
	}

	public int getInventoryStackLimit() {
		return 64;
	}

	public int getDamageVsEntity(Entity par1Entity) {
		ItemStack itemstack = getStackInSlot(currentItem);

		if (itemstack != null) {
			return itemstack.getDamageVsEntity(par1Entity);
		} else {
			return 1;
		}
	}

	public boolean canHarvestBlock(Block par1Block) {
		if (par1Block.blockMaterial.isHarvestable()) {
			return true;
		}

		ItemStack itemstack = getStackInSlot(currentItem);

		if (itemstack != null) {
			return itemstack.canHarvestBlock(par1Block);
		} else {
			return false;
		}
	}

	public ItemStack armorItemInSlot(int par1) {
		return armorInventory[par1];
	}

	public int getTotalArmorValue() {
		int i = 0;

		for (int j = 0; j < armorInventory.length; j++) {
			if (armorInventory[j] != null && (armorInventory[j].getItem() instanceof ItemArmor)) {
				int k = ((ItemArmor)armorInventory[j].getItem()).damageReduceAmount;
				i += k;
			}
		}

		return i;
	}

	public void damageArmor(int par1) {
		par1 /= 4;

		if (par1 < 1) {
			par1 = 1;
		}

		for (int i = 0; i < armorInventory.length; i++) {
			if (armorInventory[i] == null || !(armorInventory[i].getItem() instanceof ItemArmor)) {
				continue;
			}

			armorInventory[i].damageItem(par1, player);

			if (armorInventory[i].stackSize == 0) {
				armorInventory[i].onItemDestroyedByUse(player);
				armorInventory[i] = null;
			}
		}
	}

	public void dropAllItems() {
		for (int i = 0; i < mainInventory.length; i++) {
			if (mainInventory[i] != null) {
				player.dropPlayerItemWithRandomChoice(mainInventory[i], true);
				mainInventory[i] = null;
			}
		}

		for (int j = 0; j < armorInventory.length; j++) {
			if (armorInventory[j] != null) {
				player.dropPlayerItemWithRandomChoice(armorInventory[j], true);
				armorInventory[j] = null;
			}
		}
	}

	public void onInventoryChanged() {
		inventoryChanged = true;
	}

	public void setItemStack(ItemStack par1ItemStack) {
		itemStack = par1ItemStack;
		player.onItemStackChanged(par1ItemStack);
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
		if (player.isDead) {
			return false;
		}

		return par1EntityPlayer.getDistanceSqToEntity(player) <= 64D;
	}

	public boolean hasItemStack(ItemStack par1ItemStack) {
		for (int i = 0; i < armorInventory.length; i++) {
			if (armorInventory[i] != null && armorInventory[i].isStackEqual(par1ItemStack)) {
				return true;
			}
		}

		for (int j = 0; j < mainInventory.length; j++) {
			if (mainInventory[j] != null && mainInventory[j].isStackEqual(par1ItemStack)) {
				return true;
			}
		}

		return false;
	}

	public void openChest() {
	}

	public void closeChest() {
	}

	public void copyInventory(InventoryPlayer par1InventoryPlayer) {
		for (int i = 0; i < mainInventory.length; i++) {
			mainInventory[i] = ItemStack.copyItemStack(par1InventoryPlayer.mainInventory[i]);
		}

		for (int j = 0; j < armorInventory.length; j++) {
			armorInventory[j] = ItemStack.copyItemStack(par1InventoryPlayer.armorInventory[j]);
		}
	}
}
