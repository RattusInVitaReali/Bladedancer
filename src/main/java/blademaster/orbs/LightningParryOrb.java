package blademaster.orbs;

import blademaster.effects.particles.BetterFireBurstParticleEffect;
import blademaster.interfaces.onAttackedOrb;
import blademaster.powers.LightningCharge;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class LightningParryOrb
        extends AbstractOrb implements onAttackedOrb {
    public static final String ORB_ID = "ParryBlade";
    public static final String[] DESC = {"#yPassive: Whenever you're attacked, reduce the damage by #b",
            ", #band #bgain #b1 #bLightning #bCharge. NL #yEvoke: Gain #b",
            " #yThorns."};
    private static Texture ORB_BLADE = new Texture("blademasterResources/images/orbs/ParryBlade.png");
    private float vfxTimer;

    public LightningParryOrb() {
        this.vfxTimer = 0.5F;
        this.ID = ORB_ID;
        this.img = ORB_BLADE;
        this.name = "Lightning Parry Blade";
        this.baseEvokeAmount = 3;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 1;
        this.passiveAmount = this.basePassiveAmount;
        updateDescription();
        this.angle = 0.0F;
        this.channelAnimTimer = 0.5F;
        this.bobEffect = new BobEffect(15.0F, 3.0F);
    }

    public void updateDescription() {
        applyFocus();
        this.description = (DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2]);

    }


    public void onEvoke() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, this.evokeAmount), this.evokeAmount));
    }

    @Override
    public int onAttackedForOrbs(DamageInfo info, int damageAmount) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LightningCharge(AbstractDungeon.player, 1, false), 1));
        if (damageAmount < this.passiveAmount) {
            return 0;
        } else {
            return damageAmount - this.passiveAmount;
        }
    }

    public void updateAnimation() {
        super.updateAnimation();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new FrostOrbActivateEffect(this.cX, this.cY +this.bobEffect.y));
            this.vfxTimer -= Gdx.graphics.getDeltaTime();
            if (this.vfxTimer < 0.0F) {
                AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.cX, this.cY + this.bobEffect.y, 0.0F, 0.2F, 1.0F));
                this.vfxTimer = 0.07F;
            }
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(WHITE.cpy());
        sb.draw(this.img, this.cX - 96.0F, this.cY - 96.0F + this.bobEffect.y, 96.0F, 96.0F, 192.0F, 192.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.04F * Settings.scale, this.scale, this.angle, 0, 0, 192, 192, false, false);
        sb.setColor(WHITE.cpy());
        sb.setBlendFunction(770, 1);
        renderText(sb);
        this.hb.render(sb);
    }

    public AbstractOrb makeCopy() {
        return new ParryOrb();
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("CARD_BURN", 0.1F);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1F);
    }
}
