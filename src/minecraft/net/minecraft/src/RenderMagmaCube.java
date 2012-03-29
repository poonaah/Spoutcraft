package net.minecraft.src;

import java.io.PrintStream;
import org.lwjgl.opengl.GL11;

public class RenderMagmaCube extends RenderLiving {
	private int field_40276_c;

	public RenderMagmaCube() {
		super(new ModelMagmaCube(), 0.25F);
		field_40276_c = ((ModelMagmaCube)mainModel).func_40343_a();
	}

	public void renderMagmaCube(EntityMagmaCube par1EntityMagmaCube, double par2, double par4, double par6, float par8, float par9) {
		int i = ((ModelMagmaCube)mainModel).func_40343_a();

		if (i != field_40276_c) {
			field_40276_c = i;
			mainModel = new ModelMagmaCube();
			System.out.println("new lava slime model");
		}

		super.doRenderLiving(par1EntityMagmaCube, par2, par4, par6, par8, par9);
	}

	protected void scaleMagmaCube(EntityMagmaCube par1EntityMagmaCube, float par2) {
		int i = par1EntityMagmaCube.getSlimeSize();
		float f = (par1EntityMagmaCube.field_767_b + (par1EntityMagmaCube.field_768_a - par1EntityMagmaCube.field_767_b) * par2) / ((float)i * 0.5F + 1.0F);
		float f1 = 1.0F / (f + 1.0F);
		float f2 = i;
		GL11.glScalef(f1 * f2, (1.0F / f1) * f2, f1 * f2);
	}

	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		scaleMagmaCube((EntityMagmaCube)par1EntityLiving, par2);
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		renderMagmaCube((EntityMagmaCube)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		renderMagmaCube((EntityMagmaCube)par1Entity, par2, par4, par6, par8, par9);
	}
}
