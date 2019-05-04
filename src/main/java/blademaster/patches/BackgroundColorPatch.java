package blademaster.patches;


import basemod.ReflectionHacks;
import blademaster.Blademaster;
import blademaster.cards.AbstractStanceCard;
import blademaster.powers.BasicStance;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;


@SpirePatch (
        clz = AbstractCard.class,
        method = "renderCardBg"
)
public class BackgroundColorPatch {

    public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {
        Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
        if (__card_instance instanceof AbstractStanceCard) {
            if (((AbstractStanceCard) __card_instance).WindArtS) {
                if (__card_instance.type == AbstractCard.CardType.ATTACK) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindAttackSmall.png");
                    sb.setColor(reflectedColor);
                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                }
                if (__card_instance.type == AbstractCard.CardType.SKILL) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindSkillSmall.png");
                    sb.setColor(reflectedColor);
                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                }
                if (__card_instance.type == AbstractCard.CardType.POWER) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindPowerSmall.png");
                    sb.setColor(reflectedColor);
                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                }
            } else if (((AbstractStanceCard) __card_instance).LightningArtS) {
                if (__card_instance.type == AbstractCard.CardType.ATTACK) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningAttackSmall.png");
                    sb.setColor(reflectedColor);
                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                }
                if (__card_instance.type == AbstractCard.CardType.SKILL) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningSkillSmall.png");
                    sb.setColor(reflectedColor);
                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                }
                if (__card_instance.type == AbstractCard.CardType.POWER) {
                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningPowerSmall.png");
                    sb.setColor(reflectedColor);
                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                }
            } else if (CardCrawlGame.isInARun()) {
                if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
                    if (! AbstractDungeon.getMonsters().areMonstersDead()) {
                        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
                            if (__card_instance.hasTag(BlademasterTags.WIND_STANCE)) {
                                if (__card_instance.type == AbstractCard.CardType.ATTACK) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindAttackSmall.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                                if (__card_instance.type == AbstractCard.CardType.SKILL) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindSkillSmall.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                                if (__card_instance.type == AbstractCard.CardType.POWER) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/WindPowerSmall.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                            }
                        }
                        if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
                            if (__card_instance.hasTag(BlademasterTags.LIGHTNING_STANCE)) {
                                if (__card_instance.type == AbstractCard.CardType.ATTACK) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningAttackSmall.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                                if (__card_instance.type == AbstractCard.CardType.SKILL) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningSkillSmall.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                                if (__card_instance.type == AbstractCard.CardType.POWER) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/LightningPowerSmall.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                            }
                        }
                        if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
                            if (__card_instance.hasTag(BlademasterTags.WIND_STANCE) || __card_instance.hasTag(BlademasterTags.LIGHTNING_STANCE)) {
                                if (__card_instance.type == AbstractCard.CardType.ATTACK) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/bg_attack_default_gray.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                                if (__card_instance.type == AbstractCard.CardType.SKILL) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/bg_skill_default_gray.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                                if (__card_instance.type == AbstractCard.CardType.POWER) {
                                    Texture extraPowerBG = Blademaster.loadBgAddonTexture("blademasterResources/images/512/bg_power_default_gray.png");
                                    sb.setColor(reflectedColor);
                                    sb.draw(extraPowerBG, x, y, 256f, 256f, 512f, 512f, __card_instance.drawScale * Settings.scale, __card_instance.drawScale * Settings.scale, __card_instance.angle, 0, 0, 512, 512, false, false);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}