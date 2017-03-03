# HyPop

Welcome to the Tessi lab open source project, HyPop. 
HyPop is java module of hyperparameter optimization under Apache 2.0 license.


Actually HyPop is in developement. Any contribution and ideas are welcome. Especially on hyperparameter selection algorithms and how to make easier to integrate to library to a new problem. 

## Installing 

The easier way to use HyPop in your projects is use maven and add this dependency to your project : 

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

The wiki page that explain who to integrate HyPop on your own program will come soon. 
To explain quickly, you must implement the interface ProcessInterface on _io.tessilab.oss.hypop.extinterface_.
To have an idea how to do, look to the classes CombatMonsterInterface to have an implementation example. A main example can be found in the package _io.tessilab.oss.hypop.main_, in the class FirstProblemMain. 

## Javadoc 
You can get the javadoc from the maven central depository or consult it from here : 
https://tessi-lab.github.io/hyPop/latest/index.html

## Wiki
Wiki will come soon!
