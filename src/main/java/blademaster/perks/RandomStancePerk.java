package blademaster.perks;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.blights.RandomStancePerkBlight;
import blademaster.interfaces.PerkRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class RandomStancePerk extends CustomRelic implements PerkRelic {

    public static final String ID = Blademaster.makeID("RandomStancePerk");
    public static final String IMG = "blademasterResources/images/relics/perks/OffensiveStanceMasteryPerk.png";
    public static final String OUTLINE = "blademasterResources/images/relics/outline/Perk.png";
    public static Texture IMAGE = new Texture(IMG);
    public static Texture OI = new Texture(OUTLINE);

    public RandomStancePerk() {
        super(ID, IMAGE, OI, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y, new RandomStancePerkBlight());
        AbstractDungeon.player.relics.remove(this);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RandomStancePerk();
    }
}