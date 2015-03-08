package hex
//Describe a board, what is where

import variant.Variant

class Board (
    pawns: map[Pos, Color] = Nil) {

  //TODO
  def apply(move: Move): copy(pawns = pawns put (move.dest, move.color))

  def visual = format.Visual >> this

  override def toString = visual
}

object Board {

  def init(variant: Variant): Board = //TODO
}
