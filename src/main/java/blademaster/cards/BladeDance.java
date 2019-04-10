package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
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
        if ((AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) && (AbstractDungeon.player.hasPower(ComboPower.POWER_ID))) {
            return (AbstractDungeon.player.getPower(FuryPower.POWER_ID).amount >= 30 && AbstractDungeon.player.getPower(ComboPower.POWER_ID).amount >= 6);
        } else {
            return false;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FuryPower(p, - 30), - 30));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ComboPower(p, - 6), - 6));
        if (! p.hasPower(BladeDancePower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BladeDancePower(p)));
        }
        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            orb.applyFocus();
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
            this.initializeDescription();
        }
    }
}