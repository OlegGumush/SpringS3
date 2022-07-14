package com.spring.s3.test;

import com.amazonaws.services.kms.model.NotFoundException;
import com.spring.s3.S3Service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

@SpringBootTest
class SpringS3ApplicationTests {

    @Autowired
    private S3Service s3Service;

    @Test
    public void createBucketTest() {
        s3Service.createBucket("my-oleg-special-bucket-name1");
    }

    @Test
    public void uploadString() {
        s3Service.uploadString("my-oleg-special-bucket-name1", "my-key", "SomeContent");
    }

    @Test
    public void uploadFile() {
        File f = new File("src/main/resources/myfile.txt");
        if (!f.exists()) {
            throw new NotFoundException("Cannot find file");
        }
        s3Service.uploadFile("my-oleg-special-bucket-name1", "my-key-file", f);
    }

    @Test
    public void uploadInputStream() throws FileNotFoundException {
        InputStream f = new FileInputStream("src/main/resources/myfile.txt");
        Map.of("Why", "ValueWhy");
        s3Service.uploadInputStream("my-oleg-special-bucket-name1", "my-key-file-input-stream", f, Map.of("Why", "ValueWhy"));
    }

    @Test
    public void listFilesInBucket() throws FileNotFoundException {

        s3Service.listFiles("my-oleg-special-bucket-name1");
    }

    @Test
    public void makeObjectPublic() throws FileNotFoundException {

        s3Service.makeObjectPublic("my-oleg-special-bucket-name1", "my-key");
    }

    @Test
    public void makeObjectPrivate() throws FileNotFoundException {

        s3Service.makeObjectPrivate("my-oleg-special-bucket-name1", "my-key");
    }

    @Test
    public void deleteObject() throws FileNotFoundException {

        s3Service.deleteObject("my-oleg-special-bucket-name1", "my-key-file-input-stream");
    }

    @Test
    public void deleteBucket() throws FileNotFoundException {

        s3Service.deleteBucket("my-oleg-special-bucket-name");
    }

    @Test
    public void getPreSignedUrl() {

        s3Service.getPreSignedUrl("my-oleg-special-bucket-name1", "my-key");
    }

    @Test
    public void addExpirationLifeCycleToBucket() {

        s3Service.addExpirationLifeCycleToBucket("my-oleg-special-bucket-name1");
    }
}




















