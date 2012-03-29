package net.minecraft.src;

import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.Minecraft;

public abstract class TexturePackBase {
	public String texturePackFileName;
	public String firstDescriptionLine;
	public String secondDescriptionLine;
	public String texturePackID;

	public TexturePackBase() {
	}

	public void func_6482_a() {
	}

	public void closeTexturePackFile() {
	}

	public void func_6485_a(Minecraft minecraft) throws IOException {
	}

	public void unbindThumbnailTexture(Minecraft minecraft) {
	}

	public void bindThumbnailTexture(Minecraft minecraft) {
	}

	public InputStream getResourceAsStream(String par1Str) {
		return (net.minecraft.src.TexturePackBase.class).getResourceAsStream(par1Str);
	}
}
