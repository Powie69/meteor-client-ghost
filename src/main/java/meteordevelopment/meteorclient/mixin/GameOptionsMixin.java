/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.mixin;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.events.game.ChangePerspectiveEvent;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.systems.modules.render.Freecam;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public abstract class GameOptionsMixin {
    @Shadow @Final @Mutable public KeyBinding[] allKeys;

    @Inject(method = "setPerspective", at = @At("HEAD"), cancellable = true)
    private void setPerspective(Perspective perspective, CallbackInfo info) {
        if (Modules.get() == null) return; // nothing is loaded yet, shouldersurfing compat

        ChangePerspectiveEvent event = MeteorClient.EVENT_BUS.post(ChangePerspectiveEvent.get(perspective));

        if (event.isCancelled()) info.cancel();

        if (Modules.get().isActive(Freecam.class)) info.cancel();
    }
}
