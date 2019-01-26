package blademaster.actions;

import blademaster.cards.*;
import blademaster.cards.lightning.*;
import blademaster.cards.wind.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.HashMap;

public class BasicStanceAction
        extends AbstractGameAction
{
    public HashMap<String[], AbstractCard> cardsMapBasic = new HashMap<String[], AbstractCard>();

    public BasicStanceAction()
    {
        this.actionType = ActionType.SPECIAL;
        cardsMapBasic.put(new String[]{WindStrike.ID, LightningStrike.ID}, new Strike());
        cardsMapBasic.put(new String[]{WindAwakeningStrike.ID, LightningAwakeningStrike.ID}, new AwakeningStrike());
        cardsMapBasic.put(new String[]{WindBreeze.ID, LightningBreeze.ID}, new Breeze());
        cardsMapBasic.put(new String[]{WindGale.ID, LightningGale.ID}, new Gale());
        cardsMapBasic.put(new String[]{WindLacerate.ID, LightningLacerate.ID}, new Lacerate());
        cardsMapBasic.put(new String[]{WindParryingStrike.ID, LightningParryingStrike.ID}, new ParryingStrike());
        cardsMapBasic.put(new String[]{WindRagingBlow.ID, LightningRagingBlow.ID}, new RagingBlow());
        cardsMapBasic.put(new String[]{WindRollingTyphoon.ID, LightningRollingTyphoon.ID}, new RollingTyphoon());
        cardsMapBasic.put(new String[]{WindZephyr.ID, LightningZephyr.ID}, new Zephyr());
    }

    public void update() {
        for (String[] key : cardsMapBasic.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractCard card = cardsMapBasic.get(key);
                        if (c.upgraded) {
                            card.upgrade();
                        }
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, false, false));
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.drawPile));
                    }
                }
            }
        }
        for (String[] key : cardsMapBasic.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.cardID.equals(s)) {
                        AbstractCard card = cardsMapBasic.get(key);
                        if (c.upgraded) {
                            card.upgrade();
                        }
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.hand));
                    }
                }
            }
        }
        for (String[] key : cardsMapBasic.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractCard card = cardsMapBasic.get(key);
                        if (c.upgraded) {
                            card.upgrade();
                        }
                        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(card, 1));
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.discardPile));
                    }
                }
            }
        }
        this.isDone = true;
    }
}
