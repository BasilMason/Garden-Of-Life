package object newauto {

  type Site = String
  type Grid = Map[VectorN, NCell]
  type Neighbours = Map[Site, NCell]
  type DNeighbours = Map[Site, NDynamic]
  type Neighbourhood = Map[VectorN, Neighbours]
  type Transition = (NState, Neighbours) => NState
  type DTransition = (DynamicNState, DNeighbours) => DynamicNState
  type Configuration = List[NCell]

}
