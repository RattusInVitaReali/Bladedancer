package blademaster.blights;

import blademaster.cards.Lacerate;
import blademaster.powers.BleedingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BleedingLaceratePerkBlight extends AbstractBlight {

    public static final String ID = "blademaster:BleedingLaceratePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public BleedingLaceratePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/BleedingLaceratePerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/BleedingLaceratePerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.cardID.equals(Lacerate.ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new BleedingPower(m, AbstractDungeon.player, 2), 2));
        }
    }
}