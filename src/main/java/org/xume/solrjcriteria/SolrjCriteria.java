/**
 * Copyright 2011 the original author or authors.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.xume.solrjcriteria.criterion.Criterion;
import org.xume.solrjcriteria.criterion.Restrictions;
import org.xume.solrjcriteria.order.Order;
import org.xume.solrjcriteria.term.Term;

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

	public SolrjCriteria add(Term term) {
		return add(Restrictions.eq(term));
	}

	public SolrjCriteria add(String field, Term term) {
		return add(Restrictions.eq(field, term));
	}

	public SolrjCriteria field(String field) {
		solrQuery.addField(field);
		return this;
	}

	public SolrjCriteria addField(String field) {
		return field(field);
	}

	public SolrjCriteria withField(String field) {
		return field(field);
	}

	public SolrjCriteria fields(String field, String... otherFields) {
		for (String currentField : asList(field, otherFields)) {
			field(currentField);
		}
		return this;
	}

	public SolrjCriteria addFields(String field, String... otherFields) {
		return fields(field, otherFields);
	}

	public SolrjCriteria withFields(String field, String otherFields) {
		return fields(field, otherFields);
	}

	public QueryResponse execute() throws SolrServerException {
		return solrServer.query(buildQuery());
	}

	public SolrDocumentList list() throws SolrServerException {
		return execute().getResults();
	}

	public <T> List<T> list(Class<T> type) throws SolrServerException {
		return execute().getBeans(type);
	}

	public SolrjCriteria order(Order order) {
		solrQuery.addSortField(order.getField(), order.isAscending() ? ORDER.asc : ORDER.desc);
		return this;
	}

	public SolrjCriteria firstResult(int firstResult) {
		this.solrQuery.setStart(firstResult);
		return this;
	}

	public SolrjCriteria setFirstResult(int firstResult) {
		return firstResult(firstResult);
	}

	public SolrjCriteria withFirstResult(int firstResult) {
		return firstResult(firstResult);
	}

	public SolrjCriteria maxResults(int maxResults) {
		this.solrQuery.setRows(maxResults);
		return this;
	}

	public SolrjCriteria setMaxResults(int maxResults) {
		return maxResults(maxResults);
	}

	public SolrjCriteria withMaxResults(int maxResults) {
		return maxResults(maxResults);
	}

	SolrQuery buildQuery() {
		String query = query();
		if (query != null && query.trim().length() != 0) {
			solrQuery.setQuery(query);
		}
		else {
			solrQuery.setQuery("*:*");
		}
		return solrQuery;
	}

	private String query() {
		return and(criterions).toQueryFragment();
	}

	private <T> List<T> asList(T t, T... additional) {
		List<T> result = new ArrayList<T>();
		result.add(t);
		if (additional != null) {
			result.addAll(Arrays.asList(additional));
		}
		return result;
	}

}
