package org.xume.solrjcriteria;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.xume.solrjcriteria.order.Order.asc;
import static org.xume.solrjcriteria.order.Order.desc;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.xume.solrjcriteria.criterion.Criterion;

@RunWith(MockitoJUnitRunner.class)
public class SolrjCriteriaTest {

	@Mock
	private SolrServer solrServer;

	@Test
	public void testAddCriterion() {
		SolrQuery query = criteria().add(new StubCriterion("lorem ipsum")).buildQuery();
		assertThat(query.getQuery(), equalTo("lorem ipsum"));
	}

	@Test
	public void testAddMultipleCriterion() {
		SolrQuery query = criteria()
								.add(new StubCriterion("lorem"))
								.add(new StubCriterion("ipsum"))
							.buildQuery();

		assertThat(query.getQuery(), equalTo("(lorem AND ipsum)"));
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

	private SolrjCriteria criteria() {
		return new SolrjCriteria(solrServer);
	}

	private static class StubCriterion implements Criterion {

		private String value;

		public StubCriterion(String value) {
			this.value = value;
		}

		@Override
		public String toQueryFragment() {
			return value;
		}

	}

}
