package com.relaxgames.raicing.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.relaxgames.raicing.MyGdxGame
import com.relaxgames.raicing.Scenes.Hud
import com.relaxgames.raicing.Sprites.Mario
import com.relaxgames.raicing.Tools.B2WorldCreator
import com.relaxgames.raicing.Tools.WorldContactListener

open class PlayScreen : Screen {
    private var game: MyGdxGame
    var atlas: TextureAtlas

    private var gamecam: OrthographicCamera
    private var gamePort: Viewport

    private var hud: Hud

    private var mapLoader: TmxMapLoader
    private var map: TiledMap
    private var renderer: OrthogonalTiledMapRenderer

    private var world: World
    private var b2dr: Box2DDebugRenderer

    private var player: Mario

    constructor(game: MyGdxGame) {
        atlas = TextureAtlas("Mario_and_Enemies.pack")

        this.game = game

        // create cam used to follow mario through cam world
        gamecam = OrthographicCamera()
        // create FitViewpoint to maintain virtual aspect ratio despite screen size
        gamePort = FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam)

        // create our game HUD for scores/timer/level info
        hud = Hud(game.batch)

        // load our map and setup our map renderer
        mapLoader = TmxMapLoader()
        map = mapLoader.load("level1.tmx")
        renderer = OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM)

        // initially set our gamecam to be centered correctly at the start of map
        gamecam.position.set(gamePort.worldWidth / 2, gamePort.worldHeight / 2, 0f)

        //create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow bodies to sleep
        world = World(Vector2(0f, -10f), true)
        //allows for debug lines of our box2d world
        b2dr = Box2DDebugRenderer()

        B2WorldCreator(world, map)

        //create mario in our game world
        player = Mario(world, this)

        world.setContactListener(WorldContactListener())
    }

    override fun hide() {

    }

    override fun show() {

    }

    private fun update(delta: Float) {
        // handle user input first
        handleInput(delta)

        //takes 1 step in the physics simulation(60 times per second)
        world.step(1 / 60f, 6, 2)

        player.update(delta)
        hud.update(delta)

        gamecam.position.x = player.b2body.position.x

        // update our gamecam with correct coordinates after changes
        gamecam.update()

        // tell our camera to draw only what our camera can see in our game world
        renderer.setView(gamecam)
    }

    private fun handleInput(delta: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(Vector2(0f, 4f), player.b2body.worldCenter, true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.linearVelocity.x <= 2f) {
            player.b2body.applyLinearImpulse(Vector2(0.1f, 0f), player.b2body.worldCenter, true)
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.linearVelocity.x >= -2f) {
            player.b2body.applyLinearImpulse(Vector2(-0.1f, 0f), player.b2body.worldCenter, true)
        }
    }

    override fun render(delta: Float) {
        // separate our update logic from render
        update(delta)

        // clear the game screen with black
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // render our game map
        renderer.render()

        // renderer our Box2DDebugLines
        b2dr.render(world, gamecam.combined)

        // set our batch to now draw what the Hud camera sees
        game.batch.projectionMatrix = gamecam.combined
        game.batch.begin()
        player.draw(game.batch)
        game.batch.end()

        game.batch.projectionMatrix = hud.stage.camera.combined
        hud.stage.draw()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {
        gamePort.update(width, height)
    }

    override fun dispose() {
        map.dispose()
        renderer.dispose()
        world.dispose()
        b2dr.dispose()
        hud.dispose()
    }

}