package blademaster.blights;

import blademaster.interfaces.PerkBlight;
import blademaster.orbs.BladeOrb;
import blademaster.orbs.ParryOrb;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.BlightStrings;

public class RandomBladePerkBlight extends AbstractBlight implements PerkBlight {

    public static final String ID = "blademaster:RandomBladePerkBlight";
    private static final BlightStrings blightStrings = CardCrawlGame.languagePack.getBlightString(ID);
    public static final String NAME = blightStrings.NAME;
    public static final String[] DESCRIPTION = blightStrings.DESCRIPTION;

    public RandomBladePerkBlight() {
        super(ID, NAME, DESCRIPTION[0], "blademasterResources/images/relics/perks/BladesPerk.png", true);
        this.img = ImageMaster.loadImage("blademasterResources/images/relics/perks/BladesPerk.png");
        this.outlineImg = ImageMaster.loadImage("blademasterResources/images/relics/outline/Perk.png");
    }

    @Override
    public void atBattleStart() {
        this.flash();
        int wtf = AbstractDungeon.cardRandomRng.random(0, 1);
        if (wtf == 0) {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new BladeOrb()));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ChannelAction(new ParryOrb()));
        }
    }
}