package com.wano.abysmol.mixin.Graphics;

import com.mojang.blaze3d.buffers.Std140Builder;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Std140Builder.class)
public class Std140BuilderMixin {
//    @ModifyVariable(
//            method = "putFloat",
//            at = @At("HEAD"),
//            argsOnly = true
//    )
//    private float invertFloat(float value) {
//        int bits = Float.floatToRawIntBits(value);
//        bits ^= 0x8000_0000; // flip sign bit
//        return Float.intBitsToFloat(bits);
//    }

//    @ModifyVariable(
//            method = "putInt",
//            at = @At("HEAD"),
//            argsOnly = true
//    )
//    private int invertInt(int value) {
//        return ~value;
//    }
}
