package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.orbs.*;
import blademaster.patches.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class CloudOfSteel extends CustomCard {


    public static final String ID = Blademaster.makeID("CloudOfSteel");
    public static final String IMG = Blademaster.makePath("cards/CloudOfSteel.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 2;


    public CloudOfSteel() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (AbstractOrb orb : AbstractDungeon.player.orbs) {
            if (orb instanceof BladeOrb || orb instanceof WindBladeOrb || orb instanceof ParryOrb || orb instanceof WindParryOrb || orb instanceof LightningBladeOrb || orb instanceof LightningParryOrb) {
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Lacerate(), 1));

            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new CloudOfSteel();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(1);
            this.initializeDescription();
        }
    }
}