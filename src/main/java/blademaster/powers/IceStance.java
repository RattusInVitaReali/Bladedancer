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

public class IceStance extends AbstractPower {
    public static final String POWER_ID = "IceStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private float particleTimer = 0.0F;
    private float particleTimer2 = 0.03F;

    public IceStance(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.priority = 7;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = Blademaster.IceStancePNG();
    }

    @Override
    public void updateParticles() {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            int xOff = MathUtils.random(- 10, 10);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff + 53, this.owner.drawY + 210, 0.7F, 1.0F, 1.0F));
            this.particleTimer = 0.1F;
        }
        if (this.particleTimer2 < 0.0F) {
            int xOff = MathUtils.random(- 10, 10);
            AbstractDungeon.effectList.add(new BetterFireBurstParticleEffect(this.owner.drawX + xOff - 20, this.owner.drawY + 210, 0.7F, 1.0F, 1.0F));
            this.particleTimer2 = 0.1F;
        }
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}