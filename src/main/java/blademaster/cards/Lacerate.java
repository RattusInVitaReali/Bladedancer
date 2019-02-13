package blademaster.cards;

import blademaster.actions.LoadCardImageAction;
import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
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

public class Lacerate extends CustomCard {


    public static final String ID = Blademaster.makeID("Lacerate");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_ATTACK);
    public static final String LIMG = Blademaster.makePath(Blademaster.LIGHTNING_ATTACK);
    public static final String WIMG = Blademaster.makePath(Blademaster.WIND_ATTACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String WDES = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String LDES = cardStrings.EXTENDED_DESCRIPTION[1];

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 2;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;


    public Lacerate() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (p.hasPower(WindStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 1), 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 1), 1));
            }
        }
        if (p.hasPower(LightningStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 1), 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 1), 1));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID) && (!WindArt)) {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, WIMG, false));
            this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0]);
            this.initializeDescription();
            WindArt = true;
            LightningArt = false;
            BaseArt = false;
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID) && (!LightningArt)) {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
            this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1]);
            this.initializeDescription();
            WindArt = false;
            LightningArt = true;
            BaseArt = false;
        } else if (!BaseArt) {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
            this.rawDescription = DESCRIPTION;
            this.initializeDescription();
            WindArt = false;
            LightningArt = false;
            BaseArt = true;
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Lacerate();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.initializeDescription();
        }
    }
}