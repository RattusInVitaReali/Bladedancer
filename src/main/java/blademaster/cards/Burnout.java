package blademaster.cards;

import blademaster.actions.LoadCardImageAction;
import blademaster.actions.PurgeSpecificCardAction;
import blademaster.patches.BlademasterTags;
import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.vfx.combat.DaggerSprayEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;

public class Burnout extends CustomCard {


    public static final String ID = Blademaster.makeID("Burnout");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_ATTACK);
    public static final String LIMG = Blademaster.makePath(Blademaster.LIGHTNING_ATTACK);
    public static final String WIMG = Blademaster.makePath(Blademaster.WIND_ATTACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String WDES = cardStrings.EXTENDED_DESCRIPTION[0];
    public static final String LDES = cardStrings.EXTENDED_DESCRIPTION[1];

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int AMT = 2;
    private static int counter = 0;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;



    public Burnout() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
        this.magicNumber = this.baseMagicNumber = AMT;
        this.tags.add(BlademasterTags.WIND_STANCE);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        counter = 0;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.cardID.equals(Burn.ID)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
                counter += 1;
            }
            if (counter >= this.magicNumber) {
                break;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c.cardID.equals(Burn.ID)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
                counter += 1;
            }
            if (counter >= this.magicNumber) {
                break;
            }
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c.cardID.equals(Burn.ID)) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile));
                counter += 1;
            }
            if (counter >= this.magicNumber) {
                break;
            }
        }
        if (p.hasPower(WindStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 2), 2));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 1), 1));
            }
        }
        if (p.hasPower(LightningStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 2), 2));
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
        return new Burnout();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}