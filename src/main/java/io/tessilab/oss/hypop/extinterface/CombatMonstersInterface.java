/*
 * Copyright 2017 Tessi lab.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.tessilab.oss.hypop.extinterface;

import io.tessilab.oss.hypop.exceptions.HyperParameterSearchError;
import io.tessilab.oss.hypop.parameters.ParameterName;
import io.tessilab.oss.hypop.parameters.execution.ExecutionParametersSet;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.NominativeInputParameter;
import io.tessilab.oss.hypop.results.ProcessResult;
import io.tessilab.oss.hypop.results.saver.SaverAnswer;
import io.tessilab.oss.hypop.testingproblem.Arena;
import io.tessilab.oss.hypop.testingproblem.BattleInfo;
import io.tessilab.oss.hypop.testingproblem.IncoherentMonster;
import io.tessilab.oss.hypop.testingproblem.Monster;
import io.tessilab.oss.hypop.testingproblem.improvements.BlueStone;
import io.tessilab.oss.hypop.testingproblem.improvements.DummySpell;
import io.tessilab.oss.hypop.testingproblem.improvements.DummyStone;
import io.tessilab.oss.hypop.testingproblem.improvements.Enchantment;
import io.tessilab.oss.hypop.testingproblem.improvements.MagicStone;
import io.tessilab.oss.hypop.testingproblem.improvements.RedStone;
import io.tessilab.oss.hypop.testingproblem.improvements.SpellOfTheOldWitch;
import io.tessilab.oss.hypop.testingproblem.improvements.SpellOfTheSexyWitch;
import io.tessilab.oss.hypop.testingproblem.improvements.SpellOfTheTwo;
import io.tessilab.oss.hypop.testingproblem.improvements.StoneOfTheAntiHero;
import io.tessilab.oss.hypop.testingproblem.objects.CombatObject;
import io.tessilab.oss.hypop.testingproblem.objects.DummyCombatObject;
import io.tessilab.oss.hypop.testingproblem.objects.Helmet;
import io.tessilab.oss.hypop.testingproblem.objects.Scetre;
import io.tessilab.oss.hypop.testingproblem.objects.Sword;
import io.tessilab.oss.openutils.io.LoggerPrintStream;
import io.tessilab.oss.openutils.treedisplaying.TreeLinuxConsoleDisplay;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

/**
 * An example of implementation of {@link io.tessilab.oss.hypop.extinterface.ProcessInterface} 
 * with a simple testing problem. 
 * @author Andres BEL ALONSO
 */
public class CombatMonstersInterface implements ProcessInterface {

    public static class Config extends ProcessInterface.Config {

        @Override
        protected ProcessInterface build() {
            return new CombatMonstersInterface();
        }

    }

    public static class MonsterExecutionResult implements ProcessResult<Double> {

        public static final String BATTLE_SCORE_NAME = "battleScore";

        private final double battleScore;
        private final String jobId;

        public MonsterExecutionResult(double battleScore, String jobId) {
            this.battleScore = battleScore;
            this.jobId = jobId;
        }

        @Override
        public Double getResultScore() {
            return battleScore;
        }

        @Override
        public String getJobId() {
            return jobId;
        }

        @Override
        public Map<String, Object> getResultInDBFormat() {
            Map<String, Object> json = new HashMap<>();
            json.put(BATTLE_SCORE_NAME, battleScore);
            return json;
        }

        @Override
        public long getLongScoreRepresentation() {
            return (long) battleScore;
        }

    }

    public static final Monster REF_MONSTER = new Monster(100, 100, 100, 100, 100, 100, new Sword(new SpellOfTheOldWitch()));

    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CombatMonstersInterface.class);

    protected final ParameterName adName = new ParameterName("ad");
    protected final ParameterName apName = new ParameterName("ap");
    protected final ParameterName mrName = new ParameterName("mr");
    protected final ParameterName attackSpeedName = new ParameterName("attackSpeed");
    protected final ParameterName healthName = new ParameterName("health");
    protected final ParameterName arName = new ParameterName("ar");
    protected final ParameterName combatObj = new ParameterName("object");
    protected final ParameterName magicStoneName = new ParameterName("magicStone");
    protected final ParameterName spellName = new ParameterName("spell");

    @Override
    public ProcessResult computeIt(ExecutionParametersSet params) {
        try {
            Monster curMonster = buildMonster(params);
            BattleInfo res = Arena.fight(curMonster, REF_MONSTER);
            if (res == null) {
                return new MonsterExecutionResult(Arena.MAX_DEFAULT_ROUNDS, params.getDescriptionParameters());
            } else if (curMonster.equals(res.getWinner())) {
                return new MonsterExecutionResult(2 * Arena.MAX_DEFAULT_ROUNDS - res.getLenght(), params.getDescriptionParameters());
            } else {
                return new MonsterExecutionResult(res.getLenght(), params.getDescriptionParameters());
            }
        } catch (IncoherentMonster ex) {
            LOGGER.trace(ex);
            LOGGER.trace("The monster is not coherent. Returning the worst score");
            return new MonsterExecutionResult(-1, params.getDescriptionParameters());
        }
    }

    @Override
    public InputParametersSet createInputParameters() {
        InputParametersSet params = new InputParametersSet();
        try {
//            InputParameter ad = new ContinuousInterval(0.0, 600.0, adName, true, true);
//            InputParameter ap = new ContinuousInterval(0.0, 600.0, apName, true, true);
//            InputParameter mr = new ContinuousInterval(0.0, 600.0, mrName, true, true);
//            InputParameter ar = new ContinuousInterval(0.0, 600.0, arName, true, true);
//            InputParameter attackSpeed = new ContinuousInterval(0.0, 600.0, attackSpeedName, true, true);
//            InputParameter health = new ContinuousInterval(0.0, 600.0, healthName, true, true);
            Map<String,Integer> vals = new HashMap<>();
            vals.put(String.valueOf(0),0);
            vals.put(String.valueOf(100),100);
//            vals.add(200);
//            vals.add(300);
//            vals.add(400);
//            vals.add(500);
//            vals.add(600);
            NominativeInputParameter ad = new NominativeInputParameter(adName, vals);
            NominativeInputParameter ap = new NominativeInputParameter(apName, vals);
            NominativeInputParameter mr = new NominativeInputParameter(mrName, vals);
            NominativeInputParameter ar = new NominativeInputParameter(arName, vals);
            NominativeInputParameter attackSpeed = new NominativeInputParameter(attackSpeedName, vals);
            NominativeInputParameter health = new NominativeInputParameter(healthName, vals);
            Map<String,CombatObject> objectsSet = new HashMap<>();
            DummyCombatObject dummy = new DummyCombatObject(); 
            objectsSet.put(dummy.objectName(),dummy);
            Helmet helmet = new Helmet(new DummySpell());
            objectsSet.put(helmet.objectName(),helmet);
            Scetre scetre = new Scetre(new DummyStone());
            objectsSet.put(scetre.objectName(),scetre);
            Sword sword = new Sword(new DummySpell());
            objectsSet.put(sword.objectName(),sword);
            InputParameter object = new NominativeInputParameter(combatObj, objectsSet);

            Map<String,MagicStone> magicStonesSet = new HashMap<>();
            BlueStone blueStone = new BlueStone();
            magicStonesSet.put(blueStone.getImprovementName(),blueStone);
            RedStone redStone = new RedStone();
            magicStonesSet.put(redStone.getImprovementName(),redStone);
            StoneOfTheAntiHero stone = new StoneOfTheAntiHero();
            magicStonesSet.put(stone.getImprovementName(),stone);
            DummyStone dummyStone = new DummyStone();
            magicStonesSet.put(dummyStone.getImprovementName(),dummyStone);
            InputParameter magicStone = new NominativeInputParameter(this.magicStoneName, magicStonesSet);

            Map<String,Enchantment> enchantmentSet = new HashMap<>();
            DummySpell dummySpell = new DummySpell();
            enchantmentSet.put(dummySpell.getImprovementName(),dummySpell);
            SpellOfTheOldWitch spellWitch = new SpellOfTheOldWitch();
            enchantmentSet.put(spellWitch.getImprovementName(),spellWitch);
            SpellOfTheSexyWitch spellSexy = new SpellOfTheSexyWitch();
            enchantmentSet.put(spellSexy.getImprovementName(),spellSexy);
            SpellOfTheTwo spell2 = new SpellOfTheTwo();
            enchantmentSet.put(spell2.getImprovementName(),spell2);
            InputParameter spells = new NominativeInputParameter(spellName, enchantmentSet);
            // Adding the parameters
            params.addParameter(ad);
            params.addParameter(ap);
            params.addParameter(mr);
            params.addParameter(ar);
            params.addParameter(attackSpeed);
            params.addParameter(health);
            params.addParameter(object);
            params.addParameter(spells);
            params.addParameter(magicStone);

            //Adding the relations
            params.addRelation(object, helmet, spells);
            params.addRelation(object, sword, spells);
            params.addRelation(object, scetre, magicStone);

        } catch (InputParameter.NotValidParameterValue ex) {
            LOGGER.error(ex);
            throw new HyperParameterSearchError(ex);
        }
        params.displayTreeStructure(new TreeLinuxConsoleDisplay(new LoggerPrintStream(CombatMonstersInterface.class, Level.INFO)));
        return params;
    }

    @Override
    public ProcessResult createResult(SaverAnswer jobDone) {
        // TODO  : if job done does not contain the data?
        return new MonsterExecutionResult((double) jobDone.getValue(CombatMonstersInterface.MonsterExecutionResult.BATTLE_SCORE_NAME),
                jobDone.getJobId());
    }

    private Monster buildMonster(ExecutionParametersSet set) {
        
        int ad = (int) set.getExecParameter(adName).getValue();
        int ap = (int) set.getExecParameter(apName).getValue();
        int mr = (int) set.getExecParameter(mrName).getValue();
        int ar = (int) set.getExecParameter(arName).getValue();
        int attackSpeed = (int) set.getExecParameter(attackSpeedName).getValue();
        int health = (int) set.getExecParameter(healthName).getValue();
        return new Monster(ad,ap,mr,ar,attackSpeed,health,getObject(set));
    }

    private CombatObject getObject(ExecutionParametersSet set) {
        CombatObject obj = (CombatObject) set.getExecParameter(this.combatObj).getValue();
        if (obj instanceof Sword || obj instanceof Helmet) {
            obj.setObject((Enchantment) set.getExecParameter(this.spellName).getValue());
        } else if (obj instanceof Scetre) {
            obj.setObject((MagicStone) set.getExecParameter(this.magicStoneName).getValue());
        } else if (obj instanceof DummyCombatObject) {
            // No improvements
        } else {
            throw new HyperParameterSearchError("This kind of object is not suported");
        }
        return obj;
    }

}
