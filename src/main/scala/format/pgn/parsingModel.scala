package hex
package format.pgn
//I dont know what this thing does

/*case class ParsedPgn(tags: List[Tag], sans: List[San])

// Standard Algebraic Notation
sealed trait San {

  def apply(situation: Situation): Valid[hex.Move]
}

case class Std(
    dest: Pos,
    role: Role,
    capture: Boolean = false,
    file: Option[Int] = None,
    rank: Option[Int] = None,
    check: Boolean = false,
    checkmate: Boolean = false,
    promotion: Option[PromotableRole] = None) extends San {

  def withSuffixes(s: Suffixes) = copy(
    check = s.check,
    checkmate = s.checkmate,
    promotion = s.promotion)

  def apply(situation: Situation): Valid[hex.Move] =
    situation.board.pieces.foldLeft(none[hex.Move]) {
      case (None, (pos, piece)) if piece.color == situation.color && piece.role == role && compare(file, pos.x) && compare(rank, pos.y) && piece.eyesMovable(pos, dest) =>
        val a = Actor(piece, pos, situation.board)
        a trustedMoves false find { m =>
          m.dest == dest && (a kingSafety m)
        }
      case (m, _) => m
    } match {
      case None       => s"No move found: $this\n${situation.board}".failureNel
      case Some(move) => move withPromotion promotion toValid "Wrong promotion"
    }

  private def compare[A](a: Option[A], b: => A) = a.fold(true)(b==)

  // override def toString = role.forsyth + dest.toString
}

case class Suffixes(
  check: Boolean,
  checkmate: Boolean,
  promotion: Option[PromotableRole])

case class Castle(
    side: Side,
    check: Boolean = false,
    checkmate: Boolean = false) extends San {

  def withSuffixes(s: Suffixes) = copy(
    check = s.check,
    checkmate = s.checkmate)

  def apply(situation: Situation): Valid[hex.Move] = for {
    kingPos ← situation.board kingPosOf situation.color toValid "No king found"
    actor ← situation.board actorAt kingPos toValid "No actor found"
    move ← actor.castleOn(side).headOption toValid "Cannot castle / variant is " + situation.board.variant
  } yield move
}*/
