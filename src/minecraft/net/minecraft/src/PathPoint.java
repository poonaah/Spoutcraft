package net.minecraft.src;

public class PathPoint {
	public final int xCoord;
	public final int yCoord;
	public final int zCoord;
	private final int hash;
	int index;
	float totalPathDistance;
	float distanceToNext;
	float distanceToTarget;
	PathPoint previous;
	public boolean isFirst;

	public PathPoint(int par1, int par2, int par3) {
		index = -1;
		isFirst = false;
		xCoord = par1;
		yCoord = par2;
		zCoord = par3;
		hash = makeHash(par1, par2, par3);
	}

	public static int makeHash(int par0, int par1, int par2) {
		return par1 & 0xff | (par0 & 0x7fff) << 8 | (par2 & 0x7fff) << 24 | (par0 >= 0 ? 0 : 0x80000000) | (par2 >= 0 ? 0 : 0x8000);
	}

	public float distanceTo(PathPoint par1PathPoint) {
		float f = par1PathPoint.xCoord - xCoord;
		float f1 = par1PathPoint.yCoord - yCoord;
		float f2 = par1PathPoint.zCoord - zCoord;
		return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
	}

	public boolean equals(Object par1Obj) {
		if (par1Obj instanceof PathPoint) {
			PathPoint pathpoint = (PathPoint)par1Obj;
			return hash == pathpoint.hash && xCoord == pathpoint.xCoord && yCoord == pathpoint.yCoord && zCoord == pathpoint.zCoord;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return hash;
	}

	public boolean isAssigned() {
		return index >= 0;
	}

	public String toString() {
		return (new StringBuilder()).append(xCoord).append(", ").append(yCoord).append(", ").append(zCoord).toString();
	}
}
