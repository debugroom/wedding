package org.debugroom.wedding.app.batch.gallery.chunk;

import java.util.List;

import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;

@Named
public class CreateZipWriter extends AbstractItemWriter{

	@Override
	public void writeItems(List<Object> items) throws Exception {
		System.out.println(this.getClass().getName() + " : run.");
		
	}

}
