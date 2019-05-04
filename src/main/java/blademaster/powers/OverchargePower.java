package blademaster.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;

public class OverchargePower extends TwoAmountPower {
    public static final String POWER_ID = "OverchargePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int WINDAMT;
    private int LIGHTNINGAMT;
    public static TextureAtlas.AtlasRegion BigImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/Overcharge.png"), 0, 0, 84, 84);
    public static TextureAtlas.AtlasRegion SmallImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/OverchargeSmall.png"), 0, 0, 32, 32);

    public OverchargePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.region128 = BigImage;
        this.region48 = SmallImage;
        updateDescription();
    }

    public void update(int slot) {
        super.update(1);
        if (this.owner.hasPower(WindCharge.POWER_ID)) {
            this.amount = this.owner.getPower(WindCharge.POWER_ID).amount;
        } else {
            this.amount = 0;
        }
        if (this.owner.hasPower(LightningCharge.POWER_ID)) {
            this.amount2 = this.owner.getPower(LightningCharge.POWER_ID).amount;
        } else {
            this.amount2 = 0;
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS), false));

            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.amount2, DamageInfo.DamageType.THORNS), false));

    }

    public void updateDescription() {
        if (this.owner.hasPower(WindCharge.POWER_ID)) {
            WINDAMT = this.owner.getPower(WindCharge.POWER_ID).amount;
        } else {
            WINDAMT = 0;
        }
        if (this.owner.hasPower(LightningCharge.POWER_ID)) {
            LIGHTNINGAMT = this.owner.getPower(LightningCharge.POWER_ID).amount;
        } else {
            LIGHTNINGAMT = 0;
        }
        this.description = DESCRIPTIONS[0] + WINDAMT + DESCRIPTIONS[1] + LIGHTNINGAMT + DESCRIPTIONS[2];
    }
}