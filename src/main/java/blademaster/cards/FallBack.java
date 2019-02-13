package blademaster.cards;

import blademaster.actions.LoadCardImageAction;
import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;

public class FallBack extends CustomCard {


    public static final String ID = Blademaster.makeID("FallBack");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_SKILL);
    public static final String IIMG = Blademaster.makePath(Blademaster.ICE_SKILL);
    public static final String SIMG = Blademaster.makePath(Blademaster.STONE_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 2;
    private boolean IceArt = false;
    private boolean StoneArt = false;
    private boolean BaseArt = false;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    public FallBack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            AbstractDungeon.actionManager.addToBottom(new EvokeOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new DecreaseMaxOrbAction(1));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
            if (p.hasPower(IceStance.POWER_ID)) {
                if (this.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IceCharge(p, 2), 2));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IceCharge(p, 1), 1));
                }
            }
            if (p.hasPower(StoneStance.POWER_ID)) {
                if (this.upgraded) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StoneCharge(p, 2), 2));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StoneCharge(p, 1), 1));
                }
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(StoneStance.POWER_ID)) {
            if (!StoneArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, SIMG, false));
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
                initializeDescription();
                StoneArt = true;
                IceArt = false;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(IceStance.POWER_ID)) {
            if (!IceArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IIMG, false));
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
                initializeDescription();
                StoneArt = false;
                IceArt = true;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            if (!BaseArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IMG, false));
                this.rawDescription = DESCRIPTION;
                initializeDescription();
                StoneArt = false;
                IceArt = false;
                BaseArt = true;
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new FallBack();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(1);
            this.initializeDescription();
        }
    }
}