package gdd;

import gdd.scene.Scene2;
import gdd.scene.TitleScene;
import javax.swing.JFrame;

public class Game extends JFrame  {

    TitleScene titleScene;
    Scene2 scene2;

    public Game() {
        titleScene = new TitleScene(this);
        scene2 = new Scene2(this);
        initUI();
        loadTitle();
    }

    private void initUI() {

        setTitle("Space Invaders");
        setSize(Global.BOARD_WIDTH, Global.BOARD_HEIGHT);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

    }

    public void loadTitle() {
        add(titleScene);
        titleScene.start();
        revalidate();
        repaint();
    }

    public void loadScene1() {
        // ....
    }

    public void loadScene2() {
        getContentPane().removeAll();
        add(scene2);
        titleScene.stop();
        scene2.start();
        revalidate();
        repaint();
    }
}