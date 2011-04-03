/**
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.xume.solrjcriteria;

import static org.xume.solrjcriteria.criterion.Restrictions.and;
import static org.xume.solrjcriteria.criterion.Restrictions.eq;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.xume.solrjcriteria.criterion.Criterion;
import org.xume.solrjcriteria.order.Order;
import org.xume.solrjcriteria.term.Term;
import org.xume.solrjcriteria.term.Terms;

/**
 * @author Johan Siebens
 */
public final class SolrjCriteria {

	private final List<Criterion> criterions;

	private final SolrQuery solrQuery;

	private final SolrServer solrServer;

	public SolrjCriteria(SolrServer solrServer) {
		this.solrServer = solrServer;
		this.solrQuery = new SolrQuery();
		this.criterions = new ArrayList<Criterion>();
	}

	public SolrjCriteria add(Criterion criterion) {
		this.criterions.add(criterion);
		return this;
	}

	public SolrjCriteria add(String field, String value) {
		return add(field, Terms.defaultImpl(value));
	}

	public SolrjCriteria add(String field, Term value) {
		return add(eq(field, value));
	}

	public SolrjCriteria addField(String field) {
		solrQuery.addField(field);
		return this;
	}

	public SolrjCriteria addFields(String... fields) {
		for (String field : fields) {
			addField(field);
		}
		return this;
	}

	public QueryResponse execute() throws SolrServerException {
		return solrServer.query(buildQuery());
	}

	public SolrjCriteria order(Order order) {
		solrQuery.addSortField(order.getField(), order.isAscending() ? ORDER.asc : ORDER.desc);
		return this;
	}

	public SolrjCriteria setFirstResult(int firstResult) {
		this.solrQuery.setStart(firstResult);
		return this;
	}

	public SolrjCriteria setMaxResults(int maxResults) {
		this.solrQuery.setRows(maxResults);
		return this;
	}

	private SolrQuery buildQuery() {
		String query = query();
		if (query != null && query.trim().length() != 0) {
			solrQuery.setQuery(query);
		}
		return solrQuery;
	}

	private String query() {
		return and(criterions).toQueryFragment();
	}

}
