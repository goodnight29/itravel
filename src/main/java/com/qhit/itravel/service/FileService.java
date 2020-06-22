package com.qhit.itravel.service;

import java.io.IOException;

import com.qhit.itravel.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件处理的业务层
 */
public interface FileService {

	FileInfo save(MultipartFile file) throws IOException;

	void delete(String id);

}
