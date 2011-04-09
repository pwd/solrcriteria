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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.xume.solrcriteria.order.Order.asc;
import static org.xume.solrcriteria.order.Order.desc;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xume.solrcriteria.terms.Term;

/**
 * @author Johan Siebens
 */
@RunWith(MockitoJUnitRunner.class)
public class SolrjCriteriaTest {

	@Test
	public void test() {
		assertTrue(true);
	}

	@Mock
	private SolrServer solrServer;

	@Test
	public void testQuery() {
		SolrQuery query = criteria().query(new StubTerm("lorem ipsum")).buildQuery();
		assertThat(query.getQuery(), equalTo("lorem ipsum"));
	}

	@Test
	public void testQueryMultipleTerms() {
		SolrQuery query = criteria()
								.query(new StubTerm("lorem"))
								.query(new StubTerm("ipsum"))
							.buildQuery();

		assertThat(query.getQuery(), equalTo("(lorem  ipsum)"));
	}

	@Test
	public void testDefaultQuery() {
		assertThat(criteria().buildQuery().getQuery(), equalTo("*:*"));
	}

	@Test
	public void testFields() {
		assertThat(criteria().field("lorem").buildQuery().getFields(), equalTo("lorem"));
		assertThat(criteria().addField("lorem").buildQuery().getFields(), equalTo("lorem"));
		assertThat(criteria().withField("lorem").buildQuery().getFields(), equalTo("lorem"));

		assertThat(criteria().fields("lorem", "ipsum").buildQuery().getFields(), equalTo("lorem,ipsum"));
		assertThat(criteria().addFields("lorem", "ipsum").buildQuery().getFields(), equalTo("lorem,ipsum"));
		assertThat(criteria().withFields("lorem", "ipsum").buildQuery().getFields(), equalTo("lorem,ipsum"));
	}

	@Test
	public void testFirstResult() {
		assertThat(criteria().firstResult(5).buildQuery().getStart(), equalTo(5));
		assertThat(criteria().setFirstResult(5).buildQuery().getStart(), equalTo(5));
		assertThat(criteria().withFirstResult(5).buildQuery().getStart(), equalTo(5));
	}

	@Test
	public void testMaxResults() {
		assertThat(criteria().maxResults(5).buildQuery().getRows(), equalTo(5));
		assertThat(criteria().setMaxResults(5).buildQuery().getRows(), equalTo(5));
		assertThat(criteria().withMaxResults(5).buildQuery().getRows(), equalTo(5));
	}

	@Test
	public void testOrder() {
		assertThat(criteria().order(asc("lorem")).buildQuery().getSortField(), equalTo("lorem asc"));
		assertThat(criteria().order(desc("lorem")).buildQuery().getSortField(), equalTo("lorem desc"));

		assertThat(
				criteria()
						.order(desc("lorem"))
						.order(asc("ipsum"))
						.buildQuery().getSortField(), equalTo("lorem desc,ipsum asc"));
	}

	private SolrCriteria criteria() {
		return new SolrCriteria(solrServer);
	}

	private static class StubTerm implements Term {

		private String value;

		public StubTerm(String value) {
			this.value = value;
		}

		@Override
		public String value() {
			return value;
		}

	}

}
