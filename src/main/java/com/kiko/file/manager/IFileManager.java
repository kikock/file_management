package com.kiko.file.manager;

import com.kiko.file.manager.error.FileManagerException;
import com.kiko.file.manager.model.FileData;
import com.kiko.file.manager.model.InitiateData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public interface IFileManager {

    void handleRequest(HttpServletRequest request, HttpServletResponse response);

    // GET

    InitiateData actionInitiate() throws FileManagerException;

    FileData actionGetInfo(String path) throws FileManagerException;

    List<FileData> actionReadFolder(String path, String type) throws FileManagerException;

    FileData actionMove(String sourcePath, String targetPath) throws FileManagerException;

    FileData actionDelete(String path) throws FileManagerException;

    FileData actionAddFolder(String path, String name) throws FileManagerException;

    FileData actionGetImage(HttpServletResponse response, String path, Boolean thumbnail) throws FileManagerException;

    // TO test :

    Object actionSeekFolder(String folderPath, String term) throws FileManagerException;

    FileData actionCopy(String sourcePath, String targetPath) throws FileManagerException;

    FileData actionRename(String sourcePath, String targetPath) throws FileManagerException;

    FileData actionReadFile(HttpServletRequest request, HttpServletResponse response, String path) throws FileManagerException;

    FileData actionSummarize() throws FileManagerException;

    FileData actionDownload(HttpServletResponse response, String path) throws FileManagerException;

    List<FileData> actionUpload(HttpServletRequest request, String path) throws FileManagerException;

    FileData actionSaveFile(String pathParam, String contentParam) throws FileManagerException;

    List<FileData> actionExtract(String sourcePath, String targetPath) throws FileManagerException;


}
