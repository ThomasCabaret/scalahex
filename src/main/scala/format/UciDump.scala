package hex
package format
//OK use of valid

import hex.variant.Variant

object UciDump {

  // 1. a2 2. b8, ...
  def apply(replay: Replay): List[String] =
    replay.chronoMoves map {move => move.pos.key}

  def apply(moves: List[String], initialFen: Option[String], variant: Variant): Valid[List[String]] =
    moves.isEmpty.fold(
      success(Nil),
      Replay(moves, initialFen, variant) map apply
    )
}

