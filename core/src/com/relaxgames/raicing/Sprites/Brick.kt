package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Scenes.Hud

class Brick : InteractiveTileObject {

    constructor(world: World, map: TiledMap, bounds: Rectangle) : super(world, map, bounds) {
        fixtue.userData = this
        setCategoryFilter(MyGdxGame.BRICK_BIT)
    }

    override fun onHeadHit() {
        Gdx.app.log("Brick", "Collision")
        setCategoryFilter(MyGdxGame.DESTROYED_BIT)
        getCell().tile = null

        Hud.addScore(200)

        MyGdxGame.manager.get("audio/sounds/breakblock.wav", Sound::class.java).play()
    }

}