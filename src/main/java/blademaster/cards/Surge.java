package blademaster.cards;

import blademaster.powers.LightningCharge;
import blademaster.powers.LightningStance;
import blademaster.powers.WindCharge;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.powers.ArtifactPower;

public class Surge extends CustomCard {


    public static final String ID = Blademaster.makeID("Surge");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath(Blademaster.DEFAULT_COMMON_POWER);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I'm not in a Stance!";
        return (p.hasPower(WindStance.POWER_ID) || p.hasPower(LightningStance.POWER_ID));
    }



    public Surge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(WindCharge.POWER_ID) && p.hasPower(LightningCharge.POWER_ID)) {
            if (p.getPower(WindCharge.POWER_ID).amount > (p.getPower(LightningCharge.POWER_ID).amount)) {
                if (p.getPower(WindCharge.POWER_ID).amount > 3) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 3), 3));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, p.getPower(WindCharge.POWER_ID).amount), p.getPower(WindCharge.POWER_ID).amount));
                }
            } else {
                if (p.getPower(LightningCharge.POWER_ID).amount > 3) {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 3), 3));
                } else {
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, p.getPower(LightningCharge.POWER_ID).amount), p.getPower(LightningCharge.POWER_ID).amount));
                }
            }
        } else if (p.hasPower(WindCharge.POWER_ID)) {
            if (p.getPower(WindCharge.POWER_ID).amount > 3) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 3), 3));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, p.getPower(WindCharge.POWER_ID).amount), p.getPower(WindCharge.POWER_ID).amount));
            }
        } else if (p.hasPower(LightningCharge.POWER_ID)) {
            if (p.getPower(LightningCharge.POWER_ID).amount > 3) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, 3), 3));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, p.getPower(LightningCharge.POWER_ID).amount), p.getPower(LightningCharge.POWER_ID).amount));
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Surge();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.initializeDescription();
        }
    }
}