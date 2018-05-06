package server.recognition;

import java.io.File;
import java.util.Random;

/**
 * Provides the images to be sent to the cloud API.
 */
public class GetRandomImage {

    /**
     * Retrives a random Image from a file.
     *
     * @return a {@link File} object.
     */
    public File getRandomImage() {
        File dir = new File("src/main/images");
        File[] files = dir.listFiles();
        Random rand = new Random();
        return files[rand.nextInt(files.length)];
    }
}
