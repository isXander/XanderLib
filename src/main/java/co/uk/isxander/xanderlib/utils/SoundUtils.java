/*
 * Copyright (C) isXander [2019 - 2021]
 * This program comes with ABSOLUTELY NO WARRANTY
 * This is free software, and you are welcome to redistribute it
 * under the certain conditions that can be found here
 * https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package co.uk.isxander.xanderlib.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class SoundUtils {

    public static void playSound(InputStream inputStream, float volume, float pan) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream in = AudioSystem.getAudioInputStream(inputStream);
        playSound(in, volume, pan);
    }

    public static void playSound(File file, float volume, float pan) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream in = AudioSystem.getAudioInputStream(file);
        playSound(in, volume, pan);
    }

    public static void playSound(AudioInputStream in, float volume, float pan) throws IOException, LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        clip.open(in);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        FloatControl panControl = (FloatControl) clip.getControl(FloatControl.Type.PAN);
        panControl.setValue(pan);
        clip.start();
    }

}
