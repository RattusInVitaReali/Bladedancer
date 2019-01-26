package blademaster.cards;

import blademaster.actions.RemoveStancesAction;
import blademaster.actions.WindStanceAction;
import blademaster.patches.BlademasterTags;
import blademaster.powers.ComboPower;
import blademaster.powers.TiredPower;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import ratmod.powers.SilveredPower;


public class SecondWind extends CustomCard {


    public static final String ID = Blademaster.makeID("SecondWind");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int BLOCK = 11;
    private static final int AMT = 1;


    public SecondWind()
    {
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
            if (AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount >= 3) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TiredPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, ComboPower.POWER_ID));
        if (!p.hasPower(WindStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveStancesAction());
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindStance(p)));
        }
        AbstractDungeon.actionManager.addToBottom(new WindStanceAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new SecondWind();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.initializeDescription();
        }
    }
}