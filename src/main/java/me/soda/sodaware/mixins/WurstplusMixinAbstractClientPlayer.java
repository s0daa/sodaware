package me.soda.sodaware.mixins;

import me.soda.sodaware.Sodaware;
import me.soda.sodaware.client.modules.capes.Capes;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Crystalinqq
 * Updated by 20kdc on 17/02/20 - changed implementation method, made a module again, made async
 */
@Mixin(AbstractClientPlayer.class)
public class WurstplusMixinAbstractClientPlayer {

    @Inject(method = "getLocationCape", at = @At(value = "RETURN"), cancellable = true)
    public void getCape(CallbackInfoReturnable<ResourceLocation> callbackInfo) {
        if (Capes.INSTANCE == null)
            return;
        if ((!Capes.overrideOtherCapes && callbackInfo.getReturnValue() != null))
            return;
        ResourceLocation sodaCape = Capes.getCapeResource((AbstractClientPlayer) (Object) this);
        if (sodaCape != null)
            callbackInfo.setReturnValue(sodaCape);
    }
}
