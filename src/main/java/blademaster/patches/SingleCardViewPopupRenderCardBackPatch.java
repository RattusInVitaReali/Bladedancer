package blademaster.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import blademaster.Blademaster;


@SpirePatch(
        clz = SingleCardViewPopup.class,
        method = "renderCardBack"
)
public class SingleCardViewPopupRenderCardBackPatch {
    public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
        AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
        boolean isFury = Blademaster.FuryFinisher(reflectedCard);
        boolean isCombo = Blademaster.ComboFinisher(reflectedCard);
        boolean isWind = Blademaster.WindCard(reflectedCard);
        boolean isLightning = Blademaster.LightningCard(reflectedCard);

        if (isFury) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/1024/FuryFinisher.png");
            sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        } else if (isCombo) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/1024/ComboFinisher.png");
            sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        } else if (isWind) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/1024/WindCard.png");
            sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        } else if (isLightning) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/1024/LightningCard.png");
            sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        }
    }
}