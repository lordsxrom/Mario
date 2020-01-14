package com.relaxgames.raicing

import com.badlogic.gdx.Game
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.relaxgames.raicing.Screens.PlayScreen

class MyGdxGame : Game() {

    companion object {
        //Virtual Screen size and Box2D Scale(Pixels Per Meter)
        const val V_WIDTH = 400f
        const val V_HEIGHT = 208f
        const val PPM = 100f

        const val GROUND_BIT: Short = 1
        const val MARIO_BIT: Short = 2
        const val BRICK_BIT: Short = 4
        const val COIN_BIT: Short = 8
        const val DESTROYED_BIT: Short = 16
        const val OBJECT_BIT: Short = 32
        const val ENEMY_BIT: Short = 64

        @JvmStatic
        val manager = AssetManager()
    }

    lateinit var batch: SpriteBatch

    override fun create() {
        manager.load("audio/music/mario_music.ogg", Music::class.java)
        manager.load("audio/sounds/coin.wav", Sound::class.java)
        manager.load("audio/sounds/bump.wav", Sound::class.java)
        manager.load("audio/sounds/breakblock.wav", Sound::class.java)
        manager.load("audio/sounds/powerup_spawn.wav", Sound::class.java)
        manager.load("audio/sounds/powerup.wav", Sound::class.java)
        manager.load("audio/sounds/powerdown.wav", Sound::class.java)
        manager.load("audio/sounds/stomp.wav", Sound::class.java)
        manager.load("audio/sounds/mariodie.wav", Sound::class.java)
        manager.finishLoading()

        batch = SpriteBatch()
        setScreen(PlayScreen(this))
    }

    override fun dispose() {
        super.dispose()
        manager.dispose()
        batch.dispose()
    }

    override fun render() {
        super.render()
        manager.update()
    }
}
