package com.wano.abysmol.mixin.Client.Graphics;

import com.mojang.blaze3d.platform.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

// long name
@Mixin(NativeImage.Format.class)
public class NIFormatAccessor {

    @Invoker("getStbFormat")
    public static NativeImage.Format getStbFormat(final int i){
        throw new AssertionError();
    }
}
