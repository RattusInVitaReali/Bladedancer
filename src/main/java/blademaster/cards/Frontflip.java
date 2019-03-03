package blademaster.cards;

import blademaster.actions.LoadCardImageAction;
import blademaster.powers.*;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;

public class Frontflip extends CustomCard {


    public static final String ID = Blademaster.makeID("Frontflip");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IIMG = Blademaster.makePath(Blademaster.ICE_SKILL);
    public static final String SIMG = Blademaster.makePath(Blademaster.STONE_SKILL);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int DRAW = 2;


    public Frontflip() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = DRAW;
        this.block = this.baseBlock;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        if (p.hasPower(IceStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IceCharge(p, 2, false), 2));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IceCharge(p, 1, false), 1));
            }
        }
        if (p.hasPower(StoneStance.POWER_ID)) {
            if (this.upgraded) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StoneCharge(p, 2, false), 2));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StoneCharge(p, 1, false), 1));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(StoneStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, SIMG, false));
        } else if (AbstractDungeon.player.hasPower(IceStance.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IIMG, false));
        } else {
            AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Frontflip();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
}