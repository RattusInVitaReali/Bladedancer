package blademaster.patches;

import blademaster.interfaces.onLoseHpOrb;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch (
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = {DamageInfo.class}
)

public class onLoseHpOrbPatch {
    @SpireInsertPatch (
            locator = Locator.class,
            localvars = "damageAmount"
    )

    public static void Insert(AbstractPlayer __obj_instance, DamageInfo info, int damageAmount) {
        for (AbstractOrb orb : __obj_instance.orbs) {
            if (orb instanceof onLoseHpOrb) {
                ((onLoseHpOrb) orb).onLoseHpForOrbs(info, damageAmount);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractRelic.class, "onLoseHp");
            int[] found = LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);
            -- found[0];
            return found;
        }
    }
}

