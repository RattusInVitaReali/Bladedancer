package blademaster.effects.particles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur;
import com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AdditiveSlashImpactEffect;

public class BetterBloodShotParticleEffect extends AbstractGameEffect {
    private float sX;
    private float sY;
    private float tX;
    private float tY;
    private float x;
    private float y;
    private float vY;
    private float vX;
    private AtlasRegion img;
    private boolean activated = false;
    private float currX;
    private float currY;

    public BetterBloodShotParticleEffect(float sX, float sY, float tX, float tY) {
        this.img = new TextureAtlas.AtlasRegion(new Texture("blademasterResources/images/effects/Dagger81.png"), 0, 0, 81, 81);
        this.sX = sX;
        this.sY = sY;
        this.tX = tX + MathUtils.random(- 30.0F, 30.0F) * Settings.scale;
        this.tY = tY + MathUtils.random(- 90.0F, 90.0F) * Settings.scale;
        this.vX = this.sX + MathUtils.random(- 200.0F, 200.0F) * Settings.scale;
        this.vY = this.sY + MathUtils.random(- 200.0F, 200.0F) * Settings.scale;
        this.x = this.sX;
        this.y = this.sY;
        this.scale = 1F;
        this.startingDuration = 0.6F;
        this.duration = this.startingDuration;
        this.color = new Color(1.0F, 1.0F, 1.0F, 1.0F);
    }


    public void update() {
        currX = this.x;
        currY = this.y;

        this.x = Interpolation.linear.apply(this.sX, this.tX, this.startingDuration - this.duration);
        this.y = Interpolation.linear.apply(this.sY, this.tY, this.startingDuration - this.duration);


        this.rotation = MathUtils.atan2(this.y - currY, this.x - currX) * 180.0f / MathUtils.PI;

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.duration < 0.0F) {
            AbstractDungeon.effectsQueue.add(new AdditiveSlashImpactEffect(this.tX, this.tY, this.color.cpy()));
            CardCrawlGame.screenShake.shake(ShakeIntensity.MED, ShakeDur.SHORT, MathUtils.randomBoolean());
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale * 2.0F, this.scale * 2.0F, this.rotation);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {
    }
}
