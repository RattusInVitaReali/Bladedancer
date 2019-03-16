package blademaster.powers;

import blademaster.Blademaster;
import com.megacrit.cardcrawl.actions.defect.LightningOrbEvokeAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class OverchargePower extends AbstractPower {
    public static final String POWER_ID = "OverchargePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public OverchargePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = Blademaster.getDefaultPowerTexture();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.owner.hasPower(WindCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.owner.getPower(WindCharge.POWER_ID).amount, DamageInfo.DamageType.THORNS), false));
        }
        if (this.owner.hasPower(IceCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.owner.getPower(IceCharge.POWER_ID).amount, DamageInfo.DamageType.THORNS), false));
        }
        if (this.owner.hasPower(LightningCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.owner.getPower(LightningCharge.POWER_ID).amount, DamageInfo.DamageType.THORNS), false));
        }
        if (this.owner.hasPower(StoneCharge.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new LightningOrbEvokeAction(new DamageInfo(AbstractDungeon.player, this.owner.getPower(StoneCharge.POWER_ID).amount, DamageInfo.DamageType.THORNS), false));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


}