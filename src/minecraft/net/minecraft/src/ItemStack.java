package net.minecraft.src;

import java.util.*;

public final class ItemStack {
	public int stackSize;
	public int animationsToGo;
	public int itemID;
	public NBTTagCompound stackTagCompound;
	private int itemDamage;

	public ItemStack(Block par1Block) {
		this(par1Block, 1);
	}

	public ItemStack(Block par1Block, int par2) {
		this(par1Block.blockID, par2, 0);
	}

	public ItemStack(Block par1Block, int par2, int par3) {
		this(par1Block.blockID, par2, par3);
	}

	public ItemStack(Item par1Item) {
		this(par1Item.shiftedIndex, 1, 0);
	}

	public ItemStack(Item par1Item, int par2) {
		this(par1Item.shiftedIndex, par2, 0);
	}

	public ItemStack(Item par1Item, int par2, int par3) {
		this(par1Item.shiftedIndex, par2, par3);
	}

	public ItemStack(int par1, int par2, int par3) {
		stackSize = 0;
		itemID = par1;
		stackSize = par2;
		itemDamage = par3;
	}

	public static ItemStack loadItemStackFromNBT(NBTTagCompound par0NBTTagCompound) {
		ItemStack itemstack = new ItemStack();
		itemstack.readFromNBT(par0NBTTagCompound);
		return itemstack.getItem() == null ? null : itemstack;
	}

	private ItemStack() {
		stackSize = 0;
	}

	public ItemStack splitStack(int par1) {
		ItemStack itemstack = new ItemStack(itemID, par1, itemDamage);

		if (stackTagCompound != null) {
			itemstack.stackTagCompound = (NBTTagCompound)stackTagCompound.copy();
		}

		stackSize -= par1;
		return itemstack;
	}

	public Item getItem() {
		return Item.itemsList[itemID];
	}

	public int getIconIndex() {
		return getItem().getIconIndex(this);
	}

	public boolean useItem(EntityPlayer par1EntityPlayer, World par2World, int par3, int par4, int par5, int par6) {
		boolean flag = getItem().onItemUse(this, par1EntityPlayer, par2World, par3, par4, par5, par6);

		if (flag) {
			par1EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
		}

		return flag;
	}

	public float getStrVsBlock(Block par1Block) {
		return getItem().getStrVsBlock(this, par1Block);
	}

	public ItemStack useItemRightClick(World par1World, EntityPlayer par2EntityPlayer) {
		return getItem().onItemRightClick(this, par1World, par2EntityPlayer);
	}

	public ItemStack onFoodEaten(World par1World, EntityPlayer par2EntityPlayer) {
		return getItem().onFoodEaten(this, par1World, par2EntityPlayer);
	}

	public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound) {
		par1NBTTagCompound.setShort("id", (short)itemID);
		par1NBTTagCompound.setByte("Count", (byte)stackSize);
		par1NBTTagCompound.setShort("Damage", (short)itemDamage);

		if (stackTagCompound != null) {
			par1NBTTagCompound.setTag("tag", stackTagCompound);
		}

		return par1NBTTagCompound;
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		itemID = par1NBTTagCompound.getShort("id");
		stackSize = par1NBTTagCompound.getByte("Count");
		itemDamage = par1NBTTagCompound.getShort("Damage");

		if (par1NBTTagCompound.hasKey("tag")) {
			stackTagCompound = par1NBTTagCompound.getCompoundTag("tag");
		}
	}

	public int getMaxStackSize() {
		return getItem().getItemStackLimit();
	}

	public boolean isStackable() {
		return getMaxStackSize() > 1 && (!isItemStackDamageable() || !isItemDamaged());
	}

	public boolean isItemStackDamageable() {
		return Item.itemsList[itemID].getMaxDamage() > 0;
	}

	public boolean getHasSubtypes() {
		return Item.itemsList[itemID].getHasSubtypes();
	}

	public boolean isItemDamaged() {
		return isItemStackDamageable() && itemDamage > 0;
	}

	public int getItemDamageForDisplay() {
		return itemDamage;
	}

	public int getItemDamage() {
		return itemDamage;
	}

	public void setItemDamage(int par1) {
		itemDamage = par1;
	}

	public int getMaxDamage() {
		return Item.itemsList[itemID].getMaxDamage();
	}

	public void damageItem(int par1, EntityLiving par2EntityLiving) {
		if (!isItemStackDamageable()) {
			return;
		}

		if (par1 > 0 && (par2EntityLiving instanceof EntityPlayer)) {
			int i = EnchantmentHelper.getUnbreakingModifier(((EntityPlayer)par2EntityLiving).inventory);

			if (i > 0 && par2EntityLiving.worldObj.rand.nextInt(i + 1) > 0) {
				return;
			}
		}

		itemDamage += par1;

		if (itemDamage > getMaxDamage()) {
			par2EntityLiving.renderBrokenItemStack(this);

			if (par2EntityLiving instanceof EntityPlayer) {
				((EntityPlayer)par2EntityLiving).addStat(StatList.objectBreakStats[itemID], 1);
			}

			stackSize--;

			if (stackSize < 0) {
				stackSize = 0;
			}

			itemDamage = 0;
		}
	}

	public void hitEntity(EntityLiving par1EntityLiving, EntityPlayer par2EntityPlayer) {
		boolean flag = Item.itemsList[itemID].hitEntity(this, par1EntityLiving, par2EntityPlayer);

		if (flag) {
			par2EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
		}
	}

	public void onDestroyBlock(int par1, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
		boolean flag = Item.itemsList[itemID].onBlockDestroyed(this, par1, par2, par3, par4, par5EntityPlayer);

		if (flag) {
			par5EntityPlayer.addStat(StatList.objectUseStats[itemID], 1);
		}
	}

	public int getDamageVsEntity(Entity par1Entity) {
		return Item.itemsList[itemID].getDamageVsEntity(par1Entity);
	}

	public boolean canHarvestBlock(Block par1Block) {
		return Item.itemsList[itemID].canHarvestBlock(par1Block);
	}

	public void onItemDestroyedByUse(EntityPlayer entityplayer) {
	}

	public void useItemOnEntity(EntityLiving par1EntityLiving) {
		Item.itemsList[itemID].useItemOnEntity(this, par1EntityLiving);
	}

	public ItemStack copy() {
		ItemStack itemstack = new ItemStack(itemID, stackSize, itemDamage);

		if (stackTagCompound != null) {
			itemstack.stackTagCompound = (NBTTagCompound)stackTagCompound.copy();

			if (!itemstack.stackTagCompound.equals(stackTagCompound)) {
				return itemstack;
			}
		}

		return itemstack;
	}

	public static boolean func_46154_a(ItemStack par0ItemStack, ItemStack par1ItemStack) {
		if (par0ItemStack == null && par1ItemStack == null) {
			return true;
		}

		if (par0ItemStack == null || par1ItemStack == null) {
			return false;
		}

		if (par0ItemStack.stackTagCompound == null && par1ItemStack.stackTagCompound != null) {
			return false;
		}

		return par0ItemStack.stackTagCompound == null || par0ItemStack.stackTagCompound.equals(par1ItemStack.stackTagCompound);
	}

	public static boolean areItemStacksEqual(ItemStack par0ItemStack, ItemStack par1ItemStack) {
		if (par0ItemStack == null && par1ItemStack == null) {
			return true;
		}

		if (par0ItemStack == null || par1ItemStack == null) {
			return false;
		} else {
			return par0ItemStack.isItemStackEqual(par1ItemStack);
		}
	}

	private boolean isItemStackEqual(ItemStack par1ItemStack) {
		if (stackSize != par1ItemStack.stackSize) {
			return false;
		}

		if (itemID != par1ItemStack.itemID) {
			return false;
		}

		if (itemDamage != par1ItemStack.itemDamage) {
			return false;
		}

		if (stackTagCompound == null && par1ItemStack.stackTagCompound != null) {
			return false;
		}

		return stackTagCompound == null || stackTagCompound.equals(par1ItemStack.stackTagCompound);
	}

	public boolean isItemEqual(ItemStack par1ItemStack) {
		return itemID == par1ItemStack.itemID && itemDamage == par1ItemStack.itemDamage;
	}

	public static ItemStack copyItemStack(ItemStack par0ItemStack) {
		return par0ItemStack != null ? par0ItemStack.copy() : null;
	}

	public String toString() {
		return (new StringBuilder()).append(stackSize).append("x").append(Item.itemsList[itemID].getItemName()).append("@").append(itemDamage).toString();
	}

	public void updateAnimation(World par1World, Entity par2Entity, int par3, boolean par4) {
		if (animationsToGo > 0) {
			animationsToGo--;
		}

		Item.itemsList[itemID].onUpdate(this, par1World, par2Entity, par3, par4);
	}

	public void onCrafting(World par1World, EntityPlayer par2EntityPlayer, int par3) {
		par2EntityPlayer.addStat(StatList.objectCraftStats[itemID], par3);
		Item.itemsList[itemID].onCreated(this, par1World, par2EntityPlayer);
	}

	public boolean isStackEqual(ItemStack par1ItemStack) {
		return itemID == par1ItemStack.itemID && stackSize == par1ItemStack.stackSize && itemDamage == par1ItemStack.itemDamage;
	}

	public int getMaxItemUseDuration() {
		return getItem().getMaxItemUseDuration(this);
	}

	public EnumAction getItemUseAction() {
		return getItem().getItemUseAction(this);
	}

	public void onPlayerStoppedUsing(World par1World, EntityPlayer par2EntityPlayer, int par3) {
		getItem().onPlayerStoppedUsing(this, par1World, par2EntityPlayer, par3);
	}

	public boolean hasTagCompound() {
		return stackTagCompound != null;
	}

	public NBTTagCompound getTagCompound() {
		return stackTagCompound;
	}

	public NBTTagList getEnchantmentTagList() {
		if (stackTagCompound == null) {
			return null;
		} else {
			return (NBTTagList)stackTagCompound.getTag("ench");
		}
	}

	public void setTagCompound(NBTTagCompound par1NBTTagCompound) {
		stackTagCompound = par1NBTTagCompound;
	}

	public List getItemNameandInformation() {
		ArrayList arraylist = new ArrayList();
		Item item = Item.itemsList[itemID];
		arraylist.add(item.getItemDisplayName(this));
		item.addInformation(this, arraylist);

		if (hasTagCompound()) {
			NBTTagList nbttaglist = getEnchantmentTagList();

			if (nbttaglist != null) {
				for (int i = 0; i < nbttaglist.tagCount(); i++) {
					short word0 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("id");
					short word1 = ((NBTTagCompound)nbttaglist.tagAt(i)).getShort("lvl");

					if (Enchantment.enchantmentsList[word0] != null) {
						arraylist.add(Enchantment.enchantmentsList[word0].getTranslatedName(word1));
					}
				}
			}
		}

		return arraylist;
	}

	public boolean hasEffect() {
		return getItem().hasEffect(this);
	}

	public EnumRarity getRarity() {
		return getItem().getRarity(this);
	}

	public boolean isItemEnchantable() {
		if (!getItem().isItemTool(this)) {
			return false;
		}

		return !isItemEnchanted();
	}

	public void addEnchantment(Enchantment par1Enchantment, int par2) {
		if (stackTagCompound == null) {
			setTagCompound(new NBTTagCompound());
		}

		if (!stackTagCompound.hasKey("ench")) {
			stackTagCompound.setTag("ench", new NBTTagList("ench"));
		}

		NBTTagList nbttaglist = (NBTTagList)stackTagCompound.getTag("ench");
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("id", (short)par1Enchantment.effectId);
		nbttagcompound.setShort("lvl", (byte)par2);
		nbttaglist.appendTag(nbttagcompound);
	}

	public boolean isItemEnchanted() {
		return stackTagCompound != null && stackTagCompound.hasKey("ench");
	}
}
