package blademaster.powers;

import blademaster.actions.AwakenOrbAction;
import blademaster.actions.WindStanceAction;
import blademaster.cards.AbstractStanceCard;
import blademaster.cards.WrongfulFootwork;
import blademaster.effects.StanceAuraEffect;
import blademaster.effects.StanceEffect;
import blademaster.effects.particles.BetterFireBurstParticleEffect;
import blademaster.orbs.WindBladeOrb;
import blademaster.orbs.WindParryOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    public static TextureAtlas.AtlasRegion BigImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/WindStance.png"), 0, 0, 84, 84);
    public static TextureAtlas.AtlasRegion SmallImage = new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/powers/WindStanceSmall.png"), 0, 0, 32, 32);
    private float particleTimer = 0.0F;
    private float particleTimer2 = 0.03F;
    private float particleTimer3 = 0.0F;

    public WindStance(AbstractCreature owner) {
        this.name = NAME;
        this.priority = 6;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = - 1;
        this.isTurnBased = true;
        this.region128 = BigImage;
        this.region48 = SmallImage;
        updateDescription();
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        this.particleTimer3 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            int xOff = MathUtils.random(- 70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY + 20F, 0.5F, 1.0F, 0.1F));
            AbstractDungeon.effectsQueue.add(new StanceEffect(MathUtils.random(0.25f, 0.30f), MathUtils.random(0.8f, 0.82f), MathUtils.random(0.15f, 0.25f), 0.0f));
            this.particleTimer = 0.06F;
        }
        if (this.particleTimer2 < 0.0F) {
            int xOff = MathUtils.random(- 70, 70);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff, this.owner.drawY + 20F, 0.1F, 1.0F, 0.5F));
            this.particleTimer2 = 0.06F;
        }
        if (this.particleTimer3 < 0.0F) {
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect(MathUtils.random(0.25f, 0.30f), MathUtils.random(0.8f, 0.82f), MathUtils.random(0.15f, 0.25f), 0.0f));
            this.particleTimer3 = MathUtils.random(0.3F, 0.45F);
        }
    }

    public void onInitialApplication() {
        for (AbstractCard card : AbstractDungeon.player.discardPile.group) {
            if (card instanceof AbstractStanceCard) {
                ((AbstractStanceCard) card).onSwitchStance("Wind");
                ((AbstractStanceCard) card).WindArtS = true;
            }
        }
        AbstractDungeon.actionManager.addToBottom(new WindStanceAction());
        if (AbstractDungeon.player.orbs.size() == 5) {
            AbstractDungeon.actionManager.addToBottom(new IncreaseMaxOrbAction(2));
        }
        AbstractDungeon.actionManager.addToBottom(new AwakenOrbAction(new WindBladeOrb(), new WindParryOrb()));
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(LightningStance.POWER_ID)) {
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


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}