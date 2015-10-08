package error

import org.joda.time.DateTime
import org.joda.time.format._
import unfiltered.request.Path

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

import unfiltered.netty._
import unfiltered.netty.websockets._
import scala.pickling._, Defaults._, json._
import scalaz._

object SbtError {


  object WebSockets extends unfiltered.netty.websockets.Plan with ServerErrorResponse {


    private var sockets = new ListBuffer[WebSocket]
    private var socketSessions = new mutable.HashMap[String, WebSocket]

    sealed trait Msg
    case class Msg1(msg1: String) extends Msg
    case class Msg2(msg2: String) extends Msg
    case class MsgData1(ed: Data) extends Msg
    case class MsgData2(ed: Data) extends Msg

    def intent = {
      case Path("/ws") => {
        case Message(s, Text(str)) =>
          str.unpickle[Msg] match {
            case Msg1(sess) =>
              socketSessions += (sess -> s)
            case _ =>
              s.send(Msg2("error").pickle.value)
          }
          sockets foreach (_.send(str.reverse))
        case Close(s) =>
          sockets -= s
        case unfiltered.netty.websockets.Error(s, e) =>
          println("error %s" format e.getMessage)
      }
    }

    def send(sess: String, msg: Msg): \/[String, Boolean] = {
      socketSessions.get(sess) map { s =>
        s.send(msg.pickle.value)
        \/-(true)
      } getOrElse(-\/("sesión no encontrada"))
    }

    def pass = DefaultPassHandler
  }


  object SbtError {
    def main(args: Array[String]): Unit = {

      /*sealed trait Msg
      case class Msg1(msg1: String) extends Msg
      case class Msg2(msg2: String) extends Msg
      case class DataMsg1(ed: Data) extends Msg
      case class DataMsg2(ed: Data) extends Msg

      args.toList.headOption.map { str =>
        str.unpickle[Msg] match {
          case Msg1(msg) =>
            println("Ok")
          case _ =>
            println("error")
        }
      }*/

      val nettySrv = unfiltered.netty.Server.http(8888).
        handler(WebSockets).run


    }
  }

}


