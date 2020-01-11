package com.relaxgames.raicing

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.relaxgames.raicing.Screens.PlayScreen

class MyGdxGame : Game() {

    companion object {
        //Virtual Screen size and Box2D Scale(Pixels Per Meter)
        const val V_WIDTH = 400f
        const val V_HEIGHT = 208f
        const val PPM = 100f

        const val DEFAULT_BIT:Short = 1
        const val MARIO_BIT:Short = 2
        const val BRICK_BIT:Short = 4
        const val COIN_BIT:Short = 8
        const val DESTROYED_BIT:Short = 16
    }

    lateinit var batch: SpriteBatch

    override fun create() {
        batch = SpriteBatch()
        setScreen(PlayScreen(this))
    }

    override fun render() {
        super.render()
    }
}
