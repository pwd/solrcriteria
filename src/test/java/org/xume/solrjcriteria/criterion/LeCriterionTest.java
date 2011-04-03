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

package org.xume.solrjcriteria.criterion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.xume.solrjcriteria.criterion.Restrictions.le;
import static org.xume.solrjcriteria.value.Values.phrase;
import static org.xume.solrjcriteria.value.Values.terms;

import org.junit.Test;

/**
 * @author Johan Siebens
 */
public class LeCriterionTest {

	@Test
	public void testWithDefaultField() {
		String fragment = le(terms("ipsum sit amet")).toQueryFragment();
		assertThat(fragment, equalTo("([ * TO ipsum ] [ * TO sit ] [ * TO amet ])"));
	}

	@Test
	public void testWithValue() {
		String fragment = le("lorem", terms("ipsum sit amet")).toQueryFragment();
		assertThat(fragment, equalTo("(lorem:[ * TO ipsum ] lorem:[ * TO sit ] lorem:[ * TO amet ])"));
	}

	@Test
	public void testWithPhrase() {
		String fragment = le("lorem", phrase("ipsum sit amet")).toQueryFragment();
		assertThat(fragment, equalTo("lorem:[ * TO \"ipsum sit amet\" ]"));
	}

}
