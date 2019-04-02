package blademaster.powers;

import blademaster.actions.WindStanceAction;
import blademaster.effects.particles.BetterFireBurstParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WindStance extends AbstractPower {
    public static final String POWER_ID = "WindStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float particleTimer = 0.0F;
    private float particleTimer2 = 0.03F;

    public WindStance(AbstractCreature owner) {
        this.name = NAME;
        this.priority = 6;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/WindStance.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/WindStanceSmall.png"), 0, 0, 32, 32);
        updateDescription();
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            int xOff = MathUtils.random(- 70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY , 0.5F, 1.0F, 0.1F));
            this.particleTimer = 0.06F;
        }
        if (this.particleTimer2 < 0.0F) {
            int xOff = MathUtils.random(- 70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY, 0.1F, 1.0F, 0.5F));
            this.particleTimer2 = 0.06F;
        }
    }

    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new WindStanceAction());
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(LightningStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    public void onVictory() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}