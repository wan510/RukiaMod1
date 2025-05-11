package rukiamod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import rukiamod.powers.FrostbitePower;
import rukiamod.util.ModHelper;

public class Shirayuki extends CustomRelic {
    // 遗物ID
    public static final String ID = ModHelper.makePath("Shirayuki");
    // 图片路径
    private static final String IMG_PATH = "RukiaModResources/img/relics/MyRelic.png";
    // 遗物未解锁时的轮廓
    private static final String OUTLINE_PATH = "RukiaModResources/img/relics/MyRelic_Outline.png";
    // 遗物类型
    private static final RelicTier RELIC_TIER = RelicTier.STARTER;
    // 点击音效
    private static final LandingSound LANDING_SOUND = LandingSound.FLAT;

    public Shirayuki() {
        super(ID, ImageMaster.loadImage(IMG_PATH), ImageMaster.loadImage(OUTLINE_PATH), RELIC_TIER, LANDING_SOUND);
    }

    // 获取遗物描述
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null) {
            this.flash();
            // 获取当前怪物列表并检查是否为空
            if (AbstractDungeon.getMonsters() != null && !AbstractDungeon.getMonsters().monsters.isEmpty()) {
                AbstractCreature randomEnemy = AbstractDungeon.getMonsters().getRandomMonster(true);
                addToBot(new ApplyPowerAction(
                        randomEnemy, p, new FrostbitePower(randomEnemy, 1), 1
                ));
            }
        }
    }

    public AbstractRelic makeCopy() {
        return new Shirayuki();
    }
}

