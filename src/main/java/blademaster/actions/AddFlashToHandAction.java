package blademaster.actions;

import blademaster.cards.Flash;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class AddFlashToHandAction extends AbstractGameAction {
    private static final float DURATION_PER_CARD = 0.35F;
    private static final float PADDING;
    private AbstractCard c;
    private boolean isOtherCardInCenter = false;
    private int Thonk;

    public AddFlashToHandAction(boolean isUpgraded, int CounterBonus) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = 0.35F;
        this.c = new Flash();
        if (isUpgraded) {
            c.upgrade();
        }
        this.amount = 1;
        this.Thonk = CounterBonus;
    }

    public void update() {
        if (this.amount == 0) {
            this.isDone = true;
        } else {
            int discardAmount = 0;
            int handAmount = this.amount;
            if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                discardAmount = this.amount + AbstractDungeon.player.hand.size() - 10;
                handAmount -= discardAmount;
            }

            this.addToHand(handAmount);
            this.addToDiscard(discardAmount);
            if (this.amount > 0) {
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.8F));
            }

            this.isDone = true;
        }
    }

    private void addToHand(int handAmt) {
        int i;
        switch (this.amount) {
            case 0:
                break;
            case 1:
                if (handAmt == 1) {
                    AbstractCard card = new Flash();
                    ((Flash) card).counter += Thonk;
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card));
                }
                break;
            default:
                for (i = 0; i < handAmt; ++ i) {
                    AbstractCard card = new Flash();
                    ((Flash) card).counter += Thonk;
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card));
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(card, MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F), MathUtils.random((float) Settings.HEIGHT * 0.3F, (float) Settings.HEIGHT * 0.7F)));
                }
        }

    }

    private void addToDiscard(int discardAmt) {
        switch (this.amount) {
            case 0:
                break;
            case 1:
                if (discardAmt == 1) {
                    AbstractCard card = new Flash();
                    ((Flash) card).counter += Thonk;
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, (float) Settings.WIDTH / 2.0F + PADDING + AbstractCard.IMG_WIDTH, (float) Settings.HEIGHT / 2.0F));
                }
                break;
            default:
                for (int i = 0; i < discardAmt; ++ i) {
                    AbstractCard card = new Flash();
                    ((Flash) card).counter += Thonk;
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(card, MathUtils.random((float) Settings.WIDTH * 0.2F, (float) Settings.WIDTH * 0.8F), MathUtils.random((float) Settings.HEIGHT * 0.3F, (float) Settings.HEIGHT * 0.7F)));
                }
        }


    }

    static {
        PADDING = 25.0F * Settings.scale;
    }

}
