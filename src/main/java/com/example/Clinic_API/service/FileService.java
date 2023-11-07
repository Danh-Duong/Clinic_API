package com.example.Clinic_API.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public class FileService {

    public static String getNameImage(String url){
        if (url.startsWith("https://res.cloudinary.com/")){
            int startIndex= url.lastIndexOf('/');
            int endIndex=url.lastIndexOf(".");
            return url.substring(startIndex+1, endIndex);
        }
        return null;

    }

    // kiểm tra file dạng gì và kích cỡ cửa file đó
    public static Boolean isImageFile(MultipartFile file){
        if (file.getContentType()!=null && file.getContentType().startsWith("image/"))
            return true;
        throw new RuntimeException("File tải lên phải là file hình ảnh");
    }

    public static Boolean isValidSize(MultipartFile file, double maxSize){
        double sizeFile=(double) file.getSize()/(1024*1024);
        if (sizeFile<maxSize)
            return true;
        throw new RuntimeException(String.format("File tải lên phải %s MB", maxSize));
    }

    public static Boolean isNotEmpty(MultipartFile file){
        if (file.isEmpty()==false && file.getSize()>0)
            return true;
        return false;
    }
}
