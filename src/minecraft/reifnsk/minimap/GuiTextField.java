package reifnsk.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

public class GuiTextField extends GuiButton
{
    private static GuiTextField active;
    private int inputType;
    private GuiTextField prev;
    private GuiTextField next;
    private int norm;

    public GuiTextField(String s)
    {
        super(0, 0, 0, 0, 0, s);
        norm = 0;
    }

    public GuiTextField()
    {
        super(0, 0, 0, 0, 0, "");
        norm = 0;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int i, int j)
    {
        int k = active != this ? 0x80606060 : 0x80c0c0c0;
        drawRect(xPosition, yPosition, xPosition + width, yPosition + height, k);

        if (inputType == 0)
        {
            drawCenteredString(minecraft.fontRenderer, displayString, xPosition + width / 2, yPosition + 1, -1);
        }
        else
        {
            int l = minecraft.fontRenderer.getStringWidth(displayString);
            drawString(minecraft.fontRenderer, displayString, (xPosition + width) - l - 1, yPosition + 1, -1);
        }
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft minecraft, int i, int j)
    {
        if (i >= xPosition && i < xPosition + width && j >= yPosition && j < yPosition + height)
        {
            active();
        }

        return false;
    }

    public void active()
    {
        if (active != null)
        {
            active.norm();
        }

        active = this;
    }

    static void keyType(Minecraft minecraft, char c, int i)
    {
        if (active != null)
        {
            active.kt(minecraft, c, i);
        }
    }

    private void kt(Minecraft minecraft, char c, int i)
    {
        if (inputType == 0 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157)) && i == 47)
        {
            String s = GuiScreen.getClipboardString();

            if (s == null)
            {
                return;
            }

            int j = 0;

            for (int i1 = s.length(); j < i1; j++)
            {
                char c1 = s.charAt(j);

                if (c1 == '\r' || c1 == '\n')
                {
                    continue;
                }

                if (c1 == ':')
                {
                    c1 = ';';
                }

                String s2 = (new StringBuilder()).append(displayString).append(c1).toString();

                if (minecraft.fontRenderer.getStringWidth(s2) >= width - 2)
                {
                    break;
                }

                displayString = s2;
            }
        }

        if (i == 14 || i == 211)
        {
            if (!displayString.isEmpty())
            {
                displayString = displayString.substring(0, displayString.length() - 1);
            }

            return;
        }

        if (i == 15)
        {
            if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54))
            {
                prev();
            }
            else
            {
                next();
            }
        }

        if (i == 28)
        {
            next();
        }

        if (checkInput(c))
        {
            String s1 = (new StringBuilder()).append(displayString).append(c).toString();

            if (minecraft.fontRenderer.getStringWidth(s1) < width - 2)
            {
                try
                {
                    if (inputType == 1)
                    {
                        int k = Integer.parseInt(s1);
                        s1 = k >= 0xfe17b800 ? k < 0x1e84800 ? Integer.toString(k) : "31999999" : "-32000000";
                    }

                    if (inputType == 2)
                    {
                        int l = Integer.parseInt(s1);
                        s1 = l >= 0 ? l <= ReiMinimap.instance.getWorldHeight() + 2 ? Integer.toString(l) : Integer.toString(ReiMinimap.instance.getWorldHeight() + 2) : "0";
                    }
                }
                catch (NumberFormatException numberformatexception) { }

                displayString = s1;
            }
        }
    }

    boolean checkInput(char c)
    {
        switch (inputType)
        {
            case 0:
                return " !\"#$%&'()*+,-./0123456789;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u2302\307\374\351\342\344\340\345\347\352\353\350\357\356\354\304\305\311\346\306\364\366\362\373\371\377\326\334\370\243\330\327\u0192\341\355\363\372\361\321\252\272\277\256\254\275\274\241\253\273".indexOf(c) != -1;

            case 1:
                return (displayString.isEmpty() ? "-0123456789" : "0123456789").indexOf(c) != -1;

            case 2:
                return "0123456789".indexOf(c) != -1;
        }

        return false;
    }

    void norm()
    {
        String s = displayString;

        try
        {
            if (inputType == 1)
            {
                int i = Integer.parseInt(s);
                s = i >= 0xfe17b800 ? i < 0x1e84800 ? Integer.toString(i) : "31999999" : "-32000000";
            }

            if (inputType == 2)
            {
                int j = Integer.parseInt(s);
                s = j >= 0 ? j <= ReiMinimap.instance.getWorldHeight() + 2 ? Integer.toString(j) : Integer.toString(ReiMinimap.instance.getWorldHeight() + 2) : "0";
            }
        }
        catch (NumberFormatException numberformatexception)
        {
            s = Integer.toString(norm);
        }

        displayString = s;
    }

    void setInputType(int i)
    {
        inputType = i;
    }

    void setPosition(int i, int j)
    {
        xPosition = i;
        yPosition = j;
    }

    void setSize(int i, int j)
    {
        width = i;
        height = j;
    }

    void setBounds(int i, int j, int k, int l)
    {
        xPosition = i;
        yPosition = j;
        width = k;
        height = l;
    }

    void setNext(GuiTextField guitextfield)
    {
        next = guitextfield;
    }

    void setPrev(GuiTextField guitextfield)
    {
        prev = guitextfield;
    }

    static void next()
    {
        if (active != null)
        {
            active.norm();
            active = active.next;
        }
    }

    static void prev()
    {
        if (active != null)
        {
            active.norm();
            active = active.prev;
        }
    }

    static GuiTextField getActive()
    {
        return active;
    }

    void setNorm(int i)
    {
        norm = i;
    }
}
