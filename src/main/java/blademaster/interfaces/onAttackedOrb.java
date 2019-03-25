package blademaster.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface onAttackedOrb {
    int onAttackedForOrbs(DamageInfo info, int damageAmount);
}