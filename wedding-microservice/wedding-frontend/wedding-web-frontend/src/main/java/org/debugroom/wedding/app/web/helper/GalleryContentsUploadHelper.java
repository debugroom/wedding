package org.debugroom.wedding.app.web.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.debugroom.framework.common.exception.BusinessException;
import org.debugroom.framework.common.web.MediaType;
import org.debugroom.framework.spring.webmvc.fileupload.FileUploadHelper;
import org.debugroom.wedding.app.AppConsts;
import org.debugroom.wedding.app.model.gallery.Media;

@Component
public class GalleryContentsUploadHelper implements FileUploadHelper{

	@Value("${gallery.root.directory}")
	private String galleryRootDirectory;
	@Value("${gallery.image.original.directory}")
	private String galleryImageOriginalDirectory;
	@Value("${gallery.image.original.file.name}")
	private String galleryImageOriginalFileName;
	@Value("${gallery.image.thumbnail.directory}")
	private String galleryImageThumbnailDirectory;
	@Value("${gallery.image.thumbnail.file.name}")
	private String galleryImageThumbnailFileName;
	@Value("${gallery.movie.original.directory}")
	private String galleryMovieOriginalDirectory;
	@Value("${gallery.movie.original.file.name}")
	private String galleryMovieOriginalFileName;
	@Value("${gallery.movie.thumbnail.directory}")
	private String galleryMovieThumbnailDirectory;
	@Value("${gallery.movie.thumbnail.file.name}")
	private String galleryMovieThumbnailFileName;
	@Value("${gallery.image.thumbnail.width}")
	private int galleryImageThumbnailWidth; 
	@Value("${gallery.image.thumbnail.height}")
	private int galleryImageThumbnailHeight; 
	@Value("${gallery.movie.thumbnail.width}")
	private int galleryMovieThumbnailWidth; 
	@Value("${gallery.movie.thumbnail.height}")
	private int galleryMovieThumbnailHeight; 
	@Value("${gallery.movie.thumbnail.frame.start}")
	private int galleryMovieThumbnailFrameStart;

	private static final String FILE_SEPALATOR = System.getProperty("file.separator");

	public Media createMedia(MultipartFile multipartFile, String userId, String folderId) throws BusinessException{
		Media.MediaBuilder mediaBuilder = Media.builder().userId(userId).folderId(folderId)
				.originalFilename(multipartFile.getOriginalFilename());
		MediaType mediaType = MediaType.getInstance(multipartFile.getContentType());
		if(mediaType == null){
			throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
		}else{
			switch(mediaType){
				case JPEG:
					mediaBuilder
						.extension(AppConsts.JPEG_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.PHOTOGRAPH);
					break;
				case PNG:
					mediaBuilder
						.extension(AppConsts.PNG_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.PHOTOGRAPH);
					break;
				case GIF:
					mediaBuilder
						.extension(AppConsts.GIF_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.PHOTOGRAPH);
					break;
				case TIFF:
					throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
				case BMP:
					mediaBuilder
						.extension(AppConsts.BMP_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.PHOTOGRAPH);
					break;
				case MPEG_VIDEO:
					mediaBuilder
						.extension(AppConsts.MPEG_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.MOVIE);
					break;
				case MP4_VIDEO:
					mediaBuilder
						.extension(AppConsts.MP4_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.MOVIE);
					break;
				case QUICKTIME_VIDEO:
					mediaBuilder
						.extension(AppConsts.QUICKTIME_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.MOVIE);
					break;
				case WMV:
					mediaBuilder
						.extension(AppConsts.WMV_VIDEO_EXTENSION)
						.mediaType(org.debugroom.wedding.app.model.gallery.MediaType.MOVIE);
					break;
				default:
					throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
			}
		}
		Media media = mediaBuilder.filePath(saveFile(multipartFile, userId)).build();
		createThumbnail(media);
		return media;
	}

	public void createThumbnail(Media media) throws BusinessException{
		
		BufferedImage bufferedImage = new BufferedImage(galleryImageThumbnailWidth, 
				galleryImageThumbnailHeight, BufferedImage.TYPE_INT_RGB);
		
		String mediaFilePath = new StringBuilder()
				.append(galleryRootDirectory)
				.append(FILE_SEPALATOR)
				.append(media.getFilePath())
				.toString();

		String directory = null;
		String fileName = null;
		String extension = null;
		org.debugroom.wedding.app.model.gallery.MediaType mediaType = media.getMediaType();
		switch(mediaType){
			case PHOTOGRAPH:
					directory = galleryImageThumbnailDirectory;
					fileName = galleryImageThumbnailFileName;
					extension = ".jpg";
					break;
			case MOVIE:
					directory = galleryMovieThumbnailDirectory;
					fileName = galleryMovieThumbnailFileName;
					extension = ".jpg";
					break;
			default:
		}

		String thumbnailImageFilePath = new StringBuilder()
				.append(media.getUserId())
				.append(FILE_SEPALATOR)
				.append(directory)
				.append(FILE_SEPALATOR)
				.append(UUID.randomUUID().toString())
				.append(FILE_SEPALATOR)
				.append(fileName)
				.append(extension)
				.toString();
		String thumbnailImageAbsolutePath = new StringBuilder()
				.append(galleryRootDirectory)
				.append(FILE_SEPALATOR)
				.append(thumbnailImageFilePath)
				.toString();
		
		File file = new File(thumbnailImageAbsolutePath);
		try {
			switch(mediaType){
			case PHOTOGRAPH:
				bufferedImage.getGraphics().drawImage(
					ImageIO.read(new File(mediaFilePath)).getScaledInstance(
							galleryImageThumbnailWidth, galleryImageThumbnailHeight, Image.SCALE_SMOOTH),
					0, 0, null);
				if(file.getParentFile().mkdirs() && file.createNewFile()){
					ImageIO.write(bufferedImage, media.getExtension(), file);
				}else{
					throw new BusinessException("galleryContentsUploadHelper.error.0003", null, media.getOriginalFilename());
				}
					break;
			case MOVIE:
				FFmpegFrameGrabber ffmpegFrameGrabber = new FFmpegFrameGrabber(new File(mediaFilePath));
				Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();
				
				ffmpegFrameGrabber.start();
				
				int count = 0;
				
				while(ffmpegFrameGrabber.getFrameNumber() < ffmpegFrameGrabber.getLengthInFrames()){
					if(galleryMovieThumbnailFrameStart < count){
						bufferedImage = java2DFrameConverter.convert(ffmpegFrameGrabber.grab());
						if(bufferedImage == null){
							continue;
						}
						if(file.getParentFile().mkdirs() && file.createNewFile()){
							ImageIO.write(bufferedImage, "jpg", file);
						}else{
							throw new BusinessException("galleryContentsUploadHelper.error.0003", null, media.getOriginalFilename());
						}
						ffmpegFrameGrabber.stop();
						ffmpegFrameGrabber.release();
						break;
					}
					count++;
				}
				break;
			default:
			}
		} catch (IOException e) {
			throw new BusinessException("galleryContentsUploadHelper.error.0002", e, media.getOriginalFilename());
		} catch (FrameGrabber.Exception e) {
			throw new BusinessException("galleryContentsUploadHelper.error.0002", e, media.getOriginalFilename());
		}
		media.setThumbnailFilePath(thumbnailImageFilePath);
	}
	
	@Override
	public String saveFile(MultipartFile multipartFile, String userId) throws BusinessException {
		String directory = null;
		String fileName = null;
		String extension = null;
		MediaType mediaType = MediaType.getInstance(multipartFile.getContentType());
		if(mediaType == null){
			throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
		}else{
			switch(mediaType){
				case JPEG:
					directory = galleryImageOriginalDirectory;
					fileName = galleryImageOriginalFileName;
					extension = ".jpg";
					break;
				case PNG:
					directory = galleryImageOriginalDirectory;
					fileName = galleryImageOriginalFileName;
					extension = ".png";
					break;
				case GIF:
					directory = galleryImageOriginalDirectory;
					fileName = galleryImageOriginalFileName;
					extension = ".gif";
					break;
				case TIFF:
					throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
				case BMP:
					directory = galleryImageOriginalDirectory;
					fileName = galleryImageOriginalFileName;
					extension = ".bmp";
					break;
				case MPEG_VIDEO:
					directory = galleryMovieOriginalDirectory;
					fileName = galleryMovieOriginalFileName;
					extension = ".mpeg";
					break;
				case MP4_VIDEO:
					directory = galleryMovieOriginalDirectory;
					fileName = galleryMovieOriginalFileName;
					extension = ".mp4";
					break;
				case WMV:
					directory = galleryMovieOriginalDirectory;
					fileName = galleryMovieOriginalFileName;
					extension = ".wmv";
					break;
				default:
					throw new BusinessException("galleryContentsTypeHelper.error.0001", null, multipartFile.getOriginalFilename());
			}
		}
		String uploadDirectoryContextPath = new StringBuilder()
				.append(userId)
				.append(FILE_SEPALATOR)
				.append(directory)
				.append(FILE_SEPALATOR)
				.append(UUID.randomUUID().toString())
				.toString();
		String uploadDirectoryAbsolutePath = new StringBuilder()
				.append(galleryRootDirectory)
				.append(FILE_SEPALATOR)
				.append(uploadDirectoryContextPath)
				.append(FILE_SEPALATOR)
				.toString();
		String uploadFileName = new StringBuilder()
				.append(fileName).append(extension).toString();
		File uploadFile = new File(uploadDirectoryAbsolutePath, uploadFileName);
		try {
			FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), uploadFile);
		} catch (IOException e) {
			throw new BusinessException("galleryContensUploadHelper.error.0001", null, userId);
		}
		return new StringBuilder()
					.append(uploadDirectoryContextPath)
					.append(FILE_SEPALATOR)
					.append(uploadFileName)
					.toString();
	}

}
