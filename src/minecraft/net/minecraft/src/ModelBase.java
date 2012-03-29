package net.minecraft.src;

import java.util.*;

public abstract class ModelBase {
	public float onGround;
	public boolean isRiding;
	public List boxList;
	public boolean isChild;
	private Map modelTextureMap;
	public int textureWidth;
	public int textureHeight;

	public ModelBase() {
		isRiding = false;
		boxList = new ArrayList();
		isChild = true;
		modelTextureMap = new HashMap();
		textureWidth = 64;
		textureHeight = 32;
	}

	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
	}

	public void setLivingAnimations(EntityLiving entityliving, float f, float f1, float f2) {
	}

	protected void setTextureOffset(String par1Str, int par2, int par3) {
		modelTextureMap.put(par1Str, new TextureOffset(par2, par3));
	}

	public TextureOffset getTextureOffset(String par1Str) {
		return (TextureOffset)modelTextureMap.get(par1Str);
	}
}
