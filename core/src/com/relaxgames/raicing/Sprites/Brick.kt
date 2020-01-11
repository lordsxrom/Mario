package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World

class Brick : InteractiveTileObject {

    constructor(world: World, map: TiledMap, bounds: Rectangle) : super(world,map,bounds) {

    }

}