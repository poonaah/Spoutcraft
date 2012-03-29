package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderBoat extends Render {
	protected ModelBase modelBoat;

	public RenderBoat() {
		shadowSize = 0.5F;
		modelBoat = new ModelBoat();
	}

	public void renderBoat(EntityBoat par1EntityBoat, double par2, double par4, double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glRotatef(180F - par8, 0.0F, 1.0F, 0.0F);
		float f = (float)par1EntityBoat.getTimeSinceHit() - par9;
		float f1 = (float)par1EntityBoat.getDamageTaken() - par9;

		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		if (f > 0.0F) {
			GL11.glRotatef(((MathHelper.sin(f) * f * f1) / 10F) * (float)par1EntityBoat.getForwardDirection(), 1.0F, 0.0F, 0.0F);
		}

		loadTexture("/terrain.png");
		float f2 = 0.75F;
		GL11.glScalef(f2, f2, f2);
		GL11.glScalef(1.0F / f2, 1.0F / f2, 1.0F / f2);
		loadTexture("/item/boat.png");
		GL11.glScalef(-1F, -1F, 1.0F);
		modelBoat.render(par1EntityBoat, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
		GL11.glPopMatrix();
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		renderBoat((EntityBoat)par1Entity, par2, par4, par6, par8, par9);
	}
}
