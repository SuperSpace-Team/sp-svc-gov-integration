
package com.yh.svc.gov.test.springboot1.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yh.infra.svc.gov.sdk.files.command.fileS2.dto.DeleteFilesDTO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.dto.DeletePathDTO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.dto.FileListDTO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.dto.ImageSize;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.dto.SearchFileS2DTO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.vo.DeletePathVO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.vo.SearchFileS2VO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.vo.SearchPathFolderVO;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.vo.UploadFileVo;
import com.yh.infra.svc.gov.sdk.files.command.fileS2.vo.UploadImgVo;
import com.yh.infra.svc.gov.sdk.files.manager.FileS2Service;
import com.yh.infra.svc.gov.sdk.files.utils.Result;

import io.swagger.annotations.ApiOperation;

/**
 * @author msh7612
 */
@RestController
@RequestMapping("/file2")
public class FileV2Controller {
    
    @Value("${sdk-demo.app-id}")
    protected String appId;

    @Autowired
    private FileS2Service fileS2Service;

    @PostMapping("/searchFiles")
    @ApiOperation(value="searchFiles", tags="file v2 api", response = Result.class)
    public Result<Map<String, Object>> searchFiles(FileListDTO fileListDTO) {
       return fileS2Service.searchFiles(fileListDTO);
    }
    
    @PostMapping("/sSearchFiles")
    @ApiOperation(value="sSearchFiles", tags="file v2 api", response = Result.class)
    public Result<SearchFileS2VO> sSearchFiles(SearchFileS2DTO searchFileDTO) {
       return fileS2Service.sSearchFiles(searchFileDTO);
    }
    
    @PostMapping("/deleteFile")
    @ApiOperation(value="deleteFile", tags="file v2 api", response = Result.class)
    public Result<String> deleteFile(DeleteFilesDTO deleteFilesDTO) {
       return fileS2Service.deleteFile(deleteFilesDTO);
    }
    
    @PostMapping("/deleteFileByPath")
    @ApiOperation(value="deleteFileByPath", tags="file v2 api", response = Result.class)
    public Result<DeletePathVO> deleteFileByPath(DeletePathDTO deletePathDTO) {
        return fileS2Service.deleteFileByPath(deletePathDTO);
    }
    
    @PostMapping("/searchFolderByPath")
    @ApiOperation(value="searchFolderByPath", tags="file v2 api", response = Result.class)
    public Result<List<SearchPathFolderVO>> searchFolderByPath(DeletePathDTO deletePathDTO) {
        return fileS2Service.searchFolderByPath(deletePathDTO);
    }
    
    @GetMapping("/uploadFolder")
    @ApiOperation(value="test_uploadFolder", tags="file v2 api", response = Result.class)
    public Result<UploadFileVo> uploadFolder() throws Exception {
        File file = new File("D:\\file\\test.txt") ;
        InputStream is = new FileInputStream(file) ;
        return fileS2Service.uploadFolder(appId, "v2_test", is, file.length(),file.getName());
    }
    
    @GetMapping("/uploadImg")
    @ApiOperation(value="test_uploadImg", tags="file v2 api", response = Result.class)
    public Result<UploadImgVo> uploadImg() throws Exception {
        File file = new File("D:\\file\\图片1.png") ;
        InputStream is = new FileInputStream(file) ;

        List<ImageSize> imageSizes = new ArrayList<>() ;
        imageSizes.add(new ImageSize(800,800)) ;
        imageSizes.add(new ImageSize(600,600)) ;
        return fileS2Service.uploadImg(appId, "5efefa075f4aee00011da761", is, file.getName(),imageSizes);
    }
    
    @GetMapping("/uploadInputStream")
    @ApiOperation(value="test_uploadInputStream", tags="file v2 api", response = Result.class)
    public Result<UploadFileVo> uploadInputStream() throws Exception {
        File file = new File("D:\\file\\test - 副本.txt") ;
        InputStream is = new FileInputStream(file) ;

        return fileS2Service.uploadInputStream(appId, "5efefa075f4aee00011da761", is, file.length(), file.getName());
    }
    
    @GetMapping("/uploadZipFile")
    @ApiOperation(value="test_uploadZipFile", tags="file v2 api", response = Result.class)
    public Result<List<UploadFileVo>> uploadZipFile() throws Exception {
        File file = new File("D:\\file\\test.rar") ;
        InputStream is = new FileInputStream(file) ;
        
        return fileS2Service.uploadZipFile(appId, "5efefa075f4aee00011da761", is, file.length(), file.getName(), false);
    }

}
