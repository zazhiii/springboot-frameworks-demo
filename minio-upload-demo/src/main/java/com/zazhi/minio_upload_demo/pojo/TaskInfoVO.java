package com.zazhi.minio_upload_demo.pojo;

import io.minio.messages.Part;
import lombok.*;

import java.util.List;

/**
 * @author zazhi
 * @date 2025/3/25
 * @description: 上传任务信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskInfoVO extends UploadTask {
    private boolean finished;
    private String fileUrl;
    private List<Part> parts;
}
