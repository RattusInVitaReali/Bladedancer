package blademaster.patches;

import blademaster.interfaces.onAttackedOrb;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch (
        clz = AbstractPlayer.class,
        method = "damage",
        paramtypez = DamageInfo.class
)

public class onAttackedOrbPatch {
    @SpireInsertPatch (
            locator = Locator.class,
            localvars = {"damageAmount"}
    )

    public static void Insert(AbstractPlayer __obj_instance, DamageInfo info, @ByRef int[] damageAmount) {
        for (AbstractOrb orb : __obj_instance.orbs) {
            if (orb instanceof onAttackedOrb) {
                damageAmount[0] = ((onAttackedOrb) orb).onAttackedForOrbs(info, damageAmount[0]);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "onAttacked");
            int[] found = LineFinder.findInOrder(ctMethodToPatch, new ArrayList(), finalMatcher);
            -- found[0];
            return found;
        }
    }
}