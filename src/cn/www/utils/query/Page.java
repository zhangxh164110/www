package cn.www.utils.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Page<T> {
	protected List<T> elements;
	protected int pageSize;
	protected int pageNumber;
	protected int totalElements = 0;

	public Page() {
	}

	public Page(int pageNumber, int pageSize, int totalElements) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		recomputePageNumber();
	}

	public Page(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	/**
	 * 重新计算当前页的号码
	 */
	protected void recomputePageNumber() {
		if (Integer.MAX_VALUE == this.pageNumber || this.pageNumber > getLastPageNumber()) { // last
																								// page
			this.pageNumber = getLastPageNumber();
		} else if (this.pageNumber <= 0) {
			this.pageNumber = 1;
		} else {
			this.pageNumber = getPageNumber();
		}
	}

	public boolean isFirstPage() {
		return getCurrentPageNumber() == 1;
	}

	public boolean isLastPage() {
		return getCurrentPageNumber() >= getLastPageNumber();
	}

	public boolean hasNextPage() {
		return getLastPageNumber() > getCurrentPageNumber();
	}

	public boolean hasPreviousPage() {
		return getCurrentPageNumber() > 1;
	}

	public int getLastPageNumber() {
		return totalElements % this.pageSize == 0 ? totalElements / this.pageSize : totalElements / this.pageSize + 1;
	}

	public List<T> getCurrentPageElements() {
		return elements;
	}

	public int getTotalNumberOfElements() {
		return totalElements;
	}

	public int getCurrentPageFirstElementNumber() {
		return (getCurrentPageNumber() - 1) * getPageSize() + 1;
	}

	public int getCurrentPageLastElementNumber() {
		int fullPage = getCurrentPageFirstElementNumber() + getPageSize() - 1;
		return getTotalNumberOfElements() < fullPage ? getTotalNumberOfElements() : fullPage;
	}

	public int getNextPageNumber() {
		return getCurrentPageNumber() + 1;
	}

	public int getPreviousPageNumber() {
		return getCurrentPageNumber() - 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPageNumber() {
		return pageNumber;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getTotalElements() {
		return totalElements;
	}

	/**
	 * 实现Iterable遍历接口
	 * 
	 * @return 等价getCurrentPageElements().iterator()
	 * @See java.lang.Iterable
	 */
	@SuppressWarnings("rawtypes")
	public Iterator iterator() {
		return getCurrentPageElements().iterator();
	}

	public List<T> getCurrentElements() {
		List<T> currentElements = new ArrayList<T>();
		for (int i = 0; i < elements.size(); i++) {
			if (pageNumber == 1) {
				if (i < pageSize * pageNumber) {
					currentElements.add(elements.get(i));
				}
			} else {
				if (i >= pageSize * (pageNumber - 1) && i < pageSize * pageNumber) {
					currentElements.add(elements.get(i));
				}
			}
		}
		return currentElements;
	}

	public void setElements(List<T> _elements) {
		this.elements = _elements;
	}

	public List<T> getElements() {
		return elements;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public List<T> getCurrentElementsBySelf() {
		List<T> currentElements = new ArrayList<T>();
		for (int i = 0; i < elements.size(); i++) {
			currentElements.add(elements.get(i));
		}
		return currentElements;
	}
}
