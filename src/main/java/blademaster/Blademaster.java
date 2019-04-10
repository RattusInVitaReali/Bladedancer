package blademaster;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.abstracts.CustomCard;
import basemod.interfaces.*;
import blademaster.cards.*;
import blademaster.characters.BlademasterCharacter;
import blademaster.patches.AbstractCardEnum;
import blademaster.patches.BlademasterTags;
import blademaster.patches.TheDefaultEnum;
import blademaster.perks.*;
import blademaster.powers.LightningStance;
import blademaster.powers.WindStance;
import blademaster.relics.DancersAmulet;
import blademaster.variables.LightningSpirit;
import blademaster.variables.WindSpirit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class Blademaster
        implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber {
    public static boolean hasHubris;

    static {
        hasHubris = Loader.isModLoaded("Hubris");
    }

    public static final Logger logger = LogManager.getLogger(Blademaster.class.getName());
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(0.0f, 102.0f, 102.0f);
    // Card images
    public static final String DEFAULT_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_SKILL = "cards/Skill.png";

    // =============== INPUT TEXTURE LOCATION =================
    public static final String DEFAULT_COMMON_POWER = "cards/Power.png";
    public static final String WIND_ATTACK = "cards/WindAttack.png";
    public static final String WIND_SKILL = "cards/WindSkill.png";
    public static final String LIGHTNING_ATTACK = "cards/LightningAttack.png";
    public static final String LIGHTNING_SKILL = "cards/LightningSkill.png";

    public static final String RARE_POWER = "powers/placeholder_power.png";
    public static final String IceStancePNG = "powers/IceStance.png";
    public static final String CalmnessPNG = "powers/Calmness.png";
    public static final String StoneStancePNG = "powers/StoneStance.png";
    public static final String LightningChargePNG = "powers/LightningCharge.png";
    public static final String LightningStancePNG = "powers/LightningStance.png";
    public static final String MeditatePNG = "powers/Meditate.png";
    public static final String RagePNG = "powers/Rage.png";
    public static final String RecklessnessPNG = "powers/Recklessness.png";
    public static final String SharpBladesPNG = "powers/SharpBlades.png";
    public static final String TiredPNG = "powers/Tired.png";
    public static final String WindChargePNG = "powers/WindCharge.png";
    public static final String WindStancePNG = "powers/WindStance.png";
    public static final String StoneChargePNG = "powers/StoneCharge.png";
    public static final String IceChargePNG = "powers/IceCharge.png";
    //Orb images
    public static final String BLADE_ORB = "orbs/BladeOrb.png";
    // Relic images
    public static final String DancersAmulet = "relics/DancersAmulet.png";
    public static final String DancersAmuletOutline = "relics/outline/DancersAmulet.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "characters/blademasterCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "characters/blademasterCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "characters/blademasterCharacter/corpse.png";
    //Mod Badge
    public static final String BADGE_IMAGE = "Badge.png";
    //Character Skeleton
    public static final String THE_DEFAULT_SKELETON_ATLAS = "blademasterResources/images/characters/blademasterCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "blademasterResources/images/characters/blademasterCharacter/skeleton.json";
    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "The Bladedancer";
    private static final String AUTHOR = "Rattus";
    private static final String DESCRIPTION = "A character mod for Slay the Spire.";
    // Image folder name
    private static final String DEFAULT_MOD_ASSETS_FOLDER = "blademasterResources/images";
    // Card backgrounds
    private static final String ATTACK_DEAFULT_GRAY = "512/bg_attack_default_gray.png";
    private static final String POWER_DEAFULT_GRAY = "512/bg_power_default_gray.png";
    private static final String SKILL_DEAFULT_GRAY = "512/bg_skill_default_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY = "512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "512/card_small_orb.png";
    private static final String ATTACK_DEAFULT_GRAY_PORTRAIT = "1024/bg_attack_default_gray.png";
    private static final String POWER_DEAFULT_GRAY_PORTRAIT = "1024/bg_power_default_gray.png";
    private static final String SKILL_DEAFULT_GRAY_PORTRAIT = "1024/bg_skill_default_gray.png";
    private static final String ENERGY_ORB_DEAFULT_GRAY_PORTRAIT = "1024/card_default_gray_orb.png";
    // Character assets
    private static final String THE_BLADEMASTER_BUTTON = "characterSelect/DefaultCharacterButton.png";
    private static final String THE_BLADEMASTER_PORTRAIT = "characterSelect/DeafultCharacterPortraitBG.png";


    public Blademaster() {
        logger.info("Subscribe to basemod hooks");

        BaseMod.subscribe(this);

        logger.info("Done subscribing");

        logger.info("Creating the color " + AbstractCardEnum.DEFAULT_GRAY.toString());

        BaseMod.addColor(AbstractCardEnum.DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
                DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, makePath(ATTACK_DEAFULT_GRAY),
                makePath(SKILL_DEAFULT_GRAY), makePath(POWER_DEAFULT_GRAY),
                makePath(ENERGY_ORB_DEAFULT_GRAY), makePath(ATTACK_DEAFULT_GRAY_PORTRAIT),
                makePath(SKILL_DEAFULT_GRAY_PORTRAIT), makePath(POWER_DEAFULT_GRAY_PORTRAIT),
                makePath(ENERGY_ORB_DEAFULT_GRAY_PORTRAIT), makePath(CARD_ENERGY_ORB));

        logger.info("Done Creating the color");
    }

    public static Texture getDefaultPowerTexture() {
        return new Texture(makePath(RARE_POWER));
    }

    public static Texture IceStancePNG() {
        return new Texture(makePath(IceStancePNG));
    }

    public static Texture StoneStancePNG() {
        return new Texture(makePath(StoneStancePNG));
    }

    public static Texture CalmnessPNG() {
        return new Texture(makePath(CalmnessPNG));
    }

    public static Texture LightningChargePNG() {
        return new Texture(makePath(LightningChargePNG));
    }

    public static Texture LightningStancePNG() {
        return new Texture(makePath(LightningStancePNG));
    }

    public static Texture MeditatePNG() {
        return new Texture(makePath(MeditatePNG));
    }

    public static Texture FuryPNG() {
        return new Texture(makePath(RagePNG));
    }

    public static Texture RecklessnessPNG() {
        return new Texture(makePath(RecklessnessPNG));
    }

    public static Texture SharpBladesPNG() {
        return new Texture(makePath(SharpBladesPNG));
    }

    public static Texture TiredPNG() {
        return new Texture(makePath(TiredPNG));
    }

    public static Texture WindChargePNG() {
        return new Texture(makePath(WindChargePNG));
    }

    public static Texture WindStancePNG() {
        return new Texture(makePath(WindStancePNG));
    }

    public static Texture IceChargePNG() {
        return new Texture(makePath(IceChargePNG));
    }

    public static Texture StoneChargePNG() {
        return new Texture(makePath(StoneChargePNG));
    }

    // =============== /INPUT TEXTURE LOCATION/ =================

    public static Texture getBladeOrbTexture() {
        return new Texture(makePath(BLADE_ORB));
    }

    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

    /**
     * Makes a full path for a resource path
     *
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
        return DEFAULT_MOD_ASSETS_FOLDER + "/" + resource;
    }

    @SuppressWarnings ("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        Blademaster defaultmod = new Blademaster();
        logger.info("========================= /Default Mod Initialized/ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================


    // =============== LOAD THE CHARACTER =================
    public static Color GetStanceColor() {
        if (AbstractDungeon.player.hasPower(WindStance.POWER_ID)) {
            return Color.WHITE;
        } else if (AbstractDungeon.player.hasPower(LightningStance.POWER_ID)) {
            return Color.WHITE;
        } else {
            return Color.WHITE;
        }
    }


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


    public static Texture loadBgAddonTexture(String imgPath) {
        Texture extraTexture;
        if (CustomCard.imgMap.containsKey(imgPath)) {
            extraTexture = CustomCard.imgMap.get(imgPath);
        } else {
            extraTexture = ImageMaster.loadImage(imgPath);
            CustomCard.imgMap.put(imgPath, extraTexture);
        }
        extraTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        return extraTexture;
    }

    // ================ /ADD CARDS/ ===================

    // this adds "ModName: " before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "blademaster:" + idText;
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters. " + "Add " + TheDefaultEnum.THE_BLADEMASTER.toString());

        BaseMod.addCharacter(new BlademasterCharacter("The BlademasterCharacter", TheDefaultEnum.THE_BLADEMASTER),
                makePath(THE_BLADEMASTER_BUTTON), makePath(THE_BLADEMASTER_PORTRAIT), TheDefaultEnum.THE_BLADEMASTER);

        receiveEditPotions();
        logger.info("done editing characters");
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
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        logger.info("Done loading badge Image and mod options");

    }

    public void receiveEditPotions() {
        logger.info("begin editing potions");

        // Class Specific Potion If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT")


        logger.info("end editing potions");
    }

    @Override
    public void receiveEditRelics() {
        logger.info("Add relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new DancersAmulet(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new BonusComboPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new BonusFuryPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new DextrousPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new FocusedPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new RandomBladePerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new RandomStancePerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new StrongPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new ComboEveryTurnPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new FuryEveryTurnPerk(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new StanceHealPerk(), AbstractCardEnum.DEFAULT_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.

        logger.info("Done adding relics!");
    }


    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditCards() {

        BaseMod.addDynamicVariable(new WindSpirit());
        BaseMod.addDynamicVariable(new LightningSpirit());

        logger.info("Add Cards");
        // Add the cards
        BaseMod.addCard(new AncestralHealing());
        BaseMod.addCard(new AwakeningDefend());
        BaseMod.addCard(new AwakeningStrike());
        BaseMod.addCard(new BladeDance());
        BaseMod.addCard(new BladeMastery());
        BaseMod.addCard(new BladeMastery());
        BaseMod.addCard(new Blockade());
        BaseMod.addCard(new BloodyBlow());
        BaseMod.addCard(new BrainBloom());
        BaseMod.addCard(new Breeze());
        BaseMod.addCard(new Burnout());
        BaseMod.addCard(new ChargingUp());
        BaseMod.addCard(new CloudOfSteel());
        BaseMod.addCard(new ColdBlood());
        BaseMod.addCard(new CombatPrep());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new DirtyTrick());
        BaseMod.addCard(new Discharge());
        BaseMod.addCard(new ElementalDestruction());
        BaseMod.addCard(new Empower());
        BaseMod.addCard(new FinishHim());
        BaseMod.addCard(new Flash());
        BaseMod.addCard(new FlockOfBlades());
        BaseMod.addCard(new Flurry());
        BaseMod.addCard(new Focus());
        BaseMod.addCard(new Frontflip());
        BaseMod.addCard(new Gale());
        BaseMod.addCard(new Gust());
        BaseMod.addCard(new Hailwind());
        BaseMod.addCard(new Havoc());
        BaseMod.addCard(new HighVoltage());
        BaseMod.addCard(new IcyWind());
        BaseMod.addCard(new Inferno());
        BaseMod.addCard(new Insult());
        BaseMod.addCard(new IntellectualSupremacy());
        BaseMod.addCard(new IvoryBracelet());
        BaseMod.addCard(new Lacerate());
        BaseMod.addCard(new LeechingStrike());
        BaseMod.addCard(new LightningCrash());
        BaseMod.addCard(new LightningDraw());
        BaseMod.addCard(new Meditate());
        BaseMod.addCard(new Meltdown());
        BaseMod.addCard(new Momentum());
        BaseMod.addCard(new NotBarrage());
        BaseMod.addCard(new NotDualcast());
        BaseMod.addCard(new NotFission());
        BaseMod.addCard(new Overcharge());
        BaseMod.addCard(new ParryingStrike());
        BaseMod.addCard(new Quickness());
        BaseMod.addCard(new RagingBlow());
        BaseMod.addCard(new RollingTyphoon());
        BaseMod.addCard(new Safeguard());
        BaseMod.addCard(new SharpBlades());
        BaseMod.addCard(new Slice());
        BaseMod.addCard(new SlyStabs());
        BaseMod.addCard(new Stability());
        BaseMod.addCard(new StanceMastery());
        BaseMod.addCard(new Stormstrike());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Sunder());
        BaseMod.addCard(new Thrust());
        BaseMod.addCard(new ThunderingOpener());
        BaseMod.addCard(new Vortex());
        BaseMod.addCard(new Wall());
        BaseMod.addCard(new WindSlash());
        BaseMod.addCard(new Zephyr());

        logger.info("Making sure the cards are unlocked.");
        UnlockTracker.unlockCard(AncestralHealing.ID);
        UnlockTracker.unlockCard(AwakeningDefend.ID);
        UnlockTracker.unlockCard(AwakeningStrike.ID);
        UnlockTracker.unlockCard(BladeDance.ID);
        UnlockTracker.unlockCard(BladeMastery.ID);
        UnlockTracker.unlockCard(BladeMastery.ID);
        UnlockTracker.unlockCard(Blockade.ID);
        UnlockTracker.unlockCard(BloodyBlow.ID);
        UnlockTracker.unlockCard(BrainBloom.ID);
        UnlockTracker.unlockCard(Breeze.ID);
        UnlockTracker.unlockCard(Burnout.ID);
        UnlockTracker.unlockCard(ChargingUp.ID);
        UnlockTracker.unlockCard(CloudOfSteel.ID);
        UnlockTracker.unlockCard(ColdBlood.ID);
        UnlockTracker.unlockCard(CombatPrep.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(DirtyTrick.ID);
        UnlockTracker.unlockCard(Discharge.ID);
        UnlockTracker.unlockCard(Empower.ID);
        UnlockTracker.unlockCard(EyeOfTheStorm.ID);
        UnlockTracker.unlockCard(FinishHim.ID);
        UnlockTracker.unlockCard(Flash.ID);
        UnlockTracker.unlockCard(FlockOfBlades.ID);
        UnlockTracker.unlockCard(Flurry.ID);
        UnlockTracker.unlockCard(Focus.ID);
        UnlockTracker.unlockCard(Frontflip.ID);
        UnlockTracker.unlockCard(Gale.ID);
        UnlockTracker.unlockCard(Gust.ID);
        UnlockTracker.unlockCard(Hailwind.ID);
        UnlockTracker.unlockCard(Havoc.ID);
        UnlockTracker.unlockCard(HighVoltage.ID);
        UnlockTracker.unlockCard(IcyWind.ID);
        UnlockTracker.unlockCard(Inferno.ID);
        UnlockTracker.unlockCard(Insult.ID);
        UnlockTracker.unlockCard(IntellectualSupremacy.ID);
        UnlockTracker.unlockCard(IvoryBracelet.ID);
        UnlockTracker.unlockCard(Lacerate.ID);
        UnlockTracker.unlockCard(LeechingStrike.ID);
        UnlockTracker.unlockCard(LightningCrash.ID);
        UnlockTracker.unlockCard(LightningDraw.ID);
        UnlockTracker.unlockCard(Meditate.ID);
        UnlockTracker.unlockCard(Meltdown.ID);
        UnlockTracker.unlockCard(Momentum.ID);
        UnlockTracker.unlockCard(NotBarrage.ID);
        UnlockTracker.unlockCard(NotDualcast.ID);
        UnlockTracker.unlockCard(NotFission.ID);
        UnlockTracker.unlockCard(Overcharge.ID);
        UnlockTracker.unlockCard(ParryingStrike.ID);
        UnlockTracker.unlockCard(Quickness.ID);
        UnlockTracker.unlockCard(RagingBlow.ID);
        UnlockTracker.unlockCard(RollingTyphoon.ID);
        UnlockTracker.unlockCard(Safeguard.ID);
        UnlockTracker.unlockCard(SharpBlades.ID);
        UnlockTracker.unlockCard(Slice.ID);
        UnlockTracker.unlockCard(SlyStabs.ID);
        UnlockTracker.unlockCard(Stability.ID);
        UnlockTracker.unlockCard(StanceMastery.ID);
        UnlockTracker.unlockCard(Stormstrike.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Sunder.ID);
        UnlockTracker.unlockCard(Thrust.ID);
        UnlockTracker.unlockCard(ThunderingOpener.ID);
        UnlockTracker.unlockCard(Vortex.ID);
        UnlockTracker.unlockCard(Wall.ID);
        UnlockTracker.unlockCard(WindSlash.ID);
        UnlockTracker.unlockCard(Zephyr.ID);
        logger.info("Cards - added!");
    }

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editting strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "blademasterResources/localization/BlademasterCardStrings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "blademasterResources/localization/BlademasterPowerStrings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "blademasterResources/localization/BlademasterRelicStrings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "blademasterResources/localization/BlademasterPotionStrings.json");

        // BlightStrings
        BaseMod.loadCustomStringsFile(BlightStrings.class,
                "blademasterResources/localization/BlademasterBlightStrings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    @Override
    public void receiveEditKeywords() {
        final String[] finisher = {"finisher", "finishers"};
        BaseMod.addKeyword(finisher, "A powerful card that uses #rFury or #rCombo as a resource.");

        final String[] havoc = {"havoc blade"};
        BaseMod.addKeyword(havoc, "A spectral blade that deals damage to enemies you attack.");

        final String[] parry = {"parry blade"};
        BaseMod.addKeyword(parry, "A spectral blade that reduces the damage taken whenever you're struck.");

        final String[] lacerate = {"lacerate", "laceration", "lacerates"};
        BaseMod.addKeyword(lacerate, "A 0-cost attack that deals 2 damage and exhausts.");

        final String[] awaken = {"awaken", "awakened"};
        BaseMod.addKeyword(awaken, "#yAwaken one of your spectral blades, making them more potent.");

        final String[] stance = {"stance", "stances"};
        BaseMod.addKeyword(stance, "A battle #yStance which determines the type of #yCharges you get. There are 2 #yStances: #gWind and #bLightning.");

        final String[] charge = {"charge", "charges"};
        BaseMod.addKeyword(charge, "A resource for stance-oriented cards.");

        final String[] bloodied = {"bloodied"};
        BaseMod.addKeyword(bloodied, "An effect that's applied if the target has less than #b50% #yHP remaining.");

        final String[] bleeding = {"bleeding", "bleed", "bleeds"};
        BaseMod.addKeyword(bleeding, "Bleeding enemies take damage at the end of the round.");
    }
}
