package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.interfaces.onAttackedBlight;
import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class OnAttackedChargePerkBlight extends AbstractBlight implements PerkBlight, onAttackedBlight {

    public static final String ID = "blademaster:OnAttackedChargePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public OnAttackedChargePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/OnAttackedChargePerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/OnAttackedChargePerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    public int onAttackedBlights(DamageInfo info, int damageAmount) {
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WindCharge(AbstractDungeon.player, 1 ,false), 1));
        }
        if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningCharge(AbstractDungeon.player, 1 ,false), 1));
        }
        return damageAmount;
    }

}