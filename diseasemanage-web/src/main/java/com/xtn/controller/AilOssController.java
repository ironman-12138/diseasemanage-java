package com.xtn.controller;

import com.xtn.common.Result;
import com.xtn.service.AliOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 阿里云管理文件控制层
 */

@RestController
@Api(value = "阿里云文件管理")
@RequestMapping(value = "/aliOss")
@Slf4j
public class AilOssController {

    @Autowired
    private AliOssService aliOssService;

    @ApiOperation(value = "上传图片文件")
    @PostMapping("/uploadImgFile")
    public Result uploadImgFile(MultipartFile file){
        String s = aliOssService.upload(file);
        return Result.ok().data("url",s);
    }

    @ApiOperation(value = "删除上传替换之后的头像")
    @PostMapping("/deleteImgFile")
    public Result deleteImgFile(String file){
        //传过来的文件路径样式：https://diseasemanage.oss-cn-hangzhou.aliyuncs.com/2021/02/17/6f3e678106214e00bafdf139ed98abb4.png
        try {
            //只需要2021/02/17/6f3e678106214e00bafdf139ed98abb4.png
            String[] splitFile = file.split(".com/");
            aliOssService.deleteFile(splitFile[1]);
            return Result.ok();
        }catch (Exception e){
            log.info(file + "删除失败");
            return Result.error();
        }
    }

}
