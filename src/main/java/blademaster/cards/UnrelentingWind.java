package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.BleedingPower;
import blademaster.powers.ComboPower;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnrelentingWind extends CustomCard {


    public static final String ID = Blademaster.makeID("UnrelentingWind");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath("cards/UnrelentingWind.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int AMT = 6;
    private static final int BLOCK = 4;

    public UnrelentingWind() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = AMT;
        this.baseBlock = this.block = BLOCK;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I haven't played enough cards this turn!";
        if (p.hasPower(ComboPower.POWER_ID)) {
            return (p.getPower(ComboPower.POWER_ID).amount >= 5);
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, - 5), - 5));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindStance(p)));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (! monster.isDeadOrEscaped()) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new BleedingPower(monster, p, this.magicNumber), this.magicNumber));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 2, false), 2));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new UnrelentingWind();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(2);
            this.upgradeMagicNumber(3);
            this.initializeDescription();
        }
    }
}