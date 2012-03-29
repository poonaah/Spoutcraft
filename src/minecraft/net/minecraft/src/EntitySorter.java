package net.minecraft.src;

import java.util.Comparator;

public class EntitySorter implements Comparator {
	private double entityPosX;
	private double entityPosY;
	private double entityPosZ;

	public EntitySorter(Entity par1Entity) {
		entityPosX = -par1Entity.posX;
		entityPosY = -par1Entity.posY;
		entityPosZ = -par1Entity.posZ;
	}

	public int sortByDistanceToEntity(WorldRenderer par1WorldRenderer, WorldRenderer par2WorldRenderer) {
		double d = (double)par1WorldRenderer.posXPlus + entityPosX;
		double d1 = (double)par1WorldRenderer.posYPlus + entityPosY;
		double d2 = (double)par1WorldRenderer.posZPlus + entityPosZ;
		double d3 = (double)par2WorldRenderer.posXPlus + entityPosX;
		double d4 = (double)par2WorldRenderer.posYPlus + entityPosY;
		double d5 = (double)par2WorldRenderer.posZPlus + entityPosZ;
		return (int)(((d * d + d1 * d1 + d2 * d2) - (d3 * d3 + d4 * d4 + d5 * d5)) * 1024D);
	}

	public int compare(Object par1Obj, Object par2Obj) {
		return sortByDistanceToEntity((WorldRenderer)par1Obj, (WorldRenderer)par2Obj);
	}
}
