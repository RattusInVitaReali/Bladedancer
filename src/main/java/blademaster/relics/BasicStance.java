package blademaster.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;

public class BasicStance extends CustomRelic {

    public static final String ID = Blademaster.makeID("BasicStance");
    public static final String IMG = Blademaster.makePath(Blademaster.PLACEHOLDER_RELIC);
    public static final String OUTLINE = Blademaster.makePath(Blademaster.PLACEHOLDER_RELIC_OUTLINE);

    public BasicStance() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.COMMON, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public void atBattleStart()
    {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new blademaster.powers.BasicStance(AbstractDungeon.player)));
    }


    @Override
    public AbstractRelic makeCopy() {
        return new BasicStance();
    }
}