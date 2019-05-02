package blademaster.patches;

import blademaster.interfaces.onAttackedBlight;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch (
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = DamageInfo.class
)

public class onAttackedBlightPatch {
    @SpireInsertPatch (
            locator = Locator.class,
            localvars = {"damageAmount"}
    )

    public static void Insert(AbstractPlayer __obj_instance, DamageInfo info, @ByRef int[] damageAmount) {
        for (AbstractBlight blight : __obj_instance.blights) {
            if (blight instanceof onAttackedBlight) {
                damageAmount[0] = ((onAttackedBlight) blight).onAttackedBlights(info, damageAmount[0]);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class, "onAttacked");
            int[] found = LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);
            -- found[0];
            return found;
        }
    }
}