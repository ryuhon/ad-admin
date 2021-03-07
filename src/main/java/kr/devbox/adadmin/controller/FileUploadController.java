package kr.devbox.adadmin.controller;

import kr.devbox.adadmin.dto.RestDTO;
import kr.devbox.adadmin.service.FileUploadService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/api/")
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;



    @RequestMapping(value = "/upload", method = {RequestMethod.POST})
    public RestDTO handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value="width",defaultValue = "100") Integer width,
            @RequestParam(value="height",defaultValue = "100") Integer height ) throws IOException {
        String filename = RandomStringUtils.random(30, true, true);
        String result = "";
        if ( fileUploadService.uploadFile(file.getInputStream(),file.getContentType(),filename+ "." + FilenameUtils.getExtension(file.getOriginalFilename()),"adadmin",true,width,height)) {
            result = fileUploadService.getImgUrl() + "adadmin/" + filename +"." + FilenameUtils.getExtension(file.getOriginalFilename()) ;

        } else {
            throw new InternalAuthenticationServiceException("파일 업로드 실패");
        }
        return new RestDTO(result,"파일 업로드 성공");

    }
}