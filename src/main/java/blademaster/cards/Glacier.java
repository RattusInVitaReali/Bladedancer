package blademaster.cards;

import blademaster.actions.IceStanceAction;
import blademaster.actions.RemoveDefensiveStancesAction;
import blademaster.orbs.ParryOrb;
import blademaster.powers.IceStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
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

public class Glacier extends CustomCard {


    public static final String ID = Blademaster.makeID("Glacier");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.ICE_SKILL);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 2;
    private static final int BLOCK = 7;


    public Glacier() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(1));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ParryOrb()));
        AbstractDungeon.actionManager.addToBottom(new RemoveDefensiveStancesAction());
        AbstractDungeon.actionManager.addToBottom(new IceStanceAction());
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new IceStance(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Glacier();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeBlock(3);
            this.upgradeName();
            this.initializeDescription();
        }
    }
}