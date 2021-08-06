package default

import scala.concurrent.duration._
import scala.util.Random

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class ExternalBet extends Simulation {
  val feeder = Iterator.continually(Map("playerId" -> (Random.alphanumeric.take(20).mkString + "@foo.com")
    ,"id_token" -> "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW50aWFnbyIsImF1dGgiOiJST0xFX0VYVEVSTixST0xFX09QRVJBVE9SIiwiZXhwIjoxNjA3MTIxODYwfQ.8scP12t9lpCbGgDD_tpMJqaLZ5zfoMK2Hu--yVxNazDyokIzoLFFNqt4EDTGE9C5MzLljGV8gKHoSA3aYpreRA"
  ))
  var server = "http://localhost:8181"
  val httpProtocol = http
    .baseUrl(server)
    .inferHtmlResources(BlackList(""".*\.css""", """.*\.js""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*detectportal\.firefox\.com.*"""), WhiteList())
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("es-ES,es;q=0.8,en-US;q=0.5,en;q=0.3")
    .userAgentHeader("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:69.0) Gecko/20100101 Firefox/69.0")

  val scn = scenario("ExternalBet")
    .feed(feeder)
    /*.exec(
      http("authenticate")
        .post( server + "/api/authenticate")
        .headers(Map("Content-Type" -> "application/json;charset=utf-8"))
        .body(RawFileBody("default/loadgame/provider_authenticate_request.json"))
        .check(status.in(200 to 210))
        .check(jsonPath("$.id_token")saveAs("id_token"))
    )
    .exitHereIfFailed
    .exec(session => {
      val token = session("id_token").as[String]
      println("Token " + token)
      session
    })*/
    .exec(
      http("externalbet")
        .post( server + "/api/server/bingo/bet")
        .headers(Map("Content-Type" -> "application/json", "Authorization" -> "Bearer ${id_token}"))
        .body(
          //RawFileBody("default/loadgame/external_bet_request.json")
          StringBody("""{"account":{"userName":"santiago","password":"santiago"},"gameCode":"Bingo_21554_55851","amount":5000,"numCards":10,"playerId":"${playerId}","platformTransactionId":"idTransaccionExterna"}""")
        )
        .check(status.in(200 to 210))
    ).exitHereIfFailed
  setUp(scn.inject(atOnceUsers(1000))).protocols(httpProtocol)
}