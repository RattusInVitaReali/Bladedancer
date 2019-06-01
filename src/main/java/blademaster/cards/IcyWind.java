package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.powers.BleedingPower;
import blademaster.powers.WindCharge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

public class IcyWind extends CustomCard {


    public static final String ID = Blademaster.makeID("IcyWind");
    public static final String IMG = Blademaster.makePath("cards/IcyWind.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 2;
    private static final int FREEZE = 4;


    public IcyWind() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = FREEZE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BlizzardEffect(10, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.5F));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (p.hasPower(WindCharge.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, p.getPower(WindCharge.POWER_ID).amount, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new BleedingPower(monster, p, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new IcyWind();
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