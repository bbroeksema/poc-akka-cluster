package nl.bigdatarepublic.poc.node
package model

import akka.actor.{Actor, ActorRef, Props}

object ModelManager {
  def props(nodeId: String) = Props(new ModelManager(nodeId))
}

class ModelManager(nodeId: String) extends Actor {
  import ModelManager._

  val modelRegistrator: ActorRef = context.actorOf(ModelRegistrator.props(nodeId), "registrator")

  override def receive: Receive = {
    case RegisterModel(modelName) => {
      val replyTo = sender()
      modelRegistrator ! RegisterModelRequest(modelName, replyTo)
    }
  }
}
