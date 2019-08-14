package blademaster.orbs;

import blademaster.powers.LightningCharge;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningOrbPassiveEffect;

public class LightningChargeOrb
        extends AbstractOrb {
    public static final String ORB_ID = "LightningCharge";
    public static final String[] DESC = {"You currently have #b",
            " #gLightning #yCharge.",
            " #gLightning #yCharges."};
    private static Texture IMG = new Texture("blademasterResources/images/orbs/LightningCharge.png");
    private float vfxTimer;

    public LightningChargeOrb() {
        this.vfxTimer = 0.3F;
        this.ID = ORB_ID;
        this.img = IMG;
        this.name = "Lightning Charges";
        this.baseEvokeAmount = 0;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 0;
        this.passiveAmount = this.basePassiveAmount;
        updateDescription();
        this.angle = 0.0F;
        this.channelAnimTimer = 0.5F;
    }

    public void updateDescription() {

        if (this.passiveAmount == 1) {
            this.description = DESC[0] + this.passiveAmount + DESC[1];
        } else {
            this.description = DESC[0] + this.passiveAmount + DESC[2];
        }
    }

    public void onEvoke() {
    }

    @Override
    public void update() {
        if (AbstractDungeon.player.hasPower(LightningCharge.POWER_ID)) {
            this.passiveAmount = AbstractDungeon.player.getPower(LightningCharge.POWER_ID).amount;
        } else {
            this.passiveAmount = 0;
        }
    }

    @Override
    public void applyFocus() {
    }

    @Override
    public void updateAnimation() {
        super.updateAnimation();
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            if (MathUtils.randomBoolean()) {
                AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY + this.bobEffect.y));
            }
            AbstractDungeon.effectList.add(new LightningOrbPassiveEffect(this.cX, this.cY + this.bobEffect.y));
            this.vfxTimer = MathUtils.random(0.75F, 1.25F);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.setBlendFunction(770, 1);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, this.scale * 1.2F, this.angle, 0, 0, 96, 96, false, false);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale * 1.2F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.05F + 0.19634955F, - this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        sb.setColor(this.c);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle / 12.0F, 0, 0, 96, 96, false, false);
        this.renderText(sb);
        this.hb.render(sb);
    }

    public AbstractOrb makeCopy() {
        return new LightningChargeOrb();
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("CARD_BURN", 0.1F);
        AbstractDungeon.effectsQueue.add(new LightningOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1F);
    }
}
