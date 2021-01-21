package main.ru.itis.javalab;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask implements Runnable {
    private String url;
    private String folder;

    public DownloadTask(String url, String folder) {
        this.url = url;
        this.folder = folder;
    }

    @Override
    public void run() {
        try {
            downloadImage();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void downloadImage() throws IOException {
        BufferedImage image = ImageIO.read(new URL(url));
        String imageName = url.split("/")[url.split("/").length - 1];

        if (imageName.length() > 7) {
            imageName = imageName.substring(imageName.length() - 10);
        }

        File file = new File(folder + "/" + imageName);
        ImageIO.write(image,"jpg" ,file);
    }
}
