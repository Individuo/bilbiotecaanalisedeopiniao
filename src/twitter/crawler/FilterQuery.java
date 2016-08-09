package twitter.crawler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilterQuery {
	
	private List<String> filters;
	private twitter4j.FilterQuery filterQuery;

	public FilterQuery()  {
		this.filters = new ArrayList<String>();
		filterQuery = new twitter4j.FilterQuery();
	}

	public void addFilter(String filter) {
		this.filters.add(filter);
	}

	public void removeFilter(String filter) {
		this.filters.remove(filter);
	}
	
	public List<String> getListFilters() {
		return Collections.unmodifiableList(this.filters);
	}		

	public void addFilterQuery() {
		filterQuery.track(filters.toArray(new String[filters.size()]));
	}

	public twitter4j.FilterQuery getFilterQuery() {
		return this.filterQuery;
	}

}
