package blademaster.patches;

import basemod.ReflectionHacks;
import blademaster.Blademaster;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;


@SpirePatch (
        clz = SingleCardViewPopup.class,
        method = "renderCardBack"
)
public class SingleViewPopupBackgroundRenderPatch {
    public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
        if (CardCrawlGame.isInARun()) {
            AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            boolean isWind = Blademaster.WindCard(reflectedCard);
            boolean isLightning = Blademaster.LightningCard(reflectedCard);
            reflectedCard.update();
            reflectedCard.initializeDescription();
            if (isWind) {
                if (reflectedCard.type == AbstractCard.CardType.ATTACK) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/WindAttack.png");
                    sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
                }
                if (reflectedCard.type == AbstractCard.CardType.SKILL) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/WindSkill.png");
                    sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
                }
                if (reflectedCard.type == AbstractCard.CardType.POWER) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/WindPower.png");
                    sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
                }
            } else if (isLightning) {
                if (reflectedCard.type == AbstractCard.CardType.ATTACK) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("defaultModResources/images/1024/LightningAttack.png");
                    sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
                }
                if (reflectedCard.type == AbstractCard.CardType.SKILL) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/LightningSkill.png");
                    sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
                }
                if (reflectedCard.type == AbstractCard.CardType.POWER) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/1024/LightningPower.png");
                    sb.draw(extraPowerBG, Settings.WIDTH / 2.0f - 512.0f, Settings.HEIGHT / 2.0f - 512.0f, 512.0f, 512.0f, 1024.0f, 1024.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 1024, 1024, false, false);
                }
            }
        }
    }
}