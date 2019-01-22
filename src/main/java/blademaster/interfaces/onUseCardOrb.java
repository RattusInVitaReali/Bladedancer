package blademaster.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract interface onUseCardOrb
{
    public abstract void onUseCard(AbstractCard card, UseCardAction action);
}