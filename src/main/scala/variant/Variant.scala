package hex
package variant

import Pos.posAt

abstract class Variant(
    val id: Int,
    val key: String,
    val name: String,
    val shortName: String,
    val title: String,
    val standardInitialPosition: Boolean) {

  def standard = this == Standard

  def exotic = !standard

  override def toString = s"Variant($name)"
}

object Variant {

  val all = List(Standard, FromPosition)
  val byId = all map { v => (v.id, v) } toMap

  val default = Standard

  def apply(id: Int): Option[Variant] = byId get id
  def orDefault(id: Int): Variant = apply(id) | default

  def byName(name: String): Option[Variant] =
    all find (_.name.toLowerCase == name.toLowerCase)

  def exists(id: Int): Boolean = byId contains id
}

