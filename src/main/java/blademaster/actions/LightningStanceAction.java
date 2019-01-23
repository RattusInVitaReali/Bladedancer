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

public class LightningStanceAction
        extends AbstractGameAction
{
    public HashMap<String[], AbstractCard> cardsMapLightning = new HashMap<>();

    public LightningStanceAction()
    {
        this.actionType = ActionType.SPECIAL;
        cardsMapLightning.put(new String[]{Strike.ID, WindStrike.ID}, new LightningStrike());
        cardsMapLightning.put(new String[]{AwakeningStrike.ID, WindAwakeningStrike.ID}, new LightningAwakeningStrike());
        cardsMapLightning.put(new String[]{Breeze.ID, WindBreeze.ID}, new LightningBreeze());
        cardsMapLightning.put(new String[]{Gale.ID, WindGale.ID}, new LightningGale());
        cardsMapLightning.put(new String[]{Lacerate.ID, WindLacerate.ID}, new LightningLacerate());
        cardsMapLightning.put(new String[]{ParryingStrike.ID, WindParryingStrike.ID}, new LightningParryingStrike());
        cardsMapLightning.put(new String[]{RagingBlow.ID, WindRagingBlow.ID}, new LightningRagingBlow());
        cardsMapLightning.put(new String[]{RollingTyphoon.ID, WindRollingTyphoon.ID}, new LightningRollingTyphoon());
        cardsMapLightning.put(new String[]{Zephyr.ID, WindZephyr.ID}, new LightningZephyr());
    }

    public void update() {
        for (String[] key : cardsMapLightning.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.hand));
                        if (c.upgraded) {
                            AbstractCard card = cardsMapLightning.get(key);
                            card.upgrade();
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cardsMapLightning.get(key).makeStatEquivalentCopy(), 1));
                        }
                    }
                }
            }
        }
        for (String[] key : cardsMapLightning.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.drawPile));
                        if (c.upgraded) {
                            AbstractCard card = cardsMapLightning.get(key);
                            card.upgrade();
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, false, false));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(cardsMapLightning.get(key).makeStatEquivalentCopy(), 1, true, false, false));
                        }
                    }
                }
            }
        }
        for (String[] key : cardsMapLightning.keySet()) {
            for (String s : key) {
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (c.cardID.equals(s)) {
                        AbstractDungeon.actionManager.addToBottom(new PurgeSpecificCardAction(c, AbstractDungeon.player.discardPile));
                        if (c.upgraded) {
                            AbstractCard card = cardsMapLightning.get(key);
                            card.upgrade();
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(card, 1));
                        } else {
                            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(cardsMapLightning.get(key).makeStatEquivalentCopy(), 1));
                        }
                    }
                }
            }
        }
        this.isDone = true;
    }
}
