package net.minecraft.src;

public class MovingObjectPosition {
	public EnumMovingObjectType typeOfHit;
	public int blockX;
	public int blockY;
	public int blockZ;
	public int sideHit;
	public Vec3D hitVec;
	public Entity entityHit;

	public MovingObjectPosition(int par1, int par2, int par3, int par4, Vec3D par5Vec3D) {
		typeOfHit = EnumMovingObjectType.TILE;
		blockX = par1;
		blockY = par2;
		blockZ = par3;
		sideHit = par4;
		hitVec = Vec3D.createVector(par5Vec3D.xCoord, par5Vec3D.yCoord, par5Vec3D.zCoord);
	}

	public MovingObjectPosition(Entity par1Entity) {
		typeOfHit = EnumMovingObjectType.ENTITY;
		entityHit = par1Entity;
		hitVec = Vec3D.createVector(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
	}
}
