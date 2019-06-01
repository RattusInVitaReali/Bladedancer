package blademaster.relics;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DancersAmulet extends CustomRelic {

    public static final String ID = Blademaster.makeID("DancersAmulet");
    public static final String IMG = Blademaster.makePath(Blademaster.DancersAmulet);
    public static final String OUTLINE = Blademaster.makePath(Blademaster.DancersAmuletOutline);


    public DancersAmulet() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        this.counter += 1;
        if (this.counter >= 3) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, 1));
            this.counter = 0;
        }
    }

    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DancersAmulet();
    }
}