package rukiamod;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import rukiamod.cards.Strike;

@SpireInitializer
public class RukiaMod implements EditCardsSubscriber {
    public RukiaMod() {
        BaseMod.subscribe(this);

    }


    public static void initialize() {
        new rukiamod.RukiaMod();
    }
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, "RukiaModResources/localization/ZHS/cards.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "RukiaModResources/localization/ZHS/powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "RukiaModResources/localization/ZHS/relics.json");
        // 如果语言设置为简体中文，则加载ZHS文件夹的资源
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
    }
}