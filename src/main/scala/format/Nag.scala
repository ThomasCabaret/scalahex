package hex.format
//Ok with the enum question
//Might be kept for hex, why not

// http://en.wikipedia.org/wiki/Numeric_Annotation_Glyphs
sealed abstract class Nag(
  val code: Int,
  val symbol: String)

object Nag {
  case object Good extends Nag(1, "!")
  case object Mistake extends Nag(2, "?")
  case object Brilliant extends Nag(3, "!!")
  case object Blunder extends Nag(4, "??")
  case object Inaccuracy extends Nag(5, "?!")

  val badOnes = List(Inaccuracy, Mistake, Blunder)
  val goodOnes = List(Good, Brilliant)
  val all = badOnes ::: goodOnes
  val byCode = all map { n => n.code -> n } toMap

  def apply(code: Int): Option[Nag] = byCode get code
}
