package net.minecraft.src;

import java.awt.image.BufferedImage;

public class ThreadDownloadImageData {
	public BufferedImage image;
	public int referenceCount;
	public int textureName;
	public boolean textureSetupComplete;

	public ThreadDownloadImageData(String par1Str, ImageBuffer par2ImageBuffer) {
		referenceCount = 1;
		textureName = -1;
		textureSetupComplete = false;
		(new ThreadDownloadImage(this, par1Str, par2ImageBuffer)).start();
	}
}
