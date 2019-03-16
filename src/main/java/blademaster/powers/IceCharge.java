package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IceCharge extends AbstractPower {
    public static final String POWER_ID = "IceCharge";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IceCharge(AbstractCreature owner, int amount, boolean isAdditional) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.priority = 13;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = Blademaster.IceChargePNG();
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
    }


}