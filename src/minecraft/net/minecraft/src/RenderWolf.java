package net.minecraft.src;

public class RenderWolf extends RenderLiving {
	public RenderWolf(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
	}

	public void renderWolf(EntityWolf par1EntityWolf, double par2, double par4, double par6, float par8, float par9) {
		super.doRenderLiving(par1EntityWolf, par2, par4, par6, par8, par9);
	}

	protected float getTailRotation(EntityWolf par1EntityWolf, float par2) {
		return par1EntityWolf.getTailRotation();
	}

	protected void func_25006_b(EntityWolf entitywolf, float f) {
	}

	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2) {
		func_25006_b((EntityWolf)par1EntityLiving, par2);
	}

	protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2) {
		return getTailRotation((EntityWolf)par1EntityLiving, par2);
	}

	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9) {
		renderWolf((EntityWolf)par1EntityLiving, par2, par4, par6, par8, par9);
	}

	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
		renderWolf((EntityWolf)par1Entity, par2, par4, par6, par8, par9);
	}
}
