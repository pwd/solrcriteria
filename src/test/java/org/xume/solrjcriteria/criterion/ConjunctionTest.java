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

package org.xume.solrjcriteria.criterion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.xume.solrjcriteria.criterion.Restrictions.and;
import static org.xume.solrjcriteria.criterion.Restrictions.eq;
import static org.xume.solrjcriteria.term.Terms.term;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.xume.solrjcriteria.criterion.Criterion;

/**
 * @author Johan Siebens
 */
public class ConjunctionTest {

	@Test
	public void testWithMultipleCriterions() {
		String fragment = and(eq(term("lorem")), eq(term("ipsum"))).toQueryFragment();
		assertThat(fragment, equalTo("(lorem AND ipsum)"));
	}

	@Test
	public void testWithoutCriterion() {
		String fragment = and().toQueryFragment();
		assertThat(fragment, equalTo(""));
	}

	@Test
	public void testWithSingleCriterion() {
		List<Criterion> criterions = Collections.singletonList(eq(term("lorem")));
		String fragment = and(criterions).toQueryFragment();
		assertThat(fragment, equalTo("lorem"));
	}

}
