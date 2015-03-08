package hex
package variant

case object Standard extends Variant(
  id = 1,
  key = "standard",
  name = "Standard",
  shortName = "STD",
  title = "Standard rules of hex",
  standardInitialPosition = true)
