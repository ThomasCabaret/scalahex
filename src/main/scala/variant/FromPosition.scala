package hex
package variant

case object FromPosition extends Variant(
  id = 2,
  key = "fromPosition",
  name = "From Position",
  shortName = "FEN",
  title = "Custom starting position",
  standardInitialPosition = false)
