package hex
package format.pgn
//I dont know what this is supposed to do

object Dumper {

  /*def apply(situation: Situation, data: hex.Move, next: Situation): String = {
    import data._

    ((promotion, piece.role) match {
      case _ if castles =>
        if (orig ?> dest) "O-O-O" else "O-O"

      case _ if enpassant =>
        orig.file + 'x' + dest.key

      case (promotion, Pawn) =>
        captures.fold(orig.file + "x", "") +
          promotion.fold(dest.key)(p => dest.key + "=" + p.pgn)

      case (_, role) => {
        // Check whether there is a need to disambiguate:
        //   - can a piece of same role move to/capture on the same square?
        //   - if so, disambiguate, in order or preference, by:
        //       - file
        //       - rank
        //       - both (only happens w/ at least 3 pieces of the same role)
        val candidates = situation.board.pieces collect {
          case (cpos, cpiece) if cpiece == piece && cpos != orig && cpiece.eyes(cpos, dest) => cpos
        } filter { cpos =>
          // We know Role ≠ Pawn, so it is fine to always pass None as promotion target
          situation.move(cpos, dest, None).isSuccess
        }

        val disambiguation = if (candidates.isEmpty) {
          ""
        } else if (!candidates.exists(_ ?| orig)) {
          orig.file
        } else if (!candidates.exists(_ ?- orig)) {
          orig.rank
        } else {
          orig.file + orig.rank
        }

        role.pgn + disambiguation + captures.fold("x", "") + dest.key
      }
    }) + (if (next.check) if (next.checkMate) "#" else "+" else "")
  }

  def apply(data: hex.Move): String = apply(
    data.before situationOf data.color,
    data,
    data.afterWithLastMove situationOf !data.color)*/
}
