package com.wano.abysmol;

import com.mojang.serialization.Codec;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import java.util.function.Supplier;

public class AbysmolAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENTS =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "abysmol");

    public static final Supplier<AttachmentType<@NotNull Boolean>> SOLAR =
            ATTACHMENTS.register("solar", () ->
                    AttachmentType.builder(() -> false) // default value
                            .serialize(Codec.BOOL.fieldOf("solar"))
                            .build()
            );
}
