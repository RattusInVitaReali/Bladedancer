package blademaster.powers;

import blademaster.Blademaster;
import blademaster.effects.BetterFireBurstParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StoneStance extends AbstractPower {
    public static final String POWER_ID = "StoneStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float particleTimer = 0.0F;
    private float particleTimer2 = 0.03F;

    public StoneStance(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.priority = 7;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.img = Blademaster.StoneStancePNG();
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            int xOff = MathUtils.random(- 10, 10);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff + 53, this.owner.drawY + 210, 0.4F, 0.2F, 0.0F));
            this.particleTimer = 0.1F;
        }
        if (this.particleTimer2 < 0.0F) {
            int xOff = MathUtils.random(- 10, 10);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff - 20, this.owner.drawY + 210, 0.2F, 0.1F, 0.0F));
            this.particleTimer2 = 0.1F;
        }
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}