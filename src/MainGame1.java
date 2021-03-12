
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

// czp
public class MainGame1 implements ApplicationListener {
    private Viewport viewport;
    private Camera camera;

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
    //private Sprite spriteGroup1;
    //private Sprite spriteGroup2;




    private static final String TAG = MainGame1.class.getSimpleName();

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

    // 按钮
    private Button buttonStart;
    private Button buttonLoad;
    private Button buttonOption;
    private Button buttonExit;


    private float timer;


    @Override
    public void create() {


        camera = new PerspectiveCamera();
        viewport = new FitViewport(800, 480, camera);

        // 创建纹理画布
        batch = new SpriteBatch();

        texture = new Texture("background.jpg");
        Group1 = new Texture("Group1.png");
        Group2 = new Texture("Group2.png");

        //添加怪兽
        master = new Texture("master.png");
        // 使用纹理创建精灵, 精灵宽高为该纹理的宽高
        spriteMaster = new Sprite(master);


       // spriteGroup1 = new Sprite(Group1);
       // spriteGroup2 = new Sprite(Group2);

        // 设置精灵的位置（左下角绘制起点）   实验怪兽从框外出现
        spriteMaster.setPosition(0, 0);

       // spriteGroup1.setPosition(830,1200);
       // spriteGroup2.setPosition(830,1200);
       // spriteGroup1.setSize(800, 350);
      //  spriteGroup2.setSize(800, 350);
        spriteMaster.setSize(800, 800);



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
        upTextureStart = new Texture(Gdx.files.internal("start_1.png"));
        downTextureStart = new Texture(Gdx.files.internal("start.png"));

        upTextureLoad = new Texture(Gdx.files.internal("load_1.png"));
        downTextureLoad = new Texture(Gdx.files.internal("load.png"));

        upTextureOption = new Texture(Gdx.files.internal("option_1.png"));
        downTextureOption = new Texture(Gdx.files.internal("option.png"));

        upTextureExit = new Texture(Gdx.files.internal("exit_1.png"));
        downTextureExit = new Texture(Gdx.files.internal("exit.png"));

        /*
         * 第 2 步: 创建 ButtonStyle
         */
        Button.ButtonStyle styleStart = new Button.ButtonStyle();
        Button.ButtonStyle styleLoad = new Button.ButtonStyle();
        Button.ButtonStyle styleOption = new Button.ButtonStyle();
        Button.ButtonStyle styleExit = new Button.ButtonStyle();

        // 设置 style 的 弹起 和 按下 状态的纹理区域
        styleStart.up = new TextureRegionDrawable(new TextureRegion(upTextureStart));
        styleStart.down = new TextureRegionDrawable(new TextureRegion(downTextureStart));

        styleLoad.up = new TextureRegionDrawable(new TextureRegion(upTextureLoad));
        styleLoad.down = new TextureRegionDrawable(new TextureRegion(downTextureLoad));

        styleOption.up = new TextureRegionDrawable(new TextureRegion(upTextureOption));
        styleOption.down = new TextureRegionDrawable(new TextureRegion(downTextureOption));

        styleExit.up = new TextureRegionDrawable(new TextureRegion(upTextureExit));
        styleExit.down = new TextureRegionDrawable(new TextureRegion(downTextureExit));

        /*
         * 第 3 步: 创建 Button
         */
        buttonStart = new Button(styleStart);
        buttonLoad = new Button(styleLoad);
        buttonOption = new Button(styleOption);
        buttonExit = new Button(styleExit);

        GroupImage1 = new Image(Group1);
        GroupImage2 = new Image(Group2);


        GroupImage1.setPosition(400,600);
        GroupImage1.setSize(1200,400);
        GroupImage2.setPosition(410,600);
        GroupImage2.setSize(1200,400);


        // 设置按钮的位置
        buttonStart.setPosition(830, 445);
        buttonLoad.setPosition(830, 328);
        buttonOption.setPosition(830, 211);
        buttonExit.setPosition(830, 94);

        buttonStart.setSize(316,119);
        buttonLoad.setSize(316,119);
        buttonOption.setSize(316,119);
        buttonExit.setSize(316,119);


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




    }

    @Override
    public void resize(int width, int height) {
       //viewport.update(width, height);

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
        // 清屏
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 0);



        /* 使用画笔将纹理绘制在屏幕上 */

        batch.begin();				// 绘制开始
        batch.draw(texture, 0, 0);	// 在屏幕左下角绘制纹理
        /*
         * 第 4 步: 添加动画
         */


        /*
         * 开始界面按钮设置完毕
         */

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

           // timer = 0;
            spriteMaster.setPosition(1350, 170);
            spriteMaster.setSize(500,500);
            spriteMaster.setScale(1, 1);
            spriteMaster.setRotation(0);

            /*
             * 第 5 步: 添加 button 到舞台
             */


            /*
             * 开始界面按钮设置完毕
             */


            stage.addActor(buttonStart);
            stage.addActor(buttonLoad);
            stage.addActor(buttonOption);
            stage.addActor(buttonExit);

           // GroupImage1.setColor(Color.CLEAR);
            stage.addActor(GroupImage2);
          //  GroupImage2.setColor(Color.CLEAR);
            stage.addActor(GroupImage1);

        }



        //batch.begin();


        //spriteMaster.translate(20 , 20 );
        //spriteMaster.
        //spriteGroup1.draw(batch);
        spriteMaster.draw(batch);


        batch.end();				// 绘制结束

        // 更新舞台逻辑


        stage.act();
        // 绘制舞台
        stage.draw();


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

    }



}
