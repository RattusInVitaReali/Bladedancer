package blademaster.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractStanceCard extends CustomCard {
    public AbstractStanceCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                              CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);
    }

    public boolean WindArtS = false;
    public boolean LightningArtS = false;
    public AbstractCard cardToPreview1 = null;
    public AbstractCard cardToPreview2 = null;
    public boolean bullshit = false;
    public int baseChargeNumber = -1;
    public int chargeNumber = -1;
    public boolean upgradedChargeNumber = false;

    public void upgradeChargeNumber(int amount) {
        chargeNumber = baseChargeNumber + amount;
        this.upgradedChargeNumber = true;
    }


}