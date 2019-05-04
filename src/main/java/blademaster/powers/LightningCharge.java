package blademaster.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LightningCharge extends AbstractPower {
    public static final String POWER_ID = "LightningCharge";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas.AtlasRegion BigImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/LightningCharge.png"), 0, 0, 84, 84);
    public static TextureAtlas.AtlasRegion SmallImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/LightningChargeSmall.png"), 0, 0, 32, 32);

    public LightningCharge(AbstractCreature owner, int amount, boolean isAdditional) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.priority = 12;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = BigImage;
        this.region48 = SmallImage;
        updateDescription();
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }


}