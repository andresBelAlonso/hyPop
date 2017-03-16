# HyPop

Welcome to the Tessi lab open source project, HyPop. 
HyPop is a Java module of hyperparameter optimization under Apache 2.0 license.


Currently, HyPop is in developement. Any contributions and ideas are welcome. Especially on hyperparameter selection algorithms and how to ease the intregation of this library to new problems. 

## Whats is Hyperparameter optimization? 

In a machine learning context, we call hyperparameter the parameters of an algorithm, or a process, that does not depend on the input data set. Usually hyperparameters, can not be learned automatically. The hyperparameters are often the parameters of a learning algorithm. 

For example, in an artificial neural network, the number of hiden layers and their number of neurons are hyperparameters. For a Document Classifier module, the algorithm that is going to be trained to classify the documents is an hyperparameter. If we train a k-nearest neighbors ( https://en.wikipedia.org/wiki/K-nearest_neighbors_algorithm )  to classify documents, the k value is an hyperparameter. If we are solving a clustering problem with a K-means (https://en.wikipedia.org/wiki/K-means_clustering) algorithm the number of clusters to create is also an hyperparameter. 

So, Hyperparameter optimization (also called model selection), is the problem of find the best hyperparameters for a given machine learning problem and a given dataset. 

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
