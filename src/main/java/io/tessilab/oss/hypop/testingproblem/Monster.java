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

import io.tessilab.oss.hypop.testingproblem.objects.CombatObject;



/**
 *
 * @author Andres BEL ALONSO
 */
public class Monster{
    
    public static final int MAX_POINTS = 600;
    
    private int ad;
    private int ap;
    private int mr;
    private int ar;
    private int attackSpeed;
    private int health;
    private CombatObject object;

    public Monster(int ad, int ap, int mr, int ar, int attackSpeed, int health, CombatObject object) {
        this.ad = ad;
        this.ap = ap;
        this.mr = mr;
        this.ar = ar;
        this.attackSpeed = attackSpeed;
        this.health = health;
        this.object = object;
        verifyCoherence();
    }

    
    private void verifyCoherence() {
        if(ad + ap + mr + ar + attackSpeed + health > MAX_POINTS) {
            throw new IncoherentMonster("Incohherent monster");
        }
    }

    public int getAd() {
        return ad + object.getAd() + (int) (ad*object.getAdBonus());
    }

    public int getAp() {
        return ap + object.getAp() + (int) (ap*object.getApBonus());
    }

    public int getMr() {
        return mr + object.getMr() + (int) (mr*object.getMrBonus());
    }

    public int getAr() {
        return ar + object.getAr() + (int) (ar*object.getArBonus());
    }

    public int getAttackSpeed() {
        return attackSpeed + object.getAttackspeed() + (int) (attackSpeed*object.getAttackSpeedBonus());
    }

    public int getHealth() {
        return health + object.getHealth() + (int) (health*object.getHealthBonus());
    }

    @Override
    public String toString() {
        return "Monster{" + "ad=" + ad + ", ap=" + ap + ", mr=" + mr + ", ar=" + ar + ", attackSpeed=" + attackSpeed + ", health=" + health + ", combatObject : " +this.object.objectName() +'}';
    }
    
    
    
}
