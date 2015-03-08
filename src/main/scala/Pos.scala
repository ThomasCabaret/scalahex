package hex
//Ok remains TODO

import scala.collection.immutable.HashSet
import scala.math.{ min, max, abs }

sealed case class Pos private (x: Int, y: Int) {

  import Pos.posAt
  // By choice position will remain in the mathematic standard (1,1) top left
  // 1 2 3
  //1 . O O
  //2  O O O
  //3   O O . 
  lazy val upLeft: Option[Pos] = posAt(x, y - 1)
  lazy val upRight: Option[Pos] = posAt(x + 1, y - 1)
  lazy val downLeft: Option[Pos] = posAt(x - 1, y + 1)
  lazy val downRight: Option[Pos] = posAt(x, y + 1)
  lazy val right: Option[Pos] = posAt(x + 1, y)
  lazy val left: Option[Pos] = posAt(x - 1, y)

  /** The positions surrounding a given position on the board. At the edge of the board has
    * less surrounding positions than the usual six. */
  lazy val surroundingPositions : Set[Pos] =
    HashSet(left, right, upLeft, upRight, downLeft, downRight).flatten;

  // To be checked
  def touches(other: Pos): Boolean = xDist(other) <= 1 && yDist(other) <= 1
  def xDist(other: Pos) = abs(x - other.x)
  def yDist(other: Pos) = abs(y - other.y)

  val file = Pos xToString x
  val rank = y.toString
  val key = file + rank
  override val toString = key
}

object Pos {

  def posAt(x: Int, y: Int): Option[Pos] = allCoords get (x, y)

  def posAt(key: String): Option[Pos] = allKeys get key

  def xToString(x: Int) = (96 + x).toChar.toString

  val all = //TODO

  val allKeys: Map[String, Pos] = all map { pos => pos.key -> pos } toMap

  val allCoords: Map[(Int, Int), Pos] = all map { pos => (pos.x, pos.y) -> pos } toMap
}
