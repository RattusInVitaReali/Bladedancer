package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.orbs.BladeOrb;
import blademaster.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BladeMastery extends CustomCard {


    public static final String ID = Blademaster.makeID("BladeMastery");
    public static final String IMG = Blademaster.makePath("cards/BladeMastery.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 3;
    private static final int ORBS = 2;


    public BladeMastery() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = ORBS;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BladeOrb(), false));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BladeOrb(), false));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BladeOrb(), false));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BladeMastery();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
}