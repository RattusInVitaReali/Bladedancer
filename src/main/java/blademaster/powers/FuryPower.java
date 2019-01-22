package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FuryPower
        extends AbstractPower
{
    public static final String POWER_ID = Blademaster.makeID("Fury");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FuryPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.img = Blademaster.FuryPNG();
        updateDescription();
    }

    public void atStartOfTurn()
    {
        if (this.owner != null)
        {
            this.amount = 0;
        }
    }

    @Override
    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target)
    {
        if ((info.type == DamageInfo.DamageType.NORMAL) && (this.owner != null) && (!this.owner.hasPower(CalmnessPower.POWER_ID)))
        {
            this.amount += damageAmount;
        }
    }
}
