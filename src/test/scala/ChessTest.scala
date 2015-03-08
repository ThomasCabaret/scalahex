package hex

import hex.variant.Variant
//import chess.format.{Forsyth, Visual}
import org.specs2.matcher.Matcher
import org.specs2.mutable.Specification
import ornicar.scalalib.test.ValidationMatchers
import scalaz.{ Validation => V }

trait HexTest
    extends Specification
    with ValidationMatchers {

  implicit def stringToBoard(str: String): Board = Visual << str

  implicit def stringToSituationBuilder(str: String) = new {

    def as(color: Color): Situation = Situation(Visual << str, color)
  }

  implicit def richGame(game: Game) = new {

    def as(color: Color): Game = game.copy(player = color)

    def playMoves(moves: (Pos, Pos)*): Valid[Game] = playMoveList(moves)

    def playMoveList(moves: Iterable[(Pos, Pos)]): Valid[Game] = {
      val vg = moves.foldLeft(V.success(game): Valid[Game]) { (vg, move) =>
        // vg foreach { x =>
        // println(s"------------------------ ${x.turns} = $move")
        // }
        // because possible moves are asked for player highlight
        // before the move is played (on initial situation)
        vg foreach { _.situation.destinations }
        val ng = vg flatMap { g => g(move._1, move._2) map (_._1) }
        ng
      }
      // vg foreach { x => println("========= PGN: " + x.pgnMoves) }
      vg
    }

    def playMove(
      orig: Pos,
      dest: Pos,
      promotion: Option[PromotableRole] = None): Valid[Game] =
      game.apply(orig, dest, promotion) map (_._1)

    def withClock(c: Clock) = game.copy(clock = Some(c))
  }

  def fenToGame(positionString: String, variant: Variant) = {
    val situation = Forsyth << positionString

    val maybeBoard = situation map (sit => sit.color -> sit.withVariant(variant).board) toValid "Could not construct situation from FEN"

    maybeBoard map {case (color, board) => Game(variant).copy(board = board) withPlayer(color)}
  }

  def makeBoard(pieces: (Pos, Piece)*): Board =
    Board(pieces toMap, History(), chess.variant.Standard)

  def makeBoard(str: String, variant: Variant) =
    Visual << str withVariant variant

  def makeBoard: Board = Board init chess.variant.Standard

  def makeEmptyBoard: Board = Board empty chess.variant.Standard

  def bePoss(poss: Pos*): Matcher[Option[Iterable[Pos]]] = beSome.like {
    case p => sortPoss(p.toList) must_== sortPoss(poss.toList)
  }

  def makeGame: Game = Game(makeBoard)

  def bePoss(board: Board, visual: String): Matcher[Option[Iterable[Pos]]] = beSome.like {
    case p => Visual.addNewLines(Visual.>>|(board, Map(p -> 'x'))) must_== visual
  }

  def beBoard(visual: String): Matcher[Valid[Board]] = beSuccess.like {
    case b => b.visual must_== (Visual << visual).visual
  }

  def beSituation(visual: String): Matcher[Valid[Situation]] = beSuccess.like {
    case s => s.board.visual must_== (Visual << visual).visual
  }

  def beGame(visual: String): Matcher[Valid[Game]] = beSuccess.like {
    case g => g.board.visual must_== (Visual << visual).visual
  }

  def sortPoss(poss: Seq[Pos]): Seq[Pos] = poss sortBy (_.toString)

  def pieceMoves(piece: Piece, pos: Pos): Option[List[Pos]] =
    (makeEmptyBoard place piece at pos).toOption flatMap { b =>
      b actorAt pos map (_.destinations)
    }
}
