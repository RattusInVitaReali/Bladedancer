package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.RemoveSpecificOrbWithoutEvokingAction;
import blademaster.orbs.*;
import blademaster.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class BladeCross extends CustomCard {


    public static final String ID = Blademaster.makeID("BladeCross");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath("cards/BladeCross.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;


    public BladeCross() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (AbstractDungeon.player.hasOrb()) {
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb instanceof ParryOrb || orb instanceof BladeOrb || orb instanceof WindBladeOrb || orb instanceof LightningBladeOrb || orb instanceof WindParryOrb || orb instanceof LightningParryOrb) {
                    orb.triggerEvokeAnimation();
                    orb.onEvoke();
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    break;
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BladeCross();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeDamage(3);
            this.upgradeName();
            this.initializeDescription();
        }
    }
}