package net.minecraft.src;

import java.util.List;

public class ItemRecord extends Item {
	public final String recordName;

	protected ItemRecord(int par1, String par2Str) {
		super(par1);
		recordName = par2Str;
		maxStackSize = 1;
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7) {
		if (par3World.getBlockId(par4, par5, par6) == Block.jukebox.blockID && par3World.getBlockMetadata(par4, par5, par6) == 0) {
			if (par3World.isRemote) {
				return true;
			} else {
				((BlockJukeBox)Block.jukebox).insertRecord(par3World, par4, par5, par6, shiftedIndex);
				par3World.playAuxSFXAtEntity(null, 1005, par4, par5, par6, shiftedIndex);
				par1ItemStack.stackSize--;
				return true;
			}
		} else {
			return false;
		}
	}

	public void addInformation(ItemStack par1ItemStack, List par2List) {
		par2List.add((new StringBuilder()).append("C418 - ").append(recordName).toString());
	}

	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}
}
