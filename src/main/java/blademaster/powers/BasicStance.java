package blademaster.powers;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiwordKeywords;
import blademaster.Blademaster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BasicStance extends AbstractPower {
    public static final String POWER_ID = "BasicStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BasicStance(AbstractCreature owner) {
        this.name = NAME;
        this.priority = 5;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = Blademaster.getDefaultPowerTexture();
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

}