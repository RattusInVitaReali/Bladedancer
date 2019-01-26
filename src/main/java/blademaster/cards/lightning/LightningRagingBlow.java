package blademaster.cards.lightning;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.CalmnessPower;
import blademaster.powers.FuryPower;
import blademaster.powers.LightningCharge;
import blademaster.powers.WindCharge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RagePower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import ratmod.actions.ExecuteAction;

public class LightningRagingBlow extends CustomCard {

    public static final String ID = Blademaster.makeID("LightningRagingBlow");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.LIGHTNING_ATTACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 10;
    private static final int AMT = 3;

    private DamageInfo info;

    public LightningRagingBlow()
    {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = AMT;
        this.damage = this.baseDamage;
        this.tags.add(BlademasterTags.FURY_FINISHER);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);


    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I'm not furious enough!";
        if (AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            return AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= 15;
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.15F));
        if (2 * m.currentHealth <= m.maxHealth) {
            AbstractDungeon.actionManager.addToBottom(new ExecuteAction(m, new DamageInfo(p, (2 * this.damage), this.damageTypeForTurn)));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ExecuteAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CalmnessPower(p, 1), 1));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, RagePower.POWER_ID));
        if (p.hasPower(LightningCharge.POWER_ID)) {
            for (AbstractMonster mo: AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, this.magicNumber * AbstractDungeon.player.getPower(LightningCharge.POWER_ID).amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
                AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.2F));
                AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            }
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new LightningCharge(p, -p.getPower(LightningCharge.POWER_ID).amount), -p.getPower(LightningCharge.POWER_ID).amount));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new LightningRagingBlow();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.initializeDescription();
        }
    }
}