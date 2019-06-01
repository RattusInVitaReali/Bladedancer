package blademaster.perks;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.blights.DextrousPerkBlight;
import blademaster.interfaces.PerkRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DextrousPerk extends CustomRelic implements PerkRelic {

    public static final String ID = Blademaster.makeID("DextrousPerk");
    public static final String IMG = "blademasterResources/images/relics/perks/DexterousPerk.png";
    public static final String OUTLINE = "blademasterResources/images/relics/outline/Perk.png";
    public static Texture IMAGE = new Texture(IMG);
    public static Texture OI = new Texture(OUTLINE);

    public DextrousPerk() {
        super(ID, IMAGE, OI, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y, new DextrousPerkBlight());
        AbstractDungeon.player.relics.remove(this);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new DextrousPerk();
    }
}