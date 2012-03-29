package net.minecraft.src;

public class SlotFurnace extends Slot {
	private EntityPlayer thePlayer;
	private int field_48437_f;

	public SlotFurnace(EntityPlayer par1EntityPlayer, IInventory par2IInventory, int par3, int par4, int par5) {
		super(par2IInventory, par3, par4, par5);
		thePlayer = par1EntityPlayer;
	}

	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}

	public ItemStack decrStackSize(int par1) {
		if (getHasStack()) {
			field_48437_f += Math.min(par1, getStack().stackSize);
		}

		return super.decrStackSize(par1);
	}

	public void onPickupFromSlot(ItemStack par1ItemStack) {
		func_48434_c(par1ItemStack);
		super.onPickupFromSlot(par1ItemStack);
	}

	protected void func_48435_a(ItemStack par1ItemStack, int par2) {
		field_48437_f += par2;
		func_48434_c(par1ItemStack);
	}

	protected void func_48434_c(ItemStack par1ItemStack) {
		par1ItemStack.onCrafting(thePlayer.worldObj, thePlayer, field_48437_f);
		field_48437_f = 0;

		if (par1ItemStack.itemID == Item.ingotIron.shiftedIndex) {
			thePlayer.addStat(AchievementList.acquireIron, 1);
		}

		if (par1ItemStack.itemID == Item.fishCooked.shiftedIndex) {
			thePlayer.addStat(AchievementList.cookFish, 1);
		}
	}
}
