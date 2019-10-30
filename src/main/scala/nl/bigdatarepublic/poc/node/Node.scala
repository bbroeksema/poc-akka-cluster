package nl.bigdatarepublic.poc.node

import akka.actor.{Actor, ActorRef, Props}
import akka.routing.FromConfig

import nl.bigdatarepublic.poc.node.cluster.ClusterManager
import nl.bigdatarepublic.poc.node.model.ModelManager

object Node {
  def props(nodeId: String) = Props(new Node(nodeId))
}

class Node(nodeId: String) extends Actor {

  val modelManager: ActorRef = context.actorOf(ModelManager.props(nodeId), "processor")
  val processorRouter: ActorRef = context.actorOf(FromConfig.props(Props.empty), "processorRouter")
  val clusterManager: ActorRef = context.actorOf(ClusterManager.props(nodeId), "clusterManager")

  override def receive: Receive = {
    case GetClusterMembers => clusterManager forward GetClusterMembers
    case RegisterModel(name) => clusterManager forward RegisterModel(name)
    // case GetFibonacci(value) => processorRouter forward ComputeFibonacci(value)
  }
}
