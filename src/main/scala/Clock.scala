package hex
//Ok reserve design

import scala.concurrent.duration._

// All durations are expressed in seconds
sealed trait Clock {
  val limit: Int
  val increment: Int
  val color: Color
  val redTime: Float
  val blueTime: Float
  val timerOption: Option[Double]

  def time(c: Color) = c.fold(redTime, blueTime)

  def outoftime(c: Color) = remainingTime(c) == 0

  def remainingTime(c: Color) = math.max(0, limit - elapsedTime(c))

  def remainingTimes = Color.all map { c => c -> remainingTime(c) } toMap

  def elapsedTime(c: Color) = time(c)

  def limitInMinutes = limit / 60

  def estimateTotalTime = limit + 30 * increment

  def emergTime: Int = math.round(math.min(30, math.max(2, estimateTotalTime / 12)))

  def stop: PausedClock

  def addTime(c: Color, t: Float): Clock

  def giveTime(c: Color, t: Float): Clock

  def halfTime(c: Color): Clock

  def show = s"$limitInMinutes+$increment"

  def showTime(t: Float) = {
    val hours = math.floor(t / 3600).toInt
    val minutes = math.floor((t - hours * 3600) / 60).toInt
    val seconds = t.toInt % 60
    s"${if (hours > 0) hours else ""}:$minutes:$seconds"
  }

  def isRunning = timerOption.isDefined

  def isInit = elapsedTime(Red) == 0 && elapsedTime(Blue) == 0

  def switch: Clock

  def takeback: Clock

  def reset = Clock(
    limit = limit,
    increment = increment)

  protected def now = System.currentTimeMillis / 1000d
}

case class RunningClock(
    limit: Int,
    increment: Int,
    color: Color,
    redTime: Float,
    blueTime: Float,
    timer: Double) extends Clock {

  val timerOption = Some(timer)

  override def elapsedTime(c: Color) = time(c) + {
    if (c == color) now - timer else 0
  }.toFloat

  def step(lag: FiniteDuration = 0.millis) = {
    val t = now
    val spentTime = (t - timer).toFloat
    val lagSeconds = lag.toMillis.toFloat / 1000
    val lagCompensation = math.max(0,
      math.min(
        lagSeconds - Clock.naturalLag,
        Clock.maxLagToCompensate))
    addTime(
      color,
      (math.max(0, spentTime - lagCompensation) - increment)
    ).copy(
        color = !color,
        timer = t)
  }

  def stop = PausedClock(
    limit = limit,
    increment = increment,
    color = color,
    redTime = redTime + (if (color == Red) (now - timer).toFloat else 0),
    blueTime = blueTime + (if (color == Blue) (now - timer).toFloat else 0))

  def addTime(c: Color, t: Float): RunningClock = c match {
    case Red => copy(redTime = redTime + t)
    case Blue => copy(blueTime = blueTime + t)
  }

  def giveTime(c: Color, t: Float): RunningClock = addTime(c, -t)

  def halfTime(c: Color): RunningClock = addTime(c, remainingTime(c) / 2)

  def switch: RunningClock = copy(color = !color)

  def takeback: RunningClock = {
    val t = now
    val spentTime = (t - timer).toFloat
    addTime(color, spentTime).copy(
      color = !color,
      timer = t)
  }
}

case class PausedClock(
    limit: Int,
    increment: Int,
    color: Color,
    redTime: Float,
    blueTime: Float) extends Clock {

  val timerOption = None

  def stop = this

  def addTime(c: Color, t: Float): PausedClock = c match {
    case Red => copy(redTime = redTime + t)
    case Blue => copy(blueTime = blueTime + t)
  }

  def giveTime(c: Color, t: Float): PausedClock = addTime(c, -t)

  def halfTime(c: Color): PausedClock = addTime(c, remainingTime(c) / 2)

  def switch: PausedClock = copy(color = !color)

  def takeback: PausedClock = switch

  def start = RunningClock(
    color = color,
    redTime = redTime,
    blueTime = blueTime,
    increment = increment,
    limit = limit,
    timer = now)
}

object Clock {

  val minInitLimit = 2f
  // no more than this time will be offered to the lagging player
  val maxLagToCompensate = 1f
  // substracted from lag compensation
  val naturalLag = 0f

  def apply(
    limit: Int,
    increment: Int): PausedClock = {
    val clock = PausedClock(
      limit = limit / 60 * 60, // round to minutes
      increment = increment,
      color = Red,
      redTime = 0f,
      blueTime = 0f)
    if (clock.limit == 0) clock
      .giveTime(Red, increment.max(2))
      .giveTime(Blue, increment.max(2))
    else clock
  }

  def timeString(t: Int) = periodFormatter.print(
    org.joda.time.Duration.standardSeconds(t).toPeriod
  )

  private val periodFormatter = new org.joda.time.format.PeriodFormatterBuilder().
    printZeroAlways.
    minimumPrintedDigits(1).appendHours.appendSeparator(":").
    minimumPrintedDigits(2).appendMinutes.appendSeparator(":").
    appendSeconds.
    toFormatter
}
