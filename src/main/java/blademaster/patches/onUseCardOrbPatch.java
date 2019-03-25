package blademaster.patches;

import blademaster.interfaces.onUseCardOrb;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import javassist.CtBehavior;

import java.util.ArrayList;

@SpirePatch (clz = UseCardAction.class, method = "update")
public class onUseCardOrbPatch {
    @SpireInsertPatch (locator = Locator.class, localvars = {"targetCard"})
    public static void Insert(UseCardAction __instance, AbstractCard targetCard) {
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if ((orb instanceof onUseCardOrb)) {
                ((onUseCardOrb) orb).onUseCard(targetCard, __instance);
            }
        }
    }

    private static class Locator
            extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctBehavior)
                throws Exception {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getMonsters");
            return LineFinder.findInOrder(ctBehavior, new ArrayList(), finalMatcher);
        }
    }
}
