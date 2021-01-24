package com.bioast.addworms.utils.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public final class Debug {

    public static List<ITextComponent> cachLog = new ArrayList<>();
    private static int cachMax = 10;

    /**
     * @param text prints a generic Debug log into minecraft chat for fast Debuggings clear the chat afterwards
     */
    public static void log(ITextComponent text) {
        clearChat();
        logClearless(text);
    }

    /**
     * @param text prints a generic Debug log into minecraft chat for fast Debuggings without clearing
     */
    public static void logClearless(ITextComponent text) {
        Minecraft.getInstance()
                .player
                .sendMessage(
                        text,
                        UUID.randomUUID()
                );

    }

    public static void logClearless(String msg) {
        logClearless(new StringTextComponent(msg));
    }

    /**
     * @param msg to log into minecraft chat
     *            prints a generic Debug log into minecraft chat for fast Debuggings
     *            logs usually got clear by next log
     *            if you want not, you can use override of this methode with isCached Param
     */
    public static void log(String msg) {
        log(
                new StringTextComponent(msg)
                        .setStyle(
                                Style.EMPTY
                                        .setFormatting(
                                                TextFormatting.AQUA)
                        )
        );
    }

    /**
     * @param msg
     * @param isCached enables the log to be registered to not be cleared at the next calls ( false by default )
     */
    public static void log(
            String msg,
            boolean isCached
    ) {
        if (isCached)
            cachLog
                    .add(
                            new StringTextComponent(msg)
                    );
        log(msg);
    }

    /**
     * @param msg
     * @param itemStack logs an itemSatck into the chat for fast Debugging
     */
    public static void log(
            String msg,
            ItemStack itemStack
    ) {
        log(msg + " " +
                itemStack
                        .getItem()
                        .getName()
                        .getString()
        );
    }

    public static void log(BlockPos pos, @Nullable String msg) {
        if (msg == null) msg = "";
        log(new StringTextComponent(msg + "at:"));
        logClearless(new StringTextComponent("X:" + pos.getX()));
        logClearless(new StringTextComponent("Y:" + pos.getY()));
        logClearless(new StringTextComponent("Z:" + pos.getZ()));
    }

    /**
     * @param nbt logs nbt still incomplete
     */
    public static void logNBT(CompoundNBT nbt) {
        log(nbt.getString());
    }

    /**
     * clears the logs and any messages in the chat
     * exceptions are cached logs
     */
    public static void clearChat() {
        clearChat(false);
    }

    /**
     * @param withCach if true also deletes the cached logs, and resets its contents (false by default)
     */
    public static void clearChat(boolean withCach) {
        clearChat(
                withCach,
                true
        );
    }

    /**
     * @param withCach       same as above
     * @param keepCachInChat if true logs all of the cach contents and relog it after chat cleanups (true by default)
     */
    public static void clearChat(
            boolean withCach,
            boolean keepCachInChat
    ) {
        if (withCach)
            cachLog.clear();

        Minecraft.getInstance()
                .ingameGUI
                .getChatGUI()
                .clearChatMessages(false)
        ;
        if (cachLog.size() > cachMax)
            cachLog.clear();

        if (!withCach)
            if (keepCachInChat)
                cachLog();
    }

    /**
     * log the cach's logs regardless of cleanups (cleans chat by default as ussual and may reLog the caches)
     * mainly to be used internally
     */
    public static void cachLog() {
        for (
                ITextComponent text :
                cachLog
        ) {
            log(
                    text.getString()
            );
        }
    }

    @SuppressWarnings("unused")
    public static void setCachMax(int cachMax) {
        Debug.cachMax = cachMax;
    }
}
