package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.LoadCardImageAction;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class RagingBlow extends CustomCard {

    public static final String ID = Blademaster.makeID("RagingBlow");
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_ATTACK);
    public static final String LIMG = Blademaster.makePath(Blademaster.LIGHTNING_ATTACK);
    public static final String WIMG = Blademaster.makePath(Blademaster.WIND_ATTACK);
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int DAMAGE = 8;
    private static final int BLEED = 3;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;


    public RagingBlow() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = BLEED;
        this.damage = this.baseDamage;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(BlademasterTags.FURY_FINISHER);
        this.tags.add(BlademasterTags.WIND_STANCE);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I'm not furious enough!";
        if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            return AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= 10;
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuryPower(p, - 10), - 10));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.15F));
        if (2 * m.currentHealth <= m.maxHealth) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, (2 * this.damage), this.damageTypeForTurn)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindStance(p)));
        if (p.hasPower(WindStance.POWER_ID) && p.hasPower(WindCharge.POWER_ID)) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(monster.drawX, monster.drawY), 0.2F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, p.getPower(WindCharge.POWER_ID).amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
        }
        if (p.hasPower(LightningStance.POWER_ID) && p.hasPower(LightningCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.2F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, 3 * p.getPower(LightningCharge.POWER_ID).amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
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
                System.out.println("Wind art set to true.");
            }
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            if (! LightningArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
                this.rawDescription = (DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[1]);
                this.initializeDescription();
                WindArt = false;
                LightningArt = true;
                BaseArt = false;
                System.out.println("Lighting art set to true.");
            }
        } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            if (! BaseArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
                this.rawDescription = DESCRIPTION;
                this.initializeDescription();
                WindArt = false;
                LightningArt = false;
                BaseArt = true;
                System.out.println("Base art set to true.");
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new RagingBlow();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.initializeDescription();
        }
    }
}