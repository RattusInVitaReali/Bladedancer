package blademaster.cards;

import blademaster.Blademaster;
import blademaster.actions.LoadCardImageAction;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class Gale extends AbstractStanceCard {


    public static final String ID = Blademaster.makeID("Gale");
    public static final String IMG = Blademaster.makePath("cards/Gale.png");
    public static final String LIMG = Blademaster.makePath("cards/LightningGale.png");
    public static final String WIMG = Blademaster.makePath("cards/WindGale.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 0;
    private static final int DAMAGE = 1;
    private static final int AOE = 1;
    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;


    public Gale() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.isMultiDamage = true;
        this.baseMagicNumber = AOE;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        this.isEthereal = true;
        this.tags.add(BlademasterTags.WIND_STANCE);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.magicNumber, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new CleaveEffect()));
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, false));
        AbstractCard c = new Zephyr();
        if (this.upgraded) {
            c.upgrade();
        }
        AbstractDungeon.player.hand.addToHand(c);
        if (p.hasPower(WindStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 1, false), 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, 1, false), 1));
            }
        }
        if (p.hasPower(LightningStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 1, false), 1));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, 1, false), 1));
            }
        }
    }


    @Override
    public void update() {
        super.update();
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasPower(WindStance.POWER_ID) && (!AbstractDungeon.getMonsters().areMonstersDead())) {
                if (! WindArt) {
                    this.loadCardImage(WIMG);
                    initializeDescription();
                    WindArt = true;
                    LightningArt = false;
                    BaseArt = false;
                }
            } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID) && (!AbstractDungeon.getMonsters().areMonstersDead())) {
                if (! LightningArt) {
                    AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
                    initializeDescription();
                    WindArt = false;
                    LightningArt = true;
                    BaseArt = false;
                }
            } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID) && (!AbstractDungeon.getMonsters().areMonstersDead())) {
                if (! BaseArt) {
                    AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
                    initializeDescription();
                    WindArt = false;
                    LightningArt = false;
                    BaseArt = true;
                }
            }
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new Gale();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeDamage(1);
            this.initializeDescription();
        }
    }
}
