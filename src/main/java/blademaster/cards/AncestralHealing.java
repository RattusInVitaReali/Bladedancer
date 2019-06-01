package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.LightningCharge;
import blademaster.powers.WindCharge;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AncestralHealing extends CustomCard {


    public static final String ID = Blademaster.makeID("AncestralHealing");
    public static final String IMG = Blademaster.makePath("cards/AncestralHealing.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;
    private static final int AMT = 2;


    public AncestralHealing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = AMT;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.magicNumber = 0;
        if (p.hasPower(WindCharge.POWER_ID)) {
            this.magicNumber += p.getPower(WindCharge.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, WindCharge.POWER_ID));
        }
        if (p.hasPower(LightningCharge.POWER_ID)) {
            this.magicNumber += p.getPower(LightningCharge.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, LightningCharge.POWER_ID));
        }
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 2 * this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AncestralHealing();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.initializeDescription();
        }
    }
}