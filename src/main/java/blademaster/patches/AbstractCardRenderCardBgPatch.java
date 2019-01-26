package blademaster.patches;


import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import blademaster.Blademaster;


@SpirePatch(
        clz=AbstractCard.class,
        method="renderCardBg"
)
public class AbstractCardRenderCardBgPatch {

    public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {
        Color reflectedColor = (Color)ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
        boolean isFury = Blademaster.FuryFinisher(__card_instance);
        boolean isCombo = Blademaster.ComboFinisher(__card_instance);
        boolean isWind = Blademaster.WindCard(__card_instance);
        boolean isLightning = Blademaster.LightningCard(__card_instance);
        if (isFury) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/512/FuryFinisherSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
        if (isCombo) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/512/ComboFinisherSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
        if (isWind) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/512/WindCardSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
        if (isLightning) {
            Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/512/LightningCardSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
    }
}