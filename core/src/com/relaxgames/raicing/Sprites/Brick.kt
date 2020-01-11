package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.relaxgames.raicing.MyGdxGame

class Brick : InteractiveTileObject {

    constructor(world: World, map: TiledMap, bounds: Rectangle) : super(world, map, bounds) {
        fixtue.userData = this
        setCategoryFilter(MyGdxGame.BRICK_BIT)
    }

    override fun onHeadHit() {
        Gdx.app.log("Brick", "Collision")
        setCategoryFilter(MyGdxGame.DESTROYED_BIT)
        getCell().tile = null
    }

}