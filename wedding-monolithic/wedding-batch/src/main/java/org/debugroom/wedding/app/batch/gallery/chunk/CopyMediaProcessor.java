package org.debugroom.wedding.app.batch.gallery.chunk;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;

@Named
public class CopyMediaProcessor implements ItemProcessor{

	@Override
	public Object processItem(Object item) throws Exception {
		System.out.println(this.getClass().getName() + " : run.");
		return null;
	}

}
