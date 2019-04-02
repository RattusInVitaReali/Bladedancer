package blademaster.actions;

import blademaster.orbs.AwakenedBladeOrb;
import blademaster.orbs.AwakenedParryOrb;
import blademaster.orbs.BladeOrb;
import blademaster.orbs.ParryOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import static basemod.BaseMod.logger;

public class AwakenOrbAction
        extends AbstractGameAction {
    public AwakenOrbAction() {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    public void update() {
        logger.info("Action begins.");
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hasOrb()) {
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    if (orb instanceof BladeOrb) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new AwakenedBladeOrb()));
                        break;
                    } else if (orb instanceof ParryOrb) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new AwakenedParryOrb()));
                        break;
                    }
                }
            }
        }
        this.isDone = true;
    }
}
