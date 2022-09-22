package com.shareit.service.impl;

import com.cloudinary.Cloudinary;
import com.shareit.exception.BadRequestException;
import com.shareit.service.FileUploadService;
import com.shareit.utils.Constants;
import com.shareit.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        File uploadedFile = convertFile( file );
        String fileExtension = getFileExtension( file.getOriginalFilename() );
        Map uploadResult = cloudinary.uploader().upload(uploadedFile, getResourceTypeOptions( fileExtension ));
        return uploadResult.get("url").toString();
    }


    private File convertFile(MultipartFile file) {
        if(file.isEmpty() || StringUtils.isBlank(file.getOriginalFilename())){
            throw new BadRequestException("File is not uploaded or file name is empty");
        }

        File convertedFile = null;
        try {
            convertedFile = new File(file.getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(convertedFile);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();
            log.debug("Converting multipart file: {}", convertedFile);
        } catch ( Exception e ) {
            log.warn("Error occurred while writing to a file {}", ExceptionUtils.getStackTrace(e));
        }

        return convertedFile;
    }

    private String getFileExtension( String fileName ) {
        if(StringUtils.isNotBlank(fileName) && fileName.contains(".")) {
            for(String extension: Utils.getExtensionList()) {
                if(fileName.endsWith(extension)) {
                    log.debug("Accepted file type: {}", extension);
                    return extension;
                }
            }
        }

        throw new BadRequestException("This file type not permitted");
    }

    private Map<String,String> getResourceTypeOptions(String extension) {
        if(extension.equals(Constants.MP4)) {
             Map<String,String> options = new HashMap<>();
             options.put("resource_type", "video");
             return Collections.unmodifiableMap(options);
        }

        return Collections.emptyMap();
    }

}
