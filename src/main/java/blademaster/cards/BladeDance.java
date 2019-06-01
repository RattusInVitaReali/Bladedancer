package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.orbs.BladeOrb;
import blademaster.orbs.LightningBladeOrb;
import blademaster.orbs.WindBladeOrb;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.powers.BladeDancePower;
import blademaster.powers.ComboPower;
import blademaster.powers.FuryPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class BladeDance extends CustomCard {


    public static final String ID = Blademaster.makeID("BladeDance");
    public static final String IMG = Blademaster.makePath("cards/BladeDance.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    private static final int COST = 0;


    public BladeDance() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.tags.add(BlademasterTags.FURY_FINISHER);
        this.tags.add(BlademasterTags.COMBO_FINISHER);
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "I don't meet the requirements!";
        if (this.upgraded) {
            if ((AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) && (AbstractDungeon.player.hasPower(ComboPower.POWER_ID))) {
                return (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= 40 && AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount >= 8);
            }
        } else if ((AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) && (AbstractDungeon.player.hasPower(ComboPower.POWER_ID))) {
            return (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= 40 && AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount >= 8);
        } else {
            return false;
        }
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuryPower(p, - 40), - 40));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, - 8), - 8));
        if (! p.hasPower(BladeDancePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BladeDancePower(p)));
        }
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb.ID != null) {
                if (orb.ID.equals(WindBladeOrb.ORB_ID) || orb.ID.equals(LightningBladeOrb.ORB_ID) || orb.ID.equals(BladeOrb.ORB_ID))
                    orb.applyFocus();
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new BladeDance();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.retain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}