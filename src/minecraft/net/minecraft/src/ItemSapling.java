package net.minecraft.src;

public class ItemSapling extends ItemBlock {
	public ItemSapling(int par1) {
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public int getMetadata(int par1) {
		return par1;
	}

	public int getIconFromDamage(int par1) {
		return Block.sapling.getBlockTextureFromSideAndMetadata(0, par1);
	}
}
