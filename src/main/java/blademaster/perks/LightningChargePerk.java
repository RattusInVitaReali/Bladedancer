package blademaster.perks;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.blights.LightningChargePerkBlight;
import blademaster.interfaces.PerkRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class LightningChargePerk extends CustomRelic implements PerkRelic {

    public static final String ID = Blademaster.makeID("LightningChargePerk");
    public static final String IMG = "blademasterResources/images/relics/perks/LightningChargePerk.png";
    public static final String OUTLINE = "blademasterResources/images/relics/outline/Perk.png";

    public LightningChargePerk() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y, new LightningChargePerkBlight());
        AbstractDungeon.player.relics.remove(this);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new LightningChargePerk();
    }
}