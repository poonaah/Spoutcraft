package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderSnowball extends Render {
	private int itemIconIndex;

	public RenderSnowball(int par1) {
		itemIconIndex = par1;
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		loadTexture("/gui/items.png");
		Tessellator tessellator = Tessellator.instance;

		if (itemIconIndex == 154) {
			int i = PotionHelper.func_40358_a(((EntityPotion)par1Entity).getPotionDamage(), false);
			float f = (float)(i >> 16 & 0xff) / 255F;
			float f1 = (float)(i >> 8 & 0xff) / 255F;
			float f2 = (float)(i & 0xff) / 255F;
			GL11.glColor3f(f, f1, f2);
			GL11.glPushMatrix();
			func_40265_a(tessellator, 141);
			GL11.glPopMatrix();
			GL11.glColor3f(1.0F, 1.0F, 1.0F);
		}

		func_40265_a(tessellator, itemIconIndex);
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private void func_40265_a(Tessellator par1Tessellator, int par2) {
		float f = (float)((par2 % 16) * 16 + 0) / 256F;
		float f1 = (float)((par2 % 16) * 16 + 16) / 256F;
		float f2 = (float)((par2 / 16) * 16 + 0) / 256F;
		float f3 = (float)((par2 / 16) * 16 + 16) / 256F;
		float f4 = 1.0F;
		float f5 = 0.5F;
		float f6 = 0.25F;
		GL11.glRotatef(180F - renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		par1Tessellator.startDrawingQuads();
		par1Tessellator.setNormal(0.0F, 1.0F, 0.0F);
		par1Tessellator.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3);
		par1Tessellator.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3);
		par1Tessellator.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2);
		par1Tessellator.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2);
		par1Tessellator.draw();
	}
}
