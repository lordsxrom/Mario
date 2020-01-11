package com.relaxgames.raicing.Tools

import com.badlogic.gdx.physics.box2d.*
import com.relaxgames.raicing.Sprites.InteractiveTileObject

class WorldContactListener : ContactListener {
    override fun endContact(contact: Contact?) {

    }

    override fun beginContact(contact: Contact) {
        val fixA = contact.fixtureA
        val fixB = contact.fixtureB

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
    }

    override fun preSolve(contact: Contact?, oldManifold: Manifold?) {

    }

    override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {

    }
}