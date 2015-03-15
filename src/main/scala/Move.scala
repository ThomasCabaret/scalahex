package hex
//A move, just putting a pawn red or blue somewhere

import scala.concurrent.duration._

case class Move(
    dest: Pos,
    color: Color,
    lag: FiniteDuration = 0.millis) {

  def withLag(l: FiniteDuration) = copy(lag = l)

  def keyString = s"$dest"
}
