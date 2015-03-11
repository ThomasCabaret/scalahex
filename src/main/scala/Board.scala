package hex
//Describe a board, what is where

import variant.Variant
import annotation.tailrec

case class Board (size: Int,
    pawns: Map[Pos, Color]) {

  //TODO
  def apply(move: Move) = copy(pawns = pawns + ((move.dest, move.color)))

  //def visual = format.Visual >> this

  //override def toString = visual

  //TODO
  def arround(pos: Pos): Set[Pos] = Set(pos)

  //Compute if there is a path for one color for it's side to the other
  //Helper for 3 color algorithm
  def stepPull(done: Set[Pos], pulled: Set[Pos], rest: Set[Pos]): (Set[Pos], Set[Pos], Set[Pos]) = {
  	val arroundPulled = (pulled map {x => arround(x)}).fold(Set())((p: Set[Pos], e: Set[Pos]) => p union e)
  	val newPulled = arroundPulled & rest
  	val newRest = rest &~ newPulled
  	val newDone = done union pulled
  	(newDone, newPulled, newDone)
  }

  //Tail recursive method to get all connected to an initial set with 3 colors algorithm
  @tailrec private def pullAll(done: Set[Pos], pulled: Set[Pos], rest: Set[Pos]): (Set[Pos], Set[Pos], Set[Pos]) = {
  	if (pulled.isEmpty) {
  	   (done, pulled, rest)
  	}
    else {
       val (dn, np, nr) = stepPull(done, pulled, rest)
       pullAll(dn, np, nr)
    }
  }
}

object Board {

  //TODO variant
  def init(size:Int, variant: Variant): Board = new Board(size, Map[Pos, Color]());
}
