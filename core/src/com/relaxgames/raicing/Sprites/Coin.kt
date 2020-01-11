package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.maps.MapObject
import com.badlogic.gdx.maps.objects.RectangleMapObject
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.relaxgames.raicing.MyGdxGame

class Coin : InteractiveTileObject {

    constructor(world: World, map:TiledMap, bounds:Rectangle) : super(world,map,bounds) {

    }

}