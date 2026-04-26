package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// PATCH - IP server selection title is unreadable with white, changed to black.
@Mixin(ServerSelectionList.OnlineServerEntry.class)
public abstract class OnlineServerSelectionEntryMixin {
    @Shadow @Final private ServerData serverData;

    @ModifyArg(method = "renderContent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V"), index = 4)
    private static int replaceUnreadableColor(int originalColor){
        return AbysmolARGB.black(255);
    }

    @Unique
    private static final Identifier STATUS_ICONS = Identifier.fromNamespaceAndPath("abysmol", "textures/gui/status_icons_2.png");
    @Inject(method = "renderContent", at = @At("TAIL"))
    private void abysmol$drawNextToIcon(GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        ServerSelectionList.OnlineServerEntry self = (ServerSelectionList.OnlineServerEntry)(Object)this;
        if(this.serverData.state() == ServerData.State.UNREACHABLE) return;
        String versionText = this.serverData.version.getString();
        boolean isABYSMOL = versionText.contains("[ABYSMOL]");

        int iconX = self.getContentX();
        int iconY = self.getContentY();
        if (isABYSMOL) {
            graphics.blit(RenderPipelines.GUI_TEXTURED, STATUS_ICONS, iconX, iconY, 0, 0, 8, 8, 16, 8);
        } else {
            graphics.blit(RenderPipelines.GUI_TEXTURED, STATUS_ICONS, iconX, iconY, 8, 0, 8, 8, 16, 8);
        }

        if (mouseX >= iconX && mouseX <= iconX + 8 && mouseY >= iconY && mouseY <= iconY + 8) {
            Component tooltip = isABYSMOL ?
                    Component.literal("§cSupports ABYSMOL! You can play this server!") :
                    Component.literal("§aNo support for ABYSMOL. You cannot play this server!");

            graphics.setTooltipForNextFrame(tooltip, mouseX, mouseY);
        }
    }
}
