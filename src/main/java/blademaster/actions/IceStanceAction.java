package blademaster.actions;

import blademaster.patches.BlademasterTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class IceStanceAction
        extends AbstractGameAction
{
    public IceStanceAction()
    {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.hasTag(BlademasterTags.ICE_STANCE)) {
                card.flash(Color.WHITE);
            }
        }
        this.isDone = true;
    }
}
