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

import static org.xume.solrjcriteria.criterion.Patterns.eq;

import org.xume.solrjcriteria.term.Term;

/**
 * @author Johan Siebens
 */
public abstract class AbstractCriterion implements Criterion {

	private String field;

	private Term value;

	AbstractCriterion(String field, Term value) {
		this.field = field;
		this.value = value;
	}

	AbstractCriterion(Term value) {
		this(null, value);
	}

	public String toQueryFragment() {
		String[] values = value.values();
		boolean addField = field != null && field.trim().length() > 0;

		StringBuilder builder = new StringBuilder();

		if (values.length > 1) {
			builder.append("(");
		}
		for (int i = 0; i < values.length; i++) {
			if (i != 0) {
				builder.append(" ");
			}
			builder.append(addField ? getFieldFragment(field, values[i]) : getFieldFragment(values[i]));
		}
		if (values.length > 1) {
			builder.append(")");
		}

		return builder.toString();
	}

	protected String getFieldFragment(String value) {
		return eq(value);
	}

	protected String getFieldFragment(String field, String value) {
		return field + ":" + value;
	}

}
