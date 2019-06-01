package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.orbs.BladeOrb;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.ComboPower;
import blademaster.powers.FuryPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FlockOfBlades extends CustomCard {


    public static final String ID = Blademaster.makeID("FlockOfBlades");
    public static final String IMG = Blademaster.makePath("cards/FlockOfBlades.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 0;
    private static final int AMT = 3;

    public FlockOfBlades() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = AMT;
        this.tags.add(BlademasterTags.FURY_FINISHER);
        this.tags.add(BlademasterTags.COMBO_FINISHER);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I don't meet the requirements!";
        if ((AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) && (AbstractDungeon.player.hasPower(ComboPower.POWER_ID))) {
            return (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= 30 && AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount >= 6);
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuryPower(p, - 30), - 30));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, - 6), - 6));
        if (AbstractDungeon.player.filledOrbCount() > 10 - this.magicNumber) {
            for (int i = 0; i < 10 - AbstractDungeon.player.filledOrbCount(); i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BladeOrb()));
            }
        } else {
            for (int i = 0; i < this.magicNumber; i++) {
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BladeOrb()));
            }
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new FlockOfBlades();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.initializeDescription();
        }
    }
}