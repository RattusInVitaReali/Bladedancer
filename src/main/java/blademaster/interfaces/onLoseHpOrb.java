package blademaster.interfaces;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface onLoseHpOrb
{
    void onLoseHpForOrbs(DamageInfo info, int damageAmount);
}