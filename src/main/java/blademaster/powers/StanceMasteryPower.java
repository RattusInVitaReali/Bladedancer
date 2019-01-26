package blademaster.powers;

import blademaster.Blademaster;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class StanceMasteryPower extends AbstractPower implements BetterOnApplyPowerPower {
    public static final String POWER_ID = "StanceMastery";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StanceMasteryPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.img = Blademaster.getDefaultPowerTexture();
    }


    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(LightningCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new LightningCharge(source, 1), 1));
        }
        if (power.ID.equals(WindCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(target, source, new WindCharge(source, 1), 1));
        }
        return true;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


}