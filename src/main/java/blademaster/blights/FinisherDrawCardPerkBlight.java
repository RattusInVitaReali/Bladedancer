package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.patches.BlademasterTags;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FinisherDrawCardPerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:FinisherDrawCardPerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public FinisherDrawCardPerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/FinisherMasteryPerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/FinisherMasteryPerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.hasTag(BlademasterTags.FURY_FINISHER) || card.hasTag(BlademasterTags.COMBO_FINISHER)) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
        }
    }
}