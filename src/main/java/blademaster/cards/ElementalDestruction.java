package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.FuryPower;
import blademaster.powers.LightningCharge;
import blademaster.powers.WindCharge;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ElementalDestruction extends CustomCard {


    public static final String ID = Blademaster.makeID("ElementalDestruction");
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private int FURY_AMT = 25;


    public ElementalDestruction() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = FURY_AMT;
        this.baseDamage = this.damage = 0;
        this.isMultiDamage = true;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I'm not furious enough!";
        if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            return AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= this.magicNumber;
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuryPower(p, - this.magicNumber), - this.magicNumber));
        if (p.hasPower(WindCharge.POWER_ID)) {
            this.damage += p.getPower(WindCharge.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, p.getPower(WindCharge.POWER_ID).amount, false)));
        }
        if (p.hasPower(LightningCharge.POWER_ID)) {
            this.damage += p.getPower(LightningCharge.POWER_ID).amount;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, p.getPower(LightningCharge.POWER_ID).amount, false)));
        }
        AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(p, this.damage, this.damageTypeForTurn), true));

    }

    @Override
    public AbstractCard makeCopy() {
        return new ElementalDestruction();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
            this.upgradeMagicNumber(- 5);
        }
    }
}