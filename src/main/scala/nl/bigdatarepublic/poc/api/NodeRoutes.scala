package nl.bigdatarepublic.poc.api

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.marshallers.sprayjson._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.util.Timeout
import spray.json.DefaultJsonProtocol

import nl.bigdatarepublic.poc.node.GetClusterMembers

case class ProcessorResponse(nodeId: String, result: BigInt)

object ProcessorResponseJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol{
  implicit val processorResponse = jsonFormat2(ProcessorResponse)
}

trait NodeRoutes extends SprayJsonSupport {
    import ProcessorResponseJsonProtocol._

    implicit def system: ActorSystem

    def node: ActorRef

    implicit lazy val timeout = Timeout(5.seconds)

  lazy val healthRoute: Route = pathPrefix("health") {
    concat(
      pathEnd {
        concat(
          get {
            complete(StatusCodes.OK)
          }
        )
      }
    )
  }

  lazy val statusRoutes: Route = pathPrefix("status") {
    concat(
      pathPrefix("members") {
        concat(
          pathEnd {
            concat(
              get {
                val membersFuture: Future[List[String]] = (node ? GetClusterMembers).mapTo[List[String]]
                onSuccess(membersFuture) { members =>
                  complete(StatusCodes.OK, members)
                }
              }
            )
          }
        )
      }
    )
  }
}
