package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ComboPower extends AbstractPower
{
    public static final String POWER_ID = Blademaster.makeID("Combo");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ComboPower(AbstractCreature owner, int amount)
    {
        this.name = NAME;
        this.ID = POWER_ID;
        this.priority = 2;
        this.owner = owner;
        this.amount = amount;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
        this.img = Blademaster.getDefaultPowerTexture();
        updateDescription();
    }


    public void updateDescription()
    {
        this.description = (DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1]);
    }

    public void atStartOfTurn()
    {
       if (AbstractDungeon.player != null)
       {
           this.amount = 0;
       }
    }

    public void onUseCard(AbstractCard card, UseCardAction action)
    {
        if ((this.owner != null) && (!this.owner.hasPower(TiredPower.POWER_ID)))
        {
            this.amount += 1;
        }
    }
}
