package blademaster.actions;

import blademaster.orbs.BladeOrb;
import blademaster.orbs.ParryOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class AwakenOrbAction
        extends AbstractGameAction {
    private AbstractOrb orbToChannelIfParry;
    private AbstractOrb orbToChannelIfHavoc;

    public AwakenOrbAction(AbstractOrb orbToChannelIfHavoc, AbstractOrb orbToChannelIfParry) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.orbToChannelIfParry = orbToChannelIfParry;
        this.orbToChannelIfHavoc = orbToChannelIfHavoc;

    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hasOrb()) {
                for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                    if (orb instanceof ParryOrb) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(orbToChannelIfParry));
                        break;
                    }
                    if (orb instanceof BladeOrb) {
                        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                        AbstractDungeon.actionManager.addToBottom(new ChannelAction(orbToChannelIfHavoc));
                        break;
                    }
                }
            }
        }
        this.isDone = true;
    }
}
