package newauto

/**
  * Created by Basil on 31/08/2016.
  */
trait NState
trait BinaryNState extends NState
trait GardenNState extends NState
case object NDead extends BinaryNState
case object NAlive extends BinaryNState
