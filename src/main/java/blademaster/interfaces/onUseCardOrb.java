package blademaster.interfaces;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public interface onUseCardOrb {
    void onUseCard(AbstractCard card, UseCardAction action);
}