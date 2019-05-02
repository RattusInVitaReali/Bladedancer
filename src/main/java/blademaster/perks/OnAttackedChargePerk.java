package blademaster.perks;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.blights.OnAttackedChargePerkBlight;
import blademaster.interfaces.PerkRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class OnAttackedChargePerk extends CustomRelic implements PerkRelic {

    public static final String ID = Blademaster.makeID("OnAttackedChargePerk");
    public static final String IMG = "blademasterResources/images/relics/perks/OnAttackedChargePerk.png";
    public static final String OUTLINE = "blademasterResources/images/relics/outline/Perk.png";

    public OnAttackedChargePerk() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void onEquip() {
        AbstractDungeon.getCurrRoom().spawnBlightAndObtain(AbstractDungeon.player.hb_x, AbstractDungeon.player.hb_y, new OnAttackedChargePerkBlight());
        AbstractDungeon.player.relics.remove(this);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new OnAttackedChargePerk();
    }
}