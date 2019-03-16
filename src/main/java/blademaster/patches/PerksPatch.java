package blademaster.patches;

import blademaster.relics.DancersAmulet;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import javassist.CtBehavior;

@SpirePatch (
        clz = AbstractRoom.class,
        method = "update"
)
public class PerksPatch {
    @SpireInsertPatch (
            locator = Locator.class
    )
    public static void Insert(AbstractRoom __instance) {
        AbstractRelic amulet = AbstractDungeon.player.getRelic(DancersAmulet.ID);
        if (amulet != null) {
            amulet.onTrigger();
            // Re-setup the rewards screen to our changed rewards
            AbstractDungeon.combatRewardScreen.setupItemReward();
        }
    }

    private static class Locator extends SpireInsertLocator {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
            // Insert here to be after the game is saved
            // Avoids weird save/load issues
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "loading_post_combat");
            int[] found = LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            return new int[]{found[found.length - 1]};
        }
    }
}