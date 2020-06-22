package com.qhit.itravel.service.impl;

import java.io.IOException;


import com.qhit.itravel.dao.FileInfoDao;
import com.qhit.itravel.entity.FileInfo;
import com.qhit.itravel.service.FileService;
import com.qhit.itravel.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileServiceImpl implements FileService {


	@Value("${files.path}")
	private String filesPath;
	@Autowired
	private FileInfoDao fileInfoDao;

	@Override
	public FileInfo save(MultipartFile file) throws IOException {
		String fileOrigName = file.getOriginalFilename();
		if (!fileOrigName.contains(".")) {
			throw new IllegalArgumentException("缺少后缀名");
		}

		String md5 = FileUtil.fileMd5(file.getInputStream());
		FileInfo fileInfo = fileInfoDao.getById(md5);
		if (fileInfo != null) {
			fileInfoDao.update(fileInfo);
			return fileInfo;
		}

		fileOrigName = fileOrigName.substring(fileOrigName.lastIndexOf("."));
		String pathname = FileUtil.getPath() + md5 + fileOrigName;
		String fullPath = filesPath + pathname;
		FileUtil.saveFile(file, fullPath);

		long size = file.getSize();
		String contentType = file.getContentType();

		fileInfo = new FileInfo();
		fileInfo.setId(md5);
		fileInfo.setContentType(contentType);
		fileInfo.setSize(size);
		fileInfo.setPath(fullPath);
		fileInfo.setUrl(pathname);
		fileInfo.setType(contentType.startsWith("image/") ? 1 : 0);

		fileInfoDao.save(fileInfo);

		return fileInfo;

	}

	@Override
	public void delete(String id) {
		FileInfo fileInfo = fileInfoDao.getById(id);
		if (fileInfo != null) {
			String fullPath = fileInfo.getPath();
			FileUtil.deleteFile(fullPath);

			fileInfoDao.delete(id);

		}
	}

}
