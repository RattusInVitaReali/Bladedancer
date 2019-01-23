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

public class WindStanceAction
        extends AbstractGameAction
{
    public HashMap<String[], AbstractCard> cardsMapWind = new HashMap<String[], AbstractCard>();

    public WindStanceAction()
    {
        this.actionType = ActionType.SPECIAL;
        cardsMapWind.put(new String[]{Strike.ID, LightningStrike.ID}, new WindStrike());
        cardsMapWind.put(new String[]{AwakeningStrike.ID, LightningAwakeningStrike.ID}, new WindAwakeningStrike());
        cardsMapWind.put(new String[]{Breeze.ID, LightningBreeze.ID}, new WindBreeze());
        cardsMapWind.put(new String[]{Gale.ID, LightningGale.ID}, new WindGale());
        cardsMapWind.put(new String[]{Lacerate.ID, LightningLacerate.ID}, new WindLacerate());
        cardsMapWind.put(new String[]{ParryingStrike.ID, LightningParryingStrike.ID}, new WindParryingStrike());
        cardsMapWind.put(new String[]{RagingBlow.ID, LightningRagingBlow.ID}, new WindRagingBlow());
        cardsMapWind.put(new String[]{RollingTyphoon.ID, LightningRollingTyphoon.ID}, new WindRollingTyphoon());
        cardsMapWind.put(new String[]{Zephyr.ID, LightningZephyr.ID}, new WindZephyr());
    }

    public void update() {
        for (String[] key : cardsMapWind.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.drawPile));
                        if (c.upgraded) {
                            AbstractCard card = cardsMapWind.get(key);
                            card.upgrade();
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, false, false));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(cardsMapWind.get(key).makeStatEquivalentCopy(), 1, true, false, false));
                        }
                    }
                }
            }
        }
        for (String[] key : cardsMapWind.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.hand));
                        if (c.upgraded) {
                            AbstractCard card = cardsMapWind.get(key);
                            card.upgrade();
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardsMapWind.get(key).makeStatEquivalentCopy(), 1));
                        }
                    }
                }
            }
        }
        for (String[] key : cardsMapWind.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.discardPile));
                        if (c.upgraded) {
                            AbstractCard card = cardsMapWind.get(key);
                            card.upgrade();
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(card, 1));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(cardsMapWind.get(key).makeStatEquivalentCopy(), 1));
                        }
                    }
                }
            }
        }
        this.isDone = true;
    }
}
