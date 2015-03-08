package hex
// Why is this thing so complex and so big?
// Ok reserve design

sealed trait Color {

  def fold[A](r: => A, b: => A): A = if (red) r else b

  val unary_! : Color

  val letter: Char
  val name: String

  val red = this == Color.Red
  val blue = this == Color.Blue

  override def toString = name
}

object Color {

  case class Map[A](red: A, blue: A) {
    def apply(color: Color) = if (color.red) red else blue
  }

  object Map {
    def apply[A](f: Color => A): Map[A] = Map(red = f(Red), blue = f(Blue))
  }

  case object Red extends Color {

    lazy val unary_! = Blue

    val letter = 'r'
    val name = "red"
  }

  case object Blue extends Color {

    lazy val unary_! = Red

    val letter = 'b'
    val name = "blue"
  }

  def apply(b: Boolean): Color = if (b) Red else Blue

  def apply(n: String): Option[Color] =
    if (n == "red") Some(Red)
    else if (n == "blue") Some(Blue)
    else None

  def apply(c: Char): Option[Color] =
    if (c == 'r') Some(Red)
    else if (c == 'b') Some(Blue)
    else None

  val red: Color = Red
  val blue: Color = Blue

  val all = List(Red, Blue)

  val names = all map (_.name)

  def exists(name: String) = all exists (_.name == name)
}

