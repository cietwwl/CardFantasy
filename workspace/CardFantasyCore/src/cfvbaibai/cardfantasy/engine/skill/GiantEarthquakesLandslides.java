package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public class GiantEarthquakesLandslides {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero, int count) throws HeroDieSignal {
        if (defenderHero == null) {
            return;
        }
        if (attacker == null) {
            return;
        }
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), -1, true, null);
        List<CardInfo> myVictims = random.pickRandom(attacker.getOwner().getField().toList(), -1, true, null);
        List<CardInfo> candidates = new ArrayList<CardInfo>();
        List<CardInfo> myCandidates = new ArrayList<CardInfo>();
        RuneInfo defenerrune = defenderHero.getOwner().getActiveRuneOf(RuneData.磐石);
        RuneInfo attackrune = attacker.getOwner().getOwner().getActiveRuneOf(RuneData.磐石);
        if (defenerrune != null) {
            candidates = victims;
        } else {
            for (CardInfo victim : victims) {
                if (victim.containsUsableSkillsWithTag(SkillTag.不动)) {
                    candidates.add(victim);
                }
            }
        }
        if (attackrune != null) {
            myCandidates = myVictims;
        } else {
            for (CardInfo victim : myVictims) {
                if (victim.containsUsableSkillsWithTag(SkillTag.不动)) {
                    myCandidates.add(victim);
                }
            }
        }
        if (candidates.size() + myCandidates.size() < count) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        for (CardInfo effectCard : candidates) {
            if (effectCard.containsAllSkill(SkillType.免疫)|| effectCard.containsAllSkill(SkillType.结界立场)|| effectCard.containsAllSkill(SkillType.影青龙)|| effectCard.containsAllSkill(SkillType.恶龙血脉) || effectCard.containsAllSkill(SkillType.魔力抗性)) {
                continue;
            }
            ui.useSkill(attacker, effectCard, cardSkill, true);
            Return.returnCard2(resolver, cardSkill, attacker, effectCard,true);
        }
    }
}
