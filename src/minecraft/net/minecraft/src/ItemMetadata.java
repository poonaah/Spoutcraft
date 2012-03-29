package net.minecraft.src;

public class ItemMetadata extends ItemBlock {
	private Block blockObj;

	public ItemMetadata(int par1, Block par2Block) {
		super(par1);
		blockObj = par2Block;
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public int getIconFromDamage(int par1) {
		return blockObj.getBlockTextureFromSideAndMetadata(2, par1);
	}

	public int getMetadata(int par1) {
		return par1;
	}
}
