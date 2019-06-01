package blademaster.powers;

import blademaster.actions.AwakenOrbAction;
import blademaster.actions.LightningStanceAction;
import blademaster.cards.WrongfulFootwork;
import blademaster.effects.particles.BetterFireBurstParticleEffect;
import blademaster.orbs.LightningBladeOrb;
import blademaster.orbs.LightningParryOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LightningStance extends AbstractPower {
    public static final String POWER_ID = "LightningStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float particleTimer = 0.0F;
    private float particleTimer2 = 0.03F;
    public static TextureAtlas.AtlasRegion BigImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/LightningStance.png"), 0, 0, 84, 84);
    public static TextureAtlas.AtlasRegion SmallImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/LightningStanceSmall.png"), 0, 0, 32, 32);

    public LightningStance(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.priority = 6;
        this.type = PowerType.BUFF;
        this.amount = - 1;
        this.isTurnBased = false;
        this.region128 = BigImage;
        this.region48 = SmallImage;
        updateDescription();
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            int xOff = MathUtils.random(- 70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY, 0.3F, 1.0F, 1.0F));
            this.particleTimer = 0.06F;
        }
        if (this.particleTimer2 < 0.0F) {
            int xOff = MathUtils.random(- 70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY, 0.3F, 0.7F, 1.0F));
            this.particleTimer2 = 0.06F;
        }
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(WindStance.POWER_ID)) {
            if (! this.owner.hasPower(StabilityPower.POWER_ID)) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
            }
        }
        if (power.ID.equals(this.ID) && ! AbstractDungeon.player.hasPower(StabilityPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new WrongfulFootwork(), 1, true, true));
        }
    }

    public void onVictory() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this));
    }

    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new LightningStanceAction());
        if (AbstractDungeon.player.orbs.size() == 5) {
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(2));
        }
        AbstractDungeon.actionManager.addToBottom(new AwakenOrbAction(new LightningBladeOrb(), new LightningParryOrb()));
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


}