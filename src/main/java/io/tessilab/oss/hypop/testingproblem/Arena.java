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
package io.tessilab.oss.hypop.testingproblem;

/**
 *
 * @author Andres BEL ALONSO
 */
public class Arena {
    
    public static int MAX_DEFAULT_ROUNDS = 1000;

    /**
     * 
     * @param monsterA The first monsteer
     * @param monsterB The other monster
     * @return can return null if null match
     */
    public static BattleInfo fight(Monster monsterA, Monster monsterB) {
//        System.out.println("Monster A has entered to the arena" + monsterA.toString());
//        System.out.println("Monster B has entered to the arena" + monsterB.toString());
        int healthA = monsterA.getHealth()*100;
        int healthB = monsterB.getHealth()*100;
        int rounds = 0;
        while (healthA > 0 && healthB > 0 && rounds <MAX_DEFAULT_ROUNDS) {
            healthA -= monsterB.getAd() * monsterB.getAttackSpeed() / 100 + monsterB.getAp() * monsterB.getAttackSpeed() / 100;
            healthB -= monsterA.getAd() * monsterB.getAttackSpeed() / 100 + monsterA.getAp() * monsterB.getAttackSpeed() / 100;
            rounds++;
        }
        if(healthA <= 0 && healthB > 0) {
//            System.out.println("MonsterB has win");
            return new BattleInfo(monsterB, rounds);
        } else if(healthB <= 0 && healthA > 0) {
//            System.out.println("Monster A has win");
            return new BattleInfo(monsterA, rounds);
        }
//        System.out.println("No one has win");
        return null;
     }
    
    
}
