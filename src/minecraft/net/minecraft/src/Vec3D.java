package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class Vec3D {
	private static List vectorList = new ArrayList();
	private static int nextVector = 0;
	public double xCoord;
	public double yCoord;
	public double zCoord;

	public static Vec3D createVectorHelper(double par0, double par2, double par4) {
		return new Vec3D(par0, par2, par4);
	}

	public static void clearVectorList() {
		vectorList.clear();
		nextVector = 0;
	}

	public static void initialize() {
		nextVector = 0;
	}

	public static Vec3D createVector(double par0, double par2, double par4) {
		if (nextVector >= vectorList.size()) {
			vectorList.add(createVectorHelper(0.0D, 0.0D, 0.0D));
		}

		return ((Vec3D)vectorList.get(nextVector++)).setComponents(par0, par2, par4);
	}

	private Vec3D(double par1, double par3, double par5) {
		if (par1 == -0D) {
			par1 = 0.0D;
		}

		if (par3 == -0D) {
			par3 = 0.0D;
		}

		if (par5 == -0D) {
			par5 = 0.0D;
		}

		xCoord = par1;
		yCoord = par3;
		zCoord = par5;
	}

	private Vec3D setComponents(double par1, double par3, double par5) {
		xCoord = par1;
		yCoord = par3;
		zCoord = par5;
		return this;
	}

	public Vec3D subtract(Vec3D par1Vec3D) {
		return createVector(par1Vec3D.xCoord - xCoord, par1Vec3D.yCoord - yCoord, par1Vec3D.zCoord - zCoord);
	}

	public Vec3D normalize() {
		double d = MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);

		if (d < 0.0001D) {
			return createVector(0.0D, 0.0D, 0.0D);
		} else {
			return createVector(xCoord / d, yCoord / d, zCoord / d);
		}
	}

	public double dotProduct(Vec3D par1Vec3D) {
		return xCoord * par1Vec3D.xCoord + yCoord * par1Vec3D.yCoord + zCoord * par1Vec3D.zCoord;
	}

	public Vec3D crossProduct(Vec3D par1Vec3D) {
		return createVector(yCoord * par1Vec3D.zCoord - zCoord * par1Vec3D.yCoord, zCoord * par1Vec3D.xCoord - xCoord * par1Vec3D.zCoord, xCoord * par1Vec3D.yCoord - yCoord * par1Vec3D.xCoord);
	}

	public Vec3D addVector(double par1, double par3, double par5) {
		return createVector(xCoord + par1, yCoord + par3, zCoord + par5);
	}

	public double distanceTo(Vec3D par1Vec3D) {
		double d = par1Vec3D.xCoord - xCoord;
		double d1 = par1Vec3D.yCoord - yCoord;
		double d2 = par1Vec3D.zCoord - zCoord;
		return (double)MathHelper.sqrt_double(d * d + d1 * d1 + d2 * d2);
	}

	public double squareDistanceTo(Vec3D par1Vec3D) {
		double d = par1Vec3D.xCoord - xCoord;
		double d1 = par1Vec3D.yCoord - yCoord;
		double d2 = par1Vec3D.zCoord - zCoord;
		return d * d + d1 * d1 + d2 * d2;
	}

	public double squareDistanceTo(double par1, double par3, double par5) {
		double d = par1 - xCoord;
		double d1 = par3 - yCoord;
		double d2 = par5 - zCoord;
		return d * d + d1 * d1 + d2 * d2;
	}

	public double lengthVector() {
		return (double)MathHelper.sqrt_double(xCoord * xCoord + yCoord * yCoord + zCoord * zCoord);
	}

	public Vec3D getIntermediateWithXValue(Vec3D par1Vec3D, double par2) {
		double d = par1Vec3D.xCoord - xCoord;
		double d1 = par1Vec3D.yCoord - yCoord;
		double d2 = par1Vec3D.zCoord - zCoord;

		if (d * d < 1.0000000116860974E-007D) {
			return null;
		}

		double d3 = (par2 - xCoord) / d;

		if (d3 < 0.0D || d3 > 1.0D) {
			return null;
		} else {
			return createVector(xCoord + d * d3, yCoord + d1 * d3, zCoord + d2 * d3);
		}
	}

	public Vec3D getIntermediateWithYValue(Vec3D par1Vec3D, double par2) {
		double d = par1Vec3D.xCoord - xCoord;
		double d1 = par1Vec3D.yCoord - yCoord;
		double d2 = par1Vec3D.zCoord - zCoord;

		if (d1 * d1 < 1.0000000116860974E-007D) {
			return null;
		}

		double d3 = (par2 - yCoord) / d1;

		if (d3 < 0.0D || d3 > 1.0D) {
			return null;
		} else {
			return createVector(xCoord + d * d3, yCoord + d1 * d3, zCoord + d2 * d3);
		}
	}

	public Vec3D getIntermediateWithZValue(Vec3D par1Vec3D, double par2) {
		double d = par1Vec3D.xCoord - xCoord;
		double d1 = par1Vec3D.yCoord - yCoord;
		double d2 = par1Vec3D.zCoord - zCoord;

		if (d2 * d2 < 1.0000000116860974E-007D) {
			return null;
		}

		double d3 = (par2 - zCoord) / d2;

		if (d3 < 0.0D || d3 > 1.0D) {
			return null;
		} else {
			return createVector(xCoord + d * d3, yCoord + d1 * d3, zCoord + d2 * d3);
		}
	}

	public String toString() {
		return (new StringBuilder()).append("(").append(xCoord).append(", ").append(yCoord).append(", ").append(zCoord).append(")").toString();
	}

	public void rotateAroundX(float par1) {
		float f = MathHelper.cos(par1);
		float f1 = MathHelper.sin(par1);
		double d = xCoord;
		double d1 = yCoord * (double)f + zCoord * (double)f1;
		double d2 = zCoord * (double)f - yCoord * (double)f1;
		xCoord = d;
		yCoord = d1;
		zCoord = d2;
	}

	public void rotateAroundY(float par1) {
		float f = MathHelper.cos(par1);
		float f1 = MathHelper.sin(par1);
		double d = xCoord * (double)f + zCoord * (double)f1;
		double d1 = yCoord;
		double d2 = zCoord * (double)f - xCoord * (double)f1;
		xCoord = d;
		yCoord = d1;
		zCoord = d2;
	}
}
