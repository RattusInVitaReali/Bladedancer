package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class WindChargePerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:WindChargePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public WindChargePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/WindChargePerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/WindChargePerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    public void atTurnStart() {
        this.flash();
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            if (AbstractDungeon.player.hasPower(WindCharge.POWER_ID)) {
                if (AbstractDungeon.player.getPower(WindCharge.POWER_ID).amount > 0) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WindCharge(AbstractDungeon.player, 1, false), 1));
                }
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new WindCharge(AbstractDungeon.player, 3, false), 3));
            }
        }
    }
}
