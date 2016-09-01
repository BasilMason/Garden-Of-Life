package object newauto {

  type Site = String
  type Grid = Map[VectorN, NCell]
  type Neighbours = Map[Site, NCell]
  type Neighbourhood = Map[VectorN, Neighbours]
  type Transition = (NState, Neighbours) => NState
  type Configuration = List[NCell]

}
