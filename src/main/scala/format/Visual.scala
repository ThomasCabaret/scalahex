package hex
package format

/**
 * O O O O O O O
 *  O O O O O O O
 *   O O R O O O O
 *    O O O R B O O
 *     O O O B O O O
 *      O O O O O O O
 *       O O O O O O O
 *        O O O O O O O
 */
object Visual {

  //TODO
  /*def <<(source: String): Board = {
    val lines = source.lines.toList
    val size = lines.size
    val filtered = lines.size match {
      case size => lines
      case n    => (List.fill(size - n)("")) ::: lines
    }
    Board(
      pieces = (for {
        line ← (filtered.zipWithIndex)
        (l, y) = line
        char ← (l zipWithIndex)
        (c, x) = char
        color ← Color (c.toLower)
      } yield {
        posAt(x + 1, 8 - y) map { pos => pos -> (Color(c isUpper) - role) }
      }) flatten,
      variant = hex.variant.Variant.default
    )
  }*/

  //TODO
  //def >>(board: Board): String = >>|(board, Map.empty)

  //TODO
  /*def >>|(board: Board, marks: Map[Iterable[Pos], Char]): String = {
    val markedPoss: Map[Pos, Char] = marks.foldLeft(Map[Pos, Char]()) {
      case (marks, (poss, char)) => marks ++ (poss.toList map { pos => (pos, char) })
    }
    for (y ← 8 to 1 by -1) yield {
      for (x ← 1 to 8) yield {
        posAt(x, y) flatMap markedPoss.get getOrElse board(x, y).fold(' ')(_ forsyth)
      }
    } mkString
  } map { """\s*$""".r.replaceFirstIn(_, "") } mkString "\n"*/

  //def addNewLines(str: String) = "\n" + str + "\n"
}
