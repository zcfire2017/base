package com.base.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.base.file.entity.FileVO;
import com.base.file.enums.EFileType;
import com.base.tools.time.enums.DateFormat;
import lombok.AllArgsConstructor;
import manifold.ext.rt.api.auto;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * 文件上传 工具类
 */
@AllArgsConstructor
public class UploadFileHelper {
	/**
	 * 文件上传服务
	 */
	private FileStorageService service;

	/**
	 * 上传文件
	 *
	 * @param file 文件信息
	 * @param zip  是否压缩文件
	 *
	 * @return 上传后文件信息
	 */
	public FileVO uploadFile(MultipartFile file, Boolean zip) {
		return uploadFile(file.bytes, file.originalFilename);
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件信息
	 *
	 * @return 上传后文件信息
	 */
	public FileVO uploadFile(MultipartFile file) {
		return uploadFile(file.bytes, file.originalFilename);
	}

	/**
	 * 上传文件
	 *
	 * @param stream   字节流
	 * @param fileName 文件名称
	 *
	 * @return 上传后文件信息
	 */
	public FileVO uploadFile(byte[] stream, String fileName) {
		//返回
		FileVO result;
		//文件名前缀
		var filename = IdUtil.simpleUUID();
		//文件名后缀
		var suffix = FileUtil.extName(fileName);
		//保存地址
		var savePath = LocalDate.now().format(DateFormat.NO) + "/";
		//文件类型
		var fileType = EFileType.其它;
		if (suffix != null) {
			fileType = getFileType(suffix);
		}
		try {
			//是否为图片
			if (fileType == EFileType.图片) {
				//上传图片，图片地址和缩略图
				var uploadInfo = uploadImg(stream, filename, suffix, savePath);

				result = new FileVO(true, fileName, fileType, uploadInfo.url, uploadInfo.thUrl);
			}
			//其它文件
			else {
				//上传文件
				var filePath = uploadFile(stream, filename, suffix, savePath);
				result = new FileVO(true, fileName, fileType, filePath, "");
			}
		} catch (Exception e) {
			result = new FileVO(false, fileName, fileType, "", "");
		}

		return result;
	}

	/**
	 * 上传文件
	 *
	 * @param stream   文件流
	 * @param fileName 文件名称
	 *
	 * @return 上传后文件信息
	 */
	public FileVO uploadFile(InputStream stream, String fileName) {
		//返回
		FileVO result;
		//文件名前缀
		var filename = IdUtil.simpleUUID();
		//文件名后缀
		var suffix = FileUtil.extName(fileName);
		//保存地址
		var savePath = LocalDate.now().format(DateFormat.NO) + "/";
		//文件类型
		var fileType = EFileType.其它;
		if (suffix != null) {
			fileType = getFileType(suffix);
		}
		try {
			//是否为图片
			if (fileType == EFileType.图片) {
				//上传图片，图片地址和缩略图
				var uploadInfo = uploadImg(stream, filename, suffix, savePath);

				result = new FileVO(true, fileName, fileType, uploadInfo.url, uploadInfo.thUrl);
			}
			//其它文件
			else {
				//上传文件
				var filePath = uploadFile(stream, filename, suffix, savePath);
				result = new FileVO(true, fileName, fileType, filePath, "");
			}
		} catch (Exception e) {
			result = new FileVO(false, fileName, fileType, "", "");
		}

		return result;
	}

	/**
	 * 上传文件
	 *
	 * @param stream   字节流
	 * @param filename 文件名
	 * @param suffix   文件后缀
	 * @param path     保存地址
	 *
	 * @return 文件地址
	 */
	private String uploadFile(byte[] stream, String filename, String suffix, String path) {
		//保存文件名
		var saveName = "${filename}.${suffix}";
		//上传
		var fileInfo = service
				//上传文件，文件名称
				.of(stream, saveName)
				//保存地址
				.setPath(path)
				.upload();

		return fileInfo.url;
	}

	/**
	 * 上传文件
	 *
	 * @param stream   文件流
	 * @param filename 文件名
	 * @param suffix   文件后缀
	 * @param path     保存地址
	 *
	 * @return 文件地址
	 */
	private String uploadFile(InputStream stream, String filename, String suffix, String path) {
		//保存文件名
		var saveName = "${filename}.${suffix}";
		//上传
		var fileInfo = service
				//上传文件，文件名称
				.of(stream, saveName)
				//保存地址
				.setPath(path)
				.upload();

		return fileInfo.url;
	}

	/**
	 * 上传图片
	 *
	 * @param stream   字节流
	 * @param filename 文件名
	 * @param suffix   文件后缀
	 * @param path     保存地址
	 *
	 * @return （url:图片地址, thUrl:缩略图地址）
	 */
	private auto uploadImg(byte[] stream, String filename, String suffix, String path) {
		//保存文件名
		var saveName = "${filename}.${suffix}";
		//上传图片
		var fileInfo = service
				//上传图片，图片名称
				.of(stream, saveName)
				.setSaveFilename(saveName)
				//保存地址
				.setPath(path)
				//缩略图名称
				.setSaveThFilename(filename)
				//缩略图后缀
				.setThumbnailSuffix(".min.${suffix}")
				//图片压缩比例
				.thumbnail(t -> t.scale(0.4f))
				.upload();

		return fileInfo.url, fileInfo.thUrl;
	}

	/**
	 * 上传图片
	 *
	 * @param stream   文件流
	 * @param filename 文件名
	 * @param suffix   文件后缀
	 * @param path     保存地址
	 *
	 * @return （url:图片地址, thUrl:缩略图地址）
	 */
	private auto uploadImg(InputStream stream, String filename, String suffix, String path) {
		//保存文件名
		var saveName = "${filename}.${suffix}";
		//上传图片
		var fileInfo = service
				//上传图片，图片名称
				.of(stream, saveName)
				.setSaveFilename(saveName)
				//保存地址
				.setPath(path)
				//缩略图名称
				.setSaveThFilename(filename)
				//缩略图后缀
				.setThumbnailSuffix(".min.${suffix}")
				//图片压缩比例
				.thumbnail(t -> t.scale(0.4f))
				.upload();

		return fileInfo.url, fileInfo.thUrl;
	}

	/**
	 * 获取文件类型
	 *
	 * @param suffix 文件后缀
	 *
	 * @return 文件类型枚举
	 */
	public static EFileType getFileType(String suffix) {
		return switch (suffix) {
			case "png", "jpg", "jpeg", "svg", "bmp", "gif", "webp" -> EFileType.图片;
			case "avi", "mp4", "wmv", "mpeg", "mpeg1", "mpeg2", "mov", "mod", "rm", "rmvb", "mkv", "flv", "dv" -> EFileType.视频;
			case "zip", "rar", "7z", "jar", "gz", "tar", "lz", "xz", "lzh", "arj" -> EFileType.压缩文件;
			default -> EFileType.其它;
		};
	}
}