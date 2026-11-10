package org.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
public class App {
    private static final String AWS_BUCKET_NAME = "my-first-bucket-linkedin";
    private static final String TRANSIENT_BUCKET_NAME = "my-second-bucket-linkedin";
    private static final String FILE1 = "test1.txt";
    private static final String FILE2 = "test2.txt";
    private static final String FILE3 = "test3.txt";
    private static final String DIR = "/home/polina/Programming/MyProjects/Practice/JavaS3/src/main/resources";
    private static final String DOWN_DIR = "/home/polina/Programming/MyProjects/Practice/JavaS3/src/main/resources/downloads";

    private final S3Client s3;
    private final S3Presigner presigner;

    public static void main(String[] args) {
        AwsSessionCredentials cred = AwsSessionCredentials
                .create("AWS_ACCESS_KEY", "AWS_SECRET_KEY", "");
        S3Client client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(cred))
                .build();
        S3Presigner s3presigner = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider
                        .create(cred))
                .build();
        App app = new App(client, s3presigner);
        app.deleteBucket(TRANSIENT_BUCKET_NAME);
    }

    public void createBucket(String name) {
        try {
            CreateBucketRequest request = CreateBucketRequest
                    .builder()
                    .bucket(name)
                    .build();

            s3.createBucket(request);
        } catch (Exception e) {
            log.error("Error during create bucket " + e.getMessage());

        }
    }

    public void uploadFile(String bucket, String file, String dir, String key) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3.putObject(request, Paths.get(dir, file));
        } catch (Exception e) {
            log.error("Error during upload file " + e.getMessage());
        }
    }

    public void downloadFile(String bucket, String file, String dir, String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3.getObject(request, Paths.get(dir, file));
        } catch (Exception e) {
            log.error("Error during download file " + e.getMessage());
        }
    }

    public void deleteFile(String bucket, String key) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();
            s3.deleteObject(request);
        } catch (Exception e) {
            log.error("Error during delete file " + e.getMessage());
        }
    }

    public List<String> listFiles(String bucket) {
        List<String> keys = new ArrayList<>();
        try {
            ListObjectsRequest request = ListObjectsRequest.builder()
                    .bucket(bucket).build();
            ListObjectsResponse response = s3.listObjects(request);
            response.contents().forEach(content -> keys.add(content.key()));
            log.info("Number of files in bucket: " + keys.size());
        } catch (Exception e) {
            log.error("Error listing files " + e.getMessage());
        }
        return keys;
    }

    public void copyFile(String sourceBucket, String destinationBucket,
                         String sourceKey, String destinationKey) {
        try {
            String encodedUrl = URLEncoder.encode(sourceBucket + "/" + sourceKey, StandardCharsets.UTF_8);
            CopyObjectRequest request = CopyObjectRequest.builder()
                    .copySourceIfMatch(encodedUrl)
                    .destinationBucket(destinationBucket)
                    .destinationKey(destinationKey)
                    .build();
            s3.copyObject(request);
        } catch (Exception e) {
            log.error("Error copying file " + e.getMessage());
        }
    }

    public void blockPublicAccess(String bucket) {
        try {
            PutPublicAccessBlockRequest request = PutPublicAccessBlockRequest.builder()
                    .bucket(bucket)
                    .publicAccessBlockConfiguration(PublicAccessBlockConfiguration.builder()
                            .blockPublicAcls(true)
                            .blockPublicPolicy(true)
                            .ignorePublicAcls(true)
                            .restrictPublicBuckets(true)
                            .build())
                    .build();
            s3.putPublicAccessBlock(request);
        } catch (Exception e) {
            log.error("Error blocking public access " + e.getMessage());
        }
    }

    public String cratePresignedUrl(String bucket, String key) {
        String result = null;
        try {
            GetObjectPresignRequest request = GetObjectPresignRequest.builder()
                    .getObjectRequest(GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build())
                    .signatureDuration(Duration.ofSeconds(30))
                    .build();
            PresignedGetObjectRequest getObjectRequest = presigner.presignGetObject(request);
            result = getObjectRequest.url().toString();
        } catch (Exception e) {
            log.error("Error generating presigned url" + e.getMessage());
        }
        return result;
    }

    public void deleteBucket(String bucket) {
        try {
            DeleteBucketRequest request = DeleteBucketRequest.builder()
                    .bucket(bucket)
                    .build();
            s3.deleteBucket(request);
        } catch (Exception e) {
            log.error("Error during delete bucket " + e.getMessage());
        }
    }
}
