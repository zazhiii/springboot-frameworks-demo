package com.zazhi.minio_demo.controller;

import com.zazhi.minio_demo.config.MinioConfig;
import com.zazhi.minio_demo.entity.Result;
import com.zazhi.minio_demo.util.MinioUtil;
import io.minio.messages.Bucket;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "product/file")
@Tag(name = "文件管理")
public class FileController {

    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private MinioConfig prop;

    @Operation(summary = "查看存储bucket是否存在")
    @GetMapping("/bucketExists")
    public Result bucketExists(@RequestParam("bucketName") String bucketName) {
        return Result.success(minioUtil.bucketExists(bucketName));
    }

    @Operation(summary = "创建存储bucket")
    @GetMapping("/makeBucket")
    public Result makeBucket(String bucketName) {
        return Result.success(minioUtil.makeBucket(bucketName));
    }

    @Operation(summary = "删除存储bucket")
    @GetMapping("/removeBucket")
    public Result removeBucket(String bucketName) {
        return Result.success(minioUtil.removeBucket(bucketName));
    }

    @Operation(summary = "获取全部bucket")
    @GetMapping("/getAllBuckets")
    public Result<List<Bucket>> getAllBuckets() {
        return Result.success(minioUtil.getAllBuckets());
    }

    @Operation(summary = "文件上传返回url")
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        String objectName = minioUtil.upload(file);
        if (null != objectName) {
            return Result.success(prop.getEndpoint() + "/" + prop.getBucketName() + "/" + objectName);
        }
        return Result.error("上传失败");
    }

    @Operation(summary = "图片/视频预览")
    @GetMapping("/preview")
    public Result preview(@RequestParam("fileName") String fileName) {
        return Result.success(minioUtil.preview(fileName));
    }

    @Operation(summary = "文件下载")
    @GetMapping("/download")
    public Result download(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        minioUtil.download(fileName,res);
        return Result.success();
    }

    @Operation(summary = "删除文件")
    @PostMapping("/delete")
    public Result remove(String url) {
        String objName = url.substring(url.lastIndexOf(prop.getBucketName()+"/") + prop.getBucketName().length()+1);
        minioUtil.remove(objName);
        return Result.success();
    }

}
