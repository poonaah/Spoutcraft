package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class RenderOcelot extends RenderLiving {
	public RenderOcelot(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	public void func_48424_a(EntityOcelot par1EntityOcelot, double par2, double par4, double par6, float par8, float par9) {
		super.doRenderLiving(par1EntityOcelot, par2, par4, par6, par8, par9);
	}

	protected void func_48423_a(EntityOcelot par1EntityOcelot, float par2) {
		super.preRenderCallback(par1EntityOcelot, par2);

		if (par1EntityOcelot.isTamed()) {
			GL11.glScalef(0.8F, 0.8F, 0.8F);
		}
	}

	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		func_48423_a((EntityOcelot)par1EntityLiving, par2);
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		func_48424_a((EntityOcelot)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		func_48424_a((EntityOcelot)par1Entity, par2, par4, par6, par8, par9);
	}
}
