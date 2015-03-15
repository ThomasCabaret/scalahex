package hex
//Ok

//We do not filter in the class.
//The board will stay master of the topology which makes sense due to variable size of the board
case class Pos (x: Int, y: Int) {

  // By choice position will remain in the mathematic standard (1,1) top left
  //  1 2 3
  // 1 - O O
  // 2  O X O
  // 3   O O - 
  lazy val upLeft = copy(x = x, y = y - 1)
  lazy val upRight = copy(x = x + 1, y =  y - 1)
  lazy val downLeft = copy(x = x - 1, y =  y + 1)
  lazy val downRight = copy(x = x, y =  y + 1)
  lazy val right = copy(x = x + 1, y =  y)
  lazy val left = copy(x = x - 1, y =  y)

  // The positions surrounding a given position on the board
  lazy val surroundingPositions : Set[Pos] =
    Set(left, right, upLeft, upRight, downLeft, downRight);

  // To be checked
  //def touches(other: Pos): Boolean = xDist(other) <= 1 && yDist(other) <= 1
  //def xDist(other: Pos) = abs(x - other.x)
  //def yDist(other: Pos) = abs(y - other.y)

  lazy val xStr = Pos xToString x
  lazy val yStr = y.toString
  lazy val key = xStr + yStr
  override val toString = key
}

object Pos {

  //def apply(x: Int, y: Int): Pos = new Pos(x, y)

  // 1 -> a, 2 -> b etc...
  def xToString(x: Int) = (96 + x).toChar.toString
}
