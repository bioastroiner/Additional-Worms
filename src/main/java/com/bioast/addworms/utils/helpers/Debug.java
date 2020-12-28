package com.bioast.addworms.utils.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public final class Debug {

    public static List<ITextComponent> cachLog = new ArrayList<>();
    private static int cachMax = 10;
    /**
     * @param text
     * prints a generic Debug log into minecraft chat for fast Debuggings
     */
    public static void log(ITextComponent text){
        clearChat();
        Minecraft.getInstance().player.sendMessage(text);

    }
    /**
     * @param msg to log into minecraft chat
     *            prints a generic Debug log into minecraft chat for fast Debuggings
     *            logs usually got clear by next log
     *            if you want not, you can use override of this methode with isCached Param
     */
    public static void log(String msg){ log(new StringTextComponent(msg).applyTextStyle(TextFormatting.AQUA));}
    /**
     * @param msg
     * @param isCached enables the log to be registered to not be cleared at the next calls ( false by default )
     */
    public static void log(String msg,boolean isCached){
        if(isCached){
            cachLog.add(new StringTextComponent(msg));
        }
        log(msg);
    }
    /**
     * @param msg
     * @param itemStack
     *  logs an itemSatck into the chat for fast Debugging
     */
    public static void log(String msg, ItemStack itemStack) {log(msg + " " + itemStack.getItem().getName().getFormattedText());}
    /**
     * @param nbt
     *
     * logs nbt still incomplete
     */
    public static void logNBT(CompoundNBT nbt){log(nbt.getString()); }
    /**
     * clears the logs and any messages in the chat
     * exceptions are cached logs
     */
    public static void clearChat(){
        clearChat(false);
    }
    /**
     * @param withCach if true also deletes the cached logs, and resets its contents (false by default)
     */
    public static void clearChat(boolean withCach){
        clearChat(withCach,true);
    }
    /**
     * @param withCach same as above
     * @param keepCachInChat if true logs all of the cach contents and relog it after chat cleanups (true by default)
     */
    public static void clearChat(boolean withCach,boolean keepCachInChat){
        if(withCach){
            cachLog.clear();
        }
        Minecraft.getInstance().ingameGUI.getChatGUI().clearChatMessages(false);
        if(cachLog.size() > cachMax){
            cachLog.clear();
        }
        if(!withCach){
            if(keepCachInChat){
                cachLog();
            }
        }
    }
    /**
     *  log the cach's logs regardless of cleanups (cleans chat by default as ussual and may reLog the caches)
     *  mainly to be used internally
     */
    public static void cachLog(){
        for (ITextComponent text:cachLog) {
            log(text.getString());
        }
    }
    public void setCachMax(int cachMax){
        this.cachMax = cachMax;
    }
}
