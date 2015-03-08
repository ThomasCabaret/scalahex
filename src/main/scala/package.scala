import ornicar.scalalib
//Ok reserve cleanup

package object hex

    extends scalalib.Validation
    with scalalib.Common
    with scalalib.OrnicarNonEmptyList
    with scalalib.OrnicarMonoid.Instances

    with scalaz.syntax.std.ToBooleanOps

    with scalaz.std.OptionFunctions
    with scalaz.syntax.std.ToOptionOps
    with scalaz.syntax.std.ToOptionIdOps

    with scalaz.std.ListInstances
    with scalaz.syntax.std.ToListOps

    with scalaz.syntax.ToValidationOps
    with scalaz.syntax.ToFunctorOps
    with scalaz.syntax.ToIdOps {

  val Red = Color.Red
  val Blue = Color.Blue

  type PositionHash = Array[Byte]

  object implicitFailures {
    implicit def stringToFailures(str: String): Failures = scalaz.NonEmptyList(str)
  }

  def parseIntOption(str: String): Option[Int] = try {
    Some(java.lang.Integer.parseInt(str))
  }
  catch {
    case e: NumberFormatException => None
  }
}
