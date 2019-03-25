package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.LoadCardImageAction;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class Wall extends CustomCard {


    public static final String ID = Blademaster.makeID("Wall");
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_SKILL);
    public static final String IIMG = Blademaster.makePath(Blademaster.ICE_SKILL);
    public static final String SIMG = Blademaster.makePath(Blademaster.STONE_SKILL);
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 0;
    private static final int AMT = 8;
    private static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private boolean IceArt = false;
    private boolean StoneArt = false;
    private boolean BaseArt = false;

    public Wall() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = AMT;
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I haven't played enough cards this turn!";
        if (p.hasPower(ComboPower.POWER_ID)) {
            return (p.getPower(ComboPower.POWER_ID).amount >= 5);
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new MetallicizePower(p, this.magicNumber), this.magicNumber));
        if (p.hasPower(ComboPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, - 5), - 5));
        }
        if (p.hasPower(IceStance.POWER_ID)) {
            if (p.hasPower(IceCharge.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, p.getPower(IceCharge.POWER_ID).amount));
            }
        }
        if (p.hasPower(StoneStance.POWER_ID)) {
            if (p.hasPower(StoneCharge.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, p.getPower(StoneCharge.POWER_ID).amount / 3), p.getPower(StoneCharge.POWER_ID).amount / 3));
            }
        }
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(StoneStance.POWER_ID)) {
            if (! StoneArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, SIMG, false));
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[1];
                initializeDescription();
                StoneArt = true;
                IceArt = false;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(IceStance.POWER_ID)) {
            if (! IceArt) {
                AbstractDungeon.actionManager.addToBottom(new LoadCardImageAction(this, IIMG, false));
                this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
                initializeDescription();
                StoneArt = false;
                IceArt = true;
                BaseArt = false;
            }
        } else if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            if (! BaseArt) {
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
        return new Wall();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
            this.initializeDescription();
        }
    }
}