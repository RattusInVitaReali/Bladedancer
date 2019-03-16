package blademaster.actions;

import blademaster.powers.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class RemoveDefensiveStancesAction
        extends AbstractGameAction
{

    public RemoveDefensiveStancesAction()
    {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        if (AbstractDungeon.player.hasPower(BasicStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, BasicStance.POWER_ID));
        } else if (AbstractDungeon.player.hasPower(IceStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, IceStance.POWER_ID));
        } else if (AbstractDungeon.player.hasPower(StoneStance.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, StoneStance.POWER_ID));
        }
        this.isDone = true;
    }
}
