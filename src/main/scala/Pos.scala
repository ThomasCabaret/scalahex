package hex
//Ok remains TODO

//import scala.collection.immutable.HashSet
//import scala.math.{ min, max, abs }

//We do not filter in the class.
//The board will stay master of the topology which makes sense due to variable size of the board
case class Pos (x: Int, y: Int) {

  // By choice position will remain in the mathematic standard (1,1) top left
  // 1 2 3
  //1 . O O
  //2  O O O
  //3   O O . 
  lazy val upLeft = copy(x = x, y = y - 1)
  lazy val upRight = copy(x = x + 1, y =  y - 1)
  lazy val downLeft = copy(x = x - 1, y =  y + 1)
  lazy val downRight = copy(x = x, y =  y + 1)
  lazy val right = copy(x = x + 1, y =  y)
  lazy val left = copy(x = x - 1, y =  y)

  /** The positions surrounding a given position on the board. At the edge of the board has
    * less surrounding positions than the usual six. */
  lazy val surroundingPositions : Set[Pos] =
    Set(left, right, upLeft, upRight, downLeft, downRight);

  // To be checked
  //def touches(other: Pos): Boolean = xDist(other) <= 1 && yDist(other) <= 1
  //def xDist(other: Pos) = abs(x - other.x)
  //def yDist(other: Pos) = abs(y - other.y)

  val file = Pos xToString x
  val rank = y.toString
  val key = file + rank
  override val toString = key
}

object Pos {

  //def apply(x: Int, y: Int): Pos = new Pos(x, y)

  //def posAt(key: String): Option[Pos] = allKeys get key

  def xToString(x: Int) = (96 + x).toChar.toString
}
