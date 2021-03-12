package GroupProject;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import javax.swing.border.TitledBorder;

// 改1

public class MainGame implements ApplicationListener {
    private float timer;

    // 纹理画布
    private SpriteBatch batch;
    // 纹理
    private Texture texture;
    private Texture master;
    private Texture Group1;
    private Texture Group2;
    private Image GroupImage1;
    private Image GroupImage2;
    // 精灵
    private Sprite spriteMaster;
    private Sprite spriteMaster2;


    // 背景音乐
    private Music music;
    // 音效
    private Sound sound;


    private static final String TAG = MainGame.class.getSimpleName();

    // 视口世界的宽高统使用 480 * 800, 并统一使用伸展视口（StretchViewport）
    public static final float WORLD_WIDTH = 1920;
    public static final float WORLD_HEIGHT = 1080;

    // 舞台
    private Stage stage;

    // 按钮 弹起 状态的纹理
    private Texture upTextureStart;
    private Texture upTextureLoad;
    private Texture upTextureOption;
    private Texture upTextureExit;

    // 按钮 按下 状态的纹理
    private Texture downTextureStart;
    private Texture downTextureLoad;
    private Texture downTextureOption;
    private Texture downTextureExit;

    private Texture musicTextureTurnOff;
    private Texture musicTextureTurnOn;

    // 按钮
    private Button buttonStart;
    private Button buttonLoad;
    private Button buttonOption;
    private Button buttonExit;
    private Button buttonMusic;


    @Override
    public void create() {
        // 创建纹理画布
        batch = new SpriteBatch();

        texture = new Texture("GroupProject/start/background.jpg");
        Group1 = new Texture("GroupProject/start/Group1.png");
        Group2 = new Texture("GroupProject/start/Group2.png");


        //添加怪兽
        master = new Texture("GroupProject/start/Morgen.png");
        // 使用纹理创建精灵, 精灵宽高为该纹理的宽高
        spriteMaster = new Sprite(master);
        spriteMaster2 = new Sprite(master);
        // 设置精灵的位置（左下角绘制起点）   实验:大型怪兽从中心出现
        spriteMaster.setPosition(0, 0);
        spriteMaster.setSize(800, 800);

        // 加载背景音乐, 创建 Music 实例
        music = Gdx.audio.newMusic(Gdx.files.internal("GroupProject/music/GG.mp3"));  // music
        // 背景音乐设置循环播放
        music.setLooping(true);
        // 设置音量, 值范围 0.0 ~ 1.0
        music.setVolume((float) 0.8);

        music.play();

        // 加载音效, 创建 Sound 实例
        sound = Gdx.audio.newSound(Gdx.files.internal("GroupProject/music/click.mp3"));



        /*
         * 按钮的设置
         */
        // 设置日志输出级别
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // 使用伸展视口（StretchViewport）创建舞台
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));

        // 将输入处理设置到舞台（必须设置, 否则点击按钮没效果）
        Gdx.input.setInputProcessor(stage);

        /*
         * 第 1 步: 创建 弹起 和 按下 两种状态的纹理
         */
        upTextureStart = new Texture(Gdx.files.internal("GroupProject/start/start_1.png"));
        downTextureStart = new Texture(Gdx.files.internal("GroupProject/start/start.png"));

        upTextureLoad = new Texture(Gdx.files.internal("GroupProject/start/load_1.png"));
        downTextureLoad = new Texture(Gdx.files.internal("GroupProject/start/load.png"));

        upTextureOption = new Texture(Gdx.files.internal("GroupProject/start/option_1.png"));
        downTextureOption = new Texture(Gdx.files.internal("GroupProject/start/option.png"));

        upTextureExit = new Texture(Gdx.files.internal("GroupProject/start/exit_1.png"));
        downTextureExit = new Texture(Gdx.files.internal("GroupProject/start/exit.png"));

        musicTextureTurnOff = new Texture(Gdx.files.internal("GroupProject/start/musicTurnOff.png"));
        musicTextureTurnOn = new Texture(Gdx.files.internal("GroupProject/start/musicTurnOn.png"));

        /*
         * 第 2 步: 创建 ButtonStyle
         */
        Button.ButtonStyle styleStart = new Button.ButtonStyle();
        Button.ButtonStyle styleLoad = new Button.ButtonStyle();
        Button.ButtonStyle styleOption = new Button.ButtonStyle();
        Button.ButtonStyle styleExit = new Button.ButtonStyle();
        Button.ButtonStyle styleMusicOn = new Button.ButtonStyle();
        Button.ButtonStyle styleMusicOff = new Button.ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
        styleStart.up = new TextureRegionDrawable(new TextureRegion(upTextureStart));
        styleStart.down = new TextureRegionDrawable(new TextureRegion(downTextureStart));

        styleLoad.up = new TextureRegionDrawable(new TextureRegion(upTextureLoad));
        styleLoad.down = new TextureRegionDrawable(new TextureRegion(downTextureLoad));

        styleOption.up = new TextureRegionDrawable(new TextureRegion(upTextureOption));
        styleOption.down = new TextureRegionDrawable(new TextureRegion(downTextureOption));

        styleExit.up = new TextureRegionDrawable(new TextureRegion(upTextureExit));
        styleExit.down = new TextureRegionDrawable(new TextureRegion(downTextureExit));

        styleMusicOn.up = new TextureRegionDrawable(new TextureRegion(musicTextureTurnOn));
        styleMusicOff.up = new TextureRegionDrawable(new TextureRegion(musicTextureTurnOff));

        /*
         * 第 3 步: 创建 Button
         */
        buttonStart = new Button(styleStart);
        buttonLoad = new Button(styleLoad);
        buttonOption = new Button(styleOption);
        buttonExit = new Button(styleExit);
        buttonMusic = new Button(styleMusicOff);

        GroupImage1 = new Image(Group1);
        GroupImage2 = new Image(Group2);
        GroupImage1.setPosition(400,600);
        GroupImage1.setSize(1200,400);
        GroupImage2.setPosition(400,600);
        GroupImage2.setSize(1200,400);

        // 设置按钮的位置
        buttonStart.setPosition(830, 445);
        buttonLoad.setPosition(830, 328);
        buttonOption.setPosition(830, 211);
        buttonExit.setPosition(830, 94);
        buttonMusic.setPosition(1800,80);

        buttonStart.setSize(316,119);
        buttonLoad.setSize(316,119);
        buttonOption.setSize(316,119);
        buttonExit.setSize(316,119);
        buttonMusic.setSize(70,70);

        // 给按钮添加点击监听器
        buttonStart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "Game starts");
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log(TAG, "exit");
                Gdx.app.exit();
            }
        });

        buttonMusic.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buttonMusic.getStyle() == styleMusicOff) {
                    music.pause();
                    buttonMusic.setStyle(styleMusicOn);

                }
                else if(buttonMusic.getStyle() == styleMusicOn) {
                    music.play();
                    buttonMusic.setStyle(styleMusicOff);
                }

            }
        });





    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void render() {


        /*
         * 设置清屏颜色为红色（RGBA）,
         *
         * LibGDX 中使用 4 个浮点类型变量（值范围 0.0 ~ 1.0）表示一个颜色（分别表示颜色的 RGBA 四个通道）,
         *
         * 十六进制颜色与浮点颜色之间的转换: 将十六进制颜色的每一个分量除以 255 得到的浮点数就是浮点颜色对应的通道值。
         */
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 0);

        /* 使用画笔将纹理绘制在屏幕上 */

        batch.begin();				// 绘制开始
        batch.draw(texture, 0, 0);	// 在屏幕左下角绘制纹理

        timer += Gdx.graphics.getDeltaTime();
        // 1.移动
        if (timer < 2)
        {
            spriteMaster.translateX(70);


        }

        // 2.翻转
        else if (timer > 2 && timer < 4)
        {
            //每秒旋转90度
            spriteMaster.setPosition(840,445);
            spriteMaster.setScale(1, 1);
            spriteMaster.rotate(90  * Gdx.graphics.getDeltaTime()*10);

        }
        // 3.放缩
        else if (timer > 4 && timer < 6)
        {

            //每秒xy方向上放大1倍
            spriteMaster.setPosition(800 / 2, 480 / 2);
            spriteMaster.scale(1 * Gdx.graphics.getDeltaTime()*4);

        }


        else
        {
           // System.out.println(timer);

            // timer = 0;
            spriteMaster.setPosition(1350, 170);
            spriteMaster.setSize(500,500);
            spriteMaster.setScale(1, 1);
            spriteMaster.setRotation(0);

            if (Gdx.input.justTouched()) {
                // 点击屏幕一次, 播放音效一次
                sound.play();
            }

            stage.addActor(buttonStart);
            stage.addActor(buttonLoad);
            stage.addActor(buttonOption);
            stage.addActor(buttonExit);
            stage.addActor(buttonMusic);

            if (timer%2>1) {
                System.out.println(timer);
                GroupImage2.setColor(Color.YELLOW);
                GroupImage1.setColor(Color.CLEAR);
            }
            if (timer%2<1) {
               // System.out.println(timer);
                GroupImage2.setColor(Color.CLEAR);
                GroupImage1.setColor(Color.YELLOW);
            }

            //title title = new title();
            //title.start();
            stage.addActor(GroupImage2);
            //  GroupImage2.setColor(Color.CLEAR);
            stage.addActor(GroupImage1);



        }
        spriteMaster.draw(batch);


        batch.end();				// 绘制结束

        // 更新舞台逻辑
        stage.act();
        // 绘制舞台
        stage.draw();

        //音效设置


    }

    @Override
    public void dispose() {

        batch.dispose();
        texture.dispose();

        /*
         *  按钮开始
         */
        // 应用退出时释放资源
        if (upTextureStart != null) {
            upTextureStart.dispose();
        }
        if (downTextureStart != null) {
            downTextureStart.dispose();
        }

        if (upTextureLoad != null) {
            upTextureLoad.dispose();
        }
        if (downTextureLoad != null) {
            downTextureLoad.dispose();
        }

        if (upTextureOption != null) {
            upTextureOption.dispose();
        }
        if (downTextureOption != null) {
            downTextureOption.dispose();
        }

        if (upTextureExit != null) {
            upTextureExit.dispose();
        }
        if (downTextureExit != null) {
            downTextureExit.dispose();
        }
        if (stage != null) {
            stage.dispose();
        }
        /*
         * 按钮结束
         */

        if (music != null) {
            music.dispose();
        }
        if (sound != null) {
            sound.dispose();
        }

    }
}
