package nl.bigdatarepublic.poc.node

import akka.actor.ActorRef

sealed trait ClusterMessage
case object GetClusterMembers extends ClusterMessage

sealed trait ModelMessage
case class RegisterModel(name: String) extends ModelMessage
case class RegisterModelRequest(model: String, replyTo: ActorRef) extends ModelMessage
case class ModelRegistrationResponse(nodeId: String, modelName: String) extends ModelMessage
