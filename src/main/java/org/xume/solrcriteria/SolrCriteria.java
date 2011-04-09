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

package org.xume.solrcriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.xume.solrcriteria.junction.DefaultJunction;
import org.xume.solrcriteria.order.Order;
import org.xume.solrcriteria.terms.Term;

/**
 * @author Johan Siebens
 */
public class SolrCriteria extends SolrCriteriaBuilder {

	private final SolrQuery solrQuery;

	private final SolrServer solrServer;

	private final List<Term> terms;

	public SolrCriteria(SolrServer solrServer) {
		this.solrServer = solrServer;
		this.solrQuery = new SolrQuery();
		this.terms = new ArrayList<Term>();
	}

	public SolrCriteria addField(String field) {
		return field(field);
	}

	public SolrCriteria addFields(String field, String... otherFields) {
		return fields(field, otherFields);
	}

	public QueryResponse execute() throws SolrServerException {
		return solrServer.query(buildQuery());
	}

	public SolrCriteria field(String field) {
		solrQuery.addField(field);
		return this;
	}

	public SolrCriteria fields(String field, String... otherFields) {
		for (String currentField : asList(field, otherFields)) {
			field(currentField);
		}
		return this;
	}

	public SolrCriteria firstResult(int firstResult) {
		this.solrQuery.setStart(firstResult);
		return this;
	}

	public SolrDocumentList list() throws SolrServerException {
		return execute().getResults();
	}

	public <T> List<T> list(Class<T> type) throws SolrServerException {
		return execute().getBeans(type);
	}

	public SolrCriteria maxResults(int maxResults) {
		this.solrQuery.setRows(maxResults);
		return this;
	}

	public SolrCriteria order(Order order) {
		solrQuery.addSortField(order.getField(), order.isAscending() ? ORDER.asc : ORDER.desc);
		return this;
	}

	public SolrCriteria query(Term term) {
		this.terms.add(term);
		return this;
	}

	public SolrCriteria setFirstResult(int firstResult) {
		return firstResult(firstResult);
	}

	public SolrCriteria setMaxResults(int maxResults) {
		return maxResults(maxResults);
	}

	public SolrCriteria withField(String field) {
		return field(field);
	}

	public SolrCriteria withFields(String field, String... otherFields) {
		return fields(field, otherFields);
	}

	public SolrCriteria withFirstResult(int firstResult) {
		return firstResult(firstResult);
	}

	public SolrCriteria withMaxResults(int maxResults) {
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

		System.out.println(solrQuery.getQuery());

		return solrQuery;
	}

	private <T> List<T> asList(T t, T... additional) {
		List<T> result = new ArrayList<T>();
		result.add(t);
		if (additional != null) {
			result.addAll(Arrays.asList(additional));
		}
		return result;
	}

	private String query() {
		return new DefaultJunction(terms).value();
	}

}
