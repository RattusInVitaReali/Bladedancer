package blademaster.actions;

import blademaster.powers.BasicStance;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RemoveOffensiveStancesAction
        extends AbstractGameAction
{

    public RemoveOffensiveStancesAction()
    {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, BasicStance.POWER_ID));
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, LightningStance.POWER_ID));
        } else if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, WindStance.POWER_ID));
        }
        this.isDone = true;
    }
}
