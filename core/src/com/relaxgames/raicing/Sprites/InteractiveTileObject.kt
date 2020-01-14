package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.*
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Screens.PlayScreen

abstract class InteractiveTileObject {

    protected var world: World
    protected var map: TiledMap
    //    protected var tile:TiledMapTile
    protected var bounds: Rectangle
    protected var body: Body

    protected var fixtue: Fixture

    constructor(screen: PlayScreen, bounds: Rectangle) {
        this.world = screen.world
        this.map = screen.map
        this.bounds = bounds

        val bdef = BodyDef()
        val fdef = FixtureDef()
        val shape = PolygonShape()

        bdef.type = BodyDef.BodyType.StaticBody
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGdxGame.PPM)

        body = world.createBody(bdef)

        shape.setAsBox((bounds.getWidth() / 2) / MyGdxGame.PPM, (bounds.getHeight() / 2) / MyGdxGame.PPM)
        fdef.shape = shape
        fixtue = body.createFixture(fdef)
    }

    abstract fun onHeadHit()

    fun setCategoryFilter(filterBit: Short) {
        val filter = Filter()
        filter.categoryBits = filterBit
        fixtue.filterData = filter
    }

    fun getCell(): TiledMapTileLayer.Cell {
        val layer = map.layers.get(1) as TiledMapTileLayer
        return layer.getCell((body.position.x * MyGdxGame.PPM / 16).toInt(), (body.position.y * MyGdxGame.PPM / 16).toInt())
    }

}