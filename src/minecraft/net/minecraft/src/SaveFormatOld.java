package net.minecraft.src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveFormatOld implements ISaveFormat {
	protected final File savesDirectory;

	public SaveFormatOld(File par1File) {
		if (!par1File.exists()) {
			par1File.mkdirs();
		}

		savesDirectory = par1File;
	}

	public String getFormatName() {
		return "Old Format";
	}

	public List getSaveList() {
		ArrayList arraylist = new ArrayList();

		for (int i = 0; i < 5; i++) {
			String s = (new StringBuilder()).append("World").append(i + 1).toString();
			WorldInfo worldinfo = getWorldInfo(s);

			if (worldinfo != null) {
				arraylist.add(new SaveFormatComparator(s, "", worldinfo.getLastTimePlayed(), worldinfo.getSizeOnDisk(), worldinfo.getGameType(), false, worldinfo.isHardcoreModeEnabled()));
			}
		}

		return arraylist;
	}

	public void flushCache() {
	}

	public WorldInfo getWorldInfo(String par1Str) {
		File file = new File(savesDirectory, par1Str);

		if (!file.exists()) {
			return null;
		}

		File file1 = new File(file, "level.dat");

		if (file1.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
				NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Data");
				return new WorldInfo(nbttagcompound2);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		file1 = new File(file, "level.dat_old");

		if (file1.exists()) {
			try {
				NBTTagCompound nbttagcompound1 = CompressedStreamTools.readCompressed(new FileInputStream(file1));
				NBTTagCompound nbttagcompound3 = nbttagcompound1.getCompoundTag("Data");
				return new WorldInfo(nbttagcompound3);
			} catch (Exception exception1) {
				exception1.printStackTrace();
			}
		}

		return null;
	}

	public void renameWorld(String par1Str, String par2Str) {
		File file = new File(savesDirectory, par1Str);

		if (!file.exists()) {
			return;
		}

		File file1 = new File(file, "level.dat");

		if (file1.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
				NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
				nbttagcompound1.setString("LevelName", par2Str);
				CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file1));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	public void deleteWorldDirectory(String par1Str) {
		File file = new File(savesDirectory, par1Str);

		if (!file.exists()) {
			return;
		} else {
			deleteFiles(file.listFiles());
			file.delete();
			return;
		}
	}

	protected static void deleteFiles(File par0ArrayOfFile[]) {
		for (int i = 0; i < par0ArrayOfFile.length; i++) {
			if (par0ArrayOfFile[i].isDirectory()) {
				System.out.println((new StringBuilder()).append("Deleting ").append(par0ArrayOfFile[i]).toString());
				deleteFiles(par0ArrayOfFile[i].listFiles());
			}

			par0ArrayOfFile[i].delete();
		}
	}

	public ISaveHandler getSaveLoader(String par1Str, boolean par2) {
		return new SaveHandler(savesDirectory, par1Str, par2);
	}

	public boolean isOldMapFormat(String par1Str) {
		return false;
	}

	public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate) {
		return false;
	}
}
