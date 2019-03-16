package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StoneCharge extends AbstractPower {
    public static final String POWER_ID = "StoneCharge";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public StoneCharge(AbstractCreature owner, int amount, boolean isAdditional) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.priority = 14;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = Blademaster.StoneChargePNG();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

}