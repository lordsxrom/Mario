package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.relaxgames.raicing.Screens.PlayScreen

abstract class Enemy : Sprite {

    val world: World
    val map: TiledMap
    lateinit var b2body: Body
    val screen:PlayScreen
    val velocity:Vector2

    constructor(screen: PlayScreen, x: Float, y: Float) {
        this.world = screen.world
        this.map = screen.map
        this.screen = screen
        setPosition(x, y)
        defineEnemy()
        velocity = Vector2(1f,0f)
    }

    abstract fun defineEnemy()
    abstract fun hitOnHead()

    fun reverseVelocity(x:Boolean,y:Boolean){
        if (x)
            velocity.x = -velocity.x
        if (y)
            velocity.y = -velocity.y
    }
}