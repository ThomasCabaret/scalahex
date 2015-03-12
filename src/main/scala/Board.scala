package hex
//Describe a board, what is where

import variant.Variant
import annotation.tailrec

case class Board (size: Int,
                  redPawns: Set[Pos],
                  bluePawns: Set[Pos]) {

  def apply(move: Move) = {
    if (move.color == Red) {
      copy(redPawns = redPawns + move.dest)
    } else {
      copy(bluePawns = bluePawns + move.dest)
    }
  }

  //def visual = format.Visual >> this

  //override def toString = visual

  def valid(pos: Pos): Boolean = {
    pos.x >= 1 && pos.y >= 1 && pos.x <= size && pos.y <= size
  }

  //Build a set from pos in the direction dx dy until reaching the end of the board (used to compute sides)
  @tailrec private def project(pos: Pos, dx: Int, dy: Int, lineSet: Set[Pos]): Set[Pos] = {
    if (!valid(pos)) {
      lineSet
    }
    else {
      project(Pos(pos.x + dx, pos.y + dy), dx, dy, lineSet + pos)
    }
  }

  lazy val leftSide: Set[Pos] = project(Pos(1, size), 0, -1, Set())
  lazy val rightSide: Set[Pos] = project(Pos(size, size), 0, -1, Set())
  lazy val topSide: Set[Pos] = project(Pos(size, 1), -1, 0, Set())
  lazy val bottomSide: Set[Pos] = project(Pos(size, size), -1, 0, Set())

  //Real surrounding position given this board size
  def arround(pos: Pos): Set[Pos] = pos.surroundingPositions.filter( p => valid(p))

  //Compute if there is a path for one color for it's side to the other
  //Helper for 3 colors algorithm
  def stepPull(done: Set[Pos], pulled: Set[Pos], rest: Set[Pos]): (Set[Pos], Set[Pos], Set[Pos]) = {
  	val arroundPulled = (pulled map {x => arround(x)}).fold(Set())((p: Set[Pos], e: Set[Pos]) => p union e)
  	val newPulled = arroundPulled & rest
  	val newRest = rest &~ newPulled
  	val newDone = done union pulled
  	(newDone, newPulled, newDone)
  }

  //Compute if there is a path for one color for it's side to the other
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

  //Horizontally (we start the 3 colors algorithm from the pawns touching one side, 
  //              when everything connected is computed we test if some are on the opposite side)
  lazy val blueConnected: Boolean = {
    val (done, _, _) = pullAll(Set(), leftSide & bluePawns, bluePawns &~ leftSide)
    !((done & rightSide).isEmpty)
  }

  //Vertically (we start the 3 colors algorithm from the pawns touching one side, 
  //            when everything connected is computed we test if some are on the opposite side)
  lazy val redConnected: Boolean = {
    val (done, _, _) = pullAll(Set(), topSide & redPawns, redPawns &~ topSide)
    !((done & bottomSide).isEmpty)
  }
}

object Board {

  //TODO variant
  def init(size:Int, variant: Variant): Board = new Board(size, Set[Pos](), Set[Pos]());
}
