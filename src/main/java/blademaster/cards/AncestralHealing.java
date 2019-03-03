package blademaster.cards;

import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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

import java.awt.color.ICC_ColorSpace;

public class AncestralHealing extends CustomCard {


    public static final String ID = Blademaster.makeID("AncestralHealing");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;


    public AncestralHealing() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.magicNumber = 0;
        if (p.hasPower(WindCharge.POWER_ID)) {
            this.magicNumber += p.getPower(WindCharge.POWER_ID).amount;
        }
        if (p.hasPower(LightningCharge.POWER_ID)) {
            this.magicNumber += p.getPower(LightningCharge.POWER_ID).amount;
        }
        if (p.hasPower(StoneCharge.POWER_ID)) {
            this.magicNumber += p.getPower(StoneCharge.POWER_ID).amount;
        }
        if (p.hasPower(IceCharge.POWER_ID)) {
            this.magicNumber += p.getPower(IceCharge.POWER_ID).amount;
        }
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 3 * this.magicNumber));
        } else {
            AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, 2 * this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AncestralHealing();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}