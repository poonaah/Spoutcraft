package net.minecraft.src;

public abstract class EntityAIBase {
	private int mutexBits;

	public EntityAIBase() {
		mutexBits = 0;
	}

	public abstract boolean shouldExecute();

	public boolean continueExecuting() {
		return shouldExecute();
	}

	public boolean isContinuous() {
		return true;
	}

	public void startExecuting() {
	}

	public void resetTask() {
	}

	public void updateTask() {
	}

	public void setMutexBits(int par1) {
		mutexBits = par1;
	}

	public int getMutexBits() {
		return mutexBits;
	}
}
