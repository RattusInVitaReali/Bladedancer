package blademaster.actions;

import blademaster.orbs.LightningChargeOrb;
import blademaster.orbs.WindChargeOrb;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class BetterFissionAction extends AbstractGameAction {
    private boolean upgraded = false;

    public BetterFissionAction(boolean upgraded) {
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.ENERGY;
        this.upgraded = upgraded;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST) {
            int orbCount = AbstractDungeon.player.filledOrbCount();
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (orb.ID.equals(WindChargeOrb.ORB_ID) || orb.ID.equals(LightningChargeOrb.ORB_ID)) {
                    orbCount--;
                }
            }
            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, orbCount));
            AbstractDungeon.actionManager.addToTop(new GainEnergyAction(orbCount));
            for (AbstractOrb orb : AbstractDungeon.player.orbs) {
                if (! orb.ID.equals(WindChargeOrb.ORB_ID) && ! orb.ID.equals(LightningChargeOrb.ORB_ID)) {
                    if (this.upgraded) {
                        orb.triggerEvokeAnimation();
                        orb.onEvoke();
                    }
                    AbstractDungeon.actionManager.addToBottom(new RemoveSpecificOrbWithoutEvokingAction(orb));
                }
            }
        }

        this.tickDuration();
    }
}
