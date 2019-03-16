package blademaster.variables;

import basemod.abstracts.DynamicVariable;
import blademaster.powers.IceCharge;
import blademaster.powers.LightningCharge;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LightningSpirit extends DynamicVariable
{   // Custom Dynamic Variables are what you do if you need your card text to display a cool, changing number that the base game doesn't provide.
    // If the !D! and !B! (for Damage and Block) etc. are not enough for you, this is how you make your own one. It Changes In Real Time!


    // This is what you type in your card string to make the variable show up. Remember to encase it in "!"'s in the json!
    @Override
    public String key()
    {
        return "L";
    }

    // Checks whether the current value is different than the base one.
    // For example, this will check whether your damage is modified (i.e. by strength) and color the variable appropriately (Green/Red).
    @Override
    public boolean isModified(AbstractCard card)
    {
        return card.isDamageModified;
    }

    // The value the variable should display.
    // In our case, it displays the damage the card would do, multiplied by the amount of energy the player currently has.
    @Override
    public int value(AbstractCard card)
    {
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasPower(LightningCharge.POWER_ID)) {
                return AbstractDungeon.player.getPower(LightningCharge.POWER_ID).amount;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    // The baseValue the variable should display.
    // just like baseBlock or baseDamage, this is what the variable should reset to by default. (the base value before any modifications)
    @Override
    public int baseValue(AbstractCard card)
    {
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasPower(LightningCharge.POWER_ID)) {
                return AbstractDungeon.player.getPower(LightningCharge.POWER_ID).amount;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    // If the card has it's damage upgraded, this variable will glow green on the upgrade selection screen as well.
    @Override
    public boolean upgraded(AbstractCard card)
    {
        return card.upgradedDamage;
    }
}