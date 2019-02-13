package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IceStance extends AbstractPower {
    public static final String POWER_ID = "IceStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public IceStance(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.priority = 7;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = Blademaster.IceStancePNG();
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}