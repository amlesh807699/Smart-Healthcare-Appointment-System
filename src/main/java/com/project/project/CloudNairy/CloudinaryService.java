package com.project.project.CloudNairy;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) {
        try {
            Map<String, Object> options = new HashMap<>();
            options.put("folder", "patients");

            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), options);

            return result.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Image upload failed", e);
        }
    }
}