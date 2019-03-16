package blademaster.cards;

import blademaster.actions.AwakenOrbAction;
import blademaster.actions.LoadCardImageAction;
import blademaster.patches.BlademasterTags;
import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;

public class AwakeningStrike extends CustomCard {


    public static final String ID = Blademaster.makeID("AwakeningStrike");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_ATTACK);
    public static final String LIMG = Blademaster.makePath(Blademaster.LIGHTNING_ATTACK);
    public static final String WIMG = Blademaster.makePath(Blademaster.WIND_ATTACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int NUM = 1;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;



    public AwakeningStrike()
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.damage = this.baseDamage;
        this.baseMagicNumber = NUM;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(BlademasterTags.WIND_STANCE);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new AwakenOrbAction());
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new AwakenOrbAction());
        }
        if (p.hasPower(WindStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 2, false), 2));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 1, false), 1));
            }
        }
        if (p.hasPower(LightningStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 2, false), 2));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 1, false), 1));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            if (!WindArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, WIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0]);
                this.initializeDescription();
                WindArt = true;
                LightningArt = false;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            if (!LightningArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1]);
                this.initializeDescription();
                WindArt = false;
                LightningArt = true;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            if (!BaseArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
                this.rawDescription = DESCRIPTION;
                this.initializeDescription();
                WindArt = false;
                LightningArt = false;
                BaseArt = true;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AwakeningStrike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
}