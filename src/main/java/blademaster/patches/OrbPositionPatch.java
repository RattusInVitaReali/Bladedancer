package blademaster.patches;

import blademaster.characters.BlademasterCharacter;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

@SpirePatch (clz = AbstractOrb.class, method = "setSlot",
        paramtypez = {
                int.class,
                int.class})
public class OrbPositionPatch {


    public static SpireReturn <Void> Prefix(AbstractOrb abstractOrb_instance, int slotNum, int maxOrbs) {

        if (AbstractDungeon.player instanceof BlademasterCharacter) {
            abstractOrb_instance.tX = ((BlademasterCharacter) AbstractDungeon.player).orbPositionsX[slotNum];
            abstractOrb_instance.tY = ((BlademasterCharacter) AbstractDungeon.player).orbPositionsY[slotNum];
            abstractOrb_instance.hb.move(abstractOrb_instance.tX, abstractOrb_instance.tY);
            return SpireReturn.Return(null);

        } else {
            return SpireReturn.Continue();
        }
    }

}