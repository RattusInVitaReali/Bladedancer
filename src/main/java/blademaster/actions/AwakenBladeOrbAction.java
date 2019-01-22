package blademaster.actions;

import blademaster.orbs.AwakenedBladeOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AwakenBladeOrbAction
        extends AbstractGameAction
{
    public AwakenBladeOrbAction()
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.filledOrbCount() > 0) {
                AbstractDungeon.player.removeNextOrb();
                AbstractDungeon.actionManager.addToBottom(new ChannelAction(new AwakenedBladeOrb()));
            }
        }
        tickDuration();
    }
}
