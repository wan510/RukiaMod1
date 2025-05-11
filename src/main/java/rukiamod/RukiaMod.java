package rukiamod;

import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.badlogic.gdx.graphics.Color;

import rukiamod.cards.*;
import rukiamod.characters.MyCharacter;
import rukiamod.relics.Agent;
import rukiamod.relics.Shirayuki;

import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;
import static rukiamod.characters.MyCharacter.PlayerColorEnum.MY_CHARACTER;

@SpireInitializer
public class RukiaMod implements EditCardsSubscriber, EditStringsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber {
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "RukiaModResources/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "RukiaModResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "RukiaModResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "RukiaModResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "RukiaModResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "RukiaModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "RukiaModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "RukiaModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "RukiaModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "RukiaModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "RukiaModResources/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(76.0F / 255.0F, 201.0F / 255.0F, 184.0F / 255.0F, 1.0F);
    public RukiaMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(MyCharacter.PlayerColorEnum.RUKIA_BLEACH, MY_COLOR, MY_COLOR, MY_COLOR,
                MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,
                ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB
        );

    }


    public static void initialize() {new RukiaMod();}

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new Defend());
        BaseMod.addCard(new ReiryokuGather());
        BaseMod.addCard(new KickIchigo());
        BaseMod.addCard(new IceBreak());
    }

    @Override
    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new MyCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MY_CHARACTER);
    }

    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, "RukiaModResources/localization/ZHS/cards.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "RukiaModResources/localization/ZHS/powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "RukiaModResources/localization/ZHS/relics.json");

    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelic(new Agent(), RelicType.SHARED);
        BaseMod.addRelic(new Shirayuki(), RelicType.SHARED);// RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "zhs";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "zhs";
        }

        String json = Gdx.files.internal("RukiaModResources/localization/ZHS/Keywords_" + lang + ".json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 这个id要全小写
                BaseMod.addKeyword("rukiamod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
}
}