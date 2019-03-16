package blademaster.orbs;

import blademaster.interfaces.onAttackedOrb;
import blademaster.powers.BladeDancePower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;

public class AwakenedParryOrb
        extends AbstractOrb implements onAttackedOrb
{
    public static final String ORB_ID = "AwakenedParryBlade";
    public static final String[] DESC = { "#yPassive: Whenever you're attacked, reduce the damage by #b",
            " and deal #b",
            " damage to the attacker. NL #yEvoke: Gain #b",
            " #yThrons." };
    private static final float ORB_BORDER_SCALE = 1.2F;
    private float vfxTimer;
    private static final float VFX_INTERVAL_TIME = 0.25F;
    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.566371F;
    public static Texture ORB_BLADE = blademaster.Blademaster.getBladeOrbTexture();
    private static int COUNTER = 0;

    public AwakenedParryOrb()
    {
        this.vfxTimer = 0.5F;
        this.ID = ORB_ID;
        this.img = ORB_BLADE;
        this.name = "Awakened Parry Blade";
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.channelAnimTimer = 0.5F;
    }

    public void updateDescription()
    {
        applyFocus();
        this.description = (DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2] + this.evokeAmount + DESC[3]);

    }

    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if ((power != null) && (!this.ID.equals("Plasma")))
        {
            if (AbstractDungeon.player.hasPower(BladeDancePower.POWER_ID)) {
                this.passiveAmount = 2 * Math.max(0, this.basePassiveAmount + power.amount);
                this.evokeAmount = 2 *Math.max(0, this.baseEvokeAmount + power.amount);
            } else {
                this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
                this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
            }
        }
        else
        {
            if (AbstractDungeon.player.hasPower(BladeDancePower.POWER_ID)) {
                this.passiveAmount = 2 * this.basePassiveAmount;
                this.evokeAmount = 2 * this.baseEvokeAmount;
            } else {
                this.passiveAmount = this.basePassiveAmount;
                this.evokeAmount = this.baseEvokeAmount;
            }
        }
    }

    public void onEvoke()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, this.evokeAmount), this.evokeAmount));
        AbstractDungeon.actionManager.addToBottom(new DecreaseMaxOrbAction(1));
    }

    public int onAttackedForOrbs(DamageInfo info, int damage) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(info.owner, new DamageInfo(AbstractDungeon.player, this.evokeAmount), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if (damage < this.passiveAmount) {
            return 0;
        } else {
            return damage - this.passiveAmount;
        }
    }

    public void updateAnimation()
    {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F)
        {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY));
            this.vfxTimer = 0.25F;
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.04F * Settings.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, this.c.a / 2.0F));
        sb.setBlendFunction(770, 1);
        sb.draw(this.img, this.cX - 48.0F, this.cY - 48.0F + this.bobEffect.y, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale + MathUtils.sin(this.angle / 12.566371F) * 0.04F * Settings.scale, -this.angle, 0, 0, 96, 96, false, false);
        sb.setBlendFunction(770, 771);
        renderText(sb);
        this.hb.render(sb);
    }

    public AbstractOrb makeCopy()
    {
        return new AwakenedParryOrb();
    }

    public void triggerEvokeAnimation()
    {
        CardCrawlGame.sound.play("CARD_BURN", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX()
    {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1F);
    }
}
