package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.actions.RemoveSpecificOrbWithoutEvokingAction;
import blademaster.orbs.LightningChargeOrb;
import blademaster.orbs.WindChargeOrb;
import blademaster.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class NotDualcast extends CustomCard {


    public static final String ID = Blademaster.makeID("NotDualcast");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = Blademaster.makePath("cards/NotDualcast.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;

    private static final int COST = 1;


    public NotDualcast() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.filledOrbCount() > 2) {
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (!orb.ID.equals(LightningChargeOrb.ORB_ID) && !orb.ID.equals(WindChargeOrb.ORB_ID)) {
                    orb.triggerEvokeAnimation();
                    orb.onEvoke();
                    orb.triggerEvokeAnimation();
                    orb.onEvoke();
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                    break;
                }
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new NotDualcast();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
            this.initializeDescription();
        }
    }
}