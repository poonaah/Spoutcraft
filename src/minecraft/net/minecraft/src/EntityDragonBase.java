package net.minecraft.src;

public class EntityDragonBase extends EntityLiving {
	protected int maxHealth;

	public EntityDragonBase(World par1World) {
		super(par1World);
		maxHealth = 100;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public boolean attackEntityFromPart(EntityDragonPart par1EntityDragonPart, DamageSource par2DamageSource, int par3) {
		return attackEntityFrom(par2DamageSource, par3);
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		return false;
	}

	protected boolean superAttackFrom(DamageSource par1DamageSource, int par2) {
		return super.attackEntityFrom(par1DamageSource, par2);
	}
}
