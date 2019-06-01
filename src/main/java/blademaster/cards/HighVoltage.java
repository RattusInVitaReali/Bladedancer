package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.ComboPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


public class HighVoltage extends CustomCard {


    public static final String ID = Blademaster.makeID("HighVoltage");
    public static final String IMG = Blademaster.makePath("cards/HighVoltage.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int BLOCK = 8;
    private static final int AMT = 1;


    public HighVoltage() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.block = this.baseBlock;
        this.baseMagicNumber = AMT;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(BlademasterTags.COMBO_FINISHER);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);
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
    }

    @Override
    public AbstractCard makeCopy() {
        return new HighVoltage();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
            this.initializeDescription();
        }
    }
}