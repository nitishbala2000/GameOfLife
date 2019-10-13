package main_package;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLifeFrame extends JFrame {

    private JPanel controlPanel;
    private GamePanel gamePanel;
    private JPanel worldPanel;


    private Timer timer;
    private boolean playing;

    private World currentWorld;

    public GameOfLifeFrame() throws IOException {
        super("Game of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024,768);

        this.controlPanel = createControlPanel();
        this.add(controlPanel, BorderLayout.SOUTH);

        this.worldPanel = createWorldPanel();
        this.add(worldPanel, BorderLayout.WEST);

        this.gamePanel = createGamePanel();
        this.add(gamePanel,BorderLayout.CENTER);

        this.playing = false;

    }

    public static void main(String[] args) throws IOException {
        GameOfLifeFrame gui = new GameOfLifeFrame();
        gui.setVisible(true);

    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }

    private JPanel createControlPanel() {
        JPanel ctrl =  new JPanel();
        ctrl.setLayout(new GridLayout(1, 3));
        addBorder(ctrl,"Controls");

        JButton backButton = new JButton("Move back");


        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWorld.moveBackGeneration();
                gamePanel.display(currentWorld);
            }
        });
        ctrl.add(backButton);


        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (playing) {
                    timer.cancel();
                    playing=false;
                    playButton.setText("Play");
                }
                else {
                    playing = true;
                    playButton.setText("Stop");
                    timer = new Timer(true);
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            currentWorld.advanceGeneration();
                            gamePanel.display(currentWorld);
                        }
                    }, 0, 500);
                }
            }
        });
        ctrl.add(playButton);

        JButton forwardButton = new JButton("Move forwards");
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentWorld.advanceGeneration();
                gamePanel.display(currentWorld);
            }
        });
        ctrl.add(forwardButton);



        return ctrl;
    }

    private JPanel createWorldPanel() throws IOException {
        JPanel panel = new JPanel();
        addBorder(panel,"World");
        panel.setLayout(new GridLayout(1, 1));

        DefaultListModel<World> listModel = new DefaultListModel<>();

        BufferedReader br = new BufferedReader(new FileReader("formats.txt"));
        String line = "";
        while ((line = br.readLine()) != null) {
            World world = new World(line);
            listModel.addElement(world);
        }

        JList<World> worldJList = new JList<>(listModel);
        worldJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                World w = worldJList.getSelectedValue();
                currentWorld = w;
                gamePanel.display(w);
            }
        });

        JScrollPane sp = new JScrollPane(worldJList);

        panel.add(sp);
        return panel;
    }

    private GamePanel createGamePanel() {
        GamePanel g = new GamePanel();
        addBorder(g,"Game Panel");
        return g;
    }

}
