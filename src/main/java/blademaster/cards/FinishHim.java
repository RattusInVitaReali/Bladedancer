package blademaster.cards;

import basemod.abstracts.CustomCard;
import blademaster.Blademaster;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class FinishHim extends CustomCard {


    public static final String ID = Blademaster.makeID("FinishHim");
    public static final String IMG = Blademaster.makePath("cards/FinishHim.png");
    public static final CardColor COLOR = AbstractCardEnum.DEFAULT_GRAY;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    private static final int COST = 0;
    private static final int AMT = 1;

    public static CardGroup srcCommonCardPool = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
    public static com.megacrit.cardcrawl.random.Random cardRandomRng;

    public FinishHim() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList <AbstractCard> list = new ArrayList();
        for (AbstractCard card : srcCommonCardPool.group) {
            if (card.hasTag(BlademasterTags.FURY_FINISHER) || card.hasTag(BlademasterTags.COMBO_FINISHER)) {
                list.add(card);
            }
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(list.get(cardRandomRng.random(list.size() - 1)).makeCopy(), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FinishHim();
    }

    @Override
    public void upgrade() {
        if (! this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
}