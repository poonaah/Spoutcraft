package reifnsk.minimap;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;

public class GuiKeyConfigScreen extends GuiScreen implements GuiScreenInterface
{
    private int top;
    private int bottom;
    private int left;
    private int right;
    private GuiSimpleButton okButton;
    private GuiSimpleButton cancelButton;
    private GuiSimpleButton defaultButton;
    private GuiKeyConfigButton edit;
    private int currentKeyCode[];

    GuiKeyConfigScreen()
    {
        KeyInput akeyinput[] = KeyInput.values();
        currentKeyCode = new int[akeyinput.length];

        for (int i = 0; i < currentKeyCode.length; i++)
        {
            currentKeyCode[i] = akeyinput[i].getKey();
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int i = calcLabelWidth();
        int j = calcButtonWidth();
        left = (width - i - j - 12) / 2;
        right = (width + i + j + 12) / 2;
        top = (height - KeyInput.values().length * 10) / 2;
        bottom = (height + KeyInput.values().length * 10) / 2;
        int k = top;
        KeyInput akeyinput[] = KeyInput.values();
        int i1 = akeyinput.length;

        for (int j1 = 0; j1 < i1; j1++)
        {
            KeyInput keyinput = akeyinput[j1];
            GuiKeyConfigButton guikeyconfigbutton = new GuiKeyConfigButton(this, 0, left, k, i, j, keyinput);
            controlList.add(guikeyconfigbutton);
            k += 10;
        }

        int l = width / 2;
        okButton = new GuiSimpleButton(0, l - 74, bottom + 7, 46, 14, "OK");
        controlList.add(okButton);
        cancelButton = new GuiSimpleButton(0, l - 23, bottom + 7, 46, 14, "Cancel");
        controlList.add(cancelButton);
        defaultButton = new GuiSimpleButton(0, l + 28, bottom + 7, 46, 14, "Default");
        controlList.add(defaultButton);
    }

    private int calcLabelWidth()
    {
        FontRenderer fontrenderer = mc.fontRenderer;
        int i = -1;
        KeyInput akeyinput[] = KeyInput.values();
        int j = akeyinput.length;

        for (int k = 0; k < j; k++)
        {
            KeyInput keyinput = akeyinput[k];
            i = Math.max(i, fontrenderer.getStringWidth(keyinput.name()));
        }

        return i;
    }

    private int calcButtonWidth()
    {
        FontRenderer fontrenderer = mc.fontRenderer;
        int i = 30;
        KeyInput akeyinput[] = KeyInput.values();
        int j = akeyinput.length;

        for (int k = 0; k < j; k++)
        {
            KeyInput keyinput = akeyinput[k];
            i = Math.max(i, fontrenderer.getStringWidth((new StringBuilder()).append(">").append(keyinput.getKeyName()).append("<").toString()));
        }

        return i + 2;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        String s = "Key Config";
        int k = fontRenderer.getStringWidth(s);
        int l = width - k >> 1;
        int i1 = width + k >> 1;
        drawRect(l - 2, top - 22, i1 + 2, top - 8, 0xa0000000);
        drawCenteredString(fontRenderer, s, width / 2, top - 19, -1);
        drawRect(left - 2, top - 2, right + 2, bottom + 1, 0xa0000000);
        super.drawScreen(i, j, f);
    }

    GuiKeyConfigButton getEditKeyConfig()
    {
        return edit;
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton instanceof GuiKeyConfigButton)
        {
            edit = (GuiKeyConfigButton)guibutton;
        }

        if (guibutton == okButton)
        {
            if (KeyInput.saveKeyConfig())
            {
                mc.ingameGUI.addChatMessage("\247E[Rei's Minimap] Keyconfig Saved.");
            }
            else
            {
                mc.ingameGUI.addChatMessage("\247E[Rei's Minimap] Error Keyconfig Saving.");
            }

            mc.displayGuiScreen(new GuiOptionScreen());
        }

        if (guibutton == defaultButton)
        {
            KeyInput akeyinput[] = KeyInput.values();
            int i = akeyinput.length;

            for (int k = 0; k < i; k++)
            {
                KeyInput keyinput = akeyinput[k];
                keyinput.setDefault();
            }

            controlList.clear();
            initGui();
        }

        if (guibutton == cancelButton)
        {
            KeyInput akeyinput1[] = KeyInput.values();

            for (int j = 0; j < currentKeyCode.length; j++)
            {
                akeyinput1[j].setKey(currentKeyCode[j]);
            }

            mc.displayGuiScreen(new GuiOptionScreen());
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        if (edit != null)
        {
            edit.getKeyInput().setKey(i);
            edit = null;
            controlList.clear();
            initGui();
        }
        else if (i == 1)
        {
            KeyInput akeyinput[] = KeyInput.values();

            for (int j = 0; j < currentKeyCode.length; j++)
            {
                akeyinput[j].setKey(currentKeyCode[j]);
            }

            mc.displayGuiScreen((GuiScreen)null);
        }
    }
}
