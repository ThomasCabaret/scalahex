package hex
//Produce a array of byte of the different pieces types, position and color.
//TODO

import java.security.MessageDigest

object Hash {

  private[hex] val size = 3

  private def apply(str: String): PositionHash =
    MessageDigest getInstance "MD5" digest (str getBytes "UTF-8") take size

  //def apply(actors: Iterable[Actor], color: Color): PositionHash = apply {
  //  actors.map { a =>
  //    s"${a.piece.forsyth}${a.pos.key}"
  //  }.mkString + color.letter
  //}
}
