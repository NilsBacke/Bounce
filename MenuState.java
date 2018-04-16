package com.plushundred.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Nils on 4/14/18.
 */

public class MenuState extends State {
    private static final String title = "Bounce";

    private Texture playButton;
    private BitmapFont font;
    private int highScore;

    public MenuState(GameStateManager gsm, int mostRecentScore) {
        super(gsm);
        playButton = new Texture("play-button.png");

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(5);


        Preferences prefs = Gdx.app.getPreferences("My Preferences");
        highScore = prefs.getInteger("score", 0);
        highScore = checkHighScore(highScore, mostRecentScore);
        prefs.putInteger("score", highScore);
        prefs.flush();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(playButton,
                Gdx.graphics.getWidth() / 2 -  (playButton.getWidth() / 2),
                Gdx.graphics.getHeight() / 2 - (playButton.getHeight() / 2));
        GlyphLayout layout = new GlyphLayout(font, title);
        font.draw(sb, layout, Gdx.graphics.getWidth() / 2 - layout.width / 2, Gdx.graphics.getHeight() * 5 / 6);
        GlyphLayout bestLayout = new GlyphLayout(font, "BEST: " + highScore);
        font.draw(sb, bestLayout, Gdx.graphics.getWidth() / 2 - bestLayout.width / 2, Gdx.graphics.getHeight() / 3);
        sb.end();
    }

    @Override
    public void dispose() {
        playButton.dispose();
    }

    private int checkHighScore(int highScore, int mostRecentScore) {
        if (mostRecentScore >= highScore) {
            return mostRecentScore;
        }
        return highScore;
    }
}
