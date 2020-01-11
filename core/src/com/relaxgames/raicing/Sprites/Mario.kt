package com.relaxgames.raicing.Sprites

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.Array
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Screens.PlayScreen


class Mario : Sprite {

    enum class State { FALLING, JUMPING, STANDING, RUNNING }

    var currentState: State
    var previousState: State

    var world: World
    lateinit var b2body: Body
    private var marioStand: TextureRegion
    private var marioRun: Animation<TextureRegion>
    private var marioJump: Animation<TextureRegion>

    private var stateTimer: Float
    private var runningRight: Boolean

    constructor(world: World, screen: PlayScreen) : super(screen.atlas.findRegion("little_mario")) {
        this.world = world
        currentState = State.STANDING
        previousState = State.STANDING
        stateTimer = 0f
        runningRight = true

        val frames = Array<TextureRegion>()
        for (i in 1..3) {
            frames.add(TextureRegion(texture, i * 16, 11, 16, 16))
        }
        marioRun = Animation(0.1f, frames)
        frames.clear()

        for (i in 4..5) {
            frames.add(TextureRegion(texture, i * 16, 11, 16, 16))
        }
        marioJump = Animation(0.1f, frames)
        frames.clear()

        marioStand = TextureRegion(texture, 1, 11, 16, 16)

        defineMario()

        setBounds(0f, 0f, 16 / MyGdxGame.PPM, 16f / MyGdxGame.PPM)
        setRegion(marioStand)
    }

    fun update(delta: Float) {
        setPosition(b2body.position.x - width / 2, b2body.position.y - height / 2)
        setRegion(getFrame(delta))
    }

    private fun getFrame(delta: Float): TextureRegion? {
        currentState = getState()

        val region: TextureRegion = when (currentState) {
            State.JUMPING -> marioJump.getKeyFrame(stateTimer)
            State.RUNNING -> marioRun.getKeyFrame(stateTimer, true)
            State.FALLING, State.STANDING -> marioStand
        }

        // if mario is running left and the texture isnt facing left... flip it.
        if ((b2body.linearVelocity.x < 0 || !runningRight) && !region.isFlipX) {
            region.flip(true, false)
            runningRight = false
        }

        // if mario is running right and the texture isnt facing right... flip it.
        else if ((b2body.linearVelocity.x > 0 || runningRight) && region.isFlipX) {
            region.flip(true, false)
            runningRight = true
        }

        // if the current state is the same as the previous state increase the state timer.
        // otherwise the state has changed and we need to reset timer.
        stateTimer = if (currentState == previousState) (stateTimer + delta) else 0f
        // update previous state
        previousState = currentState
        // return our final adjusted frame
        return region
    }

    private fun getState(): State {
        return when {
            b2body.linearVelocity.y > 0f || (b2body.linearVelocity.y < 0f && previousState == State.JUMPING) -> State.JUMPING
            b2body.linearVelocity.y < 0f -> State.FALLING
            b2body.linearVelocity.x != 0f -> State.RUNNING
            else -> State.STANDING
        }
    }

    private fun defineMario() {
        val bdef = BodyDef()
        bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM)
        bdef.type = BodyDef.BodyType.DynamicBody
        b2body = world.createBody(bdef)

        val fdef = FixtureDef()
        val shape = CircleShape()
        shape.radius = 6 / MyGdxGame.PPM

        fdef.shape = shape
        b2body.createFixture(fdef)

        val head = EdgeShape()
        head.set(Vector2(-2 / MyGdxGame.PPM, 6 / MyGdxGame.PPM), Vector2(2 / MyGdxGame.PPM, 6 / MyGdxGame.PPM))
        fdef.shape = head
        fdef.isSensor = true

        b2body.createFixture(fdef).userData = "head"
    }

}