package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.World
import com.relaxgames.raicing.Screens.PlayScreen

abstract class Enemy : Sprite {

    val world: World
    val map: TiledMap
    lateinit var b2body: Body

    constructor(screen: PlayScreen, x: Float, y: Float) {
        this.world = screen.world
        this.map = screen.map
        setPosition(x, y)
        defineEnemy()
    }

    abstract fun defineEnemy()

}