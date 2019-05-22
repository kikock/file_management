package com.kiko.file.controller;

import com.kiko.file.manager.error.FileManagerException;
import com.kiko.file.manager.impl.LocalFileManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Controller
public class FileManagerController {

    private String fileStoreRoot = "/usr/local/project/file";


    @GetMapping(value = "/fm")
    public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "jsp/index";
    }

    @RequestMapping(value = "/api")
    public void fm(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException, FileManagerException {
        new LocalFileManager().handleRequest(request, response);
    }

    @PostMapping(value = "/rest/storeFile")
    @ResponseBody
    public String storeFile(MultipartFile multipartFile, String fullPath, String fileName) throws Exception {
        try {
            File path = new File(this.fileStoreRoot + fullPath);
            boolean b = path.mkdirs();
            File file = new File(path, fileName);
            if (file.exists()) {
                System.out.println(fileName + " exist?");
            }

            multipartFile.transferTo(file);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "failure";
    }

    @PostMapping(value = "/rest/retrieveFileStream")
    @ResponseBody
    public void retrieveFileStream(String fullPath, String fileName, HttpServletResponse response) throws Exception {
        ServletOutputStream out = response.getOutputStream();
        try {
            File path = new File(this.fileStoreRoot + fullPath);
            boolean b = path.mkdirs();
            File file = new File(path, fileName);
            if (file.exists()) {
                byte[] bytes = FileUtils.readFileToByteArray(file);
                IOUtils.write(bytes, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @PostMapping(value = "/rest/deleteFile")
    @ResponseBody
    public String deleteFile(String fullPath, String fileName, HttpServletResponse response) throws Exception {
        try {
            File path = new File(this.fileStoreRoot + fullPath);
            boolean b = path.mkdirs();
            File file = new File(path, fileName);
            if (file.exists()) {
                file.delete();
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "failure";
    }

    @RequestMapping(value = "/123")
    public String File(String fullPath, String fileName, HttpServletResponse response) throws Exception {
        return "upload-container";
    }
}
