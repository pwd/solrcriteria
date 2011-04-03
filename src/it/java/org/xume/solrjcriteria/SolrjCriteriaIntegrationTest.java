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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.xume.solrjcriteria.criterion.Restrictions.eq;
import static org.xume.solrjcriteria.criterion.Restrictions.ne;
import static org.xume.solrjcriteria.value.Values.phrase;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author Johan Siebens
 */
public class SolrjCriteriaIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testEqAndNe() throws Exception {
		SolrInputDocument doc1 = new SolrInputDocument();
		doc1.addField("id", "id1", 1.0f);
		doc1.addField("name", "lorem", 1.0f);

		SolrInputDocument doc2 = new SolrInputDocument();
		doc2.addField("id", "id2", 1.0f);
		doc2.addField("name", "ipsum", 1.0f);

		addDocument(doc1);
		addDocument(doc2);

		QueryResponse responseEq = createCriteria().add(eq("name", phrase("lorem"))).execute();
		SolrDocumentList resultsEq = responseEq.getResults();
		assertThat(resultsEq.size(), equalTo(1));
		assertThat((String) resultsEq.get(0).getFieldValue("id"), equalTo("id1"));

		QueryResponse responseNe = createCriteria().add(ne("name", phrase("lorem"))).execute();
		SolrDocumentList resultsNe = responseNe.getResults();
		assertThat(resultsNe.size(), equalTo(1));
		assertThat((String) resultsNe.get(0).getFieldValue("id"), equalTo("id2"));
	}

}
