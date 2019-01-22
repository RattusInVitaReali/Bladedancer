package blademaster.cards.wind;

import blademaster.orbs.ParryOrb;
import blademaster.powers.WindCharge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;

public class WindParryingStrike extends CustomCard {


    public static final String ID = Blademaster.makeID("WindParryingStrike");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("blademaster:ParryingStrike");
    public static final String IMG = Blademaster.makePath(Blademaster.WIND_ATTACK);
    public static final String NAME = "Wind " + cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION + " NL Gain !M! Wind Charges.";


    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int AMT = 1;


    public WindParryingStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = AMT;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ParryOrb()));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ParryOrb()));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new WindParryingStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeDamage(2);
            this.upgradeMagicNumber(1);
            this.upgradeName();
            this.initializeDescription();
        }
    }
}