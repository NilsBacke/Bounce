package com.plushundred.game.States;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.plushundred.game.sprites.Ball;
import com.plushundred.game.sprites.Bar;

/**
 * Created by Nils on 4/14/18.
 */

public class PlayState extends State {

    private Ball ball;
    private Bar bar;
    private int score;
    private BitmapFont font;
    private GlyphLayout layout;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        final GameStateManager manager = gsm;

        ball = new Ball(Gdx.graphics.getHeight() / 2);

        bar = new Bar(Gdx.graphics.getWidth() / 2 - ball.getTexture().getWidth() / 2);
        score = 0;

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            ball.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        ball.update(dt);
        bar.update(dt, score);

        if (bar.collides(ball.getBound())) {
            score++;
            ball.jump(1000);
        }

        if (ball.getPosition().y < bar.getPosition().y) {
            gsm.set(new MenuState(gsm, score));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(ball.getTexture(), ball.getPosition().x, ball.getPosition().y);
        sb.draw(bar.getTexture(), bar.getPosition().x, bar.getPosition().y);
        layout = new GlyphLayout(font, String.valueOf(score));
        font.draw(sb, layout, Gdx.graphics.getWidth() / 2 - layout.width / 2, Gdx.graphics.getHeight() * 5 / 6);
        sb.end();
    }

    @Override
    public void dispose() {
        ball.dispose();
        bar.dispose();
    }
}
