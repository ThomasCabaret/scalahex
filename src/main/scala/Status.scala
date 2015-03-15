package hex
//Technical status of a game, created, started, resigned etc...
//OK

sealed abstract class Status(val id: Int) extends Ordered[Status] {

  val name = toString.head.toLower + toString.tail

  def compare(other: Status) = id compare other.id

  def is(s: Status): Boolean = this == s

  def is(f: Status.type => Status): Boolean = is(f(Status))
}

object Status {

  case object Created extends Status(0)
  case object Started extends Status(1)
  case object Aborted extends Status(2) // from this point the game is finished
  case object ConnectedWin extends Status(3)
  case object Resign extends Status(4)
  case object Timeout extends Status(5) // when player leaves the game
  case object Outoftime extends Status(6) // clock flag

  val all = List(Created, Started, Aborted, ConnectedWin, Resign, Timeout, Outoftime)

  val finished = all filter { s =>
    s.id >= Aborted.id
  }

  val finishedWithWinner = List(ConnectedWin, Resign, Timeout, Outoftime)

  val byId = all map { v => (v.id, v) } toMap

  def apply(id: Int): Option[Status] = byId get id
}

