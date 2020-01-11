package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileSet
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.World
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Scenes.Hud
import java.util.logging.Logger

class Coin : InteractiveTileObject {

    private val tileSet: TiledMapTileSet
    private val BLANK_COIN = 28

    constructor(world: World, map: TiledMap, bounds: Rectangle) : super(world, map, bounds) {
        tileSet = map.tileSets.getTileSet("NES - Super Mario Bros - Tileset")
        fixtue.userData = this
        setCategoryFilter(MyGdxGame.COIN_BIT)
    }

    override fun onHeadHit() {
        Logger.getLogger(Coin::class.java.name).info("Collision")

        if (getCell().tile.id == BLANK_COIN) {
            MyGdxGame.manager.get("audio/sounds/bump.wav", Sound::class.java).play()
        } else {
            MyGdxGame.manager.get("audio/sounds/coin.wav", Sound::class.java).play()
        }

        getCell().tile = tileSet.getTile(BLANK_COIN)
        Hud.addScore(100)
    }

}