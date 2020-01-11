package com.relaxgames.raicing.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.relaxgames.raicing.MyGdxGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        System.setProperty("user.name","Public")
        val config = LwjglApplicationConfiguration()
        LwjglApplication(MyGdxGame(), config)
    }
}
