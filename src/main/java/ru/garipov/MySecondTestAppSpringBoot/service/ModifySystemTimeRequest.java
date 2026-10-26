package ru.garipov.MySecondTestAppSpringBoot.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.garipov.MySecondTestAppSpringBoot.model.Request;
import ru.garipov.MySecondTestAppSpringBoot.util.DateTimeUtil;

import java.util.Date;

@Service
@Qualifier("ModifySystemTimeRequestService")
public class ModifySystemTimeRequest implements ModifyRequestService {

	@Override
	public void modify(Request request) {
		request.setSystemTime(DateTimeUtil.getCustomFormat().format(new Date()));
	}
}
