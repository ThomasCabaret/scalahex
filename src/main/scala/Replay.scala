package hex
//Not sure to get the exact purpose of this thing
//Ok reserve design, Valid?
//Two last def a really hard to read

import format.pgn.{ Parser, Reader, Tag }

case class Replay(setup: Game, moves: List[Move], state: Game) {

  lazy val chronoMoves = moves.reverse

  def addMove(move: Move) = copy(
    moves = move :: moves,
    state = state(move))

  def moveAtPly(ply: Int): Option[Move] = chronoMoves lift (ply - 1)
}

object Replay {

  def apply(game: Game) = new Replay(game, Nil, game)

  def apply(
    moveStrs: List[String],
    initialFen: Option[String],
    variant: hex.variant.Variant): Valid[Replay] =
    moveStrs.some.filter(_.nonEmpty) toValid "[replay] pgn is empty" flatMap { nonEmptyMoves =>
      Reader.moves(
        nonEmptyMoves,
        List(
          initialFen map { fen => Tag(_.FEN, fen) },
          variant.some.filterNot(_.standard) map { v => Tag(_.Variant, v.name) }
        ).flatten)
    }

  def boards(moveStrs: List[String], initialFen: Option[String]): Valid[List[Board]] = {
    val sit = initialFen.flatMap(format.Forsyth.<<) | Situation(hex.variant.Standard)
    val init = sit -> List(sit.board)
    Parser moves(moveStrs, sit.board.variant)  flatMap { sans =>
      sans.foldLeft[Valid[(Situation, List[Board])]](init.success) {
        case (scalaz.Success((sit, boards)), san) =>
          san(sit) map { move =>
            val after = move.afterWithLastMove
            Situation(after, !sit.color) -> (after :: boards)
          }
        case (x, _) => x
      }
    }
  }.map(_._2.reverse)
}

