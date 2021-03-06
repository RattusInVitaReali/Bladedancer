package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.perks.RandomStancePerk;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class RandomStancePerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:RandomStancePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public RandomStancePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/OffensiveStanceMasteryPerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/OffensiveStanceMasteryPerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    public void atBattleStart() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, new RandomStancePerk()));
        int wtf = AbstractDungeon.cardRandomRng.random(0, 1);
        if (wtf == 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WindStance(AbstractDungeon.player)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningStance(AbstractDungeon.player)));
        }
    }
}