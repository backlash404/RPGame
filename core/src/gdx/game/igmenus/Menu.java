package gdx.game.igmenus;

import gdx.game.screen.Play;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Menu extends Actor
{

    private Texture texture;
    private NinePatch patch;
    protected TextureRegion selector;

    protected BitmapFont font;

    private boolean visible = false;

    private int key;

    protected boolean nextCycle;

    public Menu(int x, int y, int width, int height, int key)
    {
        texture = new Texture(
                Gdx.files.internal("Textures/menu9.png"));
        TextureRegion menu = TextureRegion.split(texture, 64, 64)[0][0];
        selector = TextureRegion.split(texture, 32, 32)[2][1];

        patch = new NinePatch(menu, 12, 12, 12, 12);
        font = new BitmapFont();
        font.getData().setScale(2);

        setBounds(x, y, width, height);
        this.key = key;
        nextCycle = false;
    }

    public void update(Entity e1, Entity e2)
    {
    }

    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        if (visible)
        {
            patch.draw(batch, getX(), getY(), getWidth(), getHeight());
            handleInputs();
            draw(batch);
        }
    }

    public void toggleVisible()
    {
        if ((key != -1 && Gdx.input.isKeyJustPressed(key))
                || (nextCycle))
        {
            forceToggle();
        }
    }

    public void forceToggle()
    {
        visible = !visible;
        Play.togglePause();
        nextCycle = false;
    }

    protected abstract void draw(Batch batch);

    protected abstract void handleInputs();

    protected void drawString(Batch batch, String str, int x, int y,
            float alpha)
    {
        BitmapFontCache cache = font.getCache();
        cache.clear();
        cache.addText(str, getX() + x, getY() + font.getLineHeight()
                + y);

        // This is the useful bit!
        cache.setAlphas(alpha);

        cache.draw(batch);
    }

    public void dispose()
    {
        font.dispose();
        texture.dispose();
    }
}
