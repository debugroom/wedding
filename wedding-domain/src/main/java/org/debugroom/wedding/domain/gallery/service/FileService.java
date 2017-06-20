package org.debugroom.wedding.domain.gallery.service;

import java.util.List;
import org.debugroom.wedding.domain.gallery.model.Media;

public interface FileService {

	public Media createZipFile(List<Media> mediaList, String destPath);

}
