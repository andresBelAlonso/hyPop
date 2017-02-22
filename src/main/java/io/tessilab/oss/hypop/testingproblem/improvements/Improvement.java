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
package io.tessilab.oss.hypop.testingproblem.improvements;

/**
 *
 * @author Andres BEL ALONSO
 */
public abstract class Improvement {
    private final int ad;
    private final int ap;
    private final int health;
    private final int attackspeed;
    private final int mr;
    private final int ar;
    private final double adBonus;
    private final double apBonus;
    private final double healthBonus;
    private final double attackSpeedBonus;
    private final double mrBonus;
    private final double arBonus;

    public Improvement(int ad, int ap, int health, int attackspeed, int mr, int ar, double adBonus, double apBonus, double healthBonus, double attackSpeedBonus, double mrBonus, double arBonus) {
        this.ad = ad;
        this.ap = ap;
        this.health = health;
        this.attackspeed = attackspeed;
        this.mr = mr;
        this.ar = ar;
        this.adBonus = adBonus;
        this.apBonus = apBonus;
        this.healthBonus = healthBonus;
        this.attackSpeedBonus = attackSpeedBonus;
        this.mrBonus = mrBonus;
        this.arBonus = arBonus;
    }
    
    public abstract String getImprovementName();

    public int getAd() {
        return ad;
    }

    public int getAp() {
        return ap;
    }

    public int getHealth() {
        return health;
    }

    public int getAttackspeed() {
        return attackspeed;
    }

    public int getMr() {
        return mr;
    }

    public int getAr() {
        return ar;
    }

    public double getAdBonus() {
        return adBonus;
    }

    public double getApBonus() {
        return apBonus;
    }

    public double getHealthBonus() {
        return healthBonus;
    }

    public double getAttackSpeedBonus() {
        return attackSpeedBonus;
    }

    public double getMrBonus() {
        return mrBonus;
    }

    public double getArBonus() {
        return arBonus;
    }

    @Override
    public String toString() {
        return this.getImprovementName();
    }
    
    
    
    
    
    
}
