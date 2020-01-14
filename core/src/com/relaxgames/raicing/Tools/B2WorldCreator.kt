package com.relaxgames.raicing.Tools

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.*
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Screens.PlayScreen
import com.relaxgames.raicing.Sprites.Brick
import com.relaxgames.raicing.Sprites.Coin

class B2WorldCreator {

    constructor(screen: PlayScreen) {
        val world = screen.world
        val map = screen.map

        val bDef = BodyDef()
        val shape = PolygonShape()
        val fdef = FixtureDef()
        var body: Body

        // create ground bodies/fixture
        for (obj: MapObject in map.layers.get(2).objects.getByType(RectangleMapObject::class.java)) {
            val rect: Rectangle? = (obj as RectangleMapObject).rectangle
            bDef.type = BodyDef.BodyType.StaticBody
            if (rect != null) {
                bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM)
                body = world.createBody(bDef)
                shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM)
                fdef.shape = shape
                body.createFixture(fdef)
            }
        }

        // create pipe bodies/fixture
        for (obj: MapObject in map.layers.get(3).objects.getByType(RectangleMapObject::class.java)) {
            var rect: Rectangle? = (obj as RectangleMapObject).rectangle
            bDef.type = BodyDef.BodyType.StaticBody
            if (rect != null) {
                bDef.position.set((rect.getX() + rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGdxGame.PPM)
                body = world.createBody(bDef)
                shape.setAsBox((rect.getWidth() / 2) / MyGdxGame.PPM, (rect.getHeight() / 2) / MyGdxGame.PPM)
                fdef.shape = shape
                fdef.filter.categoryBits = MyGdxGame.OBJECT_BIT
                body.createFixture(fdef)
            }
        }

        // create brick bodies/fixture
        for (obj: MapObject in map.layers.get(5).objects.getByType(RectangleMapObject::class.java)) {
            val rect: Rectangle = (obj as RectangleMapObject).rectangle
            Brick(screen, rect)
        }

        // create coin bodies/fixture
        for (obj: MapObject in map.layers.get(4).objects.getByType(RectangleMapObject::class.java)) {
            val rect: Rectangle = (obj as RectangleMapObject).rectangle
            Coin(screen, rect)
        }
    }

}