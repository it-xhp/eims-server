package com.gdupt.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Service
public class FilesService {

    @Value("${server.root-path}")
    private String rootPath;

    @Value("${server.base-file-path}")
    private String baseFilePath;


    /**
     * 上传
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public ApiResults uploads(HttpServletRequest request, HttpServletResponse response)  {
        JSONObject data = new JSONObject();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
                .getServletContext());
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                MultipartFile multipartFile = multiRequest.getFile(iter.next());
                if (multipartFile != null) {
                    String fileName = multipartFile.getOriginalFilename();
                    if (fileName!= null && fileName.trim().length() > 0) {
                        try {
                            File file =new File(rootPath+baseFilePath);
                            if(!file.exists()){
                                file.mkdir();
                            }
                            String  name  =  IdUtil.simpleUUID();
                            int lastIndexOf = fileName.lastIndexOf(".");
                            String suffix = fileName.substring(lastIndexOf);
                            String newFileName=name+suffix;
                            File uploadedFile = new File(rootPath+baseFilePath,newFileName);
                            data.putOnce("url","uploads/"+newFileName);
                            multipartFile.transferTo(uploadedFile);
                        } catch (Exception e) {
                        }
                        response.setContentType("text/html; charset=utf-8");
                    }
                }
            }
        }
        return ApiResultUtils.getSuccess("",data);
    }
}
