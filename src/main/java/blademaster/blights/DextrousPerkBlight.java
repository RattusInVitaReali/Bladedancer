package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class DextrousPerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:DextrousPerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public DextrousPerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "defaultModResources/images/relics/perks/DexterousPerk.png", true);
        this.img = ImageMaster.loadImage("defaultModResources/images/relics/perks/DexterousPerk.png");
        this.outlineImg = ImageMaster.loadImage("defaultModResources/images/relics/outline/Perk.png");
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, 1), 1));
    }

}