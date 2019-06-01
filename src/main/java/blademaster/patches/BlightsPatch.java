package blademaster.patches;

import blademaster.blights.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.helpers.BlightHelper;

@SpirePatch (
        clz = BlightHelper.class,
        method = "getBlight"
)
public class BlightsPatch {
    public static SpireReturn <AbstractBlight> Prefix(String id) {
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
        if (BleedingLaceratePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new BleedingLaceratePerkBlight());
        }
        if (OnAttackedChargePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new OnAttackedChargePerkBlight());
        }
        if (WindChargePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new WindChargePerkBlight());
        }
        if (LightningChargePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new LightningChargePerkBlight());
        }
        if (BleedingMoreDamagePerkBlight.ID.equals(id)) {
            return SpireReturn.Return(new BleedingMoreDamagePerkBlight());
        }
        return SpireReturn.Continue();
    }
}
