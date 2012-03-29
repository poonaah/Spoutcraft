package net.minecraft.src;

import java.util.Random;

public class EntityEnderCrystal extends Entity {
	public int innerRotation;
	public int health;

	public EntityEnderCrystal(World par1World) {
		super(par1World);
		innerRotation = 0;
		preventEntitySpawning = true;
		setSize(2.0F, 2.0F);
		yOffset = height / 2.0F;
		health = 5;
		innerRotation = rand.nextInt(0x186a0);
	}

	public EntityEnderCrystal(World par1World, double par2, double par4, double par6) {
		this(par1World);
		setPosition(par2, par4, par6);
	}

	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
		dataWatcher.addObject(8, Integer.valueOf(health));
	}

	public void onUpdate() {
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		innerRotation++;
		dataWatcher.updateObject(8, Integer.valueOf(health));
		int i = MathHelper.floor_double(posX);
		int j = MathHelper.floor_double(posY);
		int k = MathHelper.floor_double(posZ);

		if (worldObj.getBlockId(i, j, k) != Block.fire.blockID) {
			worldObj.setBlockWithNotify(i, j, k, Block.fire.blockID);
		}
	}

	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	public float getShadowSize() {
		return 0.0F;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (!isDead && !worldObj.isRemote) {
			health = 0;

			if (health <= 0) {
				if (!worldObj.isRemote) {
					setDead();
					worldObj.createExplosion(null, posX, posY, posZ, 6F);
				} else {
					setDead();
				}
			}
		}

		return true;
	}
}
