package blademaster;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.*;
import blademaster.cards.*;
import blademaster.characters.BlademasterCharacter;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterEnum;
import blademaster.patches.BlademasterTags;
import blademaster.perks.*;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import blademaster.relics.DancersAmulet;
import blademaster.variables.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class Blademaster implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber {
    
    //Logger
    private static final Logger logger = LogManager.getLogger(Blademaster.class.getName());
    
    //Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(0.0f, 102.0f, 102.0f);

    //(DEPRECATED)Placeholders
    public static final String PLACEHOLDER_ATTACK = "cards/Attack.png";
    public static final String PLACEHOLDER_WIND_ATTACK = "cards/WindAttack.png";
    public static final String PLACEHOLDER_LIGHTNING_ATTACK = "cards/LightningAttack.png";
    public static final String PLACEHOLDER_SKILL = "cards/Skill.png";
    public static final String PLACEHOLDER_WIND_SKILL = "cards/WindSkill.png";
    public static final String PLACEHOLDER_LIGHTNING_SKILL = "cards/LightningSkill.png";
    public static final String PLACEHOLDER_POWER = "cards/Power.png";
    public static final String PLACEHOLDER_POWER_ICON = "powers/Power.png";
    public static final String PLACEHOLDER_BLADE_ORB = "orbs/Orb.png";
    public static final String PLACEHOLDER_RELIC = "relics/Relic.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE = "relics/outline/Relic.png";

    //Static character stuff
    public static final String THE_DEFAULT_SHOULDER_1 = "characters/blademasterCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "characters/blademasterCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "characters/blademasterCharacter/corpse.png";

    //Character Skeleton
    public static final String THE_DEFAULT_SKELETON_ATLAS = "blademasterResources/images/characters/blademasterCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "blademasterResources/images/characters/blademasterCharacter/skeleton.json";

    //In-game mod settings panel stuff
    private static final String BADGE_IMAGE = "Badge.png";
    private static final String MOD_NAME = "The Bladedancer";
    private static final String AUTHOR = "Rattus";
    private static final String DESCRIPTION = "A character mod for Slay the Spire.";
    
    // Card backgrounds
    private static final String BLADEMASTER_ATTACK_GRAY = "512/bg_attack_default_gray.png";
    private static final String BLADEMASTER_POWER_GRAY = "512/bg_power_default_gray.png";
    private static final String BLADEMASTER_SKILL_GRAY = "512/bg_skill_default_gray.png";
    private static final String BLADEMASTER_ENERGY_ORB_GRAY = "512/card_default_gray_orb.png";
    private static final String BLADEMASTER_CARD_ENERGY_ORB = "512/card_small_orb.png";
    private static final String BLADEMASTER_ATTACK_GRAY_PORTRAIT = "1024/bg_attack_default_gray.png";
    private static final String BLADEMASTER_POWER_GRAY_PORTRAIT = "1024/bg_power_default_gray.png";
    private static final String BLADEMASTER_SKILL_GRAY_PORTRAIT = "1024/bg_skill_default_gray.png";
    private static final String BLADEMASTER_ENERGY_ORB_GRAY_PORTRAIT = "1024/card_default_gray_orb.png";

    //Character select screen
    private static final String THE_BLADEMASTER_BUTTON = "characterSelect/BlademasterCharacterButton.png";
    private static final String THE_BLADEMASTER_PORTRAIT = "characterSelect/BlademasterPortraitBG.png";


    public Blademaster() {
        logger.info("<-----BLADEMASTER----->");
        logger.info("Subscribe to basemod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + AbstractCardEnum.BLADEMASTER_GRAY.toString());

        BaseMod.addColor(AbstractCardEnum.BLADEMASTER_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, makePath(BLADEMASTER_ATTACK_GRAY),
                makePath(BLADEMASTER_SKILL_GRAY), makePath(BLADEMASTER_POWER_GRAY),
                makePath(BLADEMASTER_ENERGY_ORB_GRAY), makePath(BLADEMASTER_ATTACK_GRAY_PORTRAIT),
                makePath(BLADEMASTER_SKILL_GRAY_PORTRAIT), makePath(BLADEMASTER_POWER_GRAY_PORTRAIT),
                makePath(BLADEMASTER_ENERGY_ORB_GRAY_PORTRAIT), makePath(BLADEMASTER_CARD_ENERGY_ORB));

        logger.info("Done Creating the color");
    }

    @SuppressWarnings ("unused")
    public static void initialize() {
        logger.info("Initializing..");
        Blademaster blademaster = new Blademaster();
        logger.info("Done!");
    }


    public static String makeID(String idText) {
        return "blademaster:" + idText;
    }

    public static String makePath(String resource) {
        return "blademasterResources/images/" + resource;
    }

    //Getting Color for VFX
    public static Color GetStanceColor() {
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            return Color.WHITE;
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            return Color.WHITE;
        } else {
            return Color.WHITE;
        }
    }

    //Card tag checks
    public static boolean WindCard(AbstractCard card) {
        boolean retVal = false;
        if (card.hasTag(BlademasterTags.WIND_STANCE)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean LightningCard(AbstractCard card) {
        boolean retVal = false;
        if (card.hasTag(BlademasterTags.LIGHTNING_STANCE)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean ComboCard(AbstractCard card) {
        boolean retVal = false;
        if (card.hasTag(BlademasterTags.COMBO_FINISHER)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean FuryCard(AbstractCard card) {
        boolean retVal = false;
        if (card.hasTag(BlademasterTags.FURY_FINISHER)) {
            retVal = true;
        }
        return retVal;
    }


    //Animation runner
    public static void runAnim(final String anim) {
        AbstractDungeon.player.state.setAnimation(0, anim, false);
        AbstractDungeon.player.state.addAnimation(0, "Idle", true, 0.0f);
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("Adding the character " + BlademasterEnum.THE_BLADEMASTER.toString());

        BaseMod.addCharacter(new BlademasterCharacter("The Bladedancer", BlademasterEnum.THE_BLADEMASTER),
                makePath(THE_BLADEMASTER_BUTTON), makePath(THE_BLADEMASTER_PORTRAIT), BlademasterEnum.THE_BLADEMASTER);
        logger.info("Done!");
    }

    @Override
    public void receivePostInitialize() {
        logger.info("Load Badge Image and mod options");
        // Load the Mod Badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMAGE));

        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addUIElement(new ModLabel("Blademaster doesn't have any settings!", 400.0f, 700.0f,
                settingsPanel, (me) -> {
        }));
        BaseMod.registerModBadge(badgeTexture, MOD_NAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done!");

    }


    @Override
    public void receiveEditRelics() {
        //Relics
        logger.info("Adding relics");
        BaseMod.addRelicToCustomPool(new DancersAmulet(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new BonusComboPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new BonusFuryPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new DextrousPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new RandomBladePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new RandomStancePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new StrongPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new ComboEveryTurnPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new FuryEveryTurnPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new StanceHealPerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new BleedingLaceratePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new OnAttackedChargePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new WindChargePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new LightningChargePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        BaseMod.addRelicToCustomPool(new BleedingMoreDamagePerk(), AbstractCardEnum.BLADEMASTER_GRAY);
        logger.info("Done!");
    }

    @Override
    public void receiveEditCards() {
        //Variables
        logger.info("Adding variables..");
        BaseMod.addDynamicVariable(new WindSpirit());
        BaseMod.addDynamicVariable(new LightningSpirit());
        BaseMod.addDynamicVariable(new LightningSpirit2());
        BaseMod.addDynamicVariable(new LightningSpirit3());
        BaseMod.addDynamicVariable(new LightningSpiritDiv2());
        BaseMod.addDynamicVariable(new LightningSpiritDiv3());
        BaseMod.addDynamicVariable(new LightningSpiritPlusWindSpirit());
        BaseMod.addDynamicVariable(new ChargeNumber());
        logger.info("Done!");

        //Cards
        logger.info("Adding Cards..");
        BaseMod.addCard(new AirSlash());
        BaseMod.addCard(new AncestralHealing());
        BaseMod.addCard(new AspectOfLightning());
        BaseMod.addCard(new AspectOfWind());
        BaseMod.addCard(new AwakeningDefend());
        BaseMod.addCard(new AwakeningStrike());
        BaseMod.addCard(new BladeCross());
        BaseMod.addCard(new BladeDance());
        BaseMod.addCard(new BladeMastery());
        BaseMod.addCard(new Blindside());
        BaseMod.addCard(new Blockade());
        BaseMod.addCard(new Bloodbath());
        BaseMod.addCard(new BloodyBlow());
        BaseMod.addCard(new BrainBloom());
        BaseMod.addCard(new Breeze());
        BaseMod.addCard(new ChargingUp());
        BaseMod.addCard(new CloudOfSteel());
        BaseMod.addCard(new ColdBlood());
        BaseMod.addCard(new CombatPrep());
        BaseMod.addCard(new CullingStrike());
        BaseMod.addCard(new Cut());
        BaseMod.addCard(new DaggerAndCloak());
        BaseMod.addCard(new DancingEdge());
        BaseMod.addCard(new DeadlyDance());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new Discharge());
        BaseMod.addCard(new DragonStrike());
        BaseMod.addCard(new Empower());
        BaseMod.addCard(new EnchantedCuirass());
        BaseMod.addCard(new EyeOfTheStorm());
        BaseMod.addCard(new EyeOfTheTornado());
        BaseMod.addCard(new Flash());
        BaseMod.addCard(new FlockOfBlades());
        BaseMod.addCard(new Flurry());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new Frontflip());
        BaseMod.addCard(new FuriousStrike());
        BaseMod.addCard(new FuryOfTheElements());
        BaseMod.addCard(new Gale());
        BaseMod.addCard(new Gust());
        BaseMod.addCard(new Hailwind());
        BaseMod.addCard(new Havoc());
        BaseMod.addCard(new HighVoltage());
        BaseMod.addCard(new IcyWind());
        BaseMod.addCard(new KiBarrier());
        BaseMod.addCard(new Lacerate());
        BaseMod.addCard(new LeechingStrike());
        BaseMod.addCard(new LightningCrash());
        BaseMod.addCard(new LightningDraw());
        BaseMod.addCard(new Meditate());
        BaseMod.addCard(new Momentum());
        BaseMod.addCard(new OneTrickPony());
        BaseMod.addCard(new Overcharge());
        BaseMod.addCard(new ParryingStrike());
        BaseMod.addCard(new Quickness());
        BaseMod.addCard(new Quickstep());
        BaseMod.addCard(new RagingBlow());
        BaseMod.addCard(new Reversal());
        BaseMod.addCard(new RollingTyphoon());
        BaseMod.addCard(new Rupture());
        BaseMod.addCard(new Safeguard());
        BaseMod.addCard(new SharpBlades());
        BaseMod.addCard(new Sharpen());
        BaseMod.addCard(new Slice());
        BaseMod.addCard(new SlyStabs());
        BaseMod.addCard(new Stability());
        BaseMod.addCard(new StanceMastery());
        BaseMod.addCard(new Stormstrike());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Sunder());
        BaseMod.addCard(new ThreePointStrike());
        BaseMod.addCard(new Thrust());
        BaseMod.addCard(new ThunderingOpener());
        BaseMod.addCard(new ThunderSlash());
        BaseMod.addCard(new TraverseCut());
        BaseMod.addCard(new UnrelentingWind());
        BaseMod.addCard(new Vortex());
        BaseMod.addCard(new WindSlash());
        BaseMod.addCard(new WrongfulFootwork());
        BaseMod.addCard(new Zephyr());
        logger.info("Done!");

        logger.info("Unlocking cards..");
        UnlockTracker.unlockCard(AirSlash.ID);
        UnlockTracker.unlockCard(AncestralHealing.ID);
        UnlockTracker.unlockCard(AspectOfLightning.ID);
        UnlockTracker.unlockCard(AspectOfWind.ID);
        UnlockTracker.unlockCard(AwakeningDefend.ID);
        UnlockTracker.unlockCard(AwakeningStrike.ID);
        UnlockTracker.unlockCard(BladeCross.ID);
        UnlockTracker.unlockCard(BladeDance.ID);
        UnlockTracker.unlockCard(BladeMastery.ID);
        UnlockTracker.unlockCard(BladeMastery.ID);
        UnlockTracker.unlockCard(Blindside.ID);
        UnlockTracker.unlockCard(Blockade.ID);
        UnlockTracker.unlockCard(Bloodbath.ID);
        UnlockTracker.unlockCard(BloodyBlow.ID);
        UnlockTracker.unlockCard(BrainBloom.ID);
        UnlockTracker.unlockCard(Breeze.ID);
        UnlockTracker.unlockCard(ChargingUp.ID);
        UnlockTracker.unlockCard(CloudOfSteel.ID);
        UnlockTracker.unlockCard(ColdBlood.ID);
        UnlockTracker.unlockCard(CombatPrep.ID);
        UnlockTracker.unlockCard(CullingStrike.ID);
        UnlockTracker.unlockCard(Cut.ID);
        UnlockTracker.unlockCard(DaggerAndCloak.ID);
        UnlockTracker.unlockCard(DancingEdge.ID);
        UnlockTracker.unlockCard(DeadlyDance.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(Discharge.ID);
        UnlockTracker.unlockCard(DragonStrike.ID);
        UnlockTracker.unlockCard(Empower.ID);
        UnlockTracker.unlockCard(EnchantedCuirass.ID);
        UnlockTracker.unlockCard(EyeOfTheStorm.ID);
        UnlockTracker.unlockCard(EyeOfTheTornado.ID);
        UnlockTracker.unlockCard(Flash.ID);
        UnlockTracker.unlockCard(FlockOfBlades.ID);
        UnlockTracker.unlockCard(Flurry.ID);
        UnlockTracker.unlockCard(Frenzy.ID);
        UnlockTracker.unlockCard(Frontflip.ID);
        UnlockTracker.unlockCard(FuriousStrike.ID);
        UnlockTracker.unlockCard(FuryOfTheElements.ID);
        UnlockTracker.unlockCard(Gale.ID);
        UnlockTracker.unlockCard(Gust.ID);
        UnlockTracker.unlockCard(Hailwind.ID);
        UnlockTracker.unlockCard(Havoc.ID);
        UnlockTracker.unlockCard(HighVoltage.ID);
        UnlockTracker.unlockCard(IcyWind.ID);
        UnlockTracker.unlockCard(KiBarrier.ID);
        UnlockTracker.unlockCard(Lacerate.ID);
        UnlockTracker.unlockCard(LeechingStrike.ID);
        UnlockTracker.unlockCard(LightningCrash.ID);
        UnlockTracker.unlockCard(LightningDraw.ID);
        UnlockTracker.unlockCard(Meditate.ID);
        UnlockTracker.unlockCard(Momentum.ID);
        UnlockTracker.unlockCard(OneTrickPony.ID);
        UnlockTracker.unlockCard(Overcharge.ID);
        UnlockTracker.unlockCard(ParryingStrike.ID);
        UnlockTracker.unlockCard(Quickness.ID);
        UnlockTracker.unlockCard(Quickstep.ID);
        UnlockTracker.unlockCard(RagingBlow.ID);
        UnlockTracker.unlockCard(Reversal.ID);
        UnlockTracker.unlockCard(RollingTyphoon.ID);
        UnlockTracker.unlockCard(Rupture.ID);
        UnlockTracker.unlockCard(Safeguard.ID);
        UnlockTracker.unlockCard(SharpBlades.ID);
        UnlockTracker.unlockCard(Sharpen.ID);
        UnlockTracker.unlockCard(Slice.ID);
        UnlockTracker.unlockCard(SlyStabs.ID);
        UnlockTracker.unlockCard(Stability.ID);
        UnlockTracker.unlockCard(StanceMastery.ID);
        UnlockTracker.unlockCard(Stormstrike.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Sunder.ID);
        UnlockTracker.unlockCard(ThreePointStrike.ID);
        UnlockTracker.unlockCard(Thrust.ID);
        UnlockTracker.unlockCard(ThunderingOpener.ID);
        UnlockTracker.unlockCard(ThunderSlash.ID);
        UnlockTracker.unlockCard(TraverseCut.ID);
        UnlockTracker.unlockCard(UnrelentingWind.ID);
        UnlockTracker.unlockCard(Vortex.ID);
        UnlockTracker.unlockCard(WindSlash.ID);
        UnlockTracker.unlockCard(WrongfulFootwork.ID);
        UnlockTracker.unlockCard(Zephyr.ID);
        logger.info("Done!");
    }

    //Languages
    @Override
    public void receiveEditStrings() {
        String language = "eng";
        if (Settings.language == Settings.GameLanguage.ZHS) {
            language = "zhs";
        }

        loadLangStrings("eng");
        loadLangStrings(language);
    }

    //Language Strings
    private void loadLangStrings(String language) {
        String path = "blademasterResources/localization/" + language + "/Blademaster";
        BaseMod.loadCustomStringsFile(PowerStrings.class, path + "PowerStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, path + "RelicStrings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, path + "CardStrings.json");
        BaseMod.loadCustomStringsFile(OrbStrings.class, path + "OrbStrings.json");
        BaseMod.loadCustomStringsFile(BlightStrings.class, path + "BlightStrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, path + "BladedancerStrings.json");
    }

    //Language Keywords
    private void loadLangKeywords(String language) {
        String path = "blademasterResources/localization/" + language + "/";
        Gson gson = new Gson();
        String json = Gdx.files.internal(path + "BlademasterKeywordStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    //Keywords
    @Override
    public void receiveEditKeywords() {
        String language = "eng";
        switch (Settings.language) {
            case ZHS:
                language = "zhs";
                break;
        }
        loadLangKeywords("eng");
        loadLangKeywords(language);
    }
}
