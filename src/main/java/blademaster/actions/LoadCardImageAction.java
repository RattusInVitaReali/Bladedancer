
package blademaster.actions;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

public class LoadCardImageAction extends AbstractGameAction {
    private CustomCard card;
    private String imgPath;
    private boolean withFlash;

    public LoadCardImageAction(CustomCard card, String imgPath, boolean withFlash) {
        actionType = ActionType.SPECIAL;
        duration = Settings.ACTION_DUR_MED;
        this.card = card;
        this.imgPath = imgPath;
        this.withFlash = withFlash;
    }

    @Override
    public void update() {
        card.loadCardImage(imgPath);
        if (withFlash) {
            card.flash();
        }
        isDone = true;
        System.out.println("Card Image loaded!");
    }
}