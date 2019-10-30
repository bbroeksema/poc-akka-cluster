package nl.bigdatarepublic.poc.node
package model

import akka.actor.{Actor, ActorRef, ActorLogging, Props}
import akka.io.Tcp.Register

object ModelRegistrator {
    def props(nodeId: String) = Props(new ModelRegistrator(nodeId))
}

class ModelRegistrator(nodeId: String) extends Actor with ActorLogging{
    import ModelRegistrator._
    import ModelManager._

    override def receive: Receive = {
        case RegisterModelRequest(modelName, replyTo) => {
            log.info(s"Registered: $modelName at $nodeId")
            replyTo ! ModelRegistrationResponse(nodeId, modelName)
        }
    }
}
