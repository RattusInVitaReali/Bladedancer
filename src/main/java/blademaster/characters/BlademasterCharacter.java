package blademaster.characters;

import basemod.abstracts.CustomPlayer;
import blademaster.blights.*;
import blademaster.cards.Defend;
import blademaster.cards.HighVoltage;
import blademaster.cards.RagingBlow;
import blademaster.cards.Strike;
import blademaster.orbs.LightningChargeOrb;
import blademaster.orbs.WindChargeOrb;
import blademaster.patches.AbstractCardEnum;
import blademaster.perks.*;
import blademaster.powers.*;
import blademaster.relics.DancersAmulet;
import blademaster.rewards.LinkedRewardItem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static blademaster.Blademaster.THE_DEFAULT_SKELETON_ATLAS;
import static blademaster.Blademaster.THE_DEFAULT_SKELETON_JSON;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0

public class BlademasterCharacter extends CustomPlayer {
    private int maxFlow = 5;
    private int currentFlow;

    private static final CharacterStrings charStrings;
    public static final String NAME;
    public static final String DESCRIPTION;

    public static final Logger logger = LogManager.getLogger(blademaster.Blademaster.class.getName());
    // =============== BASE STATS =================
    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 5;
    public static final String[] orbTextures = {
            "blademasterResources/images/characters/blademasterCharacter/orb/layer1.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer2.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer3.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer4.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer5.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer6.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer1d.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer2d.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer3d.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer4d.png",
            "blademasterResources/images/characters/blademasterCharacter/orb/layer5d.png",};

    // =============== /BASE STATS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============
    private Color hbTextColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);

    public float[] orbPositionsX = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public float[] orbPositionsY = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============F


    // =============== CHARACTER CLASS START =================

    public BlademasterCharacter(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "blademasterResources/images/characters/blademasterCharacter/orb/vfx.png", (String) null, null);

        // =============== TEXTURES, ENERGY, LOADOUT =================

        initializeClass(blademaster.Blademaster.makePath("characters/blademasterCharacter/main.png"), // required call to load textures and setup energy/loadout
                blademaster.Blademaster.makePath(blademaster.Blademaster.THE_DEFAULT_SHOULDER_1), // campfire pose
                blademaster.Blademaster.makePath(blademaster.Blademaster.THE_DEFAULT_SHOULDER_2), // another campfire pose
                blademaster.Blademaster.makePath(blademaster.Blademaster.THE_DEFAULT_CORPSE), // dead corpse
                getLoadout(), 20.0F, - 10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                0.95f);

        int Hope = MathUtils.random(100);
        System.out.println(Hope);
        if (Hope == 69) {
            AnimationState.TrackEntry e = state.setAnimation(0, "Thonk", true);
            e.setTime(e.getEndTime() * MathUtils.random());
        } else {
            AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
            e.setTime(e.getEndTime() * MathUtils.random());
        }
        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
        this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values
        initializeSlotPositions();

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================


    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAME, DESCRIPTION, STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(), getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList <String> getStartingDeck() {
        ArrayList <String> retVal = new ArrayList <>();

        logger.info("Begind loading started Deck strings");

        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);

        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);

        retVal.add(RagingBlow.ID);
        retVal.add(HighVoltage.ID);


        return retVal;
    }

    // Starting Relics
    public ArrayList <String> getStartingRelics() {
        ArrayList <String> retVal = new ArrayList <>();

        retVal.add(DancersAmulet.ID);

        UnlockTracker.markRelicAsSeen(DancersAmulet.ID);

        return retVal;
    }

    @Override
    public void preBattlePrep() {
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BasicStance(AbstractDungeon.player)));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new WindChargeOrb()));
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(new LightningChargeOrb()));
        if (! AbstractDungeon.player.hasPower(FuryPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FuryPower(AbstractDungeon.player, 0), 0));
        }
        if (! AbstractDungeon.player.hasPower(ComboPower.POWER_ID)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ComboPower(AbstractDungeon.player, 0), 0));
        }
    }

    public HashMap <String, String> blightMap = new HashMap <>();


    public void RewardsTrigger() {
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
        perksList.add(new RandomBladePerk());
        perksList.add(new RandomStancePerk());
        perksList.add(new StrongPerk());
        perksList.add(new StanceHealPerk());
        perksList.add(new FinisherDrawCardPerk());
        perksList.add(new ComboEveryTurnPerk());
        perksList.add(new FuryEveryTurnPerk());
        perksList.add(new OnAttackedChargePerk());
        perksList.add(new BleedingLaceratePerk());
        perksList.add(new WindChargePerk());
        perksList.add(new LightningChargePerk());
        perksList.add(new BleedingMoreDamagePerk());

        blightMap.put(BonusComboPerkBlight.ID, BonusComboPerk.ID);
        blightMap.put(BonusFuryPerkBlight.ID, BonusFuryPerk.ID);
        blightMap.put(DextrousPerkBlight.ID, DextrousPerk.ID);
        blightMap.put(RandomBladePerkBlight.ID, RandomBladePerk.ID);
        blightMap.put(RandomStancePerkBlight.ID, RandomStancePerk.ID);
        blightMap.put(StrongPerkBlight.ID, StrongPerk.ID);
        blightMap.put(FinisherDrawCardPerkBlight.ID, FinisherDrawCardPerk.ID);
        blightMap.put(StanceHealPerkBlight.ID, StanceHealPerk.ID);
        blightMap.put(ComboEveryTurnPerkBlight.ID, ComboEveryTurnPerk.ID);
        blightMap.put(FuryEveryTurnPerkBlight.ID, FuryEveryTurnPerk.ID);
        blightMap.put(OnAttackedChargePerkBlight.ID, OnAttackedChargePerk.ID);
        blightMap.put(BleedingLaceratePerkBlight.ID, BleedingLaceratePerk.ID);
        blightMap.put(WindChargePerkBlight.ID, WindChargePerk.ID);
        blightMap.put(LightningChargePerkBlight.ID, LightningChargePerk.ID);
        blightMap.put(BleedingMoreDamagePerkBlight.ID, BleedingMoreDamagePerk.ID);

        System.out.println("Published onTrigger.");

        boolean doFlash = false;
        boolean isGenerated = false;
        for (RewardItem reward : relicRewards) {
            if (! isGenerated) {
                AbstractRelic.RelicTier tier = reward.relic.tier;
                if (tier != AbstractRelic.RelicTier.SPECIAL && tier != AbstractRelic.RelicTier.DEPRECATED && tier != AbstractRelic.RelicTier.STARTER) {
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
                        int gen1 = 0;
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
        }
    }


    public float setSlotX(float range, int slotNum, int maxOrbs) {
        float dist = range * Settings.scale + (float) maxOrbs * 10.0F * Settings.scale;
        float angle = 100.0F + (float) maxOrbs * 12.0F;
        float offsetAngle = angle / 2.0F;
        angle *= (float) slotNum / ((float) maxOrbs - 1.0F);
        angle += 90.0F - offsetAngle;
        return dist * MathUtils.cosDeg(angle) + (float) Settings.WIDTH * 0.25F;
    }

    public float setSlotY(float range, int slotNum, int maxOrbs) {
        float dist = range * Settings.scale + (float) maxOrbs * 10.0F * Settings.scale;
        float angle = 100.0F + (float) maxOrbs * 12.0F;
        float offsetAngle = angle / 2.0F;
        angle *= (float) slotNum / ((float) maxOrbs - 1.0F);
        angle += 90.0F - offsetAngle;
        return dist * MathUtils.sinDeg(angle) + 340.0F * Settings.scale + hb_h / 2.0F;
    }

    public void initializeSlotPositions() {
        for (int i = 2; i <= 4; i++) {
            orbPositionsX[i] = setSlotX(200.0F, i - 1, 5);
            orbPositionsY[i] = setSlotY(200.0F, i - 1, 5);
        }
        orbPositionsX[5] = setSlotX(200.0F, 0, 5);
        orbPositionsX[6] = setSlotX(200.0F, 4, 5);

        orbPositionsY[5] = setSlotY(200.0F, 0, 5);
        orbPositionsY[6] = setSlotY(200.0F, 4, 5);

        orbPositionsX[0] = setSlotX(120.0F, 1, 6);
        orbPositionsX[1] = setSlotX(120.0F, 4, 6);

        orbPositionsY[0] = setSlotY(120.0F, 1, 6);
        orbPositionsY[1] = setSlotY(120.0F, 4, 6);

    }

    // Character select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // Character select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.DEFAULT_GRAY;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return blademaster.Blademaster.DEFAULT_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return "The Bladedancer";
    }

    //Which starting card should specific events give you?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }

    // The class name as it appears next to your player name in game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "the Bladedancer";
    }

    // Should return a new instance of your character, sending this.name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new BlademasterCharacter(this.name, this.chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return blademaster.Blademaster.DEFAULT_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return blademaster.Blademaster.DEFAULT_GRAY;
    }

    @SpireOverride
    public void renderPowerIcons(SpriteBatch sb, float x, float y) {
        float yoffset = 0.0F;
        float offset = 10.0F;
        float doffset = 10.0F;
        for (AbstractPower p : this.powers) {
            if (p.ID.equals(LightningStance.POWER_ID) || p.ID.equals(WindStance.POWER_ID) || p.ID.equals(BasicStance.POWER_ID)) {
                p.renderIcons(sb, x + (250F * Settings.scale), y - 20F * Settings.scale, Color.WHITE.cpy());
            } else if ((p.ID.equals(FuryPower.POWER_ID)) || (p.ID.equals(ComboPower.POWER_ID)) || (p.ID.equals(CalmnessPower.POWER_ID))
                    || (p.ID.equals(TiredPower.POWER_ID)) || (p.ID.equals(WindCharge.POWER_ID)) || (p.ID.equals(LightningCharge.POWER_ID))) {
                yoffset = - 40.0F;
                p.renderIcons(sb, x + (doffset * Settings.scale), y + ((- 48.0F + yoffset) * Settings.scale), Color.WHITE.cpy());
                doffset += 48.0F;
            } else {
                yoffset = 0F;
                p.renderIcons(sb, x + (offset * Settings.scale), y + ((- 48.0F + yoffset) * Settings.scale), Color.WHITE.cpy());
                offset += 48.0F;
            }
        }
        offset = 0.0F;
        doffset = 0.0F;
        for (AbstractPower p : this.powers) {
            if (p.ID.equals(LightningStance.POWER_ID) || p.ID.equals(WindStance.POWER_ID) || p.ID.equals(BasicStance.POWER_ID)) {
                p.renderAmount(sb, x + ((300F + 32.0F) * Settings.scale), y + ((- 18.0F) * Settings.scale), Color.WHITE);
            } else if ((p.ID.equals(FuryPower.POWER_ID)) || (p.ID.equals(ComboPower.POWER_ID)) || (p.ID.equals(CalmnessPower.POWER_ID))
                    || (p.ID.equals(TiredPower.POWER_ID)) || (p.ID.equals(WindCharge.POWER_ID)) || (p.ID.equals(LightningCharge.POWER_ID))) {
                yoffset = - 40.0F;
                p.renderAmount(sb, x + ((doffset + 32.0F) * Settings.scale), y + ((- 66.0F + yoffset) * Settings.scale), Color.WHITE);
                doffset += 48.0F;
            } else {
                yoffset = 0.0F;
                p.renderAmount(sb, x + ((offset + 32.0F) * Settings.scale), y + ((- 66.0F + yoffset) * Settings.scale), Color.WHITE);
                offset += 48.0F;
            }
        }
    }


    /*
    <<TO BE WORKED ON>>

    public void renderHealth(SpriteBatch sb) {
        float x1 = this.hb.cX - this.hb.width / 2.0F;
        float y1 = this.hb.cY - this.hb.height / 2.0F + 20F;
        this.renderFlowBar(sb, x1, y1);
        this.renderFlowBarText(sb, y1);
        super.renderHealth(sb);
    }


    private void renderFlowBar(SpriteBatch sb, float x, float y) {
        if (AbstractDungeon.player.hasPower(FlowPower.POWER_ID)) {
            this.currentFlow = AbstractDungeon.player.getPower(FlowPower.POWER_ID).amount;
        } else {
            this.currentFlow = 0;
        }
        sb.setColor(Color.LIGHT_GRAY);
        if (this.currentHealth > 0) {
            sb.draw(ImageMaster.HEALTH_BAR_L, x - HEALTH_BAR_HEIGHT, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);
        }

        sb.draw(ImageMaster.HEALTH_BAR_B, x, y + HEALTH_BAR_OFFSET_Y, this.hb.width * (float) this.currentFlow / (float) this.maxFlow, HEALTH_BAR_HEIGHT);
        sb.draw(ImageMaster.HEALTH_BAR_R, x + this.hb.width * (float) this.currentFlow / (float) this.maxFlow, y + HEALTH_BAR_OFFSET_Y, HEALTH_BAR_HEIGHT, HEALTH_BAR_HEIGHT);

    }

    private void renderFlowBarText(SpriteBatch sb, float y) {
        Color FlowColor = this.hbTextColor;
        FlowColor.a = 1.0F;
        FontHelper.renderFontCentered(sb, FontHelper.healthInfoFont, this.currentFlow + "/" + this.maxFlow, this.hb.cX, y + HEALTH_BAR_OFFSET_Y + HEALTH_TEXT_OFFSET_Y + 5.0F * Settings.scale, FlowColor);
    }

    private float flowHideTimer = 0.0F;

    @Override
    protected void updateHealthBar() {
        super.updateHealthBar();
        this.updateFlowBarHoverFade();
    }

    private void updateFlowBarHoverFade() {
        if (this.healthHb.hovered) {
            this.flowHideTimer -= Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.flowHideTimer < 0.2F) {
                this.flowHideTimer = 0.2F;
            }
        } else {
            this.flowHideTimer += Gdx.graphics.getDeltaTime() * 4.0F;
            if (this.flowHideTimer > 1.0F) {
                this.flowHideTimer = 1.0F;
            }
        }

    }

    <</TO BE WORKED ON>>
    */

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in damage action and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return "You sharpen your blades...";
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[5];
    }

    private static final float HEALTH_BAR_HEIGHT;
    private static final float HEALTH_BAR_OFFSET_Y;
    private static final float HEALTH_TEXT_OFFSET_Y;
    private static final float POWER_ICON_PADDING_X;
    private static final float HEALTH_BG_OFFSET_X;

    static {
        charStrings = CardCrawlGame.languagePack.getCharacterString("Bladedancer");
        NAME = charStrings.NAMES[0];
        DESCRIPTION = charStrings.TEXT[0];
        HEALTH_BAR_HEIGHT = 20.0F * Settings.scale;
        HEALTH_BAR_OFFSET_Y = - 28.0F * Settings.scale;
        HEALTH_TEXT_OFFSET_Y = 6.0F * Settings.scale;
        POWER_ICON_PADDING_X = 48.0F * Settings.scale;
        HEALTH_BG_OFFSET_X = 31.0F * Settings.scale;
    }

}
