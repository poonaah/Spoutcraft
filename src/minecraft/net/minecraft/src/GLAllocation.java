package net.minecraft.src;

import java.nio.*;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.GL11;

public class GLAllocation {
	private static List displayLists = new ArrayList();
	private static List textureNames = new ArrayList();

	public GLAllocation() {
	}

	public static synchronized int generateDisplayLists(int par0) {
		int i = GL11.glGenLists(par0);
		displayLists.add(Integer.valueOf(i));
		displayLists.add(Integer.valueOf(par0));
		return i;
	}

	public static synchronized void generateTextureNames(IntBuffer par0IntBuffer) {
		GL11.glGenTextures(par0IntBuffer);

		for (int i = par0IntBuffer.position(); i < par0IntBuffer.limit(); i++) {
			textureNames.add(Integer.valueOf(par0IntBuffer.get(i)));
		}
	}

	public static synchronized void deleteDisplayLists(int par0) {
		int i = displayLists.indexOf(Integer.valueOf(par0));
		GL11.glDeleteLists(((Integer)displayLists.get(i)).intValue(), ((Integer)displayLists.get(i + 1)).intValue());
		displayLists.remove(i);
		displayLists.remove(i);
	}

	public static synchronized void deleteTexturesAndDisplayLists() {
		for (int i = 0; i < displayLists.size(); i += 2) {
			GL11.glDeleteLists(((Integer)displayLists.get(i)).intValue(), ((Integer)displayLists.get(i + 1)).intValue());
		}

		IntBuffer intbuffer = createDirectIntBuffer(textureNames.size());
		intbuffer.flip();
		GL11.glDeleteTextures(intbuffer);

		for (int j = 0; j < textureNames.size(); j++) {
			intbuffer.put(((Integer)textureNames.get(j)).intValue());
		}

		intbuffer.flip();
		GL11.glDeleteTextures(intbuffer);
		displayLists.clear();
		textureNames.clear();
	}

	public static synchronized ByteBuffer createDirectByteBuffer(int par0) {
		ByteBuffer bytebuffer = ByteBuffer.allocateDirect(par0).order(ByteOrder.nativeOrder());
		return bytebuffer;
	}

	public static IntBuffer createDirectIntBuffer(int par0) {
		return createDirectByteBuffer(par0 << 2).asIntBuffer();
	}

	public static FloatBuffer createDirectFloatBuffer(int par0) {
		return createDirectByteBuffer(par0 << 2).asFloatBuffer();
	}
}
