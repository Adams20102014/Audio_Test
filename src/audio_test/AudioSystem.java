package audio_test;

import java.util.Random;

/**
 *
 * @author
 */
public class AudioSystem {

    public static Audio at = null;
    public static Audio sound_effect = null;
    public static String[] audio_list;

    private static int bgm_volumn;
    private static int se_volumn;

    private AudioSystem() {

    }

    public static void init() {
        bgm_volumn = 80;
        se_volumn = 60;
//        AudioFormat bgm_format = new AudioFormat(PCM_SIGNED, (float) 44100.0, 16, 2, 4, (float) 44100.0, false);
//        DataLine.Info bgm_info = new DataLine.Info(SourceDataLine.class, bgm_format);
//
//        try {
//            auline = (SourceDataLine) javax.sound.sampled.AudioSystem.getLine(bgm_info);
//            auline.open();
//        } catch (LineUnavailableException ex) {
//        }
//        auline.start();
    }

    //one at a time. once starting a new bgm, stop the playing one
    public static void playBgm(String filename, boolean loop) {
        at = new Audio("data\\music\\111.wav", loop);
        at.initAudioVolumn(new AudioListenerVolumn() {

            @Override
            public void initMusicVolumn() {
                at.changeVolumn(bgm_volumn);
            }

        });
        at.start();
    }

    public static void playBgm(String[] file_list, int start_index) {
        audio_list = file_list;
        at = new Audio("data\\music\\" + file_list[start_index], false);
        at.initAudioVolumn(new AudioListenerVolumn() {

            @Override
            public void initMusicVolumn() {
                at.changeVolumn(bgm_volumn);
            }

        });
        at.addAudioListener(listener);
        at.start();
    }

    public static void stopBgm() {
        at.Stop();
    }

    //can play multiple SE at a time
    public static void playSe(String filename) {
        boolean loop = false;
        sound_effect = new Audio(filename, loop);
        sound_effect.initAudioVolumn(new AudioListenerVolumn() {

            @Override
            public void initMusicVolumn() {
                sound_effect.changeVolumn(se_volumn);
            }

        });
        sound_effect.start();
    }

    //0~100
    public static void setBgmVolumn(int volumn) {
        at.changeVolumn(volumn);
        bgm_volumn = volumn;
//        format = new AudioFormat("PCM_SIGNED 44100.0 Hz", "16 bit", stereo, 4 bytes/frame, little-endian);
    }

    public static int getBgmVolumn() {
        return bgm_volumn;
//        format = new AudioFormat("PCM_SIGNED 44100.0 Hz", "16 bit", stereo, 4 bytes/frame, little-endian);
    }

    //0~100
    public static void setSeVolumn(int volumn) {
        sound_effect.changeVolumn(volumn);
        se_volumn = volumn;
    }

    public static int getSeVolumn() {
        return se_volumn;
    }

    static AudioListener listener = new AudioListener() {

        @Override
        public void nextMusic() {
            Random rs = new Random(System.currentTimeMillis());
            int index = rs.nextInt(audio_list.length);
//            System.out.println(index);
//            System.out.println("data\\music\\" + audio_list[index]);
            at = new Audio("data\\music\\" + audio_list[index], false);
            at.initAudioVolumn(new AudioListenerVolumn() {

                @Override
                public void initMusicVolumn() {
                    at.changeVolumn(bgm_volumn);
                }

            });
            at.addAudioListener(listener);
            at.start();
        }

    };
}