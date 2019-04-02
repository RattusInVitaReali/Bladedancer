package blademaster.patches;

import blademaster.blights.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.helpers.BlightHelper;

@SpirePatch(
        clz=BlightHelper.class,
        method="getBlight"
)
public class BlightsPatch
{
    public static SpireReturn<AbstractBlight> Prefix(String id)
    {
        if (BonusComboPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new BonusComboPerkBlight());
        }
        if (BonusFuryPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new BonusFuryPerkBlight());
        }
        if (ComboEveryTurnPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new ComboEveryTurnPerkBlight());
        }
        if (DextrousPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new DextrousPerkBlight());
        }
        if (FinisherDrawCardPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new FinisherDrawCardPerkBlight());
        }
        if (FocusedPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new FocusedPerkBlight());
        }
        if (FuryEveryTurnPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new FuryEveryTurnPerkBlight());
        }
        if (RandomBladePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new RandomBladePerkBlight());
        }
        if (RandomStancePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new RandomStancePerkBlight());
        }
        if (StanceHealPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new StanceHealPerkBlight());
        }
        if (StrongPerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new StrongPerkBlight());
        }
        return SpireReturn.Continue();
    }
}
