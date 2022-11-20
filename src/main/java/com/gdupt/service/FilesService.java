package com.gdupt.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gdupt.entity.Files;
import com.gdupt.mapper.FilesMapper;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Service
public class FilesService {

    @Autowired
    private FilesMapper filesMapper;

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


    /**
     * 新增文件
     * @param files
     */
    public boolean saveFiles(Files files){
        int isSave = filesMapper.insert(files);
        return isSave>0;
    }

    /**
     * 删除文件
     * @param fileId
     * @return
     */
    public boolean delFiles(Integer fileId){
        int isDel = filesMapper.deleteById(fileId);
        return isDel > 0;
    }

    /**
     * 查询单个文件
     * @param fileId
     * @return
     */
    public Files findOneById(Integer fileId){
        return filesMapper.selectById(fileId);
    }

    /**
     * 通过批量id查询批量文件
     * @param fileIds
     * @return
     */
    public List<Files> findBatchByIds(List<Integer> fileIds){
        return filesMapper.selectBatchIds(fileIds);
    }


    /**
     * 通过批量id查询批量文件
     * @param fileId
     * @return
     */
    public List<Files> findBatchById(Integer fileId){
        LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.eq(Files::getFileId,fileId);
        return filesMapper.selectList(filesLambdaQueryWrapper);
    }


    public List<Files> findOneByRelationTypeIdOrRelationTypeId(Integer relationTypeId, Integer relationId){
        LambdaQueryWrapper<Files> filesLambdaQueryWrapper = new LambdaQueryWrapper<>();
        filesLambdaQueryWrapper.eq(Files::getRelationId,relationId)
                .eq(Files::getRelationTypeId,relationTypeId);
        return filesMapper.selectList(filesLambdaQueryWrapper);
    }


}
