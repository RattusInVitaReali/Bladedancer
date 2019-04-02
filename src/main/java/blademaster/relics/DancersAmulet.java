package blademaster.relics;

import basemod.abstracts.CustomRelic;
import blademaster.Blademaster;
import blademaster.blights.*;
import blademaster.perks.*;
import blademaster.powers.BasicStance;
import blademaster.powers.ComboPower;
import blademaster.powers.FuryPower;
import blademaster.rewards.LinkedRewardItem;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DancersAmulet extends CustomRelic {

    public static final String ID = Blademaster.makeID("DancersAmulet");
    public static final String IMG = Blademaster.makePath(Blademaster.DancersAmulet);
    public static final String OUTLINE = Blademaster.makePath(Blademaster.DancersAmuletOutline);
    public HashMap <String, String> blightMap = new HashMap <>();


    public DancersAmulet() {
        super(ID, new Texture(IMG), new Texture(OUTLINE), RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BasicStance(AbstractDungeon.player)));
    }

    public void atTurnStart() {
        if (! AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FuryPower(AbstractDungeon.player, 0), 0));
        }
        if (! AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ComboPower(AbstractDungeon.player, 0), 0));
        }
    }

    @Override
    public void onTrigger() {
        List <RewardItem> relicRewards = new ArrayList <>();
        for (RewardItem reward : AbstractDungeon.getCurrRoom().rewards) {
            if (reward.type == RewardItem.RewardType.RELIC && reward.relicLink == null) {
                relicRewards.add(reward);
            }
        }

        List <AbstractRelic> perksList = new ArrayList <>();
        perksList.add(new BonusFuryPerk());
        perksList.add(new BonusComboPerk());
        perksList.add(new DextrousPerk());
        perksList.add(new FocusedPerk());
        perksList.add(new RandomBladePerk());
        perksList.add(new RandomStancePerk());
        perksList.add(new StrongPerk());
        perksList.add(new StanceHealPerk());
        perksList.add(new FinisherDrawCardPerk());
        perksList.add(new ComboEveryTurnPerk());
        perksList.add(new FuryEveryTurnPerk());

        blightMap.put(BonusComboPerkBlight.ID, BonusComboPerk.ID);
        blightMap.put(BonusFuryPerkBlight.ID, BonusFuryPerk.ID);
        blightMap.put(DextrousPerkBlight.ID, DextrousPerk.ID);
        blightMap.put(FocusedPerkBlight.ID, FocusedPerk.ID);
        blightMap.put(RandomBladePerkBlight.ID, RandomBladePerk.ID);
        blightMap.put(RandomStancePerkBlight.ID, RandomStancePerk.ID);
        blightMap.put(StrongPerkBlight.ID, StrongPerk.ID);
        blightMap.put(FinisherDrawCardPerkBlight.ID, FinisherDrawCardPerk.ID);
        blightMap.put(StanceHealPerkBlight.ID, StanceHealPerk.ID);
        blightMap.put(ComboEveryTurnPerkBlight.ID, ComboEveryTurnPerk.ID);
        blightMap.put(FuryEveryTurnPerkBlight.ID, FuryEveryTurnPerk.ID);

        System.out.println("Published onTrigger.");

        boolean doFlash = false;
        boolean isGenerated = false;
        for (RewardItem reward : relicRewards) {
            if (! isGenerated) {
                RelicTier tier = reward.relic.tier;
                if (tier != RelicTier.SPECIAL && tier != RelicTier.DEPRECATED && tier != RelicTier.STARTER) {
                    int poolCount;
                    for (AbstractBlight blight : AbstractDungeon.player.blights) {
                        String wtf = blightMap.get(blight.blightID);
                        System.out.println(blight.blightID);
                        System.out.println(wtf);
                        perksList.removeIf(relic1 -> (relic1.relicId.equals(wtf)));
                        System.out.println("Relic Removed!");
                    }
                    poolCount = perksList.size();
                    if (poolCount >= 2) {
                        int gen1 = AbstractDungeon.cardRandomRng.random(0, poolCount - 1);
                        int gen2;
                        do {
                            gen2 = AbstractDungeon.cardRandomRng.random(0, poolCount - 1);
                        } while (gen2 == gen1);
                        AbstractRelic newRelic = perksList.get(gen1);
                        AbstractRelic newRelic2 = perksList.get(gen2);
                        if (newRelic != null && newRelic2 != null) {
                            doFlash = true;
                            RewardItem firstRelic = new RewardItem(newRelic);
                            RewardItem newReward = new LinkedRewardItem(firstRelic);
                            RewardItem newReward2 = new LinkedRewardItem(newReward, newRelic2);
                            int indexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(reward);
                            AbstractDungeon.getCurrRoom().rewards.add(indexOf + 1, newReward);
                            AbstractDungeon.getCurrRoom().rewards.add(indexOf + 2, newReward2);
                            isGenerated = true;
                        }
                    } else if (poolCount == 1) {
                        int gen1 = AbstractDungeon.cardRandomRng.random(0, poolCount);
                        AbstractRelic newRelic = perksList.get(gen1);
                        if (newRelic != null) {
                            doFlash = true;
                            RewardItem firstRelic = new RewardItem(newRelic);
                            int indexOf = AbstractDungeon.getCurrRoom().rewards.indexOf(reward);
                            AbstractDungeon.getCurrRoom().rewards.add(indexOf + 1, firstRelic);
                            isGenerated = true;
                        }
                    }
                }
            }

            if (doFlash) {
                flash();
                AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

            }
        }
    }


    @Override
    public AbstractRelic makeCopy() {
        return new DancersAmulet();
    }
}