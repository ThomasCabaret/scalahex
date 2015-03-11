package hex
//State of a "runing" game: board state, color to play, clock, history of moves etc...

import scala.concurrent.duration._

//import format.pgn

case class Game(
    board: Board,
    player: Color = Red,
    moves: List[Move] = Nil,
    swapped: Boolean = false,
    clock: Option[Clock] = None,
    turns: Int = 0,
    startedAtTurn: Int = 0) {

  def apply(move: Move): Game = {
    val newTurns = turns + 1
    copy(
      board = board.apply(move),
      player = !player,
      moves = move :: moves,
      turns = newTurns,
      clock = clock map {
        case c: RunningClock => c step move.lag
        case c: PausedClock if (newTurns - startedAtTurn) == 2 => c.start.switch
        case c => c
      }
    )
  }

  def swap() = copy(swapped = true)

  //def isStandardInit = board.pawns == Nil
}

object Game {

  def apply(size: Int, variant: hex.variant.Variant): Game = new Game(
    board = Board init(size, variant)
  )
}
