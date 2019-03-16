package blademaster.actions;

import blademaster.patches.BlademasterTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LightningStanceAction
        extends AbstractGameAction
{
    public LightningStanceAction()
    {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.hasTag(BlademasterTags.LIGHTNING_STANCE)) {
                card.flash(Color.BLUE.cpy());
            }
        }
        this.isDone = true;
    }
}
