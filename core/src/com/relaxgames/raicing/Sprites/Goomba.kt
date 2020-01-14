package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.utils.Array
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Screens.PlayScreen
import kotlin.experimental.or

class Goomba : Enemy {

    private var stateTime: Float
    private val walkAnimation: Animation<TextureRegion>
    private val frames: Array<TextureRegion>

    constructor(screen: PlayScreen, x: Float, y: Float) : super(screen, x, y) {
        frames = Array()
        for (i in 0..1) {
            frames.add(TextureRegion(screen.atlas.findRegion("goomba"), i * 16, 0, 16, 16))
        }
        walkAnimation = Animation(0.4f, frames)
        stateTime = 0f
        setBounds(x, y, 16 / MyGdxGame.PPM, 16 / MyGdxGame.PPM)
    }

    fun update(delta: Float) {
        stateTime += delta
        setPosition(b2body.position.x - width / 2, b2body.position.y - height / 2)
        setRegion(walkAnimation.getKeyFrame(stateTime, true))
    }

    override fun defineEnemy() {
        val bdef = BodyDef()
        bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM)
        bdef.type = BodyDef.BodyType.DynamicBody
        b2body = world.createBody(bdef)

        val fdef = FixtureDef()
        val shape = CircleShape()
        shape.radius = 6 / MyGdxGame.PPM
        fdef.filter.categoryBits = MyGdxGame.ENEMY_BIT
        fdef.filter.maskBits = MyGdxGame.GROUND_BIT or MyGdxGame.COIN_BIT or
                MyGdxGame.BRICK_BIT or MyGdxGame.ENEMY_BIT or MyGdxGame.OBJECT_BIT or MyGdxGame.MARIO_BIT

        fdef.shape = shape
        b2body.createFixture(fdef)
    }

}