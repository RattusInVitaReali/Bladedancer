package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.IceCharge;
import blademaster.powers.LightningCharge;
import blademaster.powers.StoneCharge;
import blademaster.powers.WindCharge;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SlyStabs extends CustomCard {


    public static final String ID = Blademaster.makeID("SlyStabs");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;


    public SlyStabs() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(StoneCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate()));
        }
        if (p.hasPower(LightningCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate()));
        }
        if (p.hasPower(WindCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate()));
        }
        if (p.hasPower(IceCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate()));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new SlyStabs();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }
}