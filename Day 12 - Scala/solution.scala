object Solution {
  // Puzzle Input:
  var moon_array = Array(
    // Moon  Name         X    Y   Z
    new Moon("Io",        7,  10, 17),
    new Moon("Europa",   -2,   7,  0),
    new Moon("Ganymede", 12,   5, 12),
    new Moon("Callisto",  5,  -8,  6)
  )

  def main(args: Array[String]): Unit = {
    part_1
    part_2
  }

  def part_1(): Unit = {
    for (time_step <- (1 to 1000)) take_step
    var total_energy = BigInt(0)
    for (moon <- moon_array) total_energy += moon.total_energy
    println(s"Part 1: $total_energy")
  }

  // Probably more verbose than necessary
  def part_2(): Unit = {
    var time_step = 0
    var x_looped  = 0
    var y_looped  = 0
    var z_looped  = 0
    var x_resets  = 0
    var y_resets  = 0
    var z_resets  = 0
    reset_moon_state
    while (x_looped == 0 || y_looped == 0 || z_looped == 0) {
      time_step += take_step
      x_resets = 0
      y_resets = 0
      z_resets = 0
      for (moon <- moon_array) {
        if (moon.check_x_reset) x_resets += 1
        if (moon.check_y_reset) y_resets += 1
        if (moon.check_z_reset) z_resets += 1
      }
      if (x_resets == moon_array.length && x_looped == 0) x_looped = time_step
      if (y_resets == moon_array.length && y_looped == 0) y_looped = time_step
      if (z_resets == moon_array.length && z_looped == 0) z_looped = time_step
    }
    var steps = lcm(x_looped, lcm(y_looped, z_looped))
    println(s"Part 2: $steps steps")
  }

  // go back to initial state
  def reset_moon_state(): Unit = for (moon <- moon_array) moon.reset

  // Take a step in the simulation. Return 1 to indicate one step taken
  def take_step():  Int = {
    apply_gravity
    apply_velocity
    1
  }

  def apply_gravity():  Unit = {
    for (combo <- moon_array.combinations(2)) {

      // Calculate x-axis velocity
      if (combo(0).get_xpos > combo(1).get_xpos) {
        combo(1).inc_xvel
        combo(0).dec_xvel
      } else if (combo(0).get_xpos < combo(1).get_xpos) {
        combo(0).inc_xvel
        combo(1).dec_xvel
      }

      // Calculate y-axis velocity
      if (combo(0).get_ypos > combo(1).get_ypos) {
        combo(1).inc_yvel
        combo(0).dec_yvel
      } else if (combo(0).get_ypos < combo(1).get_ypos) {
        combo(0).inc_yvel
        combo(1).dec_yvel
      }

      // Calculate z-axis velocity
      if (combo(0).get_zpos > combo(1).get_zpos) {
        combo(1).inc_zvel
        combo(0).dec_zvel
      } else if (combo(0).get_zpos < combo(1).get_zpos) {
        combo(0).inc_zvel
        combo(1).dec_zvel
      }
    }
  }

  def apply_velocity(): Unit = {
    for (moon <- moon_array) {
      moon.set_xpos(moon.get_xpos + moon.get_xvel)
      moon.set_ypos(moon.get_ypos + moon.get_yvel)
      moon.set_zpos(moon.get_zpos + moon.get_zvel)
    }
  }

  // Find the greatest common denominator
  def gcd(a: BigInt, b: BigInt): BigInt = {
    var x = a
    var y = b
    var t = BigInt(0)
  	while (y > 0) {	t = y ; y = x % y ; x = t }
  	x
  }

  // Use the GCD to find the LCM between two values
  def lcm(x: BigInt, y: BigInt): BigInt = x / gcd(x, y) * y
}

class Moon(name: String, x: Int, y: Int, z: Int) {
  private var xpos    = BigInt(x)
  private var ypos    = BigInt(y)
  private var zpos    = BigInt(z)
  private var xpos_i  = BigInt(x)
  private var ypos_i  = BigInt(y)
  private var zpos_i  = BigInt(z)
  private var xvel    = BigInt(0)
  private var yvel    = BigInt(0)
  private var zvel    = BigInt(0)
  private var loop    = BigInt(0)
  private var xreset  = BigInt(0)
  private var yreset  = BigInt(0)
  private var zreset  = BigInt(0)
  private var xvreset = BigInt(0)
  private var yvreset = BigInt(0)
  private var zvreset = BigInt(0)

  def potential_energy(): BigInt = xpos.abs + ypos.abs + zpos.abs
  def kinetic_energy():   BigInt = xvel.abs + yvel.abs + zvel.abs
  def total_energy():     BigInt = potential_energy * kinetic_energy

  def set_xpos(pos: BigInt): Unit = { xpos = pos }
  def set_ypos(pos: BigInt): Unit = { ypos = pos }
  def set_zpos(pos: BigInt): Unit = { zpos = pos }
  def get_xpos():         BigInt  = xpos
  def get_ypos():         BigInt  = ypos
  def get_zpos():         BigInt  = zpos
  def get_xvel():         BigInt  = xvel
  def get_yvel():         BigInt  = yvel
  def get_zvel():         BigInt  = zvel
  def inc_xvel():         Unit = { xvel += BigInt(1) }
  def inc_yvel():         Unit = { yvel += BigInt(1) }
  def inc_zvel():         Unit = { zvel += BigInt(1) }
  def dec_xvel():         Unit = { xvel -= BigInt(1) }
  def dec_yvel():         Unit = { yvel -= BigInt(1) }
  def dec_zvel():         Unit = { zvel -= BigInt(1) }
  def reset():            Unit = {
    xpos = xpos_i
    ypos = ypos_i
    zpos = zpos_i
    xvel = BigInt(0)
    yvel = BigInt(0)
    zvel = BigInt(0)
  }
  def check_x_reset(): Boolean = xpos == xpos_i && xvel == 0
  def check_y_reset(): Boolean = ypos == ypos_i && yvel == 0
  def check_z_reset(): Boolean = zpos == zpos_i && zvel == 0
}
