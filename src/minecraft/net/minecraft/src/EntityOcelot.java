package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class EntityOcelot extends EntityTameable {
	private EntityAITempt aiTempt;

	public EntityOcelot(World par1World) {
		super(par1World);
		texture = "/mob/ozelot.png";
		setSize(0.6F, 0.8F);
		getNavigator().func_48664_a(true);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, aiSit);
		tasks.addTask(3, aiTempt = new EntityAITempt(this, 0.18F, Item.fishRaw.shiftedIndex, true));
		tasks.addTask(4, new EntityAIAvoidEntity(this, net.minecraft.src.EntityPlayer.class, 16F, 0.23F, 0.4F));
		tasks.addTask(5, new EntityAIOcelotSit(this, 0.4F));
		tasks.addTask(6, new EntityAIFollowOwner(this, 0.3F, 10F, 5F));
		tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
		tasks.addTask(8, new EntityAIOcelotAttack(this));
		tasks.addTask(9, new EntityAIMate(this, 0.23F));
		tasks.addTask(10, new EntityAIWander(this, 0.23F));
		tasks.addTask(11, new EntityAIWatchClosest(this, net.minecraft.src.EntityPlayer.class, 10F));
		targetTasks.addTask(1, new EntityAITargetNonTamed(this, net.minecraft.src.EntityChicken.class, 14F, 750, false));
	}

	protected void entityInit() {
		super.entityInit();
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	public void updateAITick() {
		if (!getMoveHelper().func_48186_a()) {
			setSneaking(false);
			setSprinting(false);
		} else {
			float f = getMoveHelper().getSpeed();

			if (f == 0.18F) {
				setSneaking(true);
				setSprinting(false);
			} else if (f == 0.4F) {
				setSneaking(false);
				setSprinting(true);
			} else {
				setSneaking(false);
				setSprinting(false);
			}
		}
	}

	protected boolean canDespawn() {
		return !isTamed();
	}

	public String getTexture() {
		switch (func_48148_ad()) {
			case 0:
				return "/mob/ozelot.png";

			case 1:
				return "/mob/cat_black.png";

			case 2:
				return "/mob/cat_red.png";

			case 3:
				return "/mob/cat_siamese.png";
		}

		return super.getTexture();
	}

	public boolean isAIEnabled() {
		return true;
	}

	public int getMaxHealth() {
		return 10;
	}

	protected void fall(float f) {
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("CatType", func_48148_ad());
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readEntityFromNBT(par1NBTTagCompound);
		func_48147_c(par1NBTTagCompound.getInteger("CatType"));
	}

	protected String getLivingSound() {
		if (isTamed()) {
			if (isInLove()) {
				return "mob.cat.purr";
			}

			if (rand.nextInt(4) == 0) {
				return "mob.cat.purreow";
			} else {
				return "mob.cat.meow";
			}
		} else {
			return "";
		}
	}

	protected String getHurtSound() {
		return "mob.cat.hitt";
	}

	protected String getDeathSound() {
		return "mob.cat.hitt";
	}

	protected float getSoundVolume() {
		return 0.4F;
	}

	protected int getDropItemId() {
		return Item.leather.shiftedIndex;
	}

	public boolean attackEntityAsMob(Entity par1Entity) {
		return par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
	}

	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		aiSit.func_48407_a(false);
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	protected void dropFewItems(boolean flag, int i) {
	}

	public boolean interact(EntityPlayer par1EntityPlayer) {
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		if (!isTamed()) {
			if (aiTempt.func_48270_h() && itemstack != null && itemstack.itemID == Item.fishRaw.shiftedIndex && par1EntityPlayer.getDistanceSqToEntity(this) < 9D) {
				itemstack.stackSize--;

				if (itemstack.stackSize <= 0) {
					par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, null);
				}

				if (!worldObj.isRemote) {
					if (rand.nextInt(3) == 0) {
						setTamed(true);
						func_48147_c(1 + worldObj.rand.nextInt(3));
						setOwner(par1EntityPlayer.username);
						func_48142_a(true);
						aiSit.func_48407_a(true);
						worldObj.setEntityState(this, (byte)7);
					} else {
						func_48142_a(false);
						worldObj.setEntityState(this, (byte)6);
					}
				}
			}

			return true;
		}

		if (par1EntityPlayer.username.equalsIgnoreCase(getOwnerName()) && !worldObj.isRemote && !isWheat(itemstack)) {
			aiSit.func_48407_a(!isSitting());
		}

		return super.interact(par1EntityPlayer);
	}

	public EntityAnimal spawnBabyAnimal(EntityAnimal par1EntityAnimal) {
		EntityOcelot entityocelot = new EntityOcelot(worldObj);

		if (isTamed()) {
			entityocelot.setOwner(getOwnerName());
			entityocelot.setTamed(true);
			entityocelot.func_48147_c(func_48148_ad());
		}

		return entityocelot;
	}

	public boolean isWheat(ItemStack par1ItemStack) {
		return par1ItemStack != null && par1ItemStack.itemID == Item.fishRaw.shiftedIndex;
	}

	public boolean func_48135_b(EntityAnimal par1EntityAnimal) {
		if (par1EntityAnimal == this) {
			return false;
		}

		if (!isTamed()) {
			return false;
		}

		if (!(par1EntityAnimal instanceof EntityOcelot)) {
			return false;
		}

		EntityOcelot entityocelot = (EntityOcelot)par1EntityAnimal;

		if (!entityocelot.isTamed()) {
			return false;
		} else {
			return isInLove() && entityocelot.isInLove();
		}
	}

	public int func_48148_ad() {
		return dataWatcher.getWatchableObjectByte(18);
	}

	public void func_48147_c(int par1) {
		dataWatcher.updateObject(18, Byte.valueOf((byte)par1));
	}

	public boolean getCanSpawnHere() {
		if (worldObj.rand.nextInt(3) == 0) {
			return false;
		}

		if (worldObj.checkIfAABBIsClear(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox)) {
			int i = MathHelper.floor_double(posX);
			int j = MathHelper.floor_double(boundingBox.minY);
			int k = MathHelper.floor_double(posZ);

			if (j < 63) {
				return false;
			}

			int l = worldObj.getBlockId(i, j - 1, k);

			if (l == Block.grass.blockID || l == Block.leaves.blockID) {
				return true;
			}
		}

		return false;
	}
}
