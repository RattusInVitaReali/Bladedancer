package blademaster.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface onLoseHpOrb {
    int onLoseHpForOrbs(DamageInfo info, int damageAmount);
}