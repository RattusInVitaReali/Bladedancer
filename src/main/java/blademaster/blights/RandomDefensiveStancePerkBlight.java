package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.powers.IceStance;
import blademaster.powers.StoneStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class RandomDefensiveStancePerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:RandomDefensiveStancePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public RandomDefensiveStancePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "defaultModResources/images/relics/perks/DefensiveStanceMasteryPerk.png", true);
        this.img = ImageMaster.loadImage("defaultModResources/images/relics/perks/DefensiveStanceMasteryPerk.png");
        this.outlineImg = ImageMaster.loadImage("defaultModResources/images/relics/outline/Perk.png");
    }

    public void atBattleStart() {
        int wtf = AbstractDungeon.cardRandomRng.random(0, 1);
        if (wtf == 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new IceStance(AbstractDungeon.player)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StoneStance(AbstractDungeon.player)));
        }
    }
}