package cc.blisscorp.event.game.ent;

import java.util.List;

/**
 * 
 * @author tuyenpv
 *
 */
public class PageView<T> {

	List<T> items;

	int pageNumber;

	int pageSize;

	long totalItems;

	public PageView(List<T> items) {
		this.items = items;
	}

	public List<T> getItems() {
		return items;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * set pageSize
	 * 			- not set pageSize. default pageSize = 20
	 * 			- pageSize max = 100 items
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize > 100)
			pageSize = 100;
		this.pageSize = pageSize;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

}
