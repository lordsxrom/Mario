package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.*
import com.relaxgames.raicing.MyGdxGame

abstract class InteractiveTileObject {

    protected var world: World
    protected var map: TiledMap
    //    protected var tile:TiledMapTile
    protected var bounds: Rectangle
    protected var body: Body

    protected var fixtue: Fixture

    constructor(world: World, map: TiledMap, bounds: Rectangle) {
        this.world = world
        this.map = map
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

}