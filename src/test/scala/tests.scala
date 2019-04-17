package transportCardSystem.test

import transportCardSystem.card._
import transportCardSystem.model._
import scala.util.{Success, Failure}
import org.scalatest.FunSuite


class Tests extends FunSuite {

  test("check-in to a bus station with a card loaded with 10.00 will succeed and deduct the correct amount of 1.80 from balance") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Bus) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    assert(Math.abs(8.20 - card.balance) < 0.001)
  }

  test("check-in to a tube station with a card loaded with 10.00 will succeed and deduct the correct maximum amount of 3.20 from balance") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.EARLS_COURT)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    assert(Math.abs(6.80 - card.balance) < 0.001)
  }

  test("check-in to a bus station with balance less than 1.80 will fail and throw an exception") {

    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 1.75f)
    assertThrows[Exception] {
      CardService.checkIn(card, Bus) match {
        case Success(c) => card = c
        case Failure(e) => throw e
      }
    }   
  }

  test("check-in to a tube station with balance less than 3.20 will fail and throw an exception") {

    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 3.10f)
    assertThrows[Exception] {
      CardService.checkIn(card, Tube(Stations.WIMBLEDON)) match {
        case Success(c) => card = c
        case Failure(e) => throw e
      }
    }   
  }

  test("using the tube to go from a station in zone 1 to another station is zone 1 costs 2.50") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.EARLS_COURT)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Tube(Stations.HOLBORN))
    assert(Math.abs(7.50 - card.balance) < 0.001)
  }

  test("using the tube to go from a station in zone 2 to another station is zone 2 costs 2.00") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.EARLS_COURT)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Tube(Stations.HAMMERSMITH))
    assert(Math.abs(8.00 - card.balance) < 0.001)
  }

  test("using the tube to go from a station in zone 1 to a station is zone 2 costs 3.00") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.HOLBORN)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Tube(Stations.HAMMERSMITH))
    assert(Math.abs(7.00 - card.balance) < 0.001)
  }

  test("using the tube to go from a station in zone 2 to a station is zone 3 costs 2.25") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.HAMMERSMITH)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Tube(Stations.WIMBLEDON))
    assert(Math.abs(7.75 - card.balance) < 0.001)
  }

  test("using the tube to cross 3 zones costs 3.20") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.HOLBORN)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Tube(Stations.WIMBLEDON))
    assert(Math.abs(6.80 - card.balance) < 0.001)
  }

  test("a bus trip costs only 1.80") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Bus) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Bus)
    assert(Math.abs(8.20 - card.balance) < 0.001)
  }

  test("the system will favor the user when a station is in more than one zone") {
    val user = User("Baseer Kurdi", "39990848")
    var card = Card(367476, user, 10.00f)
    CardService.checkIn(card, Tube(Stations.HOLBORN)) match {
      case Success(c) => card = c
      case Failure(e) => throw e
    }
    card = CardService.checkOut(card, Tube(Stations.EARLS_COURT))
    assert(Math.abs(7.50 - card.balance) < 0.001)
  }

}
