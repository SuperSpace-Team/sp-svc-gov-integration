package com.sp.infra.svc.gov.metrics.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.micrometer.core.instrument.Tag;

/**
 * @author luchao  2020-12-29
 *
 */
public class TagUtil {
	
	/**
	 * 
	 * @param tagList
	 * @return
	 */
	public static List<Collection<Tag>> buildDimensionTags(List<String> tagList) {
		
		List<Collection<Tag>> ret = new ArrayList<>();
		if (CollectionUtils.isEmpty(tagList))
			return ret;
		
		// 保存tag set，用于校验是否所有的dimension设置正确。
		final Set<String> tagSet = new HashSet<>();
		
		for (String dimension : tagList) {
			Collection<Tag> onedimension =  new ArrayList<>();
			// 拆分出tag
			String tags[] = dimension.split(",");
			
			for (int i = 0; i < tags.length; i++) {
				String p[] = tags[i].split("=");
				onedimension.add(Tag.of(StringUtils.trimWhitespace(p[0]), p.length==2? StringUtils.trimWhitespace(p[1]): ""));
			}
			
			if (tagSet.isEmpty()) {
				// 这是第一行。
				tagSet.addAll(onedimension.stream().map(x->x.getKey()).collect(Collectors.toSet()));
			} else {
				// 检测 tag是否匹配
				if (onedimension.stream().filter(x->! tagSet.contains(x.getKey())).findAny().isPresent()) {
					return null;
				}
				Set<String> ts = onedimension.stream().map(x->x.getKey()).collect(Collectors.toSet());
				if (ts.size() != tagSet.size())
					return null;
			}
			ret.add(onedimension);
		}
		return ret;
	}

	
}
