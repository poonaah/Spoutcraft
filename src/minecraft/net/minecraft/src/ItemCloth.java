package net.minecraft.src;

public class ItemCloth extends ItemBlock {
	public ItemCloth(int par1) {
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public int getIconFromDamage(int par1) {
		return Block.cloth.getBlockTextureFromSideAndMetadata(2, BlockCloth.getBlockFromDye(par1));
	}

	public int getMetadata(int par1) {
		return par1;
	}

	public String getItemNameIS(ItemStack par1ItemStack) {
		return (new StringBuilder()).append(super.getItemName()).append(".").append(ItemDye.dyeColorNames[BlockCloth.getBlockFromDye(par1ItemStack.getItemDamage())]).toString();
	}
}
