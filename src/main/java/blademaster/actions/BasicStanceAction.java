package blademaster.actions;

import blademaster.cards.*;
import blademaster.cards.lightning.*;
import blademaster.cards.wind.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class BasicStanceAction
        extends AbstractGameAction
{
    public HashMap<String[], AbstractCard> cardsMapWind = new HashMap<String[], AbstractCard>();

    public BasicStanceAction()
    {
        this.actionType = ActionType.SPECIAL;
        cardsMapWind.put(new String[]{WindStrike.ID, LightningStrike.ID}, new Strike());
        cardsMapWind.put(new String[]{WindAwakeningStrike.ID, LightningAwakeningStrike.ID}, new AwakeningStrike());
        cardsMapWind.put(new String[]{WindBreeze.ID, LightningBreeze.ID}, new Breeze());
        cardsMapWind.put(new String[]{WindGale.ID, LightningGale.ID}, new Gale());
        cardsMapWind.put(new String[]{WindLacerate.ID, LightningLacerate.ID}, new Lacerate());
        cardsMapWind.put(new String[]{WindParryingStrike.ID, LightningParryingStrike.ID}, new ParryingStrike());
        cardsMapWind.put(new String[]{WindRagingBlow.ID, LightningRagingBlow.ID}, new RagingBlow());
        cardsMapWind.put(new String[]{WindRollingTyphoon.ID, LightningRollingTyphoon.ID}, new RollingTyphoon());
        cardsMapWind.put(new String[]{WindZephyr.ID, LightningZephyr.ID}, new Zephyr());
    }

    public void update() {
        for (String[] key : cardsMapWind.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(cardsMapWind.get(key).makeStatEquivalentCopy(), 1, false, false, false));
                    }
                }
            }
        }
        for (String[] key : cardsMapWind.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.hand, true));
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardsMapWind.get(key).makeStatEquivalentCopy(), 1));
                    }
                }
            }
        }
        for (String[] key : cardsMapWind.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, AbstractDungeon.player.discardPile, true));
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(cardsMapWind.get(key).makeStatEquivalentCopy(), 1));
                    }
                }
            }
        }
        this.isDone = true;
    }
}
