package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.utils.Array
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Screens.PlayScreen
import kotlin.experimental.or

class Goomba : Enemy {

    private var stateTime: Float
    private val walkAnimation: Animation<TextureRegion>
    private val frames: Array<TextureRegion>
    private var setToDestroy: Boolean
    private var destroyed: Boolean

    constructor(screen: PlayScreen, x: Float, y: Float) : super(screen, x, y) {
        frames = Array()
        for (i in 0..1) {
            frames.add(TextureRegion(screen.atlas.findRegion("goomba"), i * 16, 0, 16, 16))
        }
        walkAnimation = Animation(0.4f, frames)
        stateTime = 0f
        setBounds(x, y, 16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM)
        setToDestroy = false
        destroyed = false
    }

    fun update(delta: Float) {
        stateTime += delta

        if (setToDestroy && !destroyed) {
            world.destroyBody(b2body)
            destroyed = true
            setRegion(TextureRegion(screen.atlas.findRegion("goomba"), 32, 0, 16, 16))
            stateTime = 0f
        } else if (!destroyed) {
            b2body.linearVelocity = velocity
            setPosition(b2body.position.x - width / 2, b2body.position.y - height / 2)
            setRegion(walkAnimation.getKeyFrame(stateTime, true))
        }
    }

    override fun defineEnemy() {
        val bdef = BodyDef()
        bdef.position.set(100 / MyGdxGame.PPM, 32 / MyGdxGame.PPM)
        bdef.type = BodyDef.BodyType.DynamicBody
        b2body = world.createBody(bdef)

        val fdef = FixtureDef()
        val shape = CircleShape()
        shape.radius = 6 / MyGdxGame.PPM
        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT or MyGdxGame.COIN_BIT or
                MyGdxGame.BRICK_BIT or MyGdxGame.ENEMY_BIT or MyGdxGame.OBJECT_BIT or MyGdxGame.MARIO_BIT

        fdef.shape = shape
        b2body.createFixture(fdef).userData = this

        val head = PolygonShape()
        val vertice = Array(4) { Vector2() }
        vertice[0] = Vector2(-5f, 8f).scl(1 / MyGdxGame.PPM)
        vertice[1] = Vector2(5f, 8f).scl(1 / MyGdxGame.PPM)
        vertice[2] = Vector2(-3f, 3f).scl(1 / MyGdxGame.PPM)
        vertice[3] = Vector2(3f, 3f).scl(1 / MyGdxGame.PPM)
        head.set(vertice)

        fdef.shape = head
        fdef.restitution = 0.5f
        fdef.filter.categoryBits = MyGdxGame.ENEMY_HEAD_BIT
        b2body.createFixture(fdef).userData = this

    }

    override fun draw(batch: Batch) {
        if (!destroyed || stateTime < 1)
            super.draw(batch)
    }

    override fun hitOnHead() {
        setToDestroy = true
    }

}