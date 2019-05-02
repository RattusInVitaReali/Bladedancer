package blademaster.patches;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.MethodCall;

// This class handles rendering [E] energy symbols on cards in portrait view.
// Unfortunately, javassist doesn't allow Raw patches with `continue`.
// So instead we hack our way around it, by reusing the code for testing for and drawing the "[G]" mana symbol.

@SpirePatch(
        clz=SingleCardViewPopup.class,
        method="renderDescription"
)
@SpirePatch(
        clz=SingleCardViewPopup.class,
        method="renderDescriptionCN"
)
public class WindChargePopupPatch
{
    public static ExprEditor Instrument()
    {
        return new ExprEditor() {
            public void edit(MethodCall m) throws CannotCompileException
            {
                if (m.getClassName().equals(String.class.getName()) && m.getMethodName().equals("replaceEquals")) {
                    m.replace("{ $_ = blademaster.patches.WindChargePopupPatch.replaceEquals(tmp, (java.lang.String)$1); }");
                }
            }
            public void edit(FieldAccess m) throws CannotCompileException
            {
                if (m.getClassName().equals(ImageMaster.class.getName()) && m.getFieldName().equals("replaceOrbField")) {
                    m.replace("{ $_ = blademaster.patches.WindChargePopupPatch.replaceOrbField(tmp, this.card); }");
                }
            }
        };
    }

    @SuppressWarnings("unused")
    public static boolean replaceEquals(String tmp, String originalArg)
    {
        if (tmp.equals(originalArg)) {
            return true;
        }
        if (tmp.equals("[W] ") && originalArg.equals("[G] ")) {
            return true;
        }
        if (tmp.equals("[W]. ") && originalArg.equals("[G]. ")) {
            return true;
        }
        if (tmp.equals("[W]") && originalArg.equals("[G]")) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static TextureAtlas.AtlasRegion replaceOrbField(String tmp, Object card)
    {
        if (tmp.equals("[W]") || tmp.equals("[W] ") || tmp.equals("[W]. ")) {
            return new TextureAtlas.AtlasRegion(ImageMaster.loadImage("blademasterResources/images/512/WindCharge.png"), 0, 0, 24, 24);
        }
        return ImageMaster.GREEN_ORB;
    }
}