package blademaster.orbs;

import blademaster.interfaces.onUseCardOrb;
import blademaster.powers.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import com.megacrit.cardcrawl.vfx.combat.PlasmaOrbActivateEffect;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class BladeOrb
        extends AbstractOrb implements onUseCardOrb {
    public static final String ORB_ID = "HavocBlade";
    public static final String[] DESC = {"#yPassive: whenever you play a single-target attack, deal #b ",
            " additional damage. NL #yEvoke: Deal #b ",
            " damage to all enemies."};
    private static int Samt;
    private static Texture ORB_BLADE = new Texture("blademasterResources/images/orbs/HavocBlade.png");
    private float vfxTimer;

    public BladeOrb() {
        this.vfxTimer = 0.3F;
        this.ID = ORB_ID;
        this.img = ORB_BLADE;
        this.name = "Havoc Blade";
        this.baseEvokeAmount = 5;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
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
        AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS), true));
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.type == AbstractCard.CardType.ATTACK) && (card.target == AbstractCard.CardTarget.ENEMY)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.07F));
            AbstractCreature m = action.target;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE, true));
            if (AbstractDungeon.player.hasPower(SharpBladesPower.POWER_ID)) {
                Samt = AbstractDungeon.player.getPower(SharpBladesPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, AbstractDungeon.player, new BleedingPower(m, AbstractDungeon.player, Samt), Samt));
            }
            if (AbstractDungeon.player.hasPower(LifestealPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(LifestealPower.POWER_ID).amount));
            }
            if (AbstractDungeon.player.hasPower(BlockadePower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(BlockadePower.POWER_ID).amount));
            }
        }
    }

    public void applyFocus() {
        AbstractPower power = AbstractDungeon.player.getPower("Focus");
        if ((power != null) && (! this.ID.equals("Plasma"))) {
            if (AbstractDungeon.player.hasPower(BladeDancePower.POWER_ID)) {
                this.passiveAmount = 2 * Math.max(0, this.basePassiveAmount + power.amount);
                this.evokeAmount = 2 * Math.max(0, this.baseEvokeAmount + power.amount);
            } else {
                this.passiveAmount = Math.max(0, this.basePassiveAmount + power.amount);
                this.evokeAmount = Math.max(0, this.baseEvokeAmount + power.amount);
            }
        } else {
            if (AbstractDungeon.player.hasPower(BladeDancePower.POWER_ID)) {
                this.passiveAmount = 2 * this.basePassiveAmount;
                this.evokeAmount = 2 * this.baseEvokeAmount;
            } else {
                this.passiveAmount = this.basePassiveAmount;
                this.evokeAmount = this.baseEvokeAmount;
            }
        }
    }

    public void updateAnimation() {
        super.updateAnimation();
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new DarkOrbPassiveEffect(this.cX, this.cY +this.bobEffect.y));
            this.vfxTimer = 0.25F;
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
        return new BladeOrb();
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("CARD_BURN", 0.1F);
        AbstractDungeon.effectsQueue.add(new PlasmaOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1F);
    }
}
