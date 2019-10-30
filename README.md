# POC - Akka cluster

A small proof-of-concept project to gain some understanding of how akka-cluster works.

## Implementation goals

1. Write a small scala application based on Akka cluster
2. package the app in a docker image
3. Bring up a cluster of at least three nodes on local machine and play with
   program behavior when nodes die.

## Actor hierarchy

* Business logic: `ModelManager` actor to register and launch models;
    Children: `ModelRegistrator`, `ModelLauncher`
* Cluster management
    Childern: `ClusterListener`
* Node: Root of te hierarchy
