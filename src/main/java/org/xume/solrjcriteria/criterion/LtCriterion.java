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

import static org.xume.solrjcriteria.criterion.Patterns.lt;

import org.xume.solrjcriteria.term.Term;

/**
 * @author Johan Siebens
 */
public class LtCriterion extends AbstractCriterion {

	public LtCriterion(String field, Term value) {
		super(field, value);
	}

	public LtCriterion(Term value) {
		super(value);
	}

	@Override
	protected String getFieldFragment(String value) {
		return lt(value);
	}

	@Override
	protected String getFieldFragment(String field, String value) {
		return field + ":" + lt(value);
	}

}
