/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audio_test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileFilter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author s1407003
 */
public class Audio_Test extends JFrame {

    private JPanel jp_audio;

    private JComboBox jc_audio;
    private JComboBox jc_effect;

    private JButton btn_player;
    private JButton btn_stop;
    private JButton btn_loop;
    private JButton btn_play_list;
    private JButton sound_effect;

    private JLabel bgm_volumn;
    private JLabel se_volumn;

    private String[] file_list;
    private String[] effect_list;

    private JSlider bgm_slider;
    private JSlider se_slider;

    //private boolean loop = false;
    private Audio current_thread = null;

    public Audio_Test() {
        this.setTitle("Audio System");
        this.getContentPane().setPreferredSize(new Dimension(300, 300));
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //this.getContentPane().setLayout(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    public void initComponents() {
        AudioSystem.init();
        this.initJpAudio();
        this.getContentPane().add(jp_audio);
        this.setVisible(true);
    }

    public void initJpAudio() {
        jp_audio = new JPanel();
        jp_audio.setLayout(new FlowLayout());
        this.initFileList();
        jc_audio = new JComboBox(file_list);

        btn_player = new JButton("Player");
        btn_player.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (AudioSystem.at != null) {
                    AudioSystem.stopBgm();
                }
//                System.out.println("data\\music\\" + jc_audio.getSelectedItem());
                AudioSystem.playBgm("data\\music\\" + jc_audio.getSelectedItem(), false);
                // System.out.println(current_thread.toString());

            }
        });
        btn_stop = new JButton("Stop");
        btn_stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AudioSystem.stopBgm();
            }

        });
        btn_loop = new JButton("Loop");
        btn_loop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (AudioSystem.at != null) {
                    AudioSystem.stopBgm();
                }
                AudioSystem.playBgm("data\\music\\" + jc_audio.getSelectedItem(), true);
            }
        });

        btn_play_list = new JButton("Player_List");
        btn_play_list.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (AudioSystem.at != null) {
                    AudioSystem.stopBgm();
                }
                AudioSystem.playBgm(file_list, 4);
            }
        });

        bgm_volumn = new JLabel("音乐音量控制：");

        bgm_slider = new JSlider(0, 100);
        bgm_slider.setMaximum(100);
        bgm_slider.setMinimum(0);
        bgm_slider.setValueIsAdjusting(true);
        bgm_slider.setPaintTicks(true);
        bgm_slider.setPaintTrack(true);
        bgm_slider.setPaintLabels(true);
        bgm_slider.setValue(AudioSystem.getBgmVolumn());
        bgm_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
//               System.out.println(bgm_slider.getValue());
                AudioSystem.setBgmVolumn(((JSlider) e.getSource()).getValue());
            }

        });
//        bgm_slider.setPaintTicks(true);
//        bgm_slider.setPaintLabels(true);
//        bgm_slider.setSnapToTicks(true);
//        bgm_slider.setValue(20);
//        bgm_slider.setExtent(50);
//        bgm_slider.setMajorTickSpacing(10);
//        bgm_slider.setMinorTickSpacing(5);

        this.initEffectList();
        jc_effect = new JComboBox(effect_list);
        jc_effect.setPreferredSize(new Dimension(140, 20));

        sound_effect = new JButton("Sound Effect");
        sound_effect.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AudioSystem.playSe("data\\sound_effect\\" + jc_effect.getSelectedItem());
                // System.out.println(current_thread.toString());
            }
        });

        se_volumn = new JLabel("音效音量控制：");

        se_slider = new JSlider(0, 100);
        se_slider.setMaximum(100);
        se_slider.setMinimum(0);
        se_slider.setValueIsAdjusting(true);
        se_slider.setPaintTicks(true);
        se_slider.setPaintTrack(true);
        se_slider.setPaintLabels(true);
        se_slider.setValue(AudioSystem.getSeVolumn());
        se_slider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
//               System.out.println(se_slider.getValue());
                AudioSystem.setSeVolumn(((JSlider) e.getSource()).getValue());
            }

        });

        jp_audio.add(jc_audio);
        jp_audio.add(btn_player);
        jp_audio.add(btn_stop);
        jp_audio.add(btn_loop);
        jp_audio.add(btn_play_list);
        jp_audio.add(bgm_volumn);
        jp_audio.add(bgm_slider);
        jp_audio.add(jc_effect);
        jp_audio.add(sound_effect);
        jp_audio.add(se_volumn);
        jp_audio.add(se_slider);

    }

    public void initFileList() {
        File file = new File("data\\music");
        File[] file_lists = file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return false;
                } else {
                    return pathname.isFile() && pathname.getName().endsWith(".wav");
                }
            }

        });
        file_list = new String[file_lists.length];
        for (int i = 0; i < file_lists.length; i++) {
            file_list[i] = file_lists[i].getName();
        }
    }

    public void initEffectList() {
        File file = new File("data\\sound_effect");
        File[] file_lists = file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return false;
                } else {
                    return pathname.isFile() && pathname.getName().endsWith(".wav");
                }
            }

        });
        effect_list = new String[file_lists.length];
        for (int i = 0; i < file_lists.length; i++) {
            effect_list[i] = file_lists[i].getName();
        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//                    Logger.getLogger(Segment_UI.class.getName()).log(Level.SEVERE, null, ex);
                }

                Audio_Test at = new Audio_Test();
                at.initComponents();
            }
        });
        // TODO code application logic here
        //System.out.println(Audio_Test.class.getResource("music/111.wav").toString());;
    }

}
