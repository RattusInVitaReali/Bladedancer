package blademaster.blights;

import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class BleedingMoreDamagePerkBlight extends AbstractBlight {

    public static final String ID = "blademaster:BleedingMoreDamagePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public BleedingMoreDamagePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/BleedingMoreDamagePerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/BleedingMoreDamagePerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }


}