package blademaster.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindChargeDescriptionPatch {
    private static Pattern r = Pattern.compile("\\[([RGBW])\\](\\.?) ");
    public static TextureAtlas.AtlasRegion WindChargeRegion = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/WindCharge.png"), 0, 0, 24, 24);


    @SpirePatch (
            clz = AbstractCard.class,
            method = "renderDescription"
    )
    public static class AlterTmp {
        @SpireInsertPatch (
                rloc = 31,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] tmp) {
            Matcher m = r.matcher(tmp[0]);
            if (m.find()) {
                if (__instance.color.equals(AbstractCardEnum.DEFAULT_GRAY)) {
                    tmp[0] = "[W]" + (m.group(2).equals(".") ? "." : "") + " ";
                }
            }
        }
    }

    @SpirePatch (
            clz = AbstractCard.class,
            method = "renderDescriptionCN"
    )
    public static class AlterTmpCN {
        @SpireInsertPatch (
                rloc = 35,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] tmp) {
            Matcher m = r.matcher(tmp[0]);
            if (m.find()) {
                tmp[0] = "[W]" + (m.group(2).equals(".") ? "." : "") + " ";
            }
        }
    }

    @SpirePatch (
            clz = AbstractCard.class,
            method = "renderDescription"
    )
    @SpirePatch (
            clz = AbstractCard.class,
            method = "renderDescriptionCN"
    )


    public static class RenderSmallEnergyOrb {
        private static final float CARD_ENERGY_IMG_WIDTH = 24.0f * Settings.scale;

        @SpireInsertPatch (
                locator = Locator.class,
                localvars = {"spacing", "i", "start_x", "draw_y", "font", "textColor", "tmp", "gl"}
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb, float spacing, int i, @ByRef float[] start_x, float draw_y,
                                  BitmapFont font, Color textColor, @ByRef String[] tmp, GlyphLayout gl) {
            Matcher m = r.matcher(tmp[0]);
            if (tmp[0].equals("[W]") || m.find()) {
                if (__instance.color.equals(AbstractCardEnum.DEFAULT_GRAY)) {
                    gl.width = CARD_ENERGY_IMG_WIDTH * __instance.drawScale;
                    float tmp2 = (__instance.description.size() - 4) * spacing;
                    __instance.renderSmallEnergy(sb, WindChargeRegion,
                            (start_x[0] - __instance.current_x) / Settings.scale / __instance.drawScale,
                            - 100.0f - ((__instance.description.size() - 4.0f) / 2.0f - i + 1.0f) * spacing);
                    if (! tmp[0].equals("[W]") && m.group(2).equals(".")) {
                        FontHelper.renderRotatedText(sb, font, ".",
                                __instance.current_x, __instance.current_y,
                                start_x[0] - __instance.current_x + CARD_ENERGY_IMG_WIDTH * __instance.drawScale,
                                i * 1.45f * - font.getCapHeight() + draw_y - __instance.current_y - 6.0f,
                                __instance.angle, true, textColor);
                    }
                    start_x[0] += gl.width;
                    tmp[0] = "";
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctBehavior) throws Exception {
                com.evacipated.cardcrawl.modthespire.lib.Matcher matcher = new com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher(GlyphLayout.class, "setText");
                int[] lines = LineFinder.findAllInOrder(ctBehavior, new ArrayList <>(), matcher);
                return new int[]{lines[lines.length - 1]}; // Only last occurrence
            }
        }
    }

    @SpirePatch (
            clz = AbstractCard.class,
            method = "initializeDescriptionCN"
    )
    public static class FixEForChinese {
        @SpireInsertPatch (
                locator = Locator.class,
                localvars = {"word", "currentWidth", "currentLine", "numLines", "CARD_ENERGY_IMG_WIDTH", "CN_DESC_BOX_WIDTH"}
        )
        public static void Insert(AbstractCard __instance, @ByRef String[] word, @ByRef float[] currentWidth,
                                  @ByRef StringBuilder[] currentLine, @ByRef int[] numLines,
                                  float CARD_ENERGY_IMG_WIDTH, float CN_DESC_BOX_WIDTH) {
            if (word[0].equals("[W]")) {
                if (currentWidth[0] + CARD_ENERGY_IMG_WIDTH > CN_DESC_BOX_WIDTH) {
                    ++ numLines[0];
                    __instance.description.add(new DescriptionLine(currentLine[0].toString(), currentWidth[0]));
                    currentLine[0] = new StringBuilder();
                    currentWidth[0] = CARD_ENERGY_IMG_WIDTH;
                    currentLine[0].append(" ").append(word[0]).append(" ");
                } else {
                    currentLine[0].append(" ").append(word[0]).append(" ");
                    currentWidth[0] += CARD_ENERGY_IMG_WIDTH;
                }
                word[0] = "";
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                com.evacipated.cardcrawl.modthespire.lib.Matcher finalMatcher = new com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher(
                        String.class, "toCharArray");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }

    @SpirePatch (
            clz = AbstractCard.class,
            method = "initializeDescription"
    )
    @SpirePatch (
            clz = AbstractCard.class,
            method = "initializeDescriptionCN"
    )
    public static class AlterEnergyKeyword {
        @SpireInsertPatch (
                locator = Locator.class,
                localvars = {"word"}
        )
        public static void Insert(AbstractCard __instance, @ByRef String[] word) {
            if (word[0].equals("[W]") && ! __instance.keywords.contains("charge")) {
                __instance.keywords.add("charge");
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                com.evacipated.cardcrawl.modthespire.lib.Matcher finalMatcher = new com.evacipated.cardcrawl.modthespire.lib.Matcher.MethodCallMatcher(
                        String.class, "toLowerCase");

                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }

        public static void Postfix(AbstractCard __instance) {
            if (__instance.color.equals(AbstractCardEnum.DEFAULT_GRAY)) {
                int[] idxs = new int[3];
                idxs[0] = __instance.keywords.indexOf("[R]");
                idxs[1] = __instance.keywords.indexOf("[G]");
                idxs[2] = __instance.keywords.indexOf("[B]");

                int idx = Integer.MAX_VALUE;
                for (int i : idxs) {
                    if (i >= 0 && i < idx) {
                        idx = i;
                    }
                }

                if (idx >= 0 && idx != Integer.MAX_VALUE) {
                    if (! __instance.keywords.contains("charge")) {
                        __instance.keywords.add(idx, "charge");
                    }
                    __instance.keywords.remove("[R]");
                    __instance.keywords.remove("[G]");
                    __instance.keywords.remove("[B]");
                }
            }
        }
    }
}