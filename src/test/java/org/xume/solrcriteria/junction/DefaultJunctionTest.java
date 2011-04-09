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

package org.xume.solrcriteria.junction;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.xume.solrcriteria.terms.Terms.word;

import java.util.List;

import org.junit.Test;
import org.xume.solrcriteria.terms.Term;

/**
 * @author Johan Siebens
 */
public class DefaultJunctionTest {

	@Test
	public void test() {
		List<Term> terms = asList(new Term[]{word("lorem"), word("ipsum"), word("doloret")});
		assertThat(new DefaultJunction(terms).value(), equalTo("(lorem  ipsum  doloret)"));
	}
	
}
