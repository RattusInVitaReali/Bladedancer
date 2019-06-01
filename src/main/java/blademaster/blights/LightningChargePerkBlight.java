package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class LightningChargePerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:LightningChargePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public LightningChargePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/LightningChargePerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/LightningChargePerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    public void atTurnStart() {
        this.flash();
        if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            if (AbstractDungeon.player.hasPower(LightningCharge.POWER_ID)) {
                if (AbstractDungeon.player.getPower(LightningCharge.POWER_ID).amount > 0) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningCharge(AbstractDungeon.player, 1, false), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningCharge(AbstractDungeon.player, 3, false), 3));
            }
        }
    }
}