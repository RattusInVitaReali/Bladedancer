package blademaster.actions;

import blademaster.orbs.AwakenedBladeOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import blademaster.orbs.*;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static basemod.BaseMod.logger;

public class AwakenOrbAction
        extends AbstractGameAction
{
    public AwakenOrbAction()
    {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update()
    {
        logger.info("Action begins.");
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.filledOrbCount() > 0) {
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    String fma = orb.ID;
                    if (fma.equals(BladeOrb.ORB_ID)) {
                        AbstractDungeon.player.orbs.remove(orb);
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new AwakenedBladeOrb()));
                        break;
                    } else if (fma.equals(ParryOrb.ORB_ID)) {
                        AbstractDungeon.player.removeNextOrb();
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new AwakenedParryOrb()));
                        break;
                    }
                }
            }
        }
        this.isDone = true;
    }
}
