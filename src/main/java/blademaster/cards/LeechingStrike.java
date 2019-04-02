package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.LoadCardImageAction;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.BasicStance;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.BloodShotEffect;

public class LeechingStrike extends CustomCard {


    public static final String ID = Blademaster.makeID("LeechingStrike");
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    public static final String LIMG = Blademaster.makePath(Blademaster.LIGHTNING_ATTACK);
    public static final String WIMG = Blademaster.makePath(Blademaster.WIND_ATTACK);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int HEAL = 3;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;

    public LeechingStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = HEAL;
        this.baseDamage = this.damage = DAMAGE;
        this.tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (2 * m.currentHealth <= m.maxHealth) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new BloodShotEffect(m.drawX, m.drawY, p.drawX, p.drawY, this.magicNumber)));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        }
        if (p.hasPower(WindStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, 1, false), 1));
        }
        if (p.hasPower(LightningStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, 1, false), 1));
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            if (! WindArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, WIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0]);
                this.initializeDescription();
                WindArt = true;
                LightningArt = false;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            if (! LightningArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1]);
                this.initializeDescription();
                WindArt = false;
                LightningArt = true;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            if (! BaseArt) {
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
        return new LeechingStrike();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
            this.upgradeMagicNumber(2);
            this.initializeDescription();
        }
    }
}