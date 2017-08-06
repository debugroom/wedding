package org.debugroom.wedding.app.batch.gallery.chunk;

import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.batch.api.chunk.AbstractItemReader;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class MediaReader extends AbstractItemReader{

	@Inject
	JobContext jobContext;
	
	@Override
	public Object readItem() throws Exception {
		System.out.println(this.getClass().getName() + " : run.");
		return null;
	}

}
