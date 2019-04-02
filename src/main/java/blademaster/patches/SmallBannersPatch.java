package blademaster.patches;

import basemod.ReflectionHacks;
import blademaster.Blademaster;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

@SpirePatch (
        clz = AbstractCard.class,
        method = "renderPortraitFrame"
)
public class SmallBannersPatch {

    public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {
        Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
        boolean Wind = Blademaster.WindCard(__card_instance);
        boolean Lightning = Blademaster.LightningCard(__card_instance);
        boolean Combo = Blademaster.ComboCard(__card_instance);
        boolean Fury = Blademaster.FuryCard(__card_instance);

        if (Wind) {
            Texture WindB = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindBannerSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(WindB, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
        if (Lightning) {
            Texture LightningB = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningBannerSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(LightningB, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
        if (Combo) {
            Texture WindB = Blademaster.loadBgAddonTexture("blademasterResources/images/512/ComboBannerSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(WindB, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
        if (Fury) {
            Texture LightningB = Blademaster.loadBgAddonTexture("blademasterResources/images/512/FuryBannerSmall.png");
            sb.setColor(reflectedColor);
            sb.draw(LightningB, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
        }
    }
}