package org.debugroom.wedding.app.web.adapter.docker.provider;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

@Component
public class RandomAccessProvider implements ConnectPathProvider{

	@Inject
	ConnectPathHolder connectPathHolder;
	
	@Override
	public String getPath(String serviceName) {
		List<String> paths = connectPathHolder.getPaths().get(serviceName);
		String path = null;
		if(1 == paths.size()){
			path = paths.get(0);
		}else if(0 != paths.size()){
			Random random = new Random();
			path = paths.get(random.nextInt(paths.size()));
		}
		return path;
	}

	
}
