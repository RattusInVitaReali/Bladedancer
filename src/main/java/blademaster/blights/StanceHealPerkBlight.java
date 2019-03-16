package blademaster.blights;

import blademaster.powers.IceStance;
import blademaster.powers.LightningStance;
import blademaster.powers.StoneStance;
import blademaster.powers.WindStance;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class StanceHealPerkBlight extends AbstractBlight {

    public static final String ID = "blademaster:StanceHealPerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;
    private int counter = 0;

    public StanceHealPerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "defaultModResources/images/relics/perks/HealthyStancesPerk.png", true);
        this.img = ImageMaster.loadImage("defaultModResources/images/relics/perks/HealthyStancesPerk.png");
        this.outlineImg = ImageMaster.loadImage("defaultModResources/images/relics/outline/Perk.png");
    }

    public void onVictory() {
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            counter += 1;
        }
        if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            counter += 1;
        }
        if (AbstractDungeon.player.hasPower(StoneStance.POWER_ID)) {
            counter += 1;
        }
        if (AbstractDungeon.player.hasPower(IceStance.POWER_ID)) {
            counter += 1;
        }
        if (AbstractDungeon.player.currentHealth > 0) {
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 4 * counter));
        }
    }
}