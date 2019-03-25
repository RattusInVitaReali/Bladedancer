package blademaster.patches;

import blademaster.interfaces.onOrbChanneledPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;

@SpirePatch (
        clz = AbstractPlayer.class,
        method = "channelOrb"
)
public class onOrbChanneledPowerPatch {
    @SpireInsertPatch (
            locator = Locator.class
    )
    public static void Insert(AbstractPlayer __intance, AbstractOrb orbToSet) {
        for (AbstractPower power : __intance.powers) {
            if (power instanceof onOrbChanneledPower) {
                ((onOrbChanneledPower) power).onOrbChanneled(orbToSet);
            }
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(GameActionManager.class, "orbsChanneledThisCombat");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}