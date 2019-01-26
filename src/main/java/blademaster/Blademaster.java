package blademaster;

import basemod.abstracts.CustomCard;
import blademaster.cards.*;
import blademaster.cards.wind.*;
import blademaster.cards.lightning.*;
import blademaster.relics.RingOfFury;
import blademaster.relics.RingOfSpeed;
import blademaster.variables.LightningSpirit;
import blademaster.variables.WindSpirit;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;

import basemod.BaseMod;
import basemod.ModLabel;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

import blademaster.patches.*;
import blademaster.characters.*;

import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/* 
 * Welcome to this mildly over-commented Slay the Spire modding base. 
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (Character, 
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, 1 relic, etc.
 * If you're new to modding, I highly recommend going though the BaseMod wiki for whatever you wish to add 
 * https://github.com/daviscook477/BaseMod/wiki  and work your way thought your made with this base. 
 * Feel free to use this in any way you like, of course. Happy modding!
 */

@SpireInitializer
public class Blademaster
        implements EditCardsSubscriber, EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber, EditCharactersSubscriber, PostInitializeSubscriber
{
    public static final Logger logger = LogManager.getLogger(Blademaster.class.getName());

    //This is for the in-game mod settings pannel.
    private static final String MODNAME = "The Bladedancer";
    private static final String AUTHOR = "Rattus";
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";

    // =============== INPUT TEXTURE LOCATION =================

    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(128.0f, 128.0f, 128.0f);
        
    // Image folder name
    private static final String DEFAULT_MOD_ASSETS_FOLDER = "defaultModResources/images";

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

    // Card images
    public static final String DEFAULT_COMMON_ATTACK = "cards/Attack.png";
    public static final String DEFAULT_COMMON_SKILL = "cards/Skill.png";
    public static final String DEFAULT_COMMON_POWER = "cards/Power.png";

    public static final String WIND_ATTACK = "cards/WindAttack.png";
    public static final String WIND_SKILL = "cards/WindSkill.png";
    public static final String LIGHTNING_ATTACK = "cards/LightningAttack.png";
    public static final String LIGHTNING_SKILL = "cards/LightningSkill.png";

    // Power images
    public static final String RARE_POWER = "powers/placeholder_power.png";
    public static final String CalmnessPNG = "powers/Calmness.png";
    public static final String LightningChargePNG = "powers/LightningCharge.png";
    public static final String LightningStancePNG = "powers/LightningStance.png";
    public static final String MeditatePNG = "powers/Meditate.png";
    public static final String RagePNG = "powers/Rage.png";
    public static final String RecklessnessPNG = "powers/Recklessness.png";
    public static final String SharpBladesPNG = "powers/SharpBlades.png";
    public static final String TiredPNG = "powers/Tired.png";
    public static final String WindChargePNG = "powers/WindCharge.png";
    public static final String WindStancePNG = "powers/WindStance.png";

    //Orb images
    public static final String BLADE_ORB = "orbs/BladeOrb.png";

    // Relic images  
    public static final String PLACEHOLDER_RELIC = "relics/placeholder_relic.png";
    public static final String PLACEHOLDER_RELIC_OUTLINE = "relics/outline/placeholder_relic.png";
    
    // Character assets
    private static final String THE_BLADEMASTER_BUTTON = "charSelect/DefaultCharacterButton.png";
    private static final String THE_BLADEMASTER_PORTRAIT = "charSelect/DeafultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "char/defaultCharacter/corpse.png";

    //Mod Badge
    public static final String BADGE_IMAGE = "Badge.png";


    public static Texture getDefaultPowerTexture() { return new Texture(makePath(RARE_POWER)); }
    public static Texture CalmnessPNG() { return new Texture(makePath(CalmnessPNG)); }
    public static Texture LightningChargePNG() { return new Texture(makePath(LightningChargePNG)); }
    public static Texture LightningStancePNG() { return new Texture(makePath(LightningStancePNG)); }
    public static Texture MeditatePNG() { return new Texture(makePath(MeditatePNG)); }
    public static Texture FuryPNG() { return new Texture(makePath(RagePNG)); }
    public static Texture RecklessnessPNG() { return new Texture(makePath(RecklessnessPNG)); }
    public static Texture SharpBladesPNG() { return new Texture(makePath(SharpBladesPNG)); }
    public static Texture TiredPNG() { return new Texture(makePath(TiredPNG)); }
    public static Texture WindChargePNG() { return new Texture(makePath(WindChargePNG)); }
    public static Texture WindStancePNG() { return new Texture(makePath(WindStancePNG)); }


    public static Texture getBladeOrbTexture() {
        return new Texture(makePath(BLADE_ORB));
    }

    // =============== /INPUT TEXTURE LOCATION/ =================

    /**
     * Makes a full path for a resource path
     * 
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
        return DEFAULT_MOD_ASSETS_FOLDER + "/" + resource;
    }

    // =============== SUBSCRIBE, CREATE THE COLOR, INITIALIZE =================

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

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        Blademaster defaultmod = new Blademaster();
        logger.info("========================= /Default Mod Initialized/ =========================");
    }

    // ============== /SUBSCRIBE, CREATE THE COLOR, INITIALIZE/ =================

    
    // =============== LOAD THE CHARACTER =================

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters. " + "Add " + TheDefaultEnum.THE_BLADEMASTER.toString());

        BaseMod.addCharacter(new TheCharacter("The Blademaster", TheDefaultEnum.THE_BLADEMASTER),
                makePath(THE_BLADEMASTER_BUTTON), makePath(THE_BLADEMASTER_PORTRAIT), TheDefaultEnum.THE_BLADEMASTER);
        
        receiveEditPotions();
        logger.info("done editing characters");
    }

    // =============== /LOAD THE CHARACTER/ =================

    
    // =============== POST-INITIALIZE =================

    
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

    // =============== / POST-INITIALIZE/ =================

    
    // ================ ADD POTIONS ===================

       
    public void receiveEditPotions() {
        logger.info("begin editing potions");
       
        // Class Specific Potion If you want your potion to not be class-specific, just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT")

      
        logger.info("end editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================

    
    // ================ ADD RELICS ===================

    @Override
    public void receiveEditRelics() {
        logger.info("Add relics");

        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        BaseMod.addRelicToCustomPool(new RingOfFury(), AbstractCardEnum.DEFAULT_GRAY);
        BaseMod.addRelicToCustomPool(new RingOfSpeed(), AbstractCardEnum.DEFAULT_GRAY);

        // This adds a relic to the Shared pool. Every character can find this relic.

        logger.info("Done adding relics!");
    }

    // ================ /ADD RELICS/ ===================

    
    
    // ================ ADD CARDS ===================

    @Override
    public void receiveEditCards() {

        BaseMod.addDynamicVariable(new WindSpirit());
        BaseMod.addDynamicVariable(new LightningSpirit());

        logger.info("Add Cards");
        // Add the cards
        BaseMod.addCard(new AwakeningStrike());
        BaseMod.addCard(new BasicAttack());
        BaseMod.addCard(new BladeMastery());
        BaseMod.addCard(new Breeze());
        BaseMod.addCard(new ChargingUp());
        BaseMod.addCard(new CloudOfSteel());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new DefiantStance());
        BaseMod.addCard(new EyeOfTheStorm());
        BaseMod.addCard(new FallBack());
        BaseMod.addCard(new Flurry());
        BaseMod.addCard(new Frontflip());
        BaseMod.addCard(new Gale());
        BaseMod.addCard(new GreaterBladeMastery());
        BaseMod.addCard(new Lacerate());
        BaseMod.addCard(new LightningCrash());
        BaseMod.addCard(new LightningDash());
        BaseMod.addCard(new LightningDraw());
        BaseMod.addCard(new Meditate());
        BaseMod.addCard(new Parry());
        BaseMod.addCard(new ParryingStrike());
        BaseMod.addCard(new RagingBlow());
        BaseMod.addCard(new Recklessness());
        BaseMod.addCard(new Retreat());
        BaseMod.addCard(new RollingTyphoon());
        BaseMod.addCard(new Safeguard());
        BaseMod.addCard(new SecondWind());
        BaseMod.addCard(new SharpBlades());
        BaseMod.addCard(new StanceMastery());
        BaseMod.addCard(new Stormstrike());
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Sunder());
        BaseMod.addCard(new Surge());
        BaseMod.addCard(new WindSlash());
        BaseMod.addCard(new Windstorm());
        BaseMod.addCard(new Windwall());
        BaseMod.addCard(new Zephyr());
        
        //Wind
        BaseMod.addCard(new WindAwakeningStrike());
        BaseMod.addCard(new WindBreeze());
        BaseMod.addCard(new WindGale());
        BaseMod.addCard(new WindLacerate());
        BaseMod.addCard(new WindParryingStrike());
        BaseMod.addCard(new WindRagingBlow());
        BaseMod.addCard(new WindRollingTyphoon());
        BaseMod.addCard(new WindStrike());
        BaseMod.addCard(new WindZephyr());
        
        //Lightning
        BaseMod.addCard(new LightningAwakeningStrike());
        BaseMod.addCard(new LightningBreeze());
        BaseMod.addCard(new LightningGale());
        BaseMod.addCard(new LightningLacerate());
        BaseMod.addCard(new LightningParryingStrike());
        BaseMod.addCard(new LightningRagingBlow());
        BaseMod.addCard(new LightningRollingTyphoon());
        BaseMod.addCard(new LightningStrike());
        BaseMod.addCard(new LightningZephyr());
        
        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        UnlockTracker.unlockCard(AwakeningStrike.ID);
        UnlockTracker.unlockCard(BladeMastery.ID);
        UnlockTracker.unlockCard(BasicAttack.ID);
        UnlockTracker.unlockCard(Breeze.ID);
        UnlockTracker.unlockCard(ChargingUp.ID);
        UnlockTracker.unlockCard(CloudOfSteel.ID);
        UnlockTracker.unlockCard(Defend.ID);
        UnlockTracker.unlockCard(DefiantStance.ID);
        UnlockTracker.unlockCard(EyeOfTheStorm.ID);
        UnlockTracker.unlockCard(FallBack.ID);
        UnlockTracker.unlockCard(Flurry.ID);
        UnlockTracker.unlockCard(Frontflip.ID);
        UnlockTracker.unlockCard(Gale.ID);
        UnlockTracker.unlockCard(GreaterBladeMastery.ID);
        UnlockTracker.unlockCard(Lacerate.ID);
        UnlockTracker.unlockCard(LightningCrash.ID);
        UnlockTracker.unlockCard(LightningDash.ID);
        UnlockTracker.unlockCard(LightningDraw.ID);
        UnlockTracker.unlockCard(Meditate.ID);
        UnlockTracker.unlockCard(Parry.ID);
        UnlockTracker.unlockCard(ParryingStrike.ID);
        UnlockTracker.unlockCard(RagingBlow.ID);
        UnlockTracker.unlockCard(Recklessness.ID);
        UnlockTracker.unlockCard(Retreat.ID);
        UnlockTracker.unlockCard(RollingTyphoon.ID);
        UnlockTracker.unlockCard(Safeguard.ID);
        UnlockTracker.unlockCard(SecondWind.ID);
        UnlockTracker.unlockCard(SharpBlades.ID);
        UnlockTracker.unlockCard(StanceMastery.ID);
        UnlockTracker.unlockCard(Stormstrike.ID);
        UnlockTracker.unlockCard(Strike.ID);
        UnlockTracker.unlockCard(Sunder.ID);
        UnlockTracker.unlockCard(Surge.ID);
        UnlockTracker.unlockCard(WindSlash.ID);
        UnlockTracker.unlockCard(Windstorm.ID);
        UnlockTracker.unlockCard(Windwall.ID);
        UnlockTracker.unlockCard(Zephyr.ID);
        
        //Wind
        UnlockTracker.unlockCard(WindAwakeningStrike.ID);
        UnlockTracker.unlockCard(WindBreeze.ID);
        UnlockTracker.unlockCard(WindGale.ID);
        UnlockTracker.unlockCard(WindLacerate.ID);
        UnlockTracker.unlockCard(WindParryingStrike.ID);
        UnlockTracker.unlockCard(WindRagingBlow.ID);
        UnlockTracker.unlockCard(WindRollingTyphoon.ID);
        UnlockTracker.unlockCard(WindStrike.ID);
        UnlockTracker.unlockCard(WindZephyr.ID);
        
        //Lightning
        UnlockTracker.unlockCard(LightningAwakeningStrike.ID);
        UnlockTracker.unlockCard(LightningBreeze.ID);
        UnlockTracker.unlockCard(LightningGale.ID);
        UnlockTracker.unlockCard(LightningLacerate.ID);
        UnlockTracker.unlockCard(LightningParryingStrike.ID);
        UnlockTracker.unlockCard(LightningRagingBlow.ID);
        UnlockTracker.unlockCard(LightningRollingTyphoon.ID);
        UnlockTracker.unlockCard(LightningStrike.ID);
        UnlockTracker.unlockCard(LightningZephyr.ID);



        logger.info("Cards - added!");
    }

    // ================ /ADD CARDS/ ===================

    public static boolean ComboFinisher(AbstractCard card) {
        boolean retVal = false;
        if (card.hasTag(BlademasterTags.COMBO_FINISHER)) {
            retVal = true;
        }
        return retVal;
    }

    public static boolean FuryFinisher(AbstractCard card) {
        boolean retVal = false;
        if (card.hasTag(BlademasterTags.FURY_FINISHER)) {
            retVal = true;
        }
        return retVal;
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
    
    
    // ================ LOAD THE TEXT ===================

    @Override
    public void receiveEditStrings() {
        logger.info("Begin editting strings");

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                "defaultModResources/localization/DefaultMod-Card-Strings.json");

        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                "defaultModResources/localization/PowerStrings.json");

        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                "defaultModResources/localization/DefaultMod-Relic-Strings.json");

        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                "defaultModResources/localization/DefaultMod-Potion-Strings.json");

        logger.info("Done edittting strings");
    }

    // ================ /LOAD THE TEXT/ =====================

    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        final String[] fury = {"fury", "furious"};
        BaseMod.addKeyword(fury, "Resource used for #rFinishers. Gain #yFury on dealing damage.");

        final String[] combo = {"combo"};
        BaseMod.addKeyword(combo, "Resource used for #rFinishers. Gain #yCombo on playing cards.");

        final String[] finisher = {"finisher", "finishers"};
        BaseMod.addKeyword(finisher, "A powerful card that uses #yFury or #yCombo as a resource, and prevents the further generation of said resource until the next turn.");

        final String[] blade = {"blade", "blades"};
        BaseMod.addKeyword(blade, "A spectral blade that orbits around the Bladedancer and has special effects.");

        final String[] havoc = {"havoc"};
        BaseMod.addKeyword(havoc, "A #yBlade that deals damage to enemies you attack.");

        final String[] parry = {"parry"};
        BaseMod.addKeyword(parry, "A #yBlade that gives you block whenever you're struck. Doesn't stack (Not a bug, a feature (Not really, it's actually a bug and idk how to fix it)).");

        final String[] lacerate = {"lacerate", "laceration", "lacerates"};
        BaseMod.addKeyword(lacerate, "A 0-cost attack that deals 2 damage.");

        final String[] awaken = {"awaken", "awakened"};
        BaseMod.addKeyword(awaken, "#yAwaken one of your #yBlades, making them more potent.");

        final String[] stance = {"stance", "stances"};
        BaseMod.addKeyword(stance, "A battle Stance which determines the type of #yCharges you get.");

        final String[] charge = {"charge", "charges"};
        BaseMod.addKeyword(charge, "A special #yCharge that does something at the end of turn. Can be used as a resource for special cards.");

        final String[] wind = {"wind"};
        BaseMod.addKeyword(wind, "A #yCharge that gives you block at the end of your turn. NL Can be used as a resource for #rWind cards.");

        final String[] lightning = {"lightning"};
        BaseMod.addKeyword(lightning, "A #yCharge that deals damage to all enemies at the end of your turn. NL Can be used as a resource for #rLightning cards.");

        final String[] bloodied = {"bloodied"};
        BaseMod.addKeyword(bloodied, "An effect that's applied if the target has less than #b50% #yHP remaining.");



    }

    // ================ /LOAD THE KEYWORDS/ ===================    

    // this adds "ModName: " before the ID of any card/relic/power etc.
    // in order to avoid conflics if any other mod uses the same ID.
    public static String makeID(String idText) {
        return "blademaster:" + idText;
    }

}
