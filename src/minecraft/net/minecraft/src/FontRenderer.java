package net.minecraft.src;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.getspout.commons.ChatColor;
import org.lwjgl.opengl.GL11;
//Spout HD Start
import com.pclewis.mcpatcher.mod.Colorizer;
//Spout HD End

public class FontRenderer {
	
	public int FONT_HEIGHT = 8;
    private int texID;
    private int[] xPos;
    private int[] yPos;
    private int startChar;
    private int endChar;
    private FontMetrics metrics;
    public Random fontRandom = new Random();


    public FontRenderer(Minecraft mc, Object font, int size)
    {
        this(mc, font, size, 31, 127);
    }
    
	public int func_50101_a(String par1Str, int par2, int par3, int par4, boolean par5) {
			return 0;
	}
	
	public int func_50103_a(String par1Str, int par2, int par3, int par4) {

		int var5 = this.func_50101_a(par1Str, par2 + 1, par3 + 1, par4, true);
		var5 = Math.max(var5, this.func_50101_a(par1Str, par2, par3, par4, false));
		
		drawStringWithShadow(par1Str, par2, par3, par4);
		
		return var5;
	}
	
	public String func_50107_a(String par1Str, int par2) {
		return this.func_50104_a(par1Str, par2, false);
	}
	
	public String func_50104_a(String par1Str, int par2, boolean par3) {
		StringBuilder var4 = new StringBuilder();
		int var5 = 0;
		int var6 = par3?par1Str.length() - 1:0;
		int var7 = par3?-1:1;
		boolean var8 = false;
		boolean var9 = false;

		for (int var10 = var6; var10 >= 0 && var10 < par1Str.length() && var5 < par2; var10 += var7) {
			char var11 = par1Str.charAt(var10);
			int var12 = this.func_50105_a(var11);
			if (var8) {
				var8 = false;
				if (var11 != 108 && var11 != 76) {
					if (var11 == 114 || var11 == 82) {
						var9 = false;
					}
				} else {
					var9 = true;
				}
			} else if (var12 < 0) {
				var8 = true;
			} else {
				var5 += var12;
				if (var9) {
					++var5;
				}
			}

			if (var5 > par2) {
				break;
			}

			if (par3) {
				var4.insert(0, var11);
			} else {
				var4.append(var11);
		}
	}

		return var4.toString();
	}
	
	public int func_50105_a(char par1) {

				return 0;
	}
	
	public List func_50108_c(String par1Str, int par2) {
		return Arrays.asList(this.func_50113_d(par1Str, par2).split("\n"));
	}
	
	public String func_50113_d(String par1Str, int par2) {
		int var3 = this.func_50102_e(par1Str, par2);
		if (par1Str.length() <= var3) {
			return par1Str;
		} else {
			String var4 = par1Str.substring(0, var3);
			String var5 = func_50114_c(var4) + par1Str.substring(var3 + (par1Str.charAt(var3) == 32?1:0));
			return var4 + "\n" + this.func_50113_d(var5, par2);
		}
	}
	
	private static String func_50114_c(String par0Str) {
		String var1 = "";
		int var2 = -1;
		int var3 = par0Str.length();

		while ((var2 = par0Str.indexOf(167, var2 + 1)) != -1) {
			if (var2 != var3) {
				char var4 = par0Str.charAt(var2 + 1);
				if (func_50110_b(var4)) {
					var1 = "\u00a7" + var4;
				} else if (func_50109_c(var4)) {
					var1 = var1 + "\u00a7" + var4;
				}
			}
		}

		return var1;
	}
	
	private static boolean func_50110_b(char par0) {
		return par0 >= 48 && par0 <= 57 || par0 >= 97 && par0 <= 102 || par0 >= 65 && par0 <= 70;
	}
	
	private static boolean func_50109_c(char par0) {
		return par0 >= 107 && par0 <= 111 || par0 >= 75 && par0 <= 79 || par0 == 114 || par0 == 82;
	}
	
	public void setBidiFlag(boolean par1) {
		
	}
	public void setUnicodeFlag(boolean par1) {
		
	}
	
	private int func_50102_e(String par1Str, int par2) {
		int var3 = par1Str.length();
		int var4 = 0;
		int var5 = 0;
		int var6 = -1;

		for (boolean var7 = false; var5 < var3; ++var5) {
			char var8 = par1Str.charAt(var5);
			switch(var8) {
			case 32:
				var6 = var5;
			case 167:
				if (var5 != var3) {
					++var5;
					char var9 = par1Str.charAt(var5);
					if (var9 != 108 && var9 != 76) {
						if (var9 == 114 || var9 == 82) {
							var7 = false;
						}
					} else {
						var7 = true;
					}
				}
				break;
			default:
				var4 += this.func_50105_a(var8);
				if (var7) {
					++var4;
				}
			}

			if (var8 == 10) {
				++var5;
				var6 = var5;
				break;
			}

			if (var4 > par2) {
				break;
			}
		}

		return var5 != var3 && var6 != -1 && var6 < var5?var6:var5;
	}
	
	
	
	public FontRenderer(Minecraft mc, Object font, int size, int startChar, int endChar) {
        this.startChar = startChar;
        this.endChar = endChar;
        xPos = new int[endChar-startChar];
        yPos = new int[endChar-startChar];

        BufferedImage img = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        try
        {
                if (font instanceof String)
                {
                        String fontName = (String)font;
                        if (fontName.contains("/"))
                                g.setFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontName)).deriveFont((float)size));
                        else
                                g.setFont(new Font(fontName, 0, size));
                }
                else if (font instanceof InputStream)
                {
                        g.setFont(Font.createFont(Font.TRUETYPE_FONT, (InputStream)font).deriveFont((float)size));
                }
                else if (font instanceof File)
                {
                        g.setFont(Font.createFont(Font.TRUETYPE_FONT, (File)font).deriveFont((float)size));
                }
        }
        catch (Exception e)
        {
                e.printStackTrace();
        }
        
        g.setColor(new Color(255, 255, 255, 0));
        g.fillRect(0, 0, 256, 256);
        g.setColor(Color.white);
        metrics = g.getFontMetrics();

        int x = 2;
        int y = 2;
        for (int i=startChar; i<endChar; i++)
        {
                g.drawString(""+((char)i), x, y + g.getFontMetrics().getAscent());
                xPos[i-startChar] = x;
                yPos[i-startChar] = y - metrics.getMaxDescent();
                x += metrics.stringWidth(""+(char)i) + 2;
                if (x >= 250-metrics.getMaxAdvance())
                {
                        x = 2;
                        y += metrics.getMaxAscent() + metrics.getMaxDescent() + size / 2;
                }
        }

        texID = mc.renderEngine.allocateAndSetupTexture(img);
	}

	public void drawStringWithShadow(String par1Str, int par2, int par3, int par4) {

		//drawString(fontRenderer, line, chatWidget.getCursorX(), chatWidget.getCursorY() - 12 * size--, color);

		GL11.glScalef(0.5F, 0.5F, 0.5F);
		renderString(par1Str, (int)(par2*2), (par3*2)-5, true, par4);
		renderString(par1Str, (int)(par2*2), (par3*2)-5, false, par4);
		GL11.glScalef(2F, 2F, 2F);


	}

	public void drawString(String par1Str, int par2, int par3, int par4) {

		GL11.glScalef(0.5F, 0.5F, 0.5F);
		renderString(par1Str, (int)(par2*2), (par3*2)-5, false, par4);
		GL11.glScalef(2F, 2F, 2F);

	}
	
    public void renderString(String text, int x, int y, boolean shade, int colour)
    {
    	if(text != null)
    	{
    	GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glBindTexture(3553 /*GL_TEXTURE_2D*/, texID);
        
        if(shade)
        {
        	GL11.glColor4f(0.20F, 0.20F, 0.20F, 255);
        	x +=2;
        	y +=1;
        }
        else
        {
    		int par4 = Colorizer.colorizeText(colour); //Spout HD
    		
    		if ((par4 & -67108864) == 0) {
    			par4 |= -16777216;
    		}

    		GL11.glColor4f((float)(par4 >> 16 & 255) / 255.0F, (float)(par4 >> 8 & 255) / 255.0F, (float)(par4 & 255) / 255.0F, (float)(par4 >> 24 & 255) / 255.0F);
        }
        char colorCode;
        int startX = x;
        for (int i=0; i<text.length(); i++)
        {
                char c = text.charAt(i);
                if (c == '\n')
                {
                	y += metrics.getAscent() + 4;
                    x = startX;
                    i++;
                    continue;
                }
                if (c == 'á' || c == 'à' || c == 'â' || c == 'ä' || c == 'Á' || c == 'À' || c == 'Â' || c == 'Ä') { c = 'a'; }
                if (c == 'é' || c == 'è' || c == 'ê' || c == 'ë' || c == 'É' || c == 'È' || c == 'Ê' || c == 'Ë') { c = 'e'; }
                if (c == 'í' || c == 'ì' || c == 'î' || c == 'ï' || c == 'Í' || c == 'Ì' || c == 'Î' || c == 'Ï') { c = 'i'; }
                if (c == 'ó' || c == 'ò' || c == 'ô' || c == 'ö' || c == 'Ó' || c == 'Ò' || c == 'Ô' || c == 'Ö') { c = 'o'; }
                if (c == 'ú' || c == 'ù' || c == 'û' || c == 'ü' || c == 'Ú' || c == 'Ù' || c == 'Û' || c == 'Ü') { c = 'u'; }
                if (c == '§' || c == '&' || c == '\257') 
                {
                    i++;
                    if(i < text.length())
                    {
                        colorCode = text.charAt(i);
                        if(colorCode == 'a' || colorCode == 'A')
                        	if(!shade)
                        		GL11.glColor4f(0.25F, 1.0F, 0.25F, 1.0F);
                        	else
                        		GL11.glColor4f(0.05F, 0.20F, 0.05F, 1.0F);
                        if(colorCode == 'b' || colorCode == 'B')
                        	if(!shade)
                        		GL11.glColor4f(0.25F, 1.0F, 1.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.05F, 0.20F, 0.20F, 1.0F);
                        if(colorCode == 'c' || colorCode == 'C')
                        	if(!shade)
                        		GL11.glColor4f(1.0F, 0.25F, 0.25F, 1.0F);
                        	else
                        		GL11.glColor4f(0.20F, 0.05F, 0.05F, 1.0F);
                        if(colorCode == 'd' || colorCode == 'D')
                        	if(!shade)
                        		GL11.glColor4f(1.0F, 0.25F, 1.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.20F, 0.05F, 0.20F, 1.0F);
                        if(colorCode == 'e' || colorCode == 'E')
                        	if(!shade)
                        		GL11.glColor4f(1.0F, 1.0F, 0.25F, 1.0F);
                        	else
                        		GL11.glColor4f(0.20F, 0.20F, 0.05F, 1.0F);
                        if(colorCode == 'f' || colorCode == 'F')
                        	if(!shade)
                        		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.20F, 0.20F, 0.20F, 1.0F);
                        if(colorCode == '0')
                        		GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
                        if(colorCode == '1')
                        	if(!shade)
                        		GL11.glColor4f(0.0F, 0.0F, 0.75F, 1.0F);
                        	else
                        		GL11.glColor4f(0.0F, 0.0F, 0.10F, 1.0F);
                        if(colorCode == '2')
                        	if(!shade)
                        		GL11.glColor4f(0.0F, 0.75F, 0.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.0F, 0.10F, 0.0F, 1.0F);
                        if(colorCode == '3')
                        	if(!shade)
                        		GL11.glColor4f(0.0F, 0.75F, 0.75F, 1.0F);
                        	else
                        		GL11.glColor4f(0.0F, 0.10F, 0.10F, 1.0F);
                        if(colorCode == '4')
                        	if(!shade)
                        		GL11.glColor4f(0.75F, 0.0F, 0.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.10F, 0.0F, 0.0F, 1.0F);
                        if(colorCode == '5')
                        	if(!shade)
                        		GL11.glColor4f(0.75F, 0.0F, 0.75F, 1.0F);
                        	else
                        		GL11.glColor4f(0.10F, 0.0F, 0.10F, 1.0F);
                        if(colorCode == '6')
                        	if(!shade)
                        		GL11.glColor4f(1.0F, 0.75F, 0.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.10F, 0.10F, 0.00F, 1.0F);
                        if(colorCode == '7')
                        	if(!shade)
                        		GL11.glColor4f(0.75F, 0.75F, 0.75F, 1.0F);
                        	else
                        		GL11.glColor4f(0.10F, 0.10F, 0.10F, 1.0F);
                        if(colorCode == '8')
                        	if(!shade)
                        		GL11.glColor4f(0.25F, 0.25F, 0.25F, 1.0F);
                        	else
                        		GL11.glColor4f(0.05F, 0.05F, 0.05F, 1.0F);
                        if(colorCode == '9')
                        	if(!shade)
                        		GL11.glColor4f(0.25F, 0.25F, 1.0F, 1.0F);
                        	else
                        		GL11.glColor4f(0.05F, 0.05F, 0.20F, 1.0F);
                        continue;
                    }
                }
                
                if((int)c > 126)
                	continue;
                
                drawChar(c, x, y);
                x += metrics.getStringBounds(""+c, null).getWidth();
        }
    	}
        
    }
    
    private void drawChar(char c, int x, int y)
    {
        Rectangle2D bounds = metrics.getStringBounds(""+c, null);

        Gui gui = new Gui();
        gui.drawTexturedModalRect(x, y - 2, xPos[(byte)c-startChar], yPos[(byte)c-startChar], (int)bounds.getWidth(), (int)bounds.getHeight() + metrics.getMaxDescent() + 2);  
    }


    public int getStringWidth(String text)
    {
        return (int)getBounds(ChatColor.stripColor(text)).getWidth()/2;
    }
	
    public int getStringHeight(String text)
    {
        return (int)getBounds(text).getHeight();
    }
	
    private Rectangle getBounds(String text)
    {
        int w = 0;
        int h = 0;
        int tw = 0;
        if(text != null)
        {
        for (int i=0; i<text.length(); i++)
        {
                char c = text.charAt(i);
                if (c == '\\')
                {
                        char type = text.charAt(i);
                        
                        if (type == 'n')
                        {
                                h += metrics.getAscent() + 2;
                                if (tw > w)
                                        w = tw;
                                tw = 0;
                        }
                        i++;
                        continue;
                }
                tw += metrics.stringWidth(""+c);
        }
        }
        if (tw > w)
                w = tw;
        h += metrics.getAscent();
        return new Rectangle(0, 0, w, h);
    }

	public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5) {
		
		this.renderSplitStringNoShadow(par1Str, par2, par3, par4, par5);
		
	}

	private void renderSplitStringNoShadow(String par1Str, int par2, int par3, int par4, int par5) {
		
		this.renderSplitString(par1Str, par2, par3, par4, par5, false);
		
	}

	public void drawSplitString(String par1Str, int par2, int par3, int par4, int par5, boolean par6) {
		
		this.renderSplitString(par1Str, par2, par3, par4, par5, par6);
		
	}

	private void renderSplitString(String par1Str, int par2, int par3, int par4, int par5, boolean par6) {
		String[] var7 = par1Str.split("\n");
		if (var7.length > 1) {
			for (int var14 = 0; var14 < var7.length; ++var14) {
				this.renderSplitStringNoShadow(var7[var14], par2, par3, par4, par5);
				par3 += this.splitStringWidth(var7[var14], par4);
			}
		} else {
			String[] var8 = par1Str.split(" ");
			int var9 = 0;
			String var10 = "";

			while (var9 < var8.length) {
				String var11;
				for (var11 = var10 + var8[var9++] + " "; var9 < var8.length && this.getStringWidth(var11 + var8[var9]) < par4; var11 = var11 + var8[var9++] + " ") {
					;
				}

				int var12;
				for (; this.getStringWidth(var11) > par4; var11 = var10 + var11.substring(var12)) {
					for (var12 = 0; this.getStringWidth(var11.substring(0, var12 + 1)) <= par4; ++var12) {
						;
					}

					if (var11.substring(0, var12).trim().length() > 0) {
						String var13 = var11.substring(0, var12);
						if (var13.lastIndexOf("\u00a7") >= 0) {
							var10 = "\u00a7" + var13.charAt(var13.lastIndexOf("\u00a7") + 1);
						}

						this.renderString(var13, par2, par3, par6, par5);
						par3 += this.FONT_HEIGHT;
					}
				}

				if (this.getStringWidth(var11.trim()) > 0) {
					if (var11.lastIndexOf("\u00a7") >= 0) {
						var10 = "\u00a7" + var11.charAt(var11.lastIndexOf("\u00a7") + 1);
					}

					this.renderString(var11, par2, par3, par6, par5);
					par3 += this.FONT_HEIGHT;
				}
			}
		}
	}

	public int splitStringWidth(String par1Str, int par2) {
		String[] var3 = par1Str.split("\n");
		int var5;
		if (var3.length > 1) {
			int var9 = 0;

			for (var5 = 0; var5 < var3.length; ++var5) {
				var9 += this.splitStringWidth(var3[var5], par2);
			}

			return var9;
		} else {
			String[] var4 = par1Str.split(" ");
			var5 = 0;
			int var6 = 0;

			while (var5 < var4.length) {
				String var7;
				for (var7 = var4[var5++] + " "; var5 < var4.length && this.getStringWidth(var7 + var4[var5]) < par2; var7 = var7 + var4[var5++] + " ") {
					;
				}

				int var8;
				for (; this.getStringWidth(var7) > par2; var7 = var7.substring(var8)) {
					for (var8 = 0; this.getStringWidth(var7.substring(0, var8 + 1)) <= par2; ++var8) {
						;
					}

					if (var7.substring(0, var8).trim().length() > 0) {
						var6 += this.FONT_HEIGHT;
					}
				}

				if (var7.trim().length() > 0) {
					var6 += this.FONT_HEIGHT;
				}
			}

			if (var6 < this.FONT_HEIGHT) {
				var6 += this.FONT_HEIGHT;
			}

			return var6;
		}
	}

}