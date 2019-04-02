package blademaster.patches;

import basemod.ReflectionHacks;
import blademaster.Blademaster;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

@SpirePatch (
        clz = SingleCardViewPopup.class,
        method = "renderFrame"
)
public class BigBannersPatch {
    public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
        AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
        boolean Wind = Blademaster.WindCard(reflectedCard);
        boolean Lightning = Blademaster.LightningCard(reflectedCard);
        boolean Combo = Blademaster.ComboCard(reflectedCard);
        boolean Fury = Blademaster.FuryCard(reflectedCard);

        if (Wind) {
            Texture WindB = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/WindBanner.png");
            sb.draw(WindB, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        }
        if (Lightning) {
            Texture LightningB = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/LightningBanner.png");
            sb.draw(LightningB, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        }
        if (Combo) {
            Texture WindB = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/ComboBanner.png");
            sb.draw(WindB, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        }
        if (Fury) {
            Texture LightningB = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/FuryBanner.png");
            sb.draw(LightningB, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
        }
    }
}