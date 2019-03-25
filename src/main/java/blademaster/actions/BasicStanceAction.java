package blademaster.actions;

import blademaster.patches.BlademasterTags;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BasicStanceAction
        extends AbstractGameAction {
    public BasicStanceAction() {
        this.actionType = ActionType.SPECIAL;
    }

    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (card.hasTag(BlademasterTags.STONE_STANCE) || card.hasTag(BlademasterTags.ICE_STANCE) || card.hasTag(BlademasterTags.LIGHTNING_STANCE) || card.hasTag(BlademasterTags.WIND_STANCE)) {
                card.flash(Color.GRAY.cpy());
            }
        }
        this.isDone = true;
    }
}
