package net.minecraft.src;

import java.util.List;
import net.minecraft.client.Minecraft;

public class PlayerControllerCreative extends PlayerController {
	private int field_35647_c;

	public PlayerControllerCreative(Minecraft par1Minecraft) {
		super(par1Minecraft);
		isInTestMode = true;
	}

	public static void enableAbilities(EntityPlayer par0EntityPlayer) {
		par0EntityPlayer.capabilities.allowFlying = true;
		par0EntityPlayer.capabilities.isCreativeMode = true;
		par0EntityPlayer.capabilities.disableDamage = true;
	}

	public static void disableAbilities(EntityPlayer par0EntityPlayer) {
		par0EntityPlayer.capabilities.allowFlying = false;
		par0EntityPlayer.capabilities.isFlying = false;
		par0EntityPlayer.capabilities.isCreativeMode = false;
		par0EntityPlayer.capabilities.disableDamage = false;
	}

	public void func_6473_b(EntityPlayer par1EntityPlayer) {
		enableAbilities(par1EntityPlayer);

		for (int i = 0; i < 9; i++) {
			if (par1EntityPlayer.inventory.mainInventory[i] == null) {
				par1EntityPlayer.inventory.mainInventory[i] = new ItemStack((Block)Session.registeredBlocksList.get(i));
			}
		}
	}

	public static void clickBlockCreative(Minecraft par0Minecraft, PlayerController par1PlayerController, int par2, int par3, int par4, int par5) {
		if (!par0Minecraft.theWorld.func_48457_a(par0Minecraft.thePlayer, par2, par3, par4, par5)) {
			par1PlayerController.onPlayerDestroyBlock(par2, par3, par4, par5);
		}
	}

	public boolean onPlayerRightClick(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7) {
		int i = par2World.getBlockId(par4, par5, par6);

		if (i > 0 && Block.blocksList[i].blockActivated(par2World, par4, par5, par6, par1EntityPlayer)) {
			return true;
		}

		if (par3ItemStack == null) {
			return false;
		} else {
			int j = par3ItemStack.getItemDamage();
			int k = par3ItemStack.stackSize;
			boolean flag = par3ItemStack.useItem(par1EntityPlayer, par2World, par4, par5, par6, par7);
			par3ItemStack.setItemDamage(j);
			par3ItemStack.stackSize = k;
			return flag;
		}
	}

	public void clickBlock(int par1, int par2, int par3, int par4) {
		clickBlockCreative(mc, this, par1, par2, par3, par4);
		field_35647_c = 5;
	}

	public void onPlayerDamageBlock(int par1, int par2, int par3, int par4) {
		field_35647_c--;

		if (field_35647_c <= 0) {
			field_35647_c = 5;
			clickBlockCreative(mc, this, par1, par2, par3, par4);
		}
	}

	public void resetBlockRemoving() {
	}

	public boolean shouldDrawHUD() {
		return false;
	}

	public void onWorldChange(World par1World) {
		super.onWorldChange(par1World);
	}

	public float getBlockReachDistance() {
		return 5F;
	}

	public boolean isNotCreative() {
		return false;
	}

	public boolean isInCreativeMode() {
		return true;
	}

	public boolean extendedReach() {
		return true;
	}
}
