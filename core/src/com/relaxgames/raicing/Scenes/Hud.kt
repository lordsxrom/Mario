package com.relaxgames.raicing.Scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.relaxgames.raicing.MyGdxGame

class Hud {
    var stage: Stage
    private var viewport: Viewport

    private var worldTimer: Int
    private var timeCount: Float

    var countdownLabel: Label
    var timeLabel: Label
    var levelLabel: Label
    var worldLabel: Label
    var marioLabel: Label

    constructor(sb: SpriteBatch) {
        worldTimer = 300
        timeCount = 0f
        score = 0

        viewport = FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, OrthographicCamera())
        stage = Stage(viewport, sb)

        val table = Table()
        table.top()
        table.setFillParent(true)

        countdownLabel = Label(String.format("%03d", worldTimer), Label.LabelStyle(BitmapFont(), Color.WHITE))
//        scoreLabel = Label(String.format("%06d", score), Label.LabelStyle(BitmapFont(), Color.WHITE))
        timeLabel = Label("TIME", Label.LabelStyle(BitmapFont(), Color.WHITE))
        levelLabel = Label("1-1", Label.LabelStyle(BitmapFont(), Color.WHITE))
        worldLabel = Label("WORLD", Label.LabelStyle(BitmapFont(), Color.WHITE))
        marioLabel = Label("MARIO", Label.LabelStyle(BitmapFont(), Color.WHITE))

        table.add(marioLabel).expandX().padTop(10f)
        table.add(worldLabel).expandX().padTop(10f)
        table.add(timeLabel).expandX().padTop(10f)
        table.row()
        table.add(scoreLabel).expandX()
        table.add(levelLabel).expandX()
        table.add(countdownLabel).expandX()

        stage.addActor(table)
    }

    fun update(delta:Float){
        timeCount += delta
        if (timeCount >= 1) {
            worldTimer--
            countdownLabel.setText(String.format("%03d", worldTimer))
            timeCount = 0f
        }
    }

    companion object {
        private var score: Int = 0
        @JvmStatic var scoreLabel= Label(String.format("%06d", score), Label.LabelStyle(BitmapFont(), Color.WHITE))

        @JvmStatic fun addScore(value:Int) {
            score += value
            scoreLabel.setText(String.format("%06d", score))
        }
    }



    fun dispose() {
        stage.dispose()
    }
}