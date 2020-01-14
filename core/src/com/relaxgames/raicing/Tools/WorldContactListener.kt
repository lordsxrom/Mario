package com.relaxgames.raicing.Tools

import com.badlogic.gdx.physics.box2d.*
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Sprites.Enemy
import com.relaxgames.raicing.Sprites.InteractiveTileObject
import kotlin.experimental.or

class WorldContactListener : ContactListener {
    override fun endContact(contact: Contact?) {

    }

    override fun beginContact(contact: Contact) {
        val fixA = contact.fixtureA
        val fixB = contact.fixtureB

        val cDef = fixA.filterData.categoryBits or fixB.filterData.categoryBits

        if ("head" == fixA.userData || "head" == fixB.userData) {
            var head: Fixture? = null
            var obj: Fixture? = null

            if ("head" == fixA.userData) {
                head = fixA
                obj = fixB
            } else if ("head" == fixB.userData) {
                head = fixB
                obj = fixA
            }

            if (obj!!.userData != null && InteractiveTileObject::class.java.isAssignableFrom(obj.userData.javaClass)) {
                (obj.userData as InteractiveTileObject).onHeadHit()
            }
        }

        when (cDef) {
            MyGdxGame.ENEMY_HEAD_BIT or MyGdxGame.MARIO_BIT ->
                if (fixA.filterData.categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    (fixA.userData as Enemy).hitOnHead()
                else if (fixB.filterData.categoryBits == MyGdxGame.ENEMY_HEAD_BIT)
                    (fixB.userData as Enemy).hitOnHead()

        }
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}