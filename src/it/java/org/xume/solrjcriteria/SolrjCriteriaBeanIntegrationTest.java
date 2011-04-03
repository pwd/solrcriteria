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
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.xume.solrjcriteria.criterion.Restrictions.eq;
import static org.xume.solrjcriteria.term.Terms.term;

import java.util.List;

import org.junit.Test;

/**
 * @author Johan Siebens
 */
public class SolrjCriteriaBeanIntegrationTest extends AbstractIntegrationTest {

	@Test
	public void testListBeans() throws Exception {
		addBean(new ExampleBean("id1", "lorem"));
		addBean(new ExampleBean("id2", "ipsum"));

		SolrjCriteria criteria = createCriteria().add(eq("name", term("lorem")));
		List<ExampleBean> list = criteria.list(ExampleBean.class);
		assertThat(list.size(), equalTo(1));
		assertThat(list.contains(new ExampleBean("id1", "lorem")), is(true));
	}

}
