package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.StoneStanceAction;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.ComboPower;
import blademaster.powers.StoneStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class Stonework extends CustomCard {


    public static final String ID = Blademaster.makeID("Stonework");
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_SKILL);
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int BLOCK = 11;
    private static final int AMT = 1;


    public Stonework() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = AMT;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(BlademasterTags.COMBO_FINISHER);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I haven't played enough cards this turn!";
        if (AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            return AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount >= 3;
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, - 3), - 3));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (! p.hasPower(StoneStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StoneStance(p)));
        } else if (p.hasPower(StoneStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Burn(), 1));
        }
        AbstractDungeon.actionManager.addToBottom(new StoneStanceAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Stonework();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.initializeDescription();
        }
    }
}