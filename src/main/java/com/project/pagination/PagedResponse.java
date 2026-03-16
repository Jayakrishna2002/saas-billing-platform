
package com.project.pagination;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedResponse<T>
{
	
	private List<T> content;
	private boolean empty;
	private int numberOfElements;
	private int page;
	private boolean first;
	private boolean last;
	private long totalElements;
	private int size;
	
}
