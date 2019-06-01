package blademaster.powers;

import blademaster.Blademaster;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FuryPower
        extends AbstractPower {
    public static final String POWER_ID = Blademaster.makeID("Fury");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static TextureAtlas.AtlasRegion BigImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/Fury.png"), 0, 0, 84, 84);
    public static TextureAtlas.AtlasRegion SmallImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/FurySmall.png"), 0, 0, 32, 32);

    public FuryPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.priority = 1;
        this.owner = owner;
        this.amount = amount;
        this.type = AbstractPower.PowerType.BUFF;
        this.region128 = BigImage;
        this.region48 = SmallImage;
        updateDescription();
    }

    public void atStartOfTurn() {
        if (this.owner != null) {
            this.amount = 0;
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if ((info.type == DamageInfo.DamageType.NORMAL) && (this.owner != null) && (! this.owner.hasPower(CalmnessPower.POWER_ID))) {
            this.amount += info.base;
        }
    }
}
