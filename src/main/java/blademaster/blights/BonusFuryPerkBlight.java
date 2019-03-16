package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.powers.FuryPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class BonusFuryPerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:BonusFuryPerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public BonusFuryPerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "defaultModResources/images/relics/perks/FuriousPerk.png", true);
        this.img = ImageMaster.loadImage("defaultModResources/images/relics/perks/FuriousPerk.png");
        this.outlineImg = ImageMaster.loadImage("defaultModResources/images/relics/outline/Perk.png");
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FuryPower(AbstractDungeon.player, 10), 10));
    }

}