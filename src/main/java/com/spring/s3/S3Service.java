package com.spring.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.BucketLifecycleConfiguration;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.lifecycle.LifecycleFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Slf4j
@Service
public class S3Service {

    @Autowired
    private AmazonS3Client s3Client;

    public void createBucket(String bucketName) {
        s3Client.createBucket(bucketName);
    }

    public void uploadString(String bucketName, String key, String content) {
        s3Client.putObject(bucketName, key, content);
    }

    public void uploadFile(String bucketName, String key, File content) {
        s3Client.putObject(bucketName, key, content);
    }

    public void uploadInputStream(String bucketName, String key, InputStream inputStream, Map<String, String> metadata) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        metadata.entrySet().forEach(entry -> objectMetadata.addUserMetadata(entry.getKey(), entry.getValue()));
        s3Client.putObject(bucketName, key, inputStream, objectMetadata);
    }

    public void listFiles(String bucketName) {

        List<S3ObjectSummary> objectSummaries = s3Client.listObjectsV2(bucketName).getObjectSummaries();

        for (S3ObjectSummary s3ObjectSummary : objectSummaries) {
            System.out.println(objectSummaries);
        }
    }

    public void makeObjectPublic(String bucketName, String key) {

        s3Client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        URL url = s3Client.getUrl(bucketName, key);
        System.out.println("Try to access  me:" + url);
    }

    public void makeObjectPrivate(String bucketName, String key) {

        s3Client.setObjectAcl(bucketName, key, CannedAccessControlList.BucketOwnerFullControl);
        URL url = s3Client.getUrl(bucketName, key);
        System.out.println("Try to access  me:" + url);
    }

    public void deleteObject(String bucketName, String key) {
        s3Client.deleteObject(bucketName, key);
    }

    public List<String> deleteBucket(String bucketName) {
        s3Client.deleteBucket(bucketName);

        Stack<String> strings = new Stack<>();
        return strings;
    }

    public void getPreSignedUrl(String bucketName, String key) {

        List<String> a = new Stack<>();

        var date = new Date(new Date().getTime() + 60 * 1000);
        URL url = s3Client.generatePresignedUrl(bucketName, key, date);
        log.info(url.toString());
    }

    //    an id to make the rule uniquely identifiable
    //    a default LifecycleFilter, so this rule applies to all objects in the bucket
    //    a status of being ENABLED, so as soon as this rule is created, it is effective
    //    an expiration of seven days, so after a week the object gets deleted`
    public void addExpirationLifeCycleToBucket(String bucketName) {

        // delete files a week after upload
        s3Client
                .setBucketLifecycleConfiguration(
                        bucketName,
                        new BucketLifecycleConfiguration()
                                .withRules(
                                        new BucketLifecycleConfiguration.Rule()
                                                .withId("custom-expiration-id")
                                                .withFilter(new LifecycleFilter())
                                                .withStatus(BucketLifecycleConfiguration.ENABLED)
                                                .withExpirationInDays(1)
                                )
                );
    }
}