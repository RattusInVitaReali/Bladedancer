package blademaster.perks;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.interfaces.PerkRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import blademaster.blights.RandomDefensiveStancePerkBlight;

public class RandomDefensiveStancePerk extends CustomRelic implements PerkRelic {

    public static final String ID = Blademaster.makeID("RandomDefensiveStancePerk");
    public static final String IMG = "defaultModResources/images/relics/perks/DefensiveStanceMasteryPerk.png";
    public static final String OUTLINE = "defaultModResources/images/relics/outline/Perk.png";

    public RandomDefensiveStancePerk() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y, new RandomDefensiveStancePerkBlight());
        AbstractDungeon.player.relics.remove(this);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RandomDefensiveStancePerk();
    }
}