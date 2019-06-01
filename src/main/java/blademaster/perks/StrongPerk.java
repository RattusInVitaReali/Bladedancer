package blademaster.perks;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.blights.StrongPerkBlight;
import blademaster.interfaces.PerkRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class StrongPerk extends CustomRelic implements PerkRelic {

    public static final String ID = Blademaster.makeID("StrongPerk");
    public static final String IMG = "blademasterResources/images/relics/perks/StrongPerk.png";
    public static final String OUTLINE = "blademasterResources/images/relics/outline/Perk.png";
    public static Texture IMAGE = new Texture(IMG);
    public static Texture OI = new Texture(OUTLINE);

    public StrongPerk() {
        super(ID, IMAGE, OI, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public void onEquip() {
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y, new StrongPerkBlight());
        AbstractDungeon.player.relics.remove(this);
    }


    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new StrongPerk();
    }
}