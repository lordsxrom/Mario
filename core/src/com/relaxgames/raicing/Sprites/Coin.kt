package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import java.util.logging.Logger

class Coin : InteractiveTileObject {

    constructor(world: World, map: TiledMap, bounds: Rectangle) : super(world, map, bounds) {
        fixtue.userData = this
    }

    override fun onHeadHit() {
        Logger.getLogger(Coin::class.java.name).info("Collision")
    }

}