package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.LoadCardImageAction;
import blademaster.effects.BetterLightningEffect;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.*;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Discharge extends CustomCard {


    public static final String ID = Blademaster.makeID("Discharge");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath("cards/Discharge.png");
    public static final String WIMG = Blademaster.makePath("cards/WindDischarge.png");
    public static final String LIMG = Blademaster.makePath("cards/LightningDischarge.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    private boolean WindArt = false;
    private boolean LightningArt = false;
    private boolean BaseArt = false;

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int AMT = 1;

    public Discharge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = AMT;
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);
        this.tags.add(BlademasterTags.WIND_STANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (p.hasPower(WindStance.POWER_ID)) {
            AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber));

            if (p.hasPower(WindCharge.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BetterLightningEffect(monster.drawX, monster.drawY, 0.5F, Color.WHITE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, p.getPower(WindCharge.POWER_ID).amount), AbstractGameAction.AttackEffect.NONE));
            }
        }
        if (p.hasPower(LightningStance.POWER_ID)) {
            AbstractMonster monster = AbstractDungeon.getMonsters().getRandomMonster();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new VulnerablePower(monster, this.magicNumber, false), this.magicNumber));

            if (p.hasPower(LightningCharge.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new BetterLightningEffect(monster.drawX, monster.drawY, 0.5F, Color.WHITE.cpy())));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, p.getPower(LightningCharge.POWER_ID).amount), AbstractGameAction.AttackEffect.NONE));
            }
        }
    }

    public void update() {
        super.update();
        if (CardCrawlGame.isInARun()) {
            if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
                if (! WindArt) {
                    AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, WIMG, false));
                    if (this.upgraded) {
                        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[2];
                    } else {
                        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
                    }
                    initializeDescription();
                    WindArt = true;
                    LightningArt = false;
                    BaseArt = false;
                }
            } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
                if (! LightningArt) {
                    AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, LIMG, false));
                    if (this.upgraded) {
                        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[3];
                    } else {
                        this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
                    }
                    initializeDescription();
                    WindArt = false;
                    LightningArt = true;
                    BaseArt = false;
                }
            } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
                if (! BaseArt) {
                    AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
                    this.rawDescription = DESCRIPTION;
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
        return new Discharge();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeBlock(2);
            this.upgradeName();
            this.initializeDescription();
        }
    }
}