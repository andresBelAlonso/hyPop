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
import io.tessilab.oss.hypop.parameters.control.StandardFilters;
import io.tessilab.oss.hypop.parameters.input.InputParameter;
import io.tessilab.oss.hypop.parameters.input.InputParametersSet;
import io.tessilab.oss.hypop.parameters.input.IntegerInterval;
import io.tessilab.oss.hypop.parameters.input.Interval;
import io.tessilab.oss.hypop.parameters.input.NominativeInputParameter;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;

/**
 * An second version of a {@link io.tessilab.oss.hypop.extinterface.ProcessInterface} 
 * with the testing problem, shorter than the first one. 
 * @author Andres BEL ALONSO
 */
public class CombatMonsterInterfacev03 extends CombatMonstersInterface{
    
    private static final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger(CombatMonstersInterface.class);

    public static class Config extends CombatMonstersInterface.Config {

        @Override
        protected ProcessInterface build() {
            return new CombatMonsterInterfacev03();
        }
        
    }

    @Override
    public InputParametersSet createInputParameters() {
        InputParametersSet params = new InputParametersSet();
        try {
            InputParameter ad = new IntegerInterval(0, 600, adName, true, true);
            InputParameter ap = new IntegerInterval(0, 600, apName, true, true);
            InputParameter ar = new IntegerInterval(0, 600, arName, true, true);
            InputParameter mr = new IntegerInterval(0, 600, mrName, true, true);
            InputParameter attackSpeed = new IntegerInterval(0, 600, attackSpeedName, true, true);
            InputParameter health = new IntegerInterval(0, 600, healthName, true, true);
            
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
            
            //Adding the constraints on the parameters
            params.addParametersConstraint(StandardFilters.integersLesThanN(Arrays.asList(adName,apName,mrName,arName,healthName,attackSpeedName), 601));
            
        } catch (Interval.EmptyInterval | InputParameter.NotValidParameterValue ex) {
            LOGGER.error(ex);
            throw new HyperParameterSearchError("A parameter is not correctly setted");
        } 
        return params;
    }
    
    
}
