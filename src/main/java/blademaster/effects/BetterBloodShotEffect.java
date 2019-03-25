package blademaster.effects;

import blademaster.effects.particles.BetterBloodShotParticleEffect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;


public class BetterBloodShotEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private int count = 0;
    private float timer = 0.0F;

    public BetterBloodShotEffect(float sX, float sY, float tX, float tY, int count) {
        this.sX = sX;
        this.sY = sY;
        this.tX = tX;
        this.tY = tY;
        this.count = count;
    }

    public void update() {
        this.timer -= Gdx.graphics.getDeltaTime();
        if (this.timer < 0.0F) {
            this.timer += MathUtils.random(0.05F, 0.15F);
            AbstractDungeon.effectsQueue.add(new BetterBloodShotParticleEffect(this.sX, this.sY, this.tX, this.tY));
            -- this.count;
            if (this.count == 0) {
                this.isDone = true;
            }
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}
