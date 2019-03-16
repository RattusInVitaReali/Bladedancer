package blademaster.cards;

import blademaster.powers.FrozenPower;
import blademaster.powers.IceCharge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
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

public class Freeze extends CustomCard {


    public static final String ID = Blademaster.makeID("Freeze");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.ICE_ATTACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 2;
    private static final int FREEZE = 4;


    public Freeze() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = FREEZE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlizzardEffect(p.getPower(IceCharge.POWER_ID).amount, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.5F));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (p.hasPower(IceCharge.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, p.getPower(IceCharge.POWER_ID).amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new FrozenPower(monster, 4), 4));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Freeze();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}