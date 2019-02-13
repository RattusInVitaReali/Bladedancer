package blademaster.cards;

import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
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

public class Empower extends CustomCard {


    public static final String ID = Blademaster.makeID("Empower");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static String DESCRIPTION = cardStrings.DESCRIPTION;
    public static String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static int ENERGY = 1;
    private static final int CHARGES = 3;


    public Empower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = this.magicNumber = CHARGES;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, ENERGY));
        if (p.hasPower(LightningStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningCharge(p, this.magicNumber)));
        }
        if (p.hasPower(WindStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, this.magicNumber)));
        }
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY));

    }

    @Override
    public AbstractCard makeCopy() {
        return new Empower();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            ENERGY = 2;
            this.DESCRIPTION = this.UPGRADE_DESCRIPTION;
            this.upgradeMagicNumber(2);
            this.initializeDescription();
        }
    }
}