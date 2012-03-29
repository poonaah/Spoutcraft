package net.minecraft.src;

public class TileEntityRecordPlayer extends TileEntity {
	public int record;

	public TileEntityRecordPlayer() {
	}

	public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
		super.readFromNBT(par1NBTTagCompound);
		record = par1NBTTagCompound.getInteger("Record");
	}

	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);

		if (record > 0) {
			par1NBTTagCompound.setInteger("Record", record);
		}
	}
}
