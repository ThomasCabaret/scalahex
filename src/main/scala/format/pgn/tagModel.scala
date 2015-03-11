package hex
package format.pgn

case class Tag(name: TagType, value: String) {

  override def toString = """[%s "%s"]""".format(name, value)
}

sealed trait TagType {
  lazy val name = toString
  lazy val lowercase = name.toLowerCase
}

object Tag {

  case object Event extends TagType
  case object Site extends TagType
  case object Date extends TagType
  case object Red extends TagType
  case object Blue extends TagType
  case object TimeControl extends TagType
  case object RedClock extends TagType
  case object BlueClock extends TagType
  case object RedElo extends TagType
  case object BlueElo extends TagType
  case object Result extends TagType
  case object FEN extends TagType
  case object Variant extends TagType
  case object ECO extends TagType
  case object Opening extends TagType
  case class Unknown(n: String) extends TagType {
    override def toString = n
  }

  val tagTypes = List(Event, Site, Date, Red, Blue, TimeControl, RedClock, BlueClock, RedElo, BlueElo, Result, FEN, Variant, ECO)
  val tagTypesByLowercase = tagTypes map { t => t.lowercase -> t } toMap

  def apply(name: String, value: Any): Tag = new Tag(
    name = tagType(name),
    value = value.toString)

  def apply(name: Tag.type => TagType, value: Any): Tag = new Tag(
    name = name(this),
    value = value.toString)

  def tagType(name: String) =
    (tagTypesByLowercase get name.toLowerCase) | Unknown(name)
}
