package kr.devbox.adadmin.service;

import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import lombok.Getter;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class FileUploadService {
    private MinioClient minioClient ;
    @Getter
    private String imgUrl  = new String(System.getenv("IMG_URL"))  ;
    private String minioAddress;
    private String minioUrl;
    private String minioKey ;
    private String minioSecret;

    public FileUploadService() {
        this.minioAddress = new String(System.getenv("IMG_SERVER"));
        this.minioUrl = new String(System.getenv("IMG_URL"));
        this.minioKey = new String(System.getenv("IMG_SERVER_KEY"));
        this.minioSecret = new String(System.getenv("IMG_SERVER_SECRET"));

        try {
            minioClient = new MinioClient(this.minioAddress, this.minioKey, this.minioSecret);
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
    }


    public Boolean uploadFile(InputStream data, String contentsType, String filename, String bucket, Boolean resize, int width, int height) {

        try {

            int pos = filename.lastIndexOf( "." );
            String ext = filename.substring( pos + 1 );
            boolean isExist = minioClient.bucketExists(bucket);
            if (isExist) {

            } else {
                minioClient.makeBucket(bucket);
            }
            minioClient.removeObject(bucket,filename);
            InputStream uploadImageStream;

            if (resize) {

                BufferedImage image = Thumbnails.of(cropImage(ImageIO.read(data),width,height)).size(width, height).asBufferedImage();
                BufferedImage newBufferedImage = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_RGB);
                newBufferedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ImageIO.write(newBufferedImage, ext, os);
                uploadImageStream = new ByteArrayInputStream(os.toByteArray());
            } else {
                uploadImageStream = data;
            }
            minioClient.putObject(bucket,filename,uploadImageStream ,contentsType);
        } catch(Exception ex) {

            ex.printStackTrace();
            return false;
        }
        return true;

    }
    public BufferedImage cropImage(BufferedImage src, int width,int height ) {

        if( ((float) src.getWidth() / (float) src.getHeight()) > ((float)width / (float)height) ) {
            // 세로 길이  기준으로 가로 길이를 크롭

            int newWidth = ( src.getHeight() * width ) / height;
//            logger.debug("newWidth : " + String.valueOf(newWidth));
            int rectX = (src.getWidth() - newWidth) / 2 ;
//            logger.debug("rectX : " + String.valueOf(rectX));
            Rectangle rect = new Rectangle();
            rect.setBounds(rectX, 0  ,newWidth,src.getHeight());
            BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
            return dest;
        } else if ( ((float) src.getWidth() / (float) src.getHeight()) < ((float)width / (float)height)  ) {
            // 가로 길이 기준으로 세로 길이 크롭

            int newHeight = ( src.getWidth() * height ) / width;
            int rectY = (src.getHeight() - newHeight) / 2 ;
            Rectangle rect = new Rectangle();
            rect.setBounds(0, rectY  ,src.getWidth(),newHeight);
            BufferedImage dest = src.getSubimage(rect.x, rect.y, rect.width, rect.height);
            return dest;

        } else {
            // 비율 동일. 크롭 취소.
            return src;
        }



    }
}
