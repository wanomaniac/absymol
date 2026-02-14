package com.wano.abysmol.mixin.Graphics;

import com.wano.abysmol.AbsymolShaders;
import net.minecraft.client.gui.font.GlyphRenderTypes;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// this should fix the text color issues.

@Mixin(targets = "net.minecraft.client.gui.font.GlyphRenderTypes")
public class GlyphPipelineFixMixin {
    @Inject(
            method = "createForColorTexture",
            at = @At(
                    "HEAD"
            ), cancellable = true
    )
    private static void redirectColorTexturePipeline(
            Identifier id, CallbackInfoReturnable<GlyphRenderTypes> cir
    ) {
        cir.setReturnValue(new GlyphRenderTypes(RenderTypes.text(id), RenderTypes.textSeeThrough(id), RenderTypes.textPolygonOffset(id), AbsymolShaders.PROPER_GUI_TEXT));
    }
}