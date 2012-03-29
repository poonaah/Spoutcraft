package net.minecraft.src;

public class ItemSword extends Item {
	private int weaponDamage;
	private final EnumToolMaterial toolMaterial;

	public ItemSword(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1);
		toolMaterial = par2EnumToolMaterial;
		maxStackSize = 1;
		setMaxDamage(par2EnumToolMaterial.getMaxUses());
		weaponDamage = 4 + par2EnumToolMaterial.getDamageVsEntity();
	}

	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		return par2Block.blockID != Block.web.blockID ? 1.5F : 15F;
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving) {
		par1ItemStack.damageItem(2, par6EntityLiving);
		return true;
	}

	public int getDamageVsEntity(Entity par1Entity) {
		return weaponDamage;
	}

	public boolean isFull3D() {
		return true;
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack) {
		return EnumAction.block;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 0x11940;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	public boolean canHarvestBlock(Block par1Block) {
		return par1Block.blockID == Block.web.blockID;
	}

	public int getItemEnchantability() {
		return toolMaterial.getEnchantability();
	}
}
