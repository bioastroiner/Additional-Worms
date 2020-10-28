package com.bioast.addworms.utils.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Debug {
    public static void log(String msg){
        Minecraft.getInstance().player.sendMessage(new StringTextComponent(msg));
    }
    public static void log(String msg, ItemStack itemStack){ Minecraft.getInstance().player.sendMessage(new StringTextComponent(msg + " " + itemStack.getItem().getName().getFormattedText())); }

}
