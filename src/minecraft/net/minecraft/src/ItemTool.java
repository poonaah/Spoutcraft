package net.minecraft.src;

public class ItemTool extends Item {
	private Block blocksEffectiveAgainst[];
	protected float efficiencyOnProperMaterial;
	private int damageVsEntity;
	protected EnumToolMaterial toolMaterial;

	protected ItemTool(int par1, int par2, EnumToolMaterial par3EnumToolMaterial, Block par4ArrayOfBlock[]) {
		super(par1);
		efficiencyOnProperMaterial = 4F;
		toolMaterial = par3EnumToolMaterial;
		blocksEffectiveAgainst = par4ArrayOfBlock;
		maxStackSize = 1;
		setMaxDamage(par3EnumToolMaterial.getMaxUses());
		efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
		damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
	}

	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block) {
		for (int i = 0; i < blocksEffectiveAgainst.length; i++) {
			if (blocksEffectiveAgainst[i] == par2Block) {
				return efficiencyOnProperMaterial;
			}
		}

		return 1.0F;
	}

	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving) {
		par1ItemStack.damageItem(2, par3EntityLiving);
		return true;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving) {
		par1ItemStack.damageItem(1, par6EntityLiving);
		return true;
	}

	public int getDamageVsEntity(Entity par1Entity) {
		return damageVsEntity;
	}

	public boolean isFull3D() {
		return true;
	}

	public int getItemEnchantability() {
		return toolMaterial.getEnchantability();
	}
}
