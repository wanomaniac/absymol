package com.wano.abysmol.mixin.Client.Graphics.gui.menus;

import com.wano.abysmol.AbysmolARGB;
import net.minecraft.client.gui.components.EditBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Changed default edit box color to black since bg is white.
@Mixin(EditBox.class)
public class EditBoxMixin {
    @Inject(method = "<init>(Lnet/minecraft/client/gui/Font;IIIILnet/minecraft/client/gui/components/EditBox;Lnet/minecraft/network/chat/Component;)V", at = @At("RETURN"))
    private void overrideDefaultColors(CallbackInfo ci) {
        EditBox self = (EditBox) (Object) this;
        self.setTextColor(AbysmolARGB.black(255));
        self.setTextColorUneditable(AbysmolARGB.color(250, 70, 70, 70));
    }
}
