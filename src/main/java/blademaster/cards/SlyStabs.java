package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.LightningCharge;
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
    public static final String IMG = Blademaster.makePath("cards/SlyStabs.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 1;


    public SlyStabs() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(LightningCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate(), 1));
        }
        if (p.hasPower(WindCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate(), 1));
        }
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate(), 1));
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
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}