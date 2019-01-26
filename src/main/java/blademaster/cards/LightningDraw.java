package blademaster.cards;

import blademaster.actions.LightningStanceAction;
import blademaster.actions.RemoveStancesAction;
import blademaster.patches.BlademasterTags;
import blademaster.powers.ComboPower;
import blademaster.powers.LightningStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import ratmod.powers.BleedingPower;

public class LightningDraw extends CustomCard {


    public static final String ID = Blademaster.makeID("LightningDraw");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_ATTACK);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 9;
    private static final int BLEED = 3;

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ComboPower.POWER_ID)){
            return p.getPower(ComboPower.POWER_ID).amount >= 4;
        } else return false;
    }


    public LightningDraw() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = BLEED;
        this.tags.add(BlademasterTags.COMBO_FINISHER);
        this.tags.add(BlademasterTags.LIGHTNING_STANCE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            AbstractDungeon.actionManager.addToTop(new SFXAction("ORB_LIGHTNING_EVOKE"));
            AbstractDungeon.actionManager.addToTop(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.2F));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(mo, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new BleedingPower(mo, p, this.magicNumber), this.magicNumber));
        }
        if (!p.hasPower(LightningStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveStancesAction());
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LightningStance(p)));
            AbstractDungeon.actionManager.addToBottom(new LightningStanceAction());
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new LightningDraw();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
}