package com.xuan.utils.get100Image;

import lombok.Data;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author XUAN
 * @date 2021/3/9 - 14:45
 * @references
 * @purpose
 * @errors
 */
@Data
public class GetImageFromVideo {

    FFmpegFrameGrabber fFmpegFrameGrabber;
    File video;
    File videoDirectory;
    File imageDirectory;

    public GetImageFromVideo(File video){
        setfFmpegFrameGrabber(video.getAbsolutePath());
        setVideo(video);
        setVideoDirectory(video.getParentFile());
        setImageDirectory("PreviewImages");
    }

    /**
     * 时间间隔平均地获取 frameNumber 帧
     * @param frameNumber
     */
    public void getAvgFrames(int frameNumber) {
        try {
            fFmpegFrameGrabber.start();
            Map<Integer,Integer> integerMap = AverageAssign.arrayAssign(fFmpegFrameGrabber.getLengthInFrames(),frameNumber);

            List<Integer> sorted = new ArrayList<>(integerMap.keySet());
            Collections.sort(sorted);
            int i=1;
            for (int k:sorted) {
                fFmpegFrameGrabber.setFrameNumber(k);
                Frame frame=fFmpegFrameGrabber.grabImage();
                if (frame!=null){
                    Java2DFrameConverter converter = new Java2DFrameConverter();
                    BufferedImage bufferedImage = converter.getBufferedImage(frame);
                    //文件绝对路径+名字
                    File imageOutputPath =new File("PreviewImages"+ File.separator+ i + ".jpg");
                    System.out.println(imageOutputPath.getName());
                    ImageIO.write(bufferedImage, "jpg", imageOutputPath);
                    i++;
                }
            }
            fFmpegFrameGrabber.stop();
            fFmpegFrameGrabber.close();
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setfFmpegFrameGrabber(String videoPath) {
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath);
        this.fFmpegFrameGrabber = fFmpegFrameGrabber;
    }

    public void setImageDirectory(String imageDirectory) {
        File imageDir = new File(imageDirectory);
        if (!imageDir.exists()){
            imageDir.mkdir();
        }
        this.imageDirectory = imageDir;
    }
}
