package blademaster.relics;

import blademaster.powers.ComboPower;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;

public class RingOfSpeed extends CustomRelic {

    public static final String ID = Blademaster.makeID("RingOfSpeed");
    public static final String IMG = Blademaster.makePath(Blademaster.PLACEHOLDER_RELIC);
    public static final String OUTLINE = Blademaster.makePath(Blademaster.PLACEHOLDER_RELIC_OUTLINE);

    public RingOfSpeed() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atTurnStart()
    {
        if (!AbstractDungeon.player.hasPower(ComboPower.POWER_ID))
        {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ComboPower(AbstractDungeon.player, 0), 0));
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new RingOfSpeed();
    }
}