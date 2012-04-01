package net.minecraft.src;

public class ItemBucketMilk extends Item {
	public ItemBucketMilk(int par1) {
		super(par1);
		setMaxStackSize(1);
	}

	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par1ItemStack.stackSize--;

		if (!par2World.isRemote) {
			par3EntityPlayer.clearActivePotions();
		}

		if (par1ItemStack.stackSize <= 0) {
			return new ItemStack(Item.bucketEmpty);
		} else {
			return par1ItemStack;
		}
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 32;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.drink;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}
}