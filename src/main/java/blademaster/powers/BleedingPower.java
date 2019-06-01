package blademaster.powers;

import blademaster.actions.BleedingLoseHpAction;
import blademaster.blights.BleedingMoreDamagePerkBlight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

public class BleedingPower extends AbstractPower implements HealthBarRenderPower {
    public static final String POWER_ID = "Bleeding";
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static final PowerStrings powerStrings;

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Bleeding");
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public AbstractCreature source;
    public static TextureAtlas.AtlasRegion BigImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/Bleeding.png"), 0, 0, 84, 84);
    public static TextureAtlas.AtlasRegion SmallImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/BleedingSmall.png"), 0, 0, 32, 32);

    public BleedingPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = "Bleeding";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.region128 = BigImage;
        this.region48 = SmallImage;
        this.source = source;
        updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription() {
        if (this.owner != null && ! this.owner.isPlayer) {
            if (AbstractDungeon.player.hasBlight(BleedingMoreDamagePerkBlight.ID)) {
                this.description = DESCRIPTIONS[3] + this.amount + DESCRIPTIONS[1];
            } else {
                this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[1];
            }
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            if (AbstractDungeon.player.hasBlight(BleedingMoreDamagePerkBlight.ID)) {
                return damage * (1.3F);
            }
            return damage * (1.2F);
        }
        return damage;
    }

    public void atStartOfTurn() {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT && ! AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            AbstractDungeon.actionManager.addToBottom(new BleedingLoseHpAction(this.owner, this.source, this.amount, AttackEffect.POISON));
        }

    }

    public int getHealthBarAmount() {
        return this.amount;
    }

    public Color getColor() {
        return Color.SCARLET;
    }
}
