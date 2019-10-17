package blademaster.patches;

import basemod.ReflectionHacks;
import blademaster.Blademaster;
import blademaster.cards.AbstractStanceCard;
import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class BackgroundAdditionsPatch {
    private static Method cardRenderHelperMethod;
    private static Method screenRenderHelperMethod;
    private static boolean renderReflectFailureNotified = false;
    private static boolean renderActualFailureNotified = false;

    private enum BackgroundTextureToRender {
        ATTACK_WIND,
        ATTACK_LIGHTNING,
        SKILL_WIND,
        SKILL_LIGHTNING,
        POWER_WIND,
        POWER_LIGHTNING
    }

    private enum PortraitTextureToRender {
        WIND,
        LIGHTNING,
        FURY,
        COMBO
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderCardBg"
    )
    public static class BackgroundColorPatch {
        private static HashMap<BackgroundTextureToRender, TextureAtlas.AtlasRegion> textureMap;

        public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {
            if (cardRenderHelperMethod == null) {
                try {
                    cardRenderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                } catch (NoSuchMethodException e) {
                    if (!renderReflectFailureNotified) {
                        renderReflectFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to reflect AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            if (textureMap == null) {
                textureMap = new HashMap<>();
                textureMap.put(BackgroundTextureToRender.ATTACK_WIND, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/WindAttackSmall.png"), 0, 0, 512, 512));
                textureMap.put(BackgroundTextureToRender.ATTACK_LIGHTNING, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/LightningAttackSmall.png"), 0, 0, 512, 512));
                textureMap.put(BackgroundTextureToRender.SKILL_WIND, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/WindSkillSmall.png"), 0, 0, 512, 512));
                textureMap.put(BackgroundTextureToRender.SKILL_LIGHTNING, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/LightningSkillSmall.png"), 0, 0, 512, 512));
                textureMap.put(BackgroundTextureToRender.POWER_WIND, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/WindPowerSmall.png"), 0, 0, 512, 512));
                textureMap.put(BackgroundTextureToRender.POWER_LIGHTNING, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/LightningPowerSmall.png"), 0, 0, 512, 512));
            }
            BackgroundTextureToRender BackgroundTextureToRender = null;
            if (__card_instance instanceof AbstractStanceCard) {
                if (CardCrawlGame.isInARun()) {
                    if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
                        switch (__card_instance.type) {
                            case ATTACK:
                                BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.ATTACK_WIND;
                                break;
                            case SKILL:
                                BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.SKILL_WIND;
                                break;
                            case POWER:
                                BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.POWER_WIND;
                        }
                    }
                    if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
                        switch (__card_instance.type) {
                            case ATTACK:
                                BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.ATTACK_LIGHTNING;
                                break;
                            case SKILL:
                                BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.SKILL_LIGHTNING;
                                break;
                            case POWER:
                                BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.POWER_LIGHTNING;
                        }
                    }
                }
                if (((AbstractStanceCard) __card_instance).WindArtS) {
                    switch (__card_instance.type) {
                        case ATTACK:
                            BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.ATTACK_WIND;
                            break;
                        case SKILL:
                            BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.SKILL_WIND;
                            break;
                        case POWER:
                            BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.POWER_WIND;
                    }
                }
                if (((AbstractStanceCard) __card_instance).LightningArtS) {
                    switch (__card_instance.type) {
                        case ATTACK:
                            BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.ATTACK_LIGHTNING;
                            break;
                        case SKILL:
                            BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.SKILL_LIGHTNING;
                            break;
                        case POWER:
                            BackgroundTextureToRender = BackgroundAdditionsPatch.BackgroundTextureToRender.POWER_LIGHTNING;
                    }
                }
                if (BackgroundTextureToRender != null) {
                    try {
                        Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
                        cardRenderHelperMethod.setAccessible(true);
                        cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, textureMap.get(BackgroundTextureToRender), x, y);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        if (!renderActualFailureNotified) {
                            renderActualFailureNotified = true;
                            System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = AbstractCard.class,
            method = "renderPortraitFrame"
    )
    public static class SmallBannersPatch {
        private static HashMap<PortraitTextureToRender, TextureAtlas.AtlasRegion> bannerTextureMap;

        public static void Postfix(AbstractCard __card_instance, SpriteBatch sb, float x, float y) {
            if (cardRenderHelperMethod == null) {
                try {
                    cardRenderHelperMethod = AbstractCard.class.getDeclaredMethod("renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class);
                } catch (NoSuchMethodException e) {
                    if (!renderReflectFailureNotified) {
                        renderReflectFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to reflect AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            if (bannerTextureMap == null) {
                bannerTextureMap = new HashMap<>();
                bannerTextureMap.put(PortraitTextureToRender.WIND, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/WindBannerSmall.png"), 0, 0, 512, 512));
                bannerTextureMap.put(PortraitTextureToRender.LIGHTNING, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/LightningBannerSmall.png"), 0, 0, 512, 512));
                bannerTextureMap.put(PortraitTextureToRender.FURY, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/FuryBannerSmall.png"), 0, 0, 512, 512));
                bannerTextureMap.put(PortraitTextureToRender.COMBO, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/ComboBannerSmall.png"), 0, 0, 512, 512));
            }
            boolean Wind = Blademaster.WindCard(__card_instance);
            boolean Lightning = Blademaster.LightningCard(__card_instance);
            boolean Combo = Blademaster.ComboCard(__card_instance);
            boolean Fury = Blademaster.FuryCard(__card_instance);
            PortraitTextureToRender PortraitTextureToRender = null;
            if (Wind) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.WIND;
            }
            if (PortraitTextureToRender != null) {
                try {
                    Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
                    cardRenderHelperMethod.setAccessible(true);
                    cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, bannerTextureMap.get(PortraitTextureToRender), x, y);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            PortraitTextureToRender = null;
            if (Lightning) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.LIGHTNING;
            }
            if (PortraitTextureToRender != null) {
                try {
                    Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
                    cardRenderHelperMethod.setAccessible(true);
                    cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, bannerTextureMap.get(PortraitTextureToRender), x, y);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            PortraitTextureToRender = null;
            if (Fury) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.FURY;
            }
            if (PortraitTextureToRender != null) {
                try {
                    Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
                    cardRenderHelperMethod.setAccessible(true);
                    cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, bannerTextureMap.get(PortraitTextureToRender), x, y);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            PortraitTextureToRender = null;
            if (Combo) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.COMBO;
            }
            if (PortraitTextureToRender != null) {
                try {
                    Color reflectedColor = (Color) ReflectionHacks.getPrivate(__card_instance, AbstractCard.class, "renderColor");
                    cardRenderHelperMethod.setAccessible(true);
                    cardRenderHelperMethod.invoke(__card_instance, sb, reflectedColor, bannerTextureMap.get(PortraitTextureToRender), x, y);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke AbstractCard method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardBack"
    )
    public static class SingleCardViewPopupRenderCardBackPatch {
        private static HashMap<PortraitTextureToRender, TextureAtlas.AtlasRegion> bigTextureMap;

        public static void Postfix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard reflectedCard = (AbstractCard) ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (screenRenderHelperMethod == null) {
                try {
                    screenRenderHelperMethod = SingleCardViewPopup.class.getDeclaredMethod("renderHelper", SpriteBatch.class, float.class, float.class, TextureAtlas.AtlasRegion.class);
                } catch (NoSuchMethodException e) {
                    if (!renderReflectFailureNotified) {
                        renderReflectFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to reflect SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            if (bigTextureMap == null) {
                bigTextureMap = new HashMap<>();
                bigTextureMap.put(PortraitTextureToRender.WIND, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/1024/WindBanner.png"), 0, 0, 1024, 1024));
                bigTextureMap.put(PortraitTextureToRender.LIGHTNING, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/1024/LightningBanner.png"), 0, 0, 1024, 1024));
                bigTextureMap.put(PortraitTextureToRender.FURY, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/1024/FuryBanner.png"), 0, 0, 10242, 1024));
                bigTextureMap.put(PortraitTextureToRender.COMBO, new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/1024/ComboBanner.png"), 0, 0, 1024, 1024));
            }
            boolean Wind = Blademaster.WindCard(reflectedCard);
            boolean Lightning = Blademaster.LightningCard(reflectedCard);
            boolean Combo = Blademaster.ComboCard(reflectedCard);
            boolean Fury = Blademaster.FuryCard(reflectedCard);
            PortraitTextureToRender PortraitTextureToRender = null;
            if (Wind) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.WIND;
            }
            if (PortraitTextureToRender != null) {
                try {
                    screenRenderHelperMethod.setAccessible(true);
                    screenRenderHelperMethod.invoke(__instance, sb, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, bigTextureMap.get(PortraitTextureToRender));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            PortraitTextureToRender = null;
            if (Lightning) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.LIGHTNING;
            }
            if (PortraitTextureToRender != null) {
                try {
                    screenRenderHelperMethod.setAccessible(true);
                    screenRenderHelperMethod.invoke(__instance, sb, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, bigTextureMap.get(PortraitTextureToRender));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            PortraitTextureToRender = null;
            if (Fury) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.FURY;
            }
            if (PortraitTextureToRender != null) {
                try {
                    screenRenderHelperMethod.setAccessible(true);
                    screenRenderHelperMethod.invoke(__instance, sb, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, bigTextureMap.get(PortraitTextureToRender));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
            PortraitTextureToRender = null;
            if (Combo) {
                PortraitTextureToRender = BackgroundAdditionsPatch.PortraitTextureToRender.COMBO;
            }
            if (PortraitTextureToRender != null) {
                try {
                    screenRenderHelperMethod.setAccessible(true);
                    screenRenderHelperMethod.invoke(__instance, sb, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, bigTextureMap.get(PortraitTextureToRender));
                } catch (IllegalAccessException | InvocationTargetException e) {
                    if (!renderActualFailureNotified) {
                        renderActualFailureNotified = true;
                        System.out.println("ALERT: Blademaster failed to invoke SingleCardViewPopup method renderHelper");
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
