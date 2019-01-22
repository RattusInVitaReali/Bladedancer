package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LightningStance extends AbstractPower {
    public static final String POWER_ID = "LightningStance";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public LightningStance(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.img = Blademaster.LightningStancePNG();
        updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer)
    {
        this.flash();
        if (AbstractDungeon.player.hasPower(LightningCharge.POWER_ID)) {
        AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, (AbstractDungeon.player.getPower(LightningCharge.POWER_ID).amount * 2), DamageInfo.DamageType.THORNS), true));
        }
    }


    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


}