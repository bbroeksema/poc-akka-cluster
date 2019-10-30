package nl.bigdatarepublic.poc.node
package cluster

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.cluster.{Cluster, MemberStatus}

import nl.bigdatarepublic.poc.Server.system

object ClusterManager {
  def props(nodeId: String) = Props(new ClusterManager(nodeId))
}

class ClusterManager(nodeId: String) extends Actor with ActorLogging {
  import ClusterManager._

  val cluster: Cluster = Cluster(context.system)
  val listener: ActorRef = context.actorOf(ClusterListener.props(nodeId, cluster), "clusterListener")

  override def receive: Receive = {
    case GetClusterMembers => {
      sender() ! cluster.state.members.filter(_.status == MemberStatus.up)
        .map(_.address.toString)
        .toList
    }
  }
}
