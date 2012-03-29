package net.minecraft.src;

public class PathEntity {
	private final PathPoint points[];
	private int currentPathIndex;
	private int pathLength;

	public PathEntity(PathPoint par1ArrayOfPathPoint[]) {
		points = par1ArrayOfPathPoint;
		pathLength = par1ArrayOfPathPoint.length;
	}

	public void incrementPathIndex() {
		currentPathIndex++;
	}

	public boolean isFinished() {
		return currentPathIndex >= pathLength;
	}

	public PathPoint getFinalPathPoint() {
		if (pathLength > 0) {
			return points[pathLength - 1];
		} else {
			return null;
		}
	}

	public PathPoint getPathPointFromIndex(int par1) {
		return points[par1];
	}

	public int getCurrentPathLength() {
		return pathLength;
	}

	public void setCurrentPathLength(int par1) {
		pathLength = par1;
	}

	public int getCurrentPathIndex() {
		return currentPathIndex;
	}

	public void setCurrentPathIndex(int par1) {
		currentPathIndex = par1;
	}

	public Vec3D func_48646_a(Entity par1Entity, int par2) {
		double d = (double)points[par2].xCoord + (double)(int)(par1Entity.width + 1.0F) * 0.5D;
		double d1 = points[par2].yCoord;
		double d2 = (double)points[par2].zCoord + (double)(int)(par1Entity.width + 1.0F) * 0.5D;
		return Vec3D.createVector(d, d1, d2);
	}

	public Vec3D getCurrentNodeVec3d(Entity par1Entity) {
		return func_48646_a(par1Entity, currentPathIndex);
	}

	public boolean func_48647_a(PathEntity par1PathEntity) {
		if (par1PathEntity == null) {
			return false;
		}

		if (par1PathEntity.points.length != points.length) {
			return false;
		}

		for (int i = 0; i < points.length; i++) {
			if (points[i].xCoord != par1PathEntity.points[i].xCoord || points[i].yCoord != par1PathEntity.points[i].yCoord || points[i].zCoord != par1PathEntity.points[i].zCoord) {
				return false;
			}
		}

		return true;
	}

	public boolean func_48639_a(Vec3D par1Vec3D) {
		PathPoint pathpoint = getFinalPathPoint();

		if (pathpoint == null) {
			return false;
		} else {
			return pathpoint.xCoord == (int)par1Vec3D.xCoord && pathpoint.zCoord == (int)par1Vec3D.zCoord;
		}
	}
}
