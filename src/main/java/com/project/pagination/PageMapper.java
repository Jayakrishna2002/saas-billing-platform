
package com.project.pagination;

import org.springframework.data.domain.Page;

public class PageMapper
{
	
	public static <T> PagedResponse<T> map( Page<T> page )
	{
		return new PagedResponse<T>(
				page.getContent(), page.isEmpty(), page.getNumberOfElements(), page.getNumber(), page.isFirst(), page.isLast(), page.getTotalElements(), page.getSize()
		);
	}
	
}
