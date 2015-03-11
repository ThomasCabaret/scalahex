package hex
//Describe a board, what is where

import variant.Variant

case class Board (size: Int,
    pawns: Map[Pos, Color]) {

  //TODO
  def apply(move: Move) = copy(pawns = pawns + ((move.dest, move.color)))

  //def visual = format.Visual >> this

  //override def toString = visual

  //Compute if there is a path for one color for it's side to the other
  //TODO
}

object Board {

  def init(variant: Variant): Board = new Board(14, Map[Pos, Color]());//TODO
}
