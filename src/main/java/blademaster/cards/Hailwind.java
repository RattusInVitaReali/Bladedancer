package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
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
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

public class Hailwind extends CustomCard {


    public static final String ID = Blademaster.makeID("Hailwind");
    public static final String IMG = Blademaster.makePath("cards/Hailwind.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    private static final int COST = 1;
    private static final int DAMAGE = 3;

    public Hailwind() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(WindCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WindCharge(p, -p.getPower(WindCharge.POWER_ID).amount, false), -p.getPower(WindCharge.POWER_ID).amount));
            int i = 0;
            do {
                AbstractDungeon.actionManager.addToTop(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.damage), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                i += 1;
            } while (i < p.getPower(WindCharge.POWER_ID).amount);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hailwind();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.initializeDescription();
        }
    }
}