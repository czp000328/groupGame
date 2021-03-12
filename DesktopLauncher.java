package GroupProject;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;

import java.awt.*;

public class DesktopLauncher {

    public static void main(String[] args) {


        // 应用配置
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        //config.fullscreen=true;
        config.width = 1920;			// 指定窗口宽度
        config.height = 1080;		// 指定窗口高度

        config.resizable = false;	// 窗口设置为大小可改变
       // config.initialBackgroundColor = Color.BLUE;


        config.backgroundFPS=60;


        // 创建游戏主程序启动入口类 MainGame 对象, 传入配置 config, 启动游戏程序
        new LwjglApplication(new MainGame(), config);


    }
}
