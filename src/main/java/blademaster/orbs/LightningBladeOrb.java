package blademaster.orbs;

import blademaster.effects.particles.BetterFireBurstParticleEffect;
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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.combat.DarkOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import static com.badlogic.gdx.graphics.Color.WHITE;

public class LightningBladeOrb
        extends AbstractOrb implements onUseCardOrb {
    public static final String ORB_ID = "LightningHavoc";
    public static final String[] DESC = {"#yPassive: whenever you play a single-target attack, deal #b ",
            " additional damage #btwice. NL #yEvoke: Deal #b ",
            " damage #band #bapply #b1 #yVulnerable to ALL enemies."};
    private static final float ORB_BORDER_SCALE = 1.2F;
    private static final float VFX_INTERVAL_TIME = 0.25F;
    private static final float ORB_WAVY_DIST = 0.04F;
    private static final float PI_4 = 12.566371F;
    private static int Samt;
    private static Texture ORB_BLADE = new Texture("blademasterResources/images/orbs/LightningHavoc.png");
    private float vfxTimer;

    public LightningBladeOrb() {
        this.vfxTimer = 0.5F;
        this.ID = ORB_ID;
        this.img = ORB_BLADE;
        this.name = "Lightning Havoc Blade";
        this.baseEvokeAmount = 7;
        this.evokeAmount = this.baseEvokeAmount;
        this.basePassiveAmount = 2;
        this.passiveAmount = this.basePassiveAmount;
        updateDescription();
        this.angle = MathUtils.random(360.0F);
        this.bobEffect = new BobEffect(15.0F, 3.0F);
        this.channelAnimTimer = 0.5F;
    }

    public void updateDescription() {
        applyFocus();
        this.description = (DESC[0] + this.passiveAmount + DESC[1] + this.evokeAmount + DESC[2]);
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


    public void onEvoke() {
        AbstractDungeon.actionManager.addToTop(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.evokeAmount, DamageInfo.DamageType.THORNS), true));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, AbstractDungeon.player, new VulnerablePower(monster, 1, false), 1));
            }
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ((card.type == AbstractCard.CardType.ATTACK) && (card.target == AbstractCard.CardTarget.ENEMY)) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.DARK), 0.1F));
            AbstractCreature m = action.target;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
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
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, this.passiveAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
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

    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 120.0F;
        this.vfxTimer -= Gdx.graphics.getDeltaTime();
        if (this.vfxTimer < 0.0F) {
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.cX + 80F, this.cY + this.bobEffect.y + 40F, 0.0F, 0.2F, 1.0F));
            this.vfxTimer = 0.07F;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(1.0f, 1.0f, 1.0f, 0.5f);
        sb.draw(this.img, this.cX - 96.0F, this.cY - 96.0F + this.bobEffect.y, 96.0F, 96.0F, 192.0F, 192.0F, this.scale, this.scale, this.angle, 0, 0, 192, 192, false, false);
        sb.setColor(1.0f, 1.0f, 1.0f, 0.5f);
        sb.draw(this.img, this.cX - 96.0F, this.cY - 96.0F + this.bobEffect.y, 96.0F, 96.0F, 192.0F, 192.0F, this.scale, this.scale, -this.angle, 0, 0, 192, 192, false, false);
        sb.setColor(WHITE);
        sb.draw(this.img, this.cX - 96.0F, this.cY - 96.0F + this.bobEffect.y, 96.0F, 96.0F, 192.0F, 192.0F, this.scale, this.scale, 0F, 0, 0, 192, 192, false, false);
        sb.setColor(WHITE.cpy());
        sb.setBlendFunction(770, 1);
        renderText(sb);
        this.hb.render(sb);
    }

    public AbstractOrb makeCopy() {
        return new LightningBladeOrb();
    }

    public void triggerEvokeAnimation() {
        CardCrawlGame.sound.play("CARD_BURN", 0.1F);
        AbstractDungeon.effectsQueue.add(new DarkOrbActivateEffect(this.cX, this.cY));
    }

    public void playChannelSFX() {
        CardCrawlGame.sound.play("ATTACK_FIRE", 0.1F);
    }
}
