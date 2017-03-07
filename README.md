# HyPop

Welcome to the Tessi lab open source project, HyPop. 
HyPop is a Java module of hyperparameter optimization under Apache 2.0 license.


Currently, HyPop is in developement. Any contributions and ideas are welcome. Especially on hyperparameter selection algorithms and how to ease the intregation of this library to new problems. 

## Installing 

The easiest way to use HyPop in your project is use maven and add this dependency : 

        <dependency>
            <groupId>io.tessilab.oss</groupId>
            <artifactId>HyPop</artifactId>
            <version>0.2.3.0</version>
        </dependency>

## Suported and upcoming optimzation algorithms 

- [x] Grid Search 
- [x] Random Search 
- [ ] Gradient descent (only for continuous variables)
- [ ] Bayesian optimization

## How to integrate

The wiki page that explain how to integrate HyPop in your own program will come soon. 
Short explanation: you must implement the interface ProcessInterface in _io.tessilab.oss.hypop.extinterface_.
To understand how to do so, look at the classe CombatMonsterInterface in which there is an implementation example. A main example can be found in the package _io.tessilab.oss.hypop.main_, in the class FirstProblemMain. 

## Javadoc 
You can get the javadoc from the maven central depository or consult it from here : 
https://tessi-lab.github.io/hyPop/latest/index.html

## Wiki
Wiki will come soon!
