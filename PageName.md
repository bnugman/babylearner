# Introduction #

Experiment with simple pattern classifiers ("learners") and their combinations.

Provided are classes for data generation, learning algorithms and basic visualization of both learning data and learned structures.


# Details #

There are three packages, under com.pingva.ml

  * **datagen**  Contains data generator which produces multi-dimensional data drawing from combinations from normal and uniform distributions.  All components of feature vectors generated are all within the range (0;1).  Truth values (truth classes) are simply numbers 0,1,...

  * **learners**  interface and implementations for learning algorithms

  * **gui**  simple visualization that combines the pieces and displays the data and learned regions.